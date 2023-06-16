package com.assignment.voyage.dto;

import com.assignment.voyage.entity.VoyagePost;
import lombok.Getter;

import java.time.LocalDateTime;

// GetMapping 할때에 비밀번호를 주면 안되기 때문에 필드와 생성자에서 비밀번호 제거
@Getter
public class VoyageResponseDto {
    private Long id;
    private String title;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public VoyageResponseDto(VoyagePost voyagePost) {
        this.id = voyagePost.getId();
        this.title = voyagePost.getTitle();
        this.username = voyagePost.getUsername();
        this.contents = voyagePost.getContents();
        this.createdAt = voyagePost.getCreatedAt();
        this.modifiedAt = voyagePost.getModifiedAt();
    }
}
