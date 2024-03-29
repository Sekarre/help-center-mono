package com.sekarre.chatdemo.DTO.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {

    private Long id;
    private String fullName;
    private String roleName;
    private String specialization;
}
