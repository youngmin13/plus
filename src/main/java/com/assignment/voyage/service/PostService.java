package com.assignment.voyage.service;

import com.assignment.voyage.dto.PostRequestDto;
import com.assignment.voyage.dto.PostResponseDto;
import com.assignment.voyage.entity.Post;
import com.assignment.voyage.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        // RequestDto -> Entity
        Post post = new Post(postRequestDto);
        //DB 저장
        Post savePost = postRepository.save(post);
        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }

    public List<PostResponseDto> getPost() {
        // DB 조회
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto getPostContent(String title) {
        Post post = findPostByTitle(title);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    @Transactional
    public PostResponseDto updatePost(String title, PostRequestDto postRequestDto) throws Exception {
        Post post = findPostByTitle(title);
        PostResponseDto postResponseDto;
        if (isPasswordMatching(post, postRequestDto.getPassword())) {
            post.update(postRequestDto);
            postResponseDto = new PostResponseDto(post);
        }
        else throw new Exception("비밀번호가 일치하지 않습니다!!");
        // 수정된 게시글을 반환해야 한다.
        return postResponseDto;
    }

    public String deletePost(String title, PostRequestDto postRequestDto) throws Exception {
        // 해당 메모가 DB에 존재하는지 확인
        Post post = findPostByTitle(title);

        if (isPasswordMatching(post, postRequestDto.getPassword())) {
            postRepository.delete(post);
            return "{\"success\":\"true\"}";
        }
        else {
            throw new Exception("비밀번호가 일치하지 않습니다!!");
        }
    }

    private Post findPostByTitle(String title) {
        return postRepository.findByTitle(title).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

    private boolean isPasswordMatching(Post post, String password) {
        if (post.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
