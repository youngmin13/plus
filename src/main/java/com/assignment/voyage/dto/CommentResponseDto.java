package com.assignment.voyage.dto;

import com.assignment.voyage.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    String username;
    String body;

    public CommentResponseDto (Comment comment) {
        this.username = comment.getUser().getUsername();
        this.body = comment.getBody();
    }
}
