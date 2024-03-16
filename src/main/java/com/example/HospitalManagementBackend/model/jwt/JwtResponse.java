package com.example.HospitalManagementBackend.model.jwt;

import com.example.HospitalManagementBackend.model.response.UserResponse;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private UserResponse user;
}
