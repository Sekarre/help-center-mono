package com.sekarre.chatdemo.domain;

import com.sekarre.chatdemo.domain.enums.IssueTypeName;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private IssueTypeName name;

    @Builder.Default
    private boolean isAvailable = true;
}
