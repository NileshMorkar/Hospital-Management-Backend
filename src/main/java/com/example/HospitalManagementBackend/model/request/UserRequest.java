package com.example.HospitalManagementBackend.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "User Email Is Not Valid!")
    private String email;

    @Size(min = 6, message = "Password Must Contain Minimum 6 Character!")
    private String password;

    @NotBlank(message = "Gender Cannot Be Empty!")
    private String gender;

    private String image;
}
