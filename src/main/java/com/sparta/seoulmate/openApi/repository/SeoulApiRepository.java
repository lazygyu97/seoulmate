package com.sparta.seoulmate.openApi.repository;

import com.sparta.seoulmate.entity.SeoulApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeoulApiRepository extends JpaRepository<SeoulApi,String> {

    Page<SeoulApi> findByMAXCLASSNMAndSVCSTATNM(String maxClassNm, String svcStatNm, Pageable pageable);

    Optional<SeoulApi> findBySVCID(String svcid);

    Optional<List<SeoulApi>> findByAREANMAndMINCLASSNMAndSVCSTATNM(String address, String randomInterest, String svcStatNm);

    Optional<List<SeoulApi>> findByAREANMAndSVCSTATNM(String address, String svcStatNm);

    List<SeoulApi> findBySVCSTATNM(String svcStatNm);

}
