package com.assignment.voyage.service;

import com.assignment.voyage.dto.LoginRequestDto;
import com.assignment.voyage.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    /**
     * 회원 가입
     * @param requestDto 회원 가입할 유저 정보 (이름, 비밀번호, 비밀번호 체크)
     */
    void signup(SignupRequestDto requestDto);

    /**
     * 로그인
     * @param requestDto 로그인할 유저 정보 (이름, 비밀번호)
     * @param res 회원 정보를 토큰으로 만들어 쿠키에 저장하여 넘긴 HttpServletResponse
     */
    void login(LoginRequestDto requestDto, HttpServletResponse res);
}
