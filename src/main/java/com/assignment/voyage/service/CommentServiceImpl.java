package com.assignment.voyage.service;

import com.assignment.voyage.dto.CommentRequestDto;
import com.assignment.voyage.entity.Comment;
import com.assignment.voyage.entity.Post;
import com.assignment.voyage.entity.User;
import com.assignment.voyage.exception.NotFoundUserException;
import com.assignment.voyage.jwt.JwtUtil;
import com.assignment.voyage.repository.CommentRepository;
import com.assignment.voyage.repository.PostRepository;
import com.assignment.voyage.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, JwtUtil jwtUtil, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void createComment(CommentRequestDto commentRequestDto, HttpServletRequest request) {
        // 쿠키에서 받아오도록 바꿈
        String token = jwtUtil.substringToken(jwtUtil.getTokenFromRequest(request));
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new NotFoundUserException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );

            // RequestDto -> Entity
            Comment comment = new Comment (commentRequestDto);
            comment.addPost(post);
            comment.addUser(user);

            post.addComment(comment);

            //DB 저장
            commentRepository.save(comment);
            postRepository.save(post);
            // Entity -> ResponseDto
        }
        else throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
    }

    @Override
    public void modifyComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        // 쿠키에서 받아오도록 바꿈
        String token = jwtUtil.substringToken(jwtUtil.getTokenFromRequest(request));
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new NotFoundUserException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            
            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );

            comment.update(commentRequestDto);
            commentRepository.save(comment);
        }
        else throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
    }

    public void deleteComment(Long id, HttpServletRequest request) {
        // 쿠키에서 받아오도록 바꿈
        String token = jwtUtil.substringToken(jwtUtil.getTokenFromRequest(request));
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new NotFoundUserException("사용자가 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );

            commentRepository.delete(comment);
        }
        else throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
    }
}
