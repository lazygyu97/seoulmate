package com.sparta.seoulmate.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.seoulmate.dto.user.LoginRequestDto;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserRoleEnum;
import com.sparta.seoulmate.entity.redishash.RefreshToken;
import com.sparta.seoulmate.jwt.JwtUtil;
import com.sparta.seoulmate.repository.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //인증
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");

        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                    LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException {
        log.info("로그인 성공 및 JWT 생성");
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        String username = user.getUsername();
        UserRoleEnum role = user.getRole();
        Long id = user.getId();

        String refreshTokenVal = UUID.randomUUID().toString();
        refreshTokenRepository.save(new RefreshToken(refreshTokenVal));

        String token = jwtUtil.createToken(username, role);

        response.setCharacterEncoding("UTF-8");

        response.addHeader("RefreshToken", refreshTokenVal);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        String message = "RefreshToken:" + refreshTokenVal + "\n" +
                "Authorization:" + token;
        response.getWriter().write(message);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException {
        log.info("로그인 실패");

        // 응답에 실패 메시지를 추가하여 클라이언트에 전달
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 상태 코드 설정
        response.getWriter().write("메세지 : 로그인 실패\nstatus : 401");

    }


}
