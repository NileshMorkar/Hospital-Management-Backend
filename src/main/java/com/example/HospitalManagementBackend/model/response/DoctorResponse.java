package com.example.HospitalManagementBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponse {
    private Long doctorId;
    private String name;
    private String email;

    private String specialization;
    private String qualification;
    private String contactNumber;

    private String experience;
    private String description;

    private HospitalResponse hospital;
    private String role;
    private String image;
}
