package com.sparta.seoulmate.service;

import com.sparta.seoulmate.dto.SignupRequestDto;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserGenderEnum;
import com.sparta.seoulmate.entity.UserRoleEnum;
import com.sparta.seoulmate.entity.redishash.EmailVerification;
import com.sparta.seoulmate.repository.EmailVerificationRepository;
import com.sparta.seoulmate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationRepository emailVerificationRepository;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signUp(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        String phone = requestDto.getPhone();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        EmailVerification emailVerification = emailVerificationRepository.findById(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 인증 코드를 발송하지 않았습니다."));
        if (!emailVerification.isVerificated()) {
            throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
        }
        // 회원 아이디 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
        // phone 중복확인
        Optional<User> phoneCheck = userRepository.findByPhone(phone);
        if (phoneCheck.isPresent()) {
            throw new IllegalArgumentException("중복된 전화번호 입니다.");
        }
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = requestDto.toEntity(role,password);
        userRepository.save(user);
    }

}
