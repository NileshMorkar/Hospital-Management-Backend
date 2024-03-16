package com.example.HospitalManagementBackend.repository;

import com.example.HospitalManagementBackend.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {

    List<FolderEntity> findAllByPatientEmail(String email);
}
