package com.example.HospitalManagementBackend.controller;


import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.AppointmentRequest;
import com.example.HospitalManagementBackend.model.request.ReviewRequest;
import com.example.HospitalManagementBackend.model.request.UserRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.AppointmentResponse;
import com.example.HospitalManagementBackend.model.response.UserResponse;
import com.example.HospitalManagementBackend.repository.UserRepository;
import com.example.HospitalManagementBackend.service.CloudinaryImageService;
import com.example.HospitalManagementBackend.service.ImageService;
import com.example.HospitalManagementBackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class PatientController {
    @Autowired
    private UserService userService;


    @Autowired
    private CloudinaryImageService cloudinaryImageService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @PostMapping("/create-user")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) throws GlobalException {
        return userService.createNormalUser(userRequest);
    }


    @DeleteMapping
    ResponseEntity<ApiResponseMessage> delete() throws GlobalException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return userService.delete(userEmail);
    }

    @PutMapping
    ResponseEntity<ApiResponseMessage> update(@Valid @RequestBody UserRequest newUser) throws GlobalException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return userService.update(userEmail, newUser);
    }


    @GetMapping("/{patientId}/get-all-appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(@PathVariable Long patientId) {
        List<AppointmentResponse> appointmentResponses = userService.getAllAppointments(patientId);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponses);
    }


    @DeleteMapping("/{patientId}/delete-appointment/{appointmentId}")
    public ResponseEntity<ApiResponseMessage> deleteAppointmentById(@PathVariable Long patientId, @PathVariable Long appointmentId) {
        ApiResponseMessage responseMessage = userService.deleteAppointmentById(patientId, appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{patientId}/create-appointment/{doctorId}")
    public ResponseEntity<ApiResponseMessage> deleteAppointmentById(@PathVariable Long patientId, @PathVariable Long doctorId, @RequestBody AppointmentRequest appointmentRequest) {
        ApiResponseMessage responseMessage = userService.createNewAppointment(patientId, doctorId, appointmentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{patientId}/create-review/{hospitalId}")
    public ResponseEntity<ApiResponseMessage> createReview(@PathVariable Long patientId, @PathVariable Long hospitalId, @RequestBody ReviewRequest reviewRequest) {
        ApiResponseMessage responseMessage = userService.createReview(patientId, hospitalId, reviewRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/{patientId}/delete-review/{reviewId}")
    public ResponseEntity<ApiResponseMessage> deleteReview(@PathVariable Long patientId, @PathVariable Long reviewId) {
        ApiResponseMessage responseMessage = userService.deleteReview(patientId, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


}
