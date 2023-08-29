package com.sparta.seoulmate.controller;

import com.sparta.seoulmate.dto.*;
import com.sparta.seoulmate.security.UserDetailsImpl;
import com.sparta.seoulmate.service.EmailService;
import com.sparta.seoulmate.service.InterestService;
import com.sparta.seoulmate.service.SmsService;
import com.sparta.seoulmate.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final SmsService smsService;
    private final InterestService interestService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signUp(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {

        // Validation 예외처리 : 아이디와 패스워드가 pattern에 맞게 입력 되었는지 확인.
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new ApiResponseDto("입력하신 정보가 회원가입 요건에 맞지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        // Validation 예외 처리후 서비스로 전달.
        userService.signUp(requestDto);

        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    // 사용자의 관심사 등록
    @PostMapping("/users/interest")
    public ResponseEntity<ApiResponseDto> saveInterest(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @RequestBody List<String> list){
        for (String lists:list){
            System.out.println(lists);
        }
        System.out.println("담기 시도");
        interestService.saveInterest(userDetails.getUser(),list);
        return ResponseEntity.ok().body(new ApiResponseDto("관심사 등록 성공", HttpStatus.OK.value()));

    }

    //인증 메일 전송
    @PostMapping("/signup/mail")
    public ResponseEntity mailSend(@RequestBody EmailRequestDto requestDto) throws Exception {
        return ResponseEntity.status(201).body(emailService.sendSimpleMessage(requestDto.getEmail()));
    }

    // 인증 메일 인증
    @GetMapping("/signup/mail")
    public ResponseEntity mailVerification(@RequestParam String email,@RequestParam String code){
        emailService.mailVerification(email,code);
        return ResponseEntity.ok().body("이메일이 인증되었습니다.");
    }

    // 인증 문자 전송
    @PostMapping("/signup/sms")
    public ResponseEntity smsSend(@RequestBody SmsRequestDto requestDto) throws Exception {
        return  ResponseEntity.status(201).body(smsService.smsSend(requestDto.getPhone()));
    }

    //인증 문자 인증
    @GetMapping("/signup/sms")
    public ResponseEntity smsVerification(@RequestParam String phone,@RequestParam String code) {
        smsService.smsVerification(phone,code);
        return ResponseEntity.ok().body("전화번호 인증이 완료되었습니다.");
    }

    //Blacklist를 활용한 로그아웃
    @GetMapping("/logout")
    public ResponseEntity logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request){
        userService.logout(userDetails.getUser(), request);
        return ResponseEntity.ok().body("로그아웃 완료");
    }

    //관심사 등록 할때 쓰일 카테고리 테스트 코드
    @GetMapping("/test")
    public ResponseEntity<CategoryResponseDto> getCategory() {
        CategoryResponseDto responseDto = new CategoryResponseDto();
        return ResponseEntity.ok().body(responseDto);
    }
}
