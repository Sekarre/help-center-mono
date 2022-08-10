package com.sekarre.chatdemo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static com.sekarre.chatdemo.util.DateUtil.DATE_TIME_FORMAT;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommentDTO {

    private Long id;
    private String fullName;

    @NotBlank
    private String content;
    private Long replyCommentId;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdAt;
}
