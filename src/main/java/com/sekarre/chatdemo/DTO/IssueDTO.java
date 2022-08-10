package com.sekarre.chatdemo.DTO;

import com.sekarre.chatdemo.domain.enums.IssueStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String issue;
    private IssueStatus issueStatus;
    private Long issueTypeId;
    private Long channelId;
}
