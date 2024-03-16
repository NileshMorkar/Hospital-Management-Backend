package com.example.HospitalManagementBackend.repository;

import com.example.HospitalManagementBackend.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    @Query
    Optional<DoctorEntity> findByEmail(String email);

    @Query(value = "select * from doctors where name LIKE %?1%", nativeQuery = true)
    List<DoctorEntity> searchByDoctorName(String name);

}
