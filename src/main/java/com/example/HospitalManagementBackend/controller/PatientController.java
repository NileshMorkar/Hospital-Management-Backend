package com.example.HospitalManagementBackend.controller;


import com.example.HospitalManagementBackend.entity.PatientEntity;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
@CrossOrigin("*")
public class PatientController {
    @Autowired
    private UserService userService;
    @Autowired
    private CloudinaryImageService cloudinaryImageService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/create-patient")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) throws GlobalException {

        return userService.createNormalUser(userRequest);

    }

    @DeleteMapping("/{userEmail}")
    ResponseEntity<ApiResponseMessage> delete(@PathVariable String userEmail) throws GlobalException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();
        return userService.delete(userEmail);
    }

    @PutMapping("/{userEmail}")
    ResponseEntity<ApiResponseMessage> update(@PathVariable String userEmail, @Valid @RequestBody UserRequest newUser) throws GlobalException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();
        return userService.update(userEmail, newUser);
    }

    @GetMapping("/{userEmail}/get-all-appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(@PathVariable String userEmail) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        List<AppointmentResponse> appointmentResponses = userService.getAllAppointments(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponses);
    }

    @DeleteMapping("/{userEmail}/delete-appointment/{appointmentId}")
    public ResponseEntity<ApiResponseMessage> deleteAppointmentById(@PathVariable String userEmail, @PathVariable Long appointmentId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = userService.deleteAppointmentById(userEmail, appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{userEmail}/create-appointment/{doctorId}")
    public ResponseEntity<ApiResponseMessage> deleteAppointmentById(@PathVariable String userEmail, @PathVariable Long patientId, @PathVariable Long doctorId, @RequestBody AppointmentRequest appointmentRequest) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = userService.createNewAppointment(patientId, doctorId, appointmentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{userEmail}/create-review/{hospitalId}")
    public ResponseEntity<ApiResponseMessage> createReview(@PathVariable String userEmail, @PathVariable Long patientId, @PathVariable Long hospitalId, @RequestBody ReviewRequest reviewRequest) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = userService.createReview(userEmail, hospitalId, reviewRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/{userEmail}/delete-review/{reviewId}")
    public ResponseEntity<ApiResponseMessage> deleteReview(@PathVariable String userEmail, @PathVariable Long reviewId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = userService.deleteReview(userEmail, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{userEmail}/upload-profile-image")
    public ResponseEntity<UserResponse> uploadProfileImage(@PathVariable String userEmail, @RequestParam(name = "image") MultipartFile multipartFile) throws GlobalException {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        Map map = cloudinaryImageService.upload(multipartFile);
        String imageLink = map.get("secure_url").toString();

        PatientEntity patientEntity = userRepository.findByEmail(userEmail).orElse(null);
        patientEntity.setImage(imageLink);
        userRepository.save(patientEntity);

        UserResponse userResponse = modelMapper.map(patientEntity, UserResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }


}
