package com.assignment.voyage.service;

import com.assignment.voyage.dto.LoginRequestDto;
import com.assignment.voyage.dto.SignupRequestDto;
import com.assignment.voyage.entity.User;
import com.assignment.voyage.entity.UserRoleEnum;
import com.assignment.voyage.jwt.JwtUtil;
import com.assignment.voyage.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Override
    @Transactional
    public void signup(SignupRequestDto requestDto) {

        // 닉네임, 비밀번호, 비밀번호 확인을 request에서 전달받기
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String checkPassword = requestDto.getCheckPassword();

        // 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 response에 포함하기
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 닉네임과 같은 값이 포함된 경우 회원가입에 실패
        if (password.contains(username)) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함되어서는 안됩니다.");
        }

        // 비밀번호 확인은 비밀번호와 정확하게 일치하기
        if (!password.equals(checkPassword)) {
            throw new IllegalArgumentException("입력하신 비밀번호와 일치하지 않습니다.");
        }

        // 사용자 ROLE 확인 -> 우선은 default 값으로 USER 등록
        UserRoleEnum role = UserRoleEnum.USER;
//        if (requestDto.isAdmin()) {
//            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = UserRoleEnum.ADMIN;
//        }

        // 사용자 등록
        User user = new User(username, passwordEncoder.encode(password), role);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElse(null);

        // 비밀번호 확인
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
    }
}