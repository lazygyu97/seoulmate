package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<UserInterest,Long> {
    List<UserInterest> findByUser_id(Long id);


}
