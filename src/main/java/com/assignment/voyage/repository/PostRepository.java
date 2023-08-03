package com.assignment.voyage.repository;

import com.assignment.voyage.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findAllByOrderByCreatedAtDesc();
    Optional<Post> findById(Long id);

    Page<Post> findAll(Pageable pageable);
}

