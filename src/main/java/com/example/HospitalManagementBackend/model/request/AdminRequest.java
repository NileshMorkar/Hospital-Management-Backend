package com.example.HospitalManagementBackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequest {
    private String name;
    private String email;
    private String password;
    private String contactNumber;
}
