package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.redishash.EmailVerification;
import org.springframework.data.repository.CrudRepository;

public interface EmailVerificationRepository extends CrudRepository<EmailVerification, String> {
}
