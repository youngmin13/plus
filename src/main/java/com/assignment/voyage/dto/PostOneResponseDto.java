package com.assignment.voyage.dto;

import com.assignment.voyage.entity.Comment;
import com.assignment.voyage.entity.Post;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostOneResponseDto {
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> comments = new ArrayList<>();

    public PostOneResponseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        if (post.getComments() != null) {
            for (Comment comment : post.getComments()) {
                comments.add(new CommentResponseDto(comment));
            }
        }
    }
}
