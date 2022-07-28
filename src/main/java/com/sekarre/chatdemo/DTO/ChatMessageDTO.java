package com.sekarre.chatdemo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.chatdemo.util.DateUtil;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChatMessageDTO {

    @NotNull
    private String message;

    private byte[] file;

    private Long senderId;

    private String senderName;

    @JsonFormat(pattern= DateUtil.DATE_TIME_FORMAT)
    private LocalDateTime createdDateTime;
}
