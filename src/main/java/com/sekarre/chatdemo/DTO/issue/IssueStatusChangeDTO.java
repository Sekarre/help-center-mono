package com.sekarre.chatdemo.DTO.issue;

import com.sekarre.chatdemo.DTO.comment.CommentCreateRequestDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueStatusChangeDTO {

    @NotBlank
    private String status;
    private CommentCreateRequestDTO comment;
}
