package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.PasswordManager;
import com.sparta.seoulmate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordManagerRepository extends JpaRepository <PasswordManager,Long> {

    List<PasswordManager> findTop4ByUserOrderByCreatedAtDesc(User user);

}