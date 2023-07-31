package com.assignment.voyage.dto;

import com.assignment.voyage.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostOneResponseDto {
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createdAt;

    public PostOneResponseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }
}
