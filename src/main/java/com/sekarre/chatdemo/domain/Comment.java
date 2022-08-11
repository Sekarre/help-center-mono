package com.sekarre.chatdemo.domain;

import com.sekarre.chatdemo.domain.enums.IssueStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String content;
    private IssueStatus issueStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @JoinColumn(name = "issue_id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Issue issue;

    @JoinColumn(name = "reply_comment_id")
    @ManyToOne
    private Comment replyComment;
}
