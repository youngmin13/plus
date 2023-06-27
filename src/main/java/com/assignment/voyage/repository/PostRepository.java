package com.assignment.voyage.repository;

import com.assignment.voyage.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    Optional<Post> findByTitle(String title);
    Optional<Post> findById(Long id);
}
