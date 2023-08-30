package com.sparta.seoulmate.service;

import com.sparta.seoulmate.dto.SignupRequestDto;
import com.sparta.seoulmate.dto.UpdateAddressRequestDto;
import com.sparta.seoulmate.dto.UpdateNicknameRequestDto;
import com.sparta.seoulmate.dto.UserProfileResponseDto;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserRoleEnum;
import com.sparta.seoulmate.entity.redishash.Blacklist;
import com.sparta.seoulmate.entity.redishash.EmailVerification;
import com.sparta.seoulmate.entity.redishash.RefreshToken;
import com.sparta.seoulmate.entity.redishash.SmsVerification;
import com.sparta.seoulmate.jwt.JwtUtil;
import com.sparta.seoulmate.repository.*;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationRepository emailVerificationRepository;
    private final SmsVerificationRepository smsVerificationRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistRepository blacklistRepository;
    private final JwtUtil jwtUtil;
    private final PostRepository postRepository;

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
        SmsVerification smsVerification = smsVerificationRepository.findById(phone)
                .orElseThrow(() -> new IllegalArgumentException("전화번호 인증 코드를 발송하지 않았습니다."));
        if (!smsVerification.isVerificated()) {
            throw new IllegalArgumentException("전화번호 인증이 완료되지 않았습니다.");
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

    public void logout(User user, HttpServletRequest request) {
        String refreshTokenVal = request.getHeader("RefreshToken").substring(7);
        String accessTokenVal = jwtUtil.getJwtFromHeader(request);

        try {
            Key key = jwtUtil.getKey();
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessTokenVal)
                    .getBody()
                    .getExpiration();
            long expireTime = expiration.getTime() - System.currentTimeMillis();
            blacklistRepository.save(new Blacklist(accessTokenVal, expireTime / 1000));
            RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenVal)
                    .orElseThrow(() -> new IllegalArgumentException("리프레시 토큰이 없습니다."));
            refreshTokenRepository.delete(refreshToken);
            System.out.println(expiration.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 프로필 수정(닉네임)
    @Transactional
    public void updateNickname(UpdateNicknameRequestDto requestDto, User user) {
        User targetUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다"));

        if (!targetUser.getNickname().equals(user.getNickname())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 회원 중복 확인 (새로운 닉네임에 대한 중복 확인)
        Optional<User> checkNickname = userRepository.findByNickname(requestDto.getNickname());
        if (checkNickname.isPresent() && !checkNickname.get().equals(targetUser)) {
            throw new IllegalArgumentException("이미 사용중인 닉네임 입니다.");
        }

        targetUser.updateNickname(requestDto.getNickname());
    }

    // 프로필 수정(주소)
    @Transactional
    public void updateAddress(UpdateAddressRequestDto requestDto, User user) {
        User targetUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다"));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        targetUser.updateAddress(requestDto.getCity(), requestDto.getDistrict(), requestDto.getAddress());
    }

    // 프로필 조회
    public UserProfileResponseDto getUserProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 ID를 찾을 수 없습니다. : " + id));
        return UserProfileResponseDto.of(user);
    }
}
