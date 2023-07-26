package com.assignment.voyage.controller;

import com.assignment.voyage.dto.ApiResultDto;
import com.assignment.voyage.dto.LoginRequestDto;
import com.assignment.voyage.dto.SignupRequestDto;
import com.assignment.voyage.service.UserService;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiResultDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {

        try {
            userService.signup(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResultDto("회원가입 성공", HttpStatus.CREATED.value()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResultDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResultDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        try {
            userService.login(requestDto, response);
            return ResponseEntity.ok().body(new ApiResultDto("로그인 성공", HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResultDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}