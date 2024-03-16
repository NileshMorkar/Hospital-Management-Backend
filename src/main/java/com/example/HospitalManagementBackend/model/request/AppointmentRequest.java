package com.example.HospitalManagementBackend.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequest {
    private Date appointmentDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime appointmentTime;
    private String status;
    private String description;
}
