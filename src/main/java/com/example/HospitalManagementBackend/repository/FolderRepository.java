package com.example.HospitalManagementBackend.repository;

import com.example.HospitalManagementBackend.entity.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
}
