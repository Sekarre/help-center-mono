package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.DTO.GroupedIssueDTO;
import com.sekarre.chatdemo.DTO.IssueDTO;
import com.sekarre.chatdemo.DTO.IssueStatusChangeDTO;
import com.sekarre.chatdemo.DTO.IssueTypeDTO;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.IssueType;
import com.sekarre.chatdemo.domain.enums.IssueStatus;
import com.sekarre.chatdemo.exceptions.issue.IssueNotFoundException;
import com.sekarre.chatdemo.mappers.IssueMapper;
import com.sekarre.chatdemo.repositories.IssueRepository;
import com.sekarre.chatdemo.repositories.IssueTypeRepository;
import com.sekarre.chatdemo.services.ChatService;
import com.sekarre.chatdemo.services.CommentService;
import com.sekarre.chatdemo.services.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sekarre.chatdemo.security.UserDetailsHelper.getCurrentUser;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final IssueMapper issueMapper;
    private final ChatService chatService;
    private final CommentService commentService;

    @Override
    public List<IssueTypeDTO> getAllIssueTypes() {
        return issueTypeRepository.findAll().stream()
                .map(issueMapper::mapIssueTypeToIssueTypeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getIssueStatuses() {
        return Arrays.stream(IssueStatus.values())
                .map(Objects::toString)
                .collect(Collectors.toList());
    }

    @Override
    public IssueDTO getUserIssue() {
        return issueRepository.findByAuthorId(getCurrentUser().getId())
                .map(issueMapper::mapIssueToIssueDTO)
                .orElse(null);
    }

    @Override
    public List<IssueDTO> getAllUserIssues() {
        return issueRepository.findAllByAuthorId(getCurrentUser().getId()).stream()
                .map(issueMapper::mapIssueToIssueDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewIssue(IssueDTO issueDTO) {
        Issue issue = issueMapper.mapIssueDTOToIssue(issueDTO);
        issue.setAuthor(getCurrentUser());
        issue.setIssueType(getIssueTypeById(issueDTO.getIssueTypeId()));
        issue.setIssueStatus(IssueStatus.PENDING);
        issue.setChat(chatService.createNewChat(issueDTO.getTitle()));
        issueRepository.save(issue);
    }

    @Override
    public void changeIssueStatus(Long issueId, IssueStatusChangeDTO issueStatusChangeDTO) {
        IssueStatus newIssueStatus = IssueStatus.valueOf(issueStatusChangeDTO.getStatus());
        Issue issue = getIssueEntityById(issueId);
        if (issue.getIssueStatus().equals(newIssueStatus)) {
            return;
        }
        commentService.createNewCommentWithStatusChanged(issueStatusChangeDTO, issue);
        issue.setIssueStatus(newIssueStatus);
        issueRepository.save(issue);
    }

    @Override
    public List<IssueDTO> getAllIssuesWithStatus(IssueStatus status) {
        if (Objects.isNull(status)) {
            return issueRepository.findAll().stream().map(issueMapper::mapIssueToIssueDTO).collect(Collectors.toList());
        }
        return issueRepository.findAllByIssueStatus(status).stream()
                .map(issueMapper::mapIssueToIssueDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GroupedIssueDTO getAllIssuesGrouped() {
        GroupedIssueDTO groupedIssueDTO = new GroupedIssueDTO();
        issueRepository.findAll().stream()
                .map(issueMapper::mapIssueToIssueDTO)
                .forEach(groupedIssueDTO::addIssueDTO);
        return groupedIssueDTO;
    }

    @Override
    public IssueDTO getIssueById(Long issueId) {
        return issueRepository.findById(issueId).map(issueMapper::mapIssueToIssueDTO)
                .orElseThrow(() -> new IssueNotFoundException("Issue with id: " + issueId + " not found"));
    }

    @Override
    public Issue getIssueEntityById(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException("Issue with id: " + issueId + " not found"));
    }

    private IssueType getIssueTypeById(Long issueTypeId) {
        return issueTypeRepository.findById(issueTypeId)
                .orElseThrow(() -> new IssueNotFoundException("IssueType with id: " + issueTypeId + " not found"));
    }
}
