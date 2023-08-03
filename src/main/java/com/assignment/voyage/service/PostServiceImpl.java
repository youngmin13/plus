package com.assignment.voyage.service;

import com.assignment.voyage.dto.PostOneResponseDto;
import com.assignment.voyage.dto.PostRequestDto;
import com.assignment.voyage.dto.PostResponseDto;
import com.assignment.voyage.entity.Post;
import com.assignment.voyage.entity.User;
import com.assignment.voyage.jwt.JwtUtil;
import com.assignment.voyage.repository.PostRepository;
import com.assignment.voyage.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void createPost(PostRequestDto postRequestDto, HttpServletRequest request) {

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
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // RequestDto -> Entity
            Post post = new Post(postRequestDto, user);
            //DB 저장
            postRepository.save(post);
        }
        else throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
    }

    @Override
    public List<PostResponseDto> getPost() {
        // DB 조회
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    @Override
    public PostOneResponseDto getPostContent(Long id) {
        Post post = findPostById(id);
        PostOneResponseDto postOneResponseDto = new PostOneResponseDto(post);
        return postOneResponseDto;
    }

    @Override
    @Transactional
    public void updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) {

        // 쿠키에서 받아오도록 바꿈
        String token = jwtUtil.substringToken(jwtUtil.getTokenFromRequest(request));
        // token의 payload에는 정보가 담겨져 있는데
        // 여기에 담겨진 정보의 한가지 조각을 claim이라고 한다.
        // 따라서 token의 validation을 검사한 다음에, 토큰의 값을 claims에 담는다.
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("가입한 사용자가 아닙니다.")
            );

            Post post = findPostById(id);

            if (post.getUser().getUsername().equals(user.getUsername())) {
                post.update(postRequestDto);
            }
            else throw new IllegalArgumentException("해당 게시글을 작성한 사용자가 아닙니다.");
        }
        else throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
    }

    @Override
    public void deletePost(Long id, HttpServletRequest request) {

        // 헤더 말고 쿠키에서 토큰 받아오도록 수정
        String token = jwtUtil.substringToken(jwtUtil.getTokenFromRequest(request));
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = findPostById(id);

            if (post.getUser().getUsername().equals(user.getUsername())) {
                postRepository.delete(post);
            }
            else throw new IllegalArgumentException("해당 게시글을 작성한 사용자가 아닙니다.");
        }
        else throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }

}
