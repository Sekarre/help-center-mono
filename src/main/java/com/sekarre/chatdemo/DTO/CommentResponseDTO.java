package com.sekarre.chatdemo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.chatdemo.domain.enums.IssueStatus;
import lombok.*;

import java.time.LocalDateTime;

import static com.sekarre.chatdemo.util.DateUtil.DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommentResponseDTO {

    private Long id;
    private String fullName;
    private String content;
    private Long replyCommentId;
    private IssueStatus issueStatus;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
}
