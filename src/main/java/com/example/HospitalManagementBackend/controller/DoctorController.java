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
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        ApiResponseMessage responseMessage = doctorService.updateDoctorNamePassword(doctorRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/{doctorId}/update-status/{appointmentId}")
    public ResponseEntity<ApiResponseMessage> updateStatusOfAppointment(@PathVariable Long doctorId, @PathVariable Long appointmentId) {
        ApiResponseMessage responseMessage = doctorService.updateStatusOfAppointment(doctorId, appointmentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/{doctorId}/get-all-appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(@PathVariable Long doctorId) {
        List<AppointmentResponse> appointmentResponseList = doctorService.getAllAppointments(doctorId);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentResponseList);
    }

    @GetMapping("/get-all-doctors/{name}")
    public ResponseEntity<List<DoctorResponse>> searchDoctorByName(@PathVariable String name) {
        List<DoctorResponse> doctorResponseList = doctorService.searchDoctorByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResponseList);
    }

}
