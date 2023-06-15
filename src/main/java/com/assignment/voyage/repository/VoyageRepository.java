package com.assignment.voyage.repository;

import com.assignment.voyage.entity.VoyagePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoyageRepository extends JpaRepository<VoyagePost, Long> {
    List<VoyagePost> findAllByOrderByCreatedAtDesc();
}
