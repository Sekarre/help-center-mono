package com.sekarre.chatdemo.domain;


import com.sekarre.chatdemo.domain.enums.RoleName;
import com.sekarre.chatdemo.domain.enums.Specialization;
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

    private RoleName name;

    private Specialization specialization;

    @Builder.Default
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
