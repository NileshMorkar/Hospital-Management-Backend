package com.example.HospitalManagementBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalResponse {
    private Long hospitalId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String email;
    private String image;
    private Integer capacity;
    private Date establishedDate;
    private String contactNumber;
    private String description;
    private int doctorsCount;
}
