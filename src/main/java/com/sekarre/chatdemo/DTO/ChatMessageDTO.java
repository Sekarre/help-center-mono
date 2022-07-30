package com.sekarre.chatdemo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.chatdemo.util.DateUtil;
import com.sekarre.chatdemo.validators.AtLeastOneFieldNotEmpty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@AtLeastOneFieldNotEmpty(fields = {"message", "file"})
public class ChatMessageDTO {

    private String message;

    private String file;

    private Long senderId;

    private String senderName;

    @JsonFormat(pattern= DateUtil.DATE_TIME_FORMAT)
    private LocalDateTime createdDateTime;
}
