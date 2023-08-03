package com.assignment.voyage.controller;

import com.assignment.voyage.dto.ApiResultDto;
import com.assignment.voyage.dto.CommentRequestDto;
import com.assignment.voyage.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController (CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping ("/comments")
    public ResponseEntity<ApiResultDto> createComment (@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {

        commentService.createComment(commentRequestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResultDto("댓글 작성 성공", HttpStatus.CREATED.value()));

    }

    @PutMapping ("/comments/{id}")
    public ResponseEntity<ApiResultDto> modifyComment (@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {

        commentService.modifyComment(id, commentRequestDto, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ApiResultDto("댓글 수정 성공", HttpStatus.ACCEPTED.value()));

    }

    @DeleteMapping ("/comments/{id}")
    public ResponseEntity<ApiResultDto> deleteComment (@PathVariable Long id, HttpServletRequest request) {

        commentService.deleteComment(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResultDto("댓글 삭제 성공", HttpStatus.OK.value()));

    }
}
