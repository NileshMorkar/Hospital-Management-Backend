package com.example.HospitalManagementBackend.controller;


import com.example.HospitalManagementBackend.entity.PatientEntity;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.jwt.JwtRequest;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/create-patient")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) throws GlobalException {
        return userService.createNormalUser(userRequest);
    }


    @GetMapping("/get-details")
    public ResponseEntity<UserResponse> getDetails() throws GlobalException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        PatientEntity patientEntity = userRepository.findByEmail(userEmail).orElseThrow(() -> new GlobalException("User Not Found", HttpStatus.NOT_FOUND));
        UserResponse userResponse = modelMapper.map(patientEntity, UserResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponseMessage> signIn(@RequestBody JwtRequest jwtRequest) throws GlobalException {

        PatientEntity patientEntity = userRepository.findByEmail(jwtRequest.getEmail()).orElseThrow(() -> new GlobalException("User Not Found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.encode(jwtRequest.getPassword()).equals(patientEntity.getPassword())) {
            throw new GlobalException("Password Is Incorrect", HttpStatus.BAD_REQUEST);
        }

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("User Log In Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);
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

    @GetMapping("/get-all-appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        List<AppointmentResponse> appointmentResponses = userService.getAllAppointments(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponses);
    }

    @DeleteMapping("/delete-appointment/{appointmentId}")
    public ResponseEntity<ApiResponseMessage> deleteAppointmentById(@PathVariable Long appointmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = userService.deleteAppointmentById(userEmail, appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/create-appointment/{doctorId}")
    public ResponseEntity<ApiResponseMessage> deleteAppointmentById(@PathVariable Long patientId, @PathVariable Long doctorId, @RequestBody AppointmentRequest appointmentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = userService.createNewAppointment(patientId, doctorId, appointmentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/create-review/{hospitalId}")
    public ResponseEntity<ApiResponseMessage> createReview(@PathVariable Long patientId, @PathVariable Long hospitalId, @RequestBody ReviewRequest reviewRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = userService.createReview(userEmail, hospitalId, reviewRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/delete-review/{reviewId}")
    public ResponseEntity<ApiResponseMessage> deleteReview(@PathVariable Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = userService.deleteReview(userEmail, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/upload-profile-image")
    public ResponseEntity<UserResponse> uploadProfileImage(@RequestParam(name = "image") MultipartFile multipartFile) throws GlobalException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Map map = cloudinaryImageService.upload(multipartFile);
        String imageLink = map.get("secure_url").toString();

        PatientEntity patientEntity = userRepository.findByEmail(userEmail).orElse(null);
        patientEntity.setImage(imageLink);
        userRepository.save(patientEntity);

        UserResponse userResponse = modelMapper.map(patientEntity, UserResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }


}
