package com.example.HospitalManagementBackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorRequest {

    private String name;
    private String email;
    private String password;
    private String specialization;
    private String qualification;
    private String contactNumber;
    private String experience;
    private String description;

}
