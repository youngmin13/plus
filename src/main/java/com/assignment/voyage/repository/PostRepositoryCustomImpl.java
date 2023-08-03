package com.assignment.voyage.repository;

import com.assignment.voyage.dto.PostResponseDto;
import com.assignment.voyage.entity.Post;
import com.assignment.voyage.entity.QPost;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostWithTitle(String title) {

        QPost post = QPost.post;

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, post.createdAt);

        return jpaQueryFactory.select(post)
                .from(post)
                .where(post.title.like("%" + title + "%"))
                .orderBy(orderSpecifier)
                .fetch();
    }

    @Override
    public List<Post> getPostListWithPageAndTitle(long offset, int pageSize, String title) {

        QPost post = QPost.post;

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, post.createdAt);

        return jpaQueryFactory.selectFrom(post)
                .offset(offset)
                .limit(pageSize)
                .where(post.title.like("%" + title + "%"))
                .orderBy(orderSpecifier)
                .fetch();
    }
}
