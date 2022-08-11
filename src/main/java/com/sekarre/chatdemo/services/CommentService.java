package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.CommentCreateRequestDTO;
import com.sekarre.chatdemo.DTO.CommentResponseDTO;
import com.sekarre.chatdemo.DTO.IssueStatusChangeDTO;
import com.sekarre.chatdemo.domain.Issue;

import java.util.List;

public interface CommentService {

    List<CommentResponseDTO> getAllIssueComments(Long issueId);

    void createNewCommentWithStatusChanged(CommentCreateRequestDTO commentCreateRequestDTO, Long issueId);

    void createNewCommentWithStatusChanged(IssueStatusChangeDTO issueStatusChangeDTO, Issue issue);
}
