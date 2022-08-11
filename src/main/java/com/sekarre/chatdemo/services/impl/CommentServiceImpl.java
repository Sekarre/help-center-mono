package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.DTO.CommentCreateRequestDTO;
import com.sekarre.chatdemo.DTO.CommentResponseDTO;
import com.sekarre.chatdemo.DTO.IssueStatusChangeDTO;
import com.sekarre.chatdemo.domain.Comment;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.enums.IssueStatus;
import com.sekarre.chatdemo.exceptions.comment.CommentNotFoundException;
import com.sekarre.chatdemo.exceptions.issue.IssueNotFoundException;
import com.sekarre.chatdemo.factories.StatusChangedCommentFactory;
import com.sekarre.chatdemo.mappers.CommentMapper;
import com.sekarre.chatdemo.repositories.CommentRepository;
import com.sekarre.chatdemo.repositories.IssueRepository;
import com.sekarre.chatdemo.services.CommentService;
import com.sekarre.chatdemo.services.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sekarre.chatdemo.factories.StatusChangedCommentFactory.getStatusChangedComment;
import static com.sekarre.chatdemo.security.UserDetailsHelper.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final IssueRepository issueRepository;

    @Override
    public List<CommentResponseDTO> getAllIssueComments(Long issueId) {
        return commentRepository.findAllByIssueId(issueId).stream()
                .map(commentMapper::mapCommentToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewCommentWithStatusChanged(CommentCreateRequestDTO commentCreateRequestDTO, Long issueId) {
        Comment comment = getComment(commentCreateRequestDTO, getIssueById(issueId));
        if (Objects.nonNull(commentCreateRequestDTO.getReplyCommentId())) {
            comment.setReplyComment(getCommentById(commentCreateRequestDTO.getReplyCommentId()));
        }
        commentRepository.save(comment);
    }

    @Override
    public void createNewCommentWithStatusChanged(IssueStatusChangeDTO issueStatusChangeDTO, Issue issue) {
        Comment comment = null;
        if (Objects.nonNull(issueStatusChangeDTO.getComment()) && Objects.nonNull(issueStatusChangeDTO.getComment().getContent())) {
            comment = getComment(issueStatusChangeDTO.getComment(), issue);
            comment = commentRepository.save(comment);
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
