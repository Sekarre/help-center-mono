package com.sekarre.chatdemo.DTO;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatMessageDTO {

    @NotNull
    private String message;

    private Long senderId;
    private LocalDateTime createdDateTime;
    private List<Long> readByIds;
}
