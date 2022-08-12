package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.CommentCreateRequestDTO;
import com.sekarre.chatdemo.DTO.CommentResponseDTO;
import com.sekarre.chatdemo.services.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sekarre.chatdemo.controllers.CommentController.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = BASE_COMMENT_URL)
public class CommentController {

    public static final String BASE_COMMENT_URL = "/api/v1/comments";
    private final CommentService commentService;

    @GetMapping("/{issueId}")
    public ResponseEntity<List<CommentResponseDTO>> getAllIssueComments(@PathVariable Long issueId) {
        return ResponseEntity.ok(commentService.getAllIssueComments(issueId));
    }

    @PostMapping("/{issueId}")
    public ResponseEntity<?> createNewComment(@PathVariable Long issueId,
                                              @RequestBody @Valid CommentCreateRequestDTO commentCreateRequestDTO) {
        commentService.createNewComment(commentCreateRequestDTO, issueId);
        return ResponseEntity.ok().build();
    }
}
