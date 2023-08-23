package com.sparta.seoulmate.repository;

import com.sparta.seoulmate.entity.redishash.Blacklist;
import org.springframework.data.repository.CrudRepository;

public interface BlacklistRepository extends CrudRepository<Blacklist, String> {
}