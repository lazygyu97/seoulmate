package com.sparta.seoulmate.openApi.repository;

import com.sparta.seoulmate.entity.SeoulApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeoulApiRepository extends JpaRepository<SeoulApi,String> {

    Page<SeoulApi> findByMAXCLASSNMAndSVCSTATNM(String maxClassNm, String svcStatNm, Pageable pageable);

    Optional<SeoulApi> findBySVCID(String svcid);

}
