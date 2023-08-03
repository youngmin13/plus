package com.assignment.voyage.service;

import com.assignment.voyage.dto.PostOneResponseDto;
import com.assignment.voyage.dto.PostRequestDto;
import com.assignment.voyage.dto.PostResponseDto;
import com.assignment.voyage.entity.Post;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostService {

    /**
     * 게시글 생성
     * @param postRequestDto 게시글 생성 요청 정보
     * @param request 쿠키에 담긴 토큰으로 게시글 요청자 확인할 HttpServletRequest
     */
    void createPost(PostRequestDto postRequestDto, HttpServletRequest request);

    /**
     * 게시글 전체 목록 조회
     * @return 게시글 전체 목록 (리스트 - 제목, 작성자명, 작성 날짜)
     */
    List<PostResponseDto> getPost();

    /**
     * 게시글 조회
     * @param id 조회할 게시글 id
     * @return 조회한 게시글 (제목, 작성자명, 작성 날짜, 작성 내용)
     */

    PostOneResponseDto getPostContent(Long id);

    /**
     * 게시글 수정
     * @param id 수정할 게시글 아이디
     * @param postRequestDto 수정할 게시글 정보 (제목, 작성 내용)
     * @param request 쿠키에 담긴 토큰으로 요청자 확인할 HttpServletRequest
     */

    void updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest request);

    /**
     * 게시글 삭제
     * @param id 삭제할 게시글 아이디
     * @param request 쿠키에 담긴 토큰으로 요청자 확인할 HttpServletRequest
     */
    void deletePost(Long id, HttpServletRequest request);

    /**
     * 해당 제목을 가진 게시글 검색
     * @param title 제목
     */
    List<PostResponseDto> getPostListWithTitle(String title);

    /**
     * 원하는 페이지 사이즈 만큼 조회하는 메서드 (title 입력하면 검색까지)
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return
     */
    List<PostResponseDto> getPostListWithPageAndTitle (int page, int size, String title);

    /**
     * JpaRepository 용 페이지 정렬
     * @param page
     * @param size
     * @return
     */
    List<PostResponseDto> getPostListWithPage(int page, int size);
}
