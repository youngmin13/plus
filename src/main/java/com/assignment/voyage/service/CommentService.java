package com.assignment.voyage.service;

import com.assignment.voyage.dto.CommentRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface CommentService {

    /**
     * 댓글 생성
     * @param commentRequestDto 생성할 댓글 내용 (댓글이 속한 게시글 아이디, 댓글 내용)
     * @param request 쿠키에 담긴 토큰을 받아와 댓글 생성자를 확인할 HttpServletRequest
     */
    void createComment(CommentRequestDto commentRequestDto, HttpServletRequest request);

    /**
     * 댓글 수정
     * @param id 수정할 댓글 아이디
     * @param commentRequestDto 수정할 댓글 내용 (댓글이 속한 게시글 아이디, 댓글 내용)
     * @param request 쿠키에 담긴 토큰을 받아와 댓글 수정자가 해당 댓글 생성자가 맞는지를 확인할 HttpServletRequest
     */
    void modifyComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request);

    /**
     * 댓글 삭제
     * @param id 삭제할 댓글 아이디
     * @param request 쿠키에 담긴 토큰을 받아와 댓글 삭제자가 해당 댓글 생성자가 맞는지를 확인할 HttpServletRequest
     */
    void deleteComment(Long id, HttpServletRequest request);
}
