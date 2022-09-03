package com.sekarre.chatdemo.DTO.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.chatdemo.util.DateUtil;
import com.sekarre.chatdemo.validators.AtLeastOneFieldNotEmpty;
import com.sekarre.chatdemo.validators.Base64Encoded;
import lombok.*;

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

    @Base64Encoded(nullAllowed = true)
    private String file;

    private Long senderId;

    private String senderName;
    private String senderLastname;

    @JsonFormat(pattern= DateUtil.DATE_TIME_FORMAT)
    private LocalDateTime createdDateTime;
}
