package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.redishash.SmsVerification;
import org.springframework.data.repository.CrudRepository;

public interface SmsVerificationRepository extends CrudRepository<SmsVerification, String> {
}
