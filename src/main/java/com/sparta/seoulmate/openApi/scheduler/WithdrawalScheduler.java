package com.sparta.seoulmate.openApi.scheduler;

import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.Withdrawal;
import com.sparta.seoulmate.repository.UserRepository;
import com.sparta.seoulmate.repository.WithdrawalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@AllArgsConstructor
@Slf4j
public class WithdrawalScheduler {

    private final WithdrawalRepository withdrawalRepository;
    private final UserRepository userRepository;

    // 스케줄링 주기 설정
    @Scheduled(cron = "0 0 1,10,19 * * *") // 60000 : 1분
    @Transactional
    public void cleanupExpiredWithdrawals() {
        LocalDateTime standardTime = LocalDateTime.now().minusDays(7); // 현재 시간보다 n시간 전에 있었던 것을 삭제 -> n시간이 유예기간
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<Withdrawal> expiryWithdrawals = withdrawalRepository.findByRequestTimeBefore(standardTime);

        // 만료된 회원 탈퇴 신청 정보 삭제
        withdrawalRepository.deleteAll(expiryWithdrawals);

        // withdrawals에 있는 정보가 삭제 되면서 users에 있는 관련된 정보도 삭제
        List<User> users = expiryWithdrawals.stream().map(Withdrawal::getUser).toList();
        userRepository.deleteAll(users);

        log.info("현재 시간: {}", currentDateTime);
        log.info("탈퇴 요청: {}", standardTime);
        log.info("현재 시간 - 탈퇴 요청 = 탈퇴 유예 기간");
    }
}
