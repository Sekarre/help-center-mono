package com.sekarre.chatdemo.repositories;

import com.sekarre.chatdemo.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findByUserId(Long userId);

    List<Issue> findAllByUserId(Long userId);
}
