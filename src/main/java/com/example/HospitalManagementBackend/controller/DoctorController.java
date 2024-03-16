package com.example.HospitalManagementBackend.controller;


import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.DoctorRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.AppointmentResponse;
import com.example.HospitalManagementBackend.model.response.DoctorResponse;
import com.example.HospitalManagementBackend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/{hospitalId}/create-doctor")
    public ResponseEntity<DoctorResponse> createNewDoctor(@RequestBody DoctorRequest doctorRequest, @PathVariable Long hospitalId) throws GlobalException, GlobalException {
        DoctorResponse doctorResponse = doctorService.createNewDoctor(doctorRequest, hospitalId);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResponse);
    }

    @PutMapping("/update-doctor")
    public ResponseEntity<ApiResponseMessage> updateDoctorNamePassword(@RequestBody DoctorRequest doctorRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

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

    @PutMapping("/update-status/{appointmentId}")
    public ResponseEntity<ApiResponseMessage> updateStatusOfAppointment(@PathVariable Long doctorId, @PathVariable Long appointmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = doctorService.updateStatusOfAppointment(userEmail, appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/get-all-appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        List<AppointmentResponse> appointmentResponseList = doctorService.getAllAppointments(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponseList);
    }

    @GetMapping("/get-all-doctors/{name}")
    public ResponseEntity<List<DoctorResponse>> searchDoctorByName(@PathVariable String name) {
        List<DoctorResponse> doctorResponseList = doctorService.searchDoctorByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResponseList);
    }

}
