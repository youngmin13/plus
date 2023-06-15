package com.assignment.voyage.dto;

import com.assignment.voyage.entity.VoyagePost;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VoyageResponseDto {
    private Long id;
    private String title;
    private String username;
    private String password;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public VoyageResponseDto(VoyagePost voyagePost) {
        this.id = voyagePost.getId();
        this.title = voyagePost.getTitle();
        this.username = voyagePost.getUsername();
        this.password = voyagePost.getPassword();
        this.contents = voyagePost.getContents();
        this.createdAt = voyagePost.getCreatedAt();
        this.modifiedAt = voyagePost.getModifiedAt();
    }
}
