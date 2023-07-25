package com.assignment.voyage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    // 닉네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기
    @NotBlank
    @Size(min = 3)
    @Pattern(regexp = "[a-z0-9]*$")
    private String username;

    // 비밀번호는 최소 4자 이상
    @NotBlank
    @Size(min = 4)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]*$")
    private String password;

    @NotBlank
    @Size(min = 4)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]*$")
    private String checkPassword;

//    private boolean admin = false;
//    private String adminToken = "";
}