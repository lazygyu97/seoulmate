package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    // 만료 시간과 상태로 회원 탈퇴 신청 정보를 검색하는 메서드
    List<Withdrawal> findByRequestTimeBefore(LocalDateTime standardTime);

    Optional<Withdrawal> findByUserId(Long id);

    List<Withdrawal> findByExpireTimeBefore(LocalDateTime expireTime);

}
