package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.PasswordManager;
import com.sparta.seoulmate.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PasswordManagerRepository extends JpaRepository <PasswordManager,Long> {

    Optional<PasswordManager> findByUser(User user);

    //DESC : 내림차순(높은숫자부터 낮은숫자)
    //LIMIT 3 : 3건만 조회
    @Query("SELECT pm.password FROM PasswordManager pm WHERE pm.user = :user ORDER BY pm.id DESC LIMIT 3")
    List<String> findPasswordTopThree(@Param("user") User user);

}