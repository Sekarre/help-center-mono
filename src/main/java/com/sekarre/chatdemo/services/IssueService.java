package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.IssueDTO;
import com.sekarre.chatdemo.DTO.IssueTypeDTO;
import com.sekarre.chatdemo.domain.enums.IssueStatus;

import java.util.List;

public interface IssueService {

    List<IssueTypeDTO> getAllIssueTypes();

    IssueDTO getUserIssue();

    List<IssueDTO> getAllUserIssues();

    void createNewIssue(IssueDTO issueDTO);

    List<IssueDTO> getAllIssuesWithStatus(IssueStatus status);

    IssueDTO getIssueById(Long issueId);
}
