package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.redishash.Password;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PasswordRepository extends CrudRepository<Password,Long> {

    List<Password> findByMemberId(Long id);
}
