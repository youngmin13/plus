package com.assignment.voyage.controller;

import com.assignment.voyage.dto.ApiResultDto;
import com.assignment.voyage.dto.PostOneResponseDto;
import com.assignment.voyage.dto.PostRequestDto;
import com.assignment.voyage.dto.PostResponseDto;
import com.assignment.voyage.entity.Post;
import com.assignment.voyage.service.PostServiceImpl;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostServiceImpl postServiceImpl;

    public PostController(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    //  1. 전체 게시글 목록 조회 API
    //  - 제목, 작성자명(username), 작성 내용, 작성 날짜를 조회하기
    //  - 작성 날짜 기준 내림차순으로 정렬하기
    @GetMapping("/posts")
    public List<PostResponseDto> getPost() {
        return postServiceImpl.getPost();
    }

    //  2. 게시글 작성 API
    //  - 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
    //  - 제목, 작성 내용을 저장하고
    //  - 저장된 게시글을 Client 로 반환하기(username은 로그인 된 사용자)
    @PostMapping("/posts")
    public ResponseEntity<ApiResultDto> createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {

        postServiceImpl.createPost(postRequestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResultDto("게시글 작성 성공", HttpStatus.CREATED.value()));

    }
    //  3. 선택한 게시글 조회 API
    //  - 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기
    //  (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
    @GetMapping("/posts/{id}")
    public PostOneResponseDto getPostContent(@PathVariable Long id) {
        return postServiceImpl.getPostContent(id);
    }

    //  4. 선택한 게시글 수정 API
    //  - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
    //  - 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
    // 생각해보니, 제목이랑 내용을 수정해야하니까 수정기능에는 아이디 (변하지 않는 값) 을 넣는 것이 더 나을 듯
    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResultDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) throws Exception {

        postServiceImpl.updatePost(id, postRequestDto, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new ApiResultDto("게시글 수정 성공", HttpStatus.ACCEPTED.value()));

    }

    //  5. 선택한 게시글 삭제 API
    //  - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능
    //  - 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResultDto> deletePost(@PathVariable Long id, HttpServletRequest request) throws Exception {

        postServiceImpl.deletePost(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResultDto("게시글 삭제 성공", HttpStatus.OK.value()));
    }

    // @RequestParam으로 줬기 때문에 url 뒤에 ?title= 이 붙어있어야함
    @GetMapping("/findPostsWithTitle")
    public List<PostResponseDto> getPostListWithTitle(@RequestParam("title") String title) {

        return postServiceImpl.getPostListWithTitle(title);
    }

    @GetMapping("/findPostWithPage")
    public List<PostResponseDto> getPostListWithPage (@RequestParam("page") int page,
            @RequestParam("size") int size) {
        return postServiceImpl.getPostListWithPage(page, size);
    }

    @GetMapping("/findPostsWithPageAndTitle")
    public List<PostResponseDto> getPostListWithPageAndTitle (@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("title") String title) {

        return postServiceImpl.getPostListWithPageAndTitle(page, size, title);
    }
}
