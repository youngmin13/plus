package com.assignment.voyage.dto;

import com.assignment.voyage.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

// GetMapping 할때에 비밀번호를 주면 안되기 때문에 필드와 생성자에서 비밀번호 제거
@Getter
public class PostResponseDto {
//    private Long id;
    private String title;
    private String username;
//    private String contents;
    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
//        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
//        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
//        this.modifiedAt = post.getModifiedAt();
    }
}
