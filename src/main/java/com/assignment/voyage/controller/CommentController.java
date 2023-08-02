package com.assignment.voyage.controller;

import com.assignment.voyage.dto.CommentRequestDto;
import com.assignment.voyage.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
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
    public void createComment (@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        commentService.createComment(commentRequestDto, request);
    }

    @PutMapping ("/comments/{id}")
    public void modifyComment (@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        commentService.modifyComment(id, commentRequestDto, request);
    }

    @DeleteMapping ("/comments/{id}")
    public void deleteComment (@PathVariable Long id, HttpServletRequest request) {
        commentService.deleteComment(id, request);
    }
}
