package com.assignment.voyage.controller;

import com.assignment.voyage.dto.ApiResultDto;
import com.assignment.voyage.dto.LoginRequestDto;
import com.assignment.voyage.dto.SignupRequestDto;
import com.assignment.voyage.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiResultDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {

        userServiceImpl.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResultDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResultDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {

        userServiceImpl.login(requestDto, response);
        return ResponseEntity.ok().body(new ApiResultDto("로그인 성공", HttpStatus.OK.value()));
    }
}