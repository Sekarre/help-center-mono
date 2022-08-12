package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.DTO.CommentCreateRequestDTO;
import com.sekarre.chatdemo.DTO.CommentResponseDTO;
import com.sekarre.chatdemo.DTO.IssueStatusChangeDTO;
import com.sekarre.chatdemo.domain.Comment;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.enums.EventType;
import com.sekarre.chatdemo.domain.enums.IssueStatus;
import com.sekarre.chatdemo.exceptions.comment.CommentNotFoundException;
import com.sekarre.chatdemo.exceptions.issue.IssueNotFoundException;
import com.sekarre.chatdemo.mappers.CommentMapper;
import com.sekarre.chatdemo.repositories.CommentRepository;
import com.sekarre.chatdemo.repositories.IssueRepository;
import com.sekarre.chatdemo.services.CommentService;
import com.sekarre.chatdemo.services.EventEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sekarre.chatdemo.factories.StatusChangedCommentFactory.getStatusChangedComment;
import static com.sekarre.chatdemo.security.UserDetailsHelper.getCurrentUser;
import static com.sekarre.chatdemo.security.UserDetailsHelper.getCurrentUserFullName;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final IssueRepository issueRepository;
    private final EventEmitterService eventEmitterService;

    @Override
    public List<CommentResponseDTO> getAllIssueComments(Long issueId) {
        return commentRepository.findAllByIssueId(issueId).stream()
                .map(commentMapper::mapCommentToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewComment(CommentCreateRequestDTO commentCreateRequestDTO, Long issueId) {
        Issue issue = getIssueById(issueId);
        Comment comment = getComment(commentCreateRequestDTO, issue);
        if (Objects.nonNull(commentCreateRequestDTO.getReplyCommentId())) {
            comment.setReplyComment(getCommentById(commentCreateRequestDTO.getReplyCommentId()));
        }
        commentRepository.save(comment);
        issue.setUpdatedAt(LocalDateTime.now());
        issueRepository.save(issue);
        senNewCommentEventMessage(issueId, issue);
    }

    //fixme: check this one
    private void senNewCommentEventMessage(Long issueId, Issue issue) {
        List<Long> usersId = new ArrayList<>();
        issue.getParticipants().stream()
                .filter(user -> !user.getId().equals(getCurrentUser().getId()))
                .forEach(user -> usersId.add(user.getId()));
        if (!issue.getAuthor().getId().equals(getCurrentUser().getId())) {
            usersId.add(issue.getAuthor().getId());
        }
        eventEmitterService.sendNewEventMessage(
                EventType.NEW_ISSUE_COMMENT, String.valueOf(issueId), usersId.toArray(Long[]::new));
    }

    @Override
    public void createNewCommentWithStatusChanged(IssueStatusChangeDTO issueStatusChangeDTO, Issue issue) {
        Comment comment = null;
        if (Objects.nonNull(issueStatusChangeDTO.getComment()) && Objects.nonNull(issueStatusChangeDTO.getComment().getContent())) {
            comment = getComment(issueStatusChangeDTO.getComment(), issue);
            comment = commentRepository.save(comment);
            senNewCommentEventMessage(issue.getId(), issue);
        }
        if (StringUtils.isNotBlank(issueStatusChangeDTO.getStatus())) {
            Comment statusChangedComment =  getComment(issueStatusChangeDTO.getComment(), issue);
            IssueStatus issueStatus = IssueStatus.valueOf(issueStatusChangeDTO.getStatus());
            statusChangedComment.setContent(getStatusChangedComment(issueStatus));
            statusChangedComment.setIssueStatus(issueStatus);
            if (Objects.nonNull(comment)) {
                statusChangedComment.setReplyComment(comment);
            }
            commentRepository.save(statusChangedComment);
            senNewCommentEventMessage(issue.getId(), issue);
        }
    }

    private Comment getComment(CommentCreateRequestDTO issueStatusChangeDTO, Issue issue) {
        Comment comment = commentMapper.mapCommentCreateRequestDTOToComment(issueStatusChangeDTO);
        comment.setIssue(issue);
        comment.setFullName(getCurrentUserFullName());
        return comment;
    }

    private Issue getIssueById(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException("Issue with id: " + issueId + " not found"));
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment with id: " + commentId + " not found"));
    }
}
