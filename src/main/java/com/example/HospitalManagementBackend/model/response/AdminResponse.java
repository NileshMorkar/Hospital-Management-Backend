package com.example.HospitalManagementBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponse {
    private Long adminId;
    private String name;
    private String email;
    private HospitalResponse hospital;
    private String role;
    private String image;
}
