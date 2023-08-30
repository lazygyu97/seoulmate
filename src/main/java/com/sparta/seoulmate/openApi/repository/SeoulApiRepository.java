package com.sparta.seoulmate.openApi.repository;

import com.sparta.seoulmate.entity.SeoulApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SeoulApiRepository extends JpaRepository<SeoulApi,Long> {

    //데이터베이스를 스케줄링을 업데이트 할 때 seoul_apis 테이블을 드랍하고 다시 생성
    @Transactional
    @Modifying
    @Query(value = "truncate seoul_apis", nativeQuery = true)
    void truncateTable();

    List<SeoulApi> findByMAXCLASSNMAndSVCSTATNM(String maxClassNm, String svcStatNm);;
}
