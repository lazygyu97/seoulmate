package com.sparta.seoulmate.service;

import com.sparta.seoulmate.entity.redishash.SmsVerification;
import com.sparta.seoulmate.repository.SmsVerificationRepository;
import com.sparta.seoulmate.repository.UserRepository;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;


@Service
public class SmsService {

    final DefaultMessageService messageService;
    private final SmsVerificationRepository smsVerificationRepository;
    private final UserRepository userRepository;

    private String SMS_API_KEY="NCSFJRZWHZOQHJUT";

    private String SMS_SECRET_KEY="2OUZKNS6HZ5UN0Z7PPLO5YXGQEXFHSYN";

    private String DOMAIN="https://api.coolsms.co.kr";

    public SmsService(SmsVerificationRepository smsVerificationRepository, UserRepository userRepository) {
        this.smsVerificationRepository = smsVerificationRepository;
        this.userRepository = userRepository;
        this.messageService = NurigoApp.INSTANCE.initialize(SMS_API_KEY, SMS_SECRET_KEY, DOMAIN);

    }

    @Transactional
    public String smsSend(String phone) throws Exception {
        if (userRepository.findByPhone(phone).isPresent()) throw new IllegalArgumentException("이미 가입한 전화번호입니다.");
        userRepository.deleteById(4L);

        Message message = new Message();
        String key = createKey();
        try {
            message.setFrom("01089547253");
            message.setTo(phone);
            message.setText("안녕하세요 seoulmate 입니다. 인증번호는 [" + key + "] 입니다.");
            this.messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new IllegalArgumentException();
        }
        Optional<SmsVerification> smsVerification = smsVerificationRepository.findById(phone);
        smsVerification.ifPresent(smsVerificationRepository::delete);
        smsVerificationRepository.save(new SmsVerification(phone, key));

        return key;
    }

    public void smsVerification(String phone, String code) {
        SmsVerification smsVerification = smsVerificationRepository.findById(phone)
                .orElseThrow(() -> new IllegalArgumentException("인증코드를 발송해주세요."));

        if (smsVerification.getCode().equals(code)) {
            smsVerification.setVerificated();
            smsVerificationRepository.save(smsVerification);
        } else throw new IllegalArgumentException("인증에 실패했습니다.");
    }

    public String createKey() {
        // 원하는 숫자 범위를 지정
        int min = 0;
        int max = 9;

        // 랜덤 숫자 생성을 위한 Random 객체 생성
        Random random = new Random();

        StringBuilder codeBuilder = new StringBuilder();

        // 지정한 길이만큼 랜덤 숫자 생성 후 코드에 추가
        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(max - min + 1) + min;
            codeBuilder.append(randomNumber);
        }

        return codeBuilder.toString();
    }


}
