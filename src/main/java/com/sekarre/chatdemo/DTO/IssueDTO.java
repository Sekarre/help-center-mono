package com.sekarre.chatdemo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.chatdemo.domain.enums.IssueStatus;
import com.sekarre.chatdemo.util.DateUtil;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueDTO {

    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String issue;
    private IssueStatus issueStatus;
    private Long issueTypeId;
    private String channelId;

    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
