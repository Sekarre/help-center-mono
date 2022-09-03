package com.sekarre.chatdemo.DTO.issue;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueTypeDTO {
    private String id;
    private String name;
}
