package com.sekarre.chatdemo.DTO;

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
}
