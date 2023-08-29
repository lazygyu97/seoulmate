package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<UserInterest,Long> {
}
