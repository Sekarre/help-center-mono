package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.DTO.CommentDTO;
import com.sekarre.chatdemo.domain.Comment;
import com.sekarre.chatdemo.exceptions.comment.CommentNotFoundException;
import com.sekarre.chatdemo.mappers.CommentMapper;
import com.sekarre.chatdemo.repositories.CommentRepository;
import com.sekarre.chatdemo.security.UserDetailsHelper;
import com.sekarre.chatdemo.services.CommentService;
import com.sekarre.chatdemo.services.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sekarre.chatdemo.security.UserDetailsHelper.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final IssueService issueService;

    @Override
    public List<CommentDTO> getAllIssueComments(Long issueId) {
        return commentRepository.findAllByIssueId(issueId).stream()
                .map(commentMapper::mapCommentToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewComment(CommentDTO commentDTO, Long issueId) {
        Comment comment = commentMapper.mapCommentDTOToComment(commentDTO);
        comment.setIssue(issueService.getIssueEntityById(issueId));
        comment.setFullName(getCurrentUserFullName());
        if (Objects.nonNull(commentDTO.getReplyCommentId())) {
            comment.setReplyComment(getCommentById(commentDTO.getReplyCommentId()));
        }
        commentRepository.save(comment);
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("Comment with id: " + commentId + " not found"));
    }
}
