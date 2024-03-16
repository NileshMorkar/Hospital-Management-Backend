package com.example.HospitalManagementBackend.repository;

import com.example.HospitalManagementBackend.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByEmail(String userEmail);

    List<PatientEntity> findByNameContaining(String name);
}
