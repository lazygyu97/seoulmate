package com.sparta.seoulmate.entity.redishash;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "sms_verification", timeToLive = 60*3)
@Getter
public class SmsVerification {
    @Id
    private String phone;
    private String code;
    private boolean verificated = false;


    public SmsVerification(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }

    public void setVerificated() {
        this.verificated = true;
    }
}
