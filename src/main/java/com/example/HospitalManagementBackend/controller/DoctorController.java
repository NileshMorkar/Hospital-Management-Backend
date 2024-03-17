package com.example.HospitalManagementBackend.controller;


import com.example.HospitalManagementBackend.entity.DoctorEntity;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.DoctorRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.AppointmentResponse;
import com.example.HospitalManagementBackend.model.response.DoctorResponse;
import com.example.HospitalManagementBackend.repository.DoctorRepository;
import com.example.HospitalManagementBackend.service.CloudinaryImageService;
import com.example.HospitalManagementBackend.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/doctor")
@CrossOrigin("*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/{hospitalId}/create-doctor")
    public ResponseEntity<DoctorResponse> createNewDoctor(@RequestBody DoctorRequest doctorRequest, @PathVariable Long hospitalId) throws GlobalException, GlobalException {
        DoctorResponse doctorResponse = doctorService.createNewDoctor(doctorRequest, hospitalId);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
    }

    @PutMapping("/{userEmail}/update-doctor")
    public ResponseEntity<ApiResponseMessage> updateDoctorNamePassword(@PathVariable String userEmail, @RequestBody DoctorRequest doctorRequest) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        if (!Objects.equals(userEmail, doctorRequest.getEmail())) {
            ApiResponseMessage apiResponseMessage = ApiResponseMessage
                    .builder()
                    .message("Email Is Not Valid!")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);
        }


        ApiResponseMessage responseMessage = doctorService.updateDoctorNamePassword(doctorRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/{userEmail}/update-status/{appointmentId}")
    public ResponseEntity<ApiResponseMessage> updateStatusOfAppointment(@PathVariable String userEmail, @PathVariable Long doctorId, @PathVariable Long appointmentId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = doctorService.updateStatusOfAppointment(userEmail, appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/{userEmail}/get-all-appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(@PathVariable String userEmail) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        List<AppointmentResponse> appointmentResponseList = doctorService.getAllAppointments(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponseList);
    }

    @GetMapping("/get-all-doctors/{name}")
    public ResponseEntity<List<DoctorResponse>> searchDoctorByName(@PathVariable String name) {
        List<DoctorResponse> doctorResponseList = doctorService.searchDoctorByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResponseList);
    }


    @PostMapping("/{userEmail}/upload-profile-image")
    public ResponseEntity<DoctorResponse> uploadProfileImage(@PathVariable String userEmail, @RequestParam(name = "image") MultipartFile multipartFile) throws GlobalException {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        Map map = cloudinaryImageService.upload(multipartFile);
        String imageLink = map.get("secure_url").toString();

        DoctorEntity doctorEntity = doctorRepository.findByEmail(userEmail).orElse(null);
        assert doctorEntity != null;
        doctorEntity.setImage(imageLink);
        doctorRepository.save(doctorEntity);

        DoctorResponse doctorResponse = modelMapper.map(doctorEntity, DoctorResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
    }

}
