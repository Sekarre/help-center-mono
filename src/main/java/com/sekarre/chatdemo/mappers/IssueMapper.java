package com.sekarre.chatdemo.mappers;

import com.sekarre.chatdemo.DTO.IssueDTO;
import com.sekarre.chatdemo.DTO.IssueTypeDTO;
import com.sekarre.chatdemo.domain.Issue;
import com.sekarre.chatdemo.domain.IssueType;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(builder = @Builder(disableBuilder = true))
public abstract class IssueMapper {

    public abstract IssueTypeDTO mapIssueTypeToIssueTypeDTO(IssueType issueType);
    public abstract IssueType mapIssueTypeDTOToIssueType(IssueTypeDTO issueTypeDTO);
    @Mapping(target = "issueTypeId", source = "issue.issueType.id")
    @Mapping(target = "channelId", source = "issue.chat.channelId")
    public abstract IssueDTO mapIssueToIssueDTO(Issue issue);
    public abstract Issue mapIssueDTOToIssue(IssueDTO issueDTO);
}
