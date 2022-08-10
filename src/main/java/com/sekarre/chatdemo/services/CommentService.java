package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getAllIssueComments(Long issueId);

    void createNewComment(CommentDTO commentDTO, Long issueId);
}
