package com.assignment.voyage.repository;

import com.assignment.voyage.dto.PostResponseDto;
import com.assignment.voyage.entity.Post;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositoryCustom {
    List<Post> getPostWithTitle (String title);
    List<Post> getPostListWithPageAndTitle (long offset, int pageSize, String title);
}
