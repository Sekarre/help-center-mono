package com.sekarre.chatdemo.services.comment;

import com.sekarre.chatdemo.DTO.comment.CommentCreateRequestDTO;
import com.sekarre.chatdemo.DTO.comment.CommentResponseDTO;
import com.sekarre.chatdemo.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.chatdemo.domain.Issue;

import java.util.List;

public interface CommentService {

    List<CommentResponseDTO> getAllIssueComments(Long issueId);

    void createNewComment(CommentCreateRequestDTO commentCreateRequestDTO, Long issueId);

    void createNewCommentWithStatusChanged(IssueStatusChangeDTO issueStatusChangeDTO, Issue issue);
}
