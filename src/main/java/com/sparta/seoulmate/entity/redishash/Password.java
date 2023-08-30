package com.sparta.seoulmate.entity.redishash;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "password")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Password {
    @Id
    private Long memberId; // 사용자 식별

    private String updatedPassword; // 변경된 비밀번호

}
