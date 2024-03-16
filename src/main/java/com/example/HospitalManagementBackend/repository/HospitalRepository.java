package com.example.HospitalManagementBackend.repository;

import com.example.HospitalManagementBackend.entity.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<HospitalEntity, Long> {

    @Query
    Optional<HospitalEntity> findByEmail(String email);


    @Query(value = "select * from hospitals where address LIKE %?1% OR city LIKE %?1% OR state LIKE %?1% OR zip_code LIKE %?1%", nativeQuery = true)
    List<HospitalEntity> searchByAddress(String address);


}
