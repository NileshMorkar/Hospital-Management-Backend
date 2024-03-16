package com.example.HospitalManagementBackend.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {
    private Long patientId;

    private String name;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private String contactNumber;
    private String address;

    private List<AppointmentResponse> appointments;
}
