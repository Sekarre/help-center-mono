package com.sekarre.chatdemo.services.issue;

import com.sekarre.chatdemo.DTO.issue.GroupedByStatusIssueDTO;
import com.sekarre.chatdemo.DTO.issue.IssueDTO;
import com.sekarre.chatdemo.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.chatdemo.DTO.issue.IssueTypeDTO;
import com.sekarre.chatdemo.DTO.user.UserDTO;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.enums.IssueStatus;

import java.util.List;

public interface IssueService {

    List<IssueTypeDTO> getAllIssueTypes();

    List<String> getIssueStatuses();

    void createNewIssue(IssueDTO issueDTO);

    void changeIssueStatus(Long issueId, IssueStatusChangeDTO issueStatusChangeDTO);

    void addUsersToIssue(Long issueId, Long[] usersId);

    List<UserDTO> getIssueParticipants(Long issueId);

    List<IssueDTO> getAllIssuesWithStatus(IssueStatus status);

    GroupedByStatusIssueDTO getAllIssuesGrouped();

    IssueDTO getIssueById(Long issueId);

    Issue getIssueEntityById(Long issueId);

    void deleteIssue(Long issueId);
}
