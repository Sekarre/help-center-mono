package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.IssueDTO;
import com.sekarre.chatdemo.DTO.IssueTypeDTO;
import com.sekarre.chatdemo.domain.enums.IssueStatus;
import com.sekarre.chatdemo.services.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/issues")
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/types")
    public ResponseEntity<List<IssueTypeDTO>> getIssueTypes() {
        return ResponseEntity.ok(issueService.getAllIssueTypes());
    }

    @PostMapping
    public ResponseEntity<?> createNewIssue(@RequestBody @Valid IssueDTO issueDTO) {
        issueService.createNewIssue(issueDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<IssueDTO>> getAllIssues(@RequestParam(required = false) IssueStatus status) {
        return ResponseEntity.ok(issueService.getAllIssuesWithStatus(status));
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDTO> getIssue(@PathVariable Long issueId) {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }
}
