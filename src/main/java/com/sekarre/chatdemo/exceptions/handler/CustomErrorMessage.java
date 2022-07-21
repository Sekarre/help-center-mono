package com.sekarre.chatdemo.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sekarre.chatdemo.util.DateUtil;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomErrorMessage {
    private String cause;

    @JsonFormat(pattern = DateUtil.DATE_TIME_FORMAT)
    private LocalDateTime timestamp;
}
