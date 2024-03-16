package com.example.HospitalManagementBackend.repository;

import com.example.HospitalManagementBackend.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
