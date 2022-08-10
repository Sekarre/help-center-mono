package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.DTO.IssueDTO;
import com.sekarre.chatdemo.DTO.IssueTypeDTO;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.IssueType;
import com.sekarre.chatdemo.exceptions.issue.IssueNotFoundException;
import com.sekarre.chatdemo.mappers.IssueMapper;
import com.sekarre.chatdemo.repositories.IssueRepository;
import com.sekarre.chatdemo.repositories.IssueTypeRepository;
import com.sekarre.chatdemo.services.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sekarre.chatdemo.security.UserDetailsHelper.getCurrentUser;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final IssueMapper issueMapper;

    @Override
    public List<IssueTypeDTO> getAllIssueTypes() {
        return issueTypeRepository.findAll().stream()
                .map(issueMapper::mapIssueTypeToIssueTypeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public IssueDTO getUserIssue() {
        return issueRepository.findByUserId(getCurrentUser().getId())
                .map(issueMapper::mapIssueToIssueDTO)
                .orElse(null);
    }

    @Override
    public List<IssueDTO> getAllUserIssues() {
        return issueRepository.findAllByUserId(getCurrentUser().getId()).stream()
                .map(issueMapper::mapIssueToIssueDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewIssue(IssueDTO issueDTO) {
        Issue issue = issueMapper.mapIssueDTOToIssue(issueDTO);
        issue.setUser(getCurrentUser());
        issue.setIssueType(getIssueTypeById(issueDTO.getIssueTypeId()));
        issueRepository.save(issue);
    }

    private IssueType getIssueTypeById(Long issueTypeId) {
        return issueTypeRepository.findById(issueTypeId)
                .orElseThrow(() -> new IssueNotFoundException("IssueType with id: " + issueTypeId + " not found"));
    }
}
