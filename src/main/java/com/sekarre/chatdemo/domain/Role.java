package com.sekarre.chatdemo.domain;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
