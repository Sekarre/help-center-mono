package com.sekarre.chatdemo.domain;

import com.sekarre.chatdemo.domain.enums.IssueStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private String title;

    @Lob
    private String issue;

    @Builder.Default
    private IssueStatus issueStatus = IssueStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.MERGE)
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "chat_id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Chat chat;

    @JoinColumn(name = "issue_type_id")
    @ManyToOne
    private IssueType issueType;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private User author;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "issue_has_user",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<>();

    public void addParticipant(User user) {
        if (user == null) {
            return;
        }
        this.participants.add(user);
    }
}
