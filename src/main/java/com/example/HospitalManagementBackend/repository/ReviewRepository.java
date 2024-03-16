package com.example.HospitalManagementBackend.repository;

import com.example.HospitalManagementBackend.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query(value = "select * from reviews where hospital_id = ?1", nativeQuery = true)
    List<ReviewEntity> findByHospitalId(Long hospitalId);

}
