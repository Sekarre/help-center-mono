package com.sekarre.chatdemo.domain;

import com.sekarre.chatdemo.domain.enums.IssueStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String issue;
    private String firstName;
    private String lastName;
    private String email;
    private IssueStatus issueStatus;

    @JoinColumn(name = "chat_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Chat chat;

    @JoinColumn(name = "issue_type_id")
    @ManyToOne
    private IssueType issueType;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;
}
