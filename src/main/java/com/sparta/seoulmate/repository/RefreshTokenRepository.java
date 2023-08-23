package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.redishash.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByMemberId(Long id);

    void deleteByMemberId(Long id);
}