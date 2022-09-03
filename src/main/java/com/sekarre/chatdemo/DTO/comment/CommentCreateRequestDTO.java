package com.sekarre.chatdemo.DTO.comment;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommentCreateRequestDTO {

    @NotBlank
    private String content;
    private Long replyCommentId;
}
