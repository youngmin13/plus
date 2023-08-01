package com.assignment.voyage.service;

import com.assignment.voyage.dto.ApiResultDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest request) {

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
            Post post = new Post(postRequestDto, user.getUsername());
            //DB 저장
            Post savePost = postRepository.save(post);
            // Entity -> ResponseDto
            PostResponseDto postResponseDto = new PostResponseDto(savePost);

            return postResponseDto;
        }
        else throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
    }

    public List<PostResponseDto> getPost() {
        // DB 조회
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    public PostOneResponseDto getPostContent(Long id) {
        Post post = findPostById(id);
        PostOneResponseDto postOneResponseDto = new PostOneResponseDto(post);
        return postOneResponseDto;
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request) throws Exception {

        // 쿠키에서 받아오도록 바꿈
        String token = jwtUtil.substringToken(jwtUtil.getTokenFromRequest(request));
        // token의 payload에는 정보가 담겨져 있는데
        // 여기에 담겨진 정보의 한가지 조각을 claim이라고 한다.
        // 따라서 token의 validation을 검사한 다음에, 토큰의 값을 claims에 담는다.
        Claims claims;

        PostResponseDto postResponseDto;

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

            if (post.getUsername().equals(user.getUsername())) {
                post.update(postRequestDto);
                postResponseDto = new PostResponseDto(post);
                // 수정된 게시글을 반환해야 한다.
                return postResponseDto;
            }
            else throw new IllegalArgumentException("해당 게시글을 작성한 사용자가 아닙니다.");
        }
        else throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
    }

    public ApiResultDto deletePost(String title, HttpServletRequest request) throws Exception {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        PostResponseDto postResponseDto;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = findPostByTitle(title);

            if (post.getUsername().equals(user.getUsername())) {
                postRepository.delete(post);
                return new ApiResultDto("삭제 성공", HttpStatus.OK.value());
            }
            else throw new IllegalArgumentException("해당 게시글을 작성한 사용자가 아닙니다.");
        }
        else throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
    }

    private Post findPostByTitle(String title) {
        return postRepository.findByTitle(title).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }

}
