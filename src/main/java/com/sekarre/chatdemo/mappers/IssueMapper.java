package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.issue.IssueDTO;
import com.sekarre.chatdemo.DTO.issue.IssueStatusChangeDTO;
import com.sekarre.chatdemo.DTO.issue.IssueTypeDTO;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.IssueType;
import com.sekarre.chatdemo.domain.enums.IssueStatus;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class IssueMapper {

    public abstract IssueTypeDTO mapIssueTypeToIssueTypeDTO(IssueType issueType);

    public abstract IssueStatusChangeDTO mapIssueStatusToIssueStatusDTO(IssueStatus issueStatus);

    public abstract IssueType mapIssueTypeDTOToIssueType(IssueTypeDTO issueTypeDTO);

    @Mapping(target = "issueTypeId", source = "issue.issueType.id")
    @Mapping(target = "channelId", source = "issue.chat.channelId")
    @Mapping(target = "firstName", source = "issue.author.firstName")
    @Mapping(target = "lastName", source = "issue.author.lastName")
    @Mapping(target = "email", source = "issue.author.email")
    public abstract IssueDTO mapIssueToIssueDTO(Issue issue);

    public abstract Issue mapIssueDTOToIssue(IssueDTO issueDTO);
}
