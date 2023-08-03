package com.assignment.voyage.repository;

import com.assignment.voyage.entity.Post;
import com.assignment.voyage.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostWithTitle(String title) {

        QPost post =QPost.post;

        return jpaQueryFactory.select(post)
                .from(post)
                .where(post.title.eq(title))
                .fetch();
    }
}
