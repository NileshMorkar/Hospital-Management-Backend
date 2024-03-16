package com.example.HospitalManagementBackend.repository;


import com.example.HospitalManagementBackend.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    @Query
    Optional<AdminEntity> findByEmail(String email);

}
