package com.example.HospitalManagementBackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalRequest {

    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    private String email;

    private Integer capacity;
    private Date establishedDate;
    private String contactNumber;

    private String description;

}
