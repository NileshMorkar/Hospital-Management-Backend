package com.example.HospitalManagementBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseMessage {
    private String message;
    private HttpStatus httpStatus;
}
