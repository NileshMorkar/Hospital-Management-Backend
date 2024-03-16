package com.example.HospitalManagementBackend.controller;


import com.example.HospitalManagementBackend.model.response.DoctorResponse;
import com.example.HospitalManagementBackend.model.response.HospitalResponse;
import com.example.HospitalManagementBackend.model.response.ReviewResponse;
import com.example.HospitalManagementBackend.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
@CrossOrigin("*")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;


    @GetMapping("/get-all-hospitals")
    public ResponseEntity<List<HospitalResponse>> getAllHospitals() {
        List<HospitalResponse> hospitalResponseList = hospitalService.getAllHospitals();
        return ResponseEntity.status(HttpStatus.OK).body(hospitalResponseList);
    }

    @GetMapping("/{hospitalId}/get-all-doctors")
    public ResponseEntity<List<DoctorResponse>> getAllDoctors(@PathVariable Long hospitalId) {
        List<DoctorResponse> doctorResponseList = hospitalService.getAllDoctors(hospitalId);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResponseList);
    }

    @GetMapping("/{hospitalId}/get-all-reviews")
    public ResponseEntity<List<ReviewResponse>> getAllReviews(@PathVariable Long hospitalId) {
        List<ReviewResponse> doctorResponseList = hospitalService.getAllReviews(hospitalId);
        return ResponseEntity.status(HttpStatus.OK).body(doctorResponseList);
    }

    @GetMapping("/get-all-hospitals/{address}")
    public ResponseEntity<List<HospitalResponse>> searchHospitalByAddress(@PathVariable String address) {
        List<HospitalResponse> hospitalResponseList = hospitalService.searchHospitalByAddress(address);
        return ResponseEntity.status(HttpStatus.OK).body(hospitalResponseList);
    }


}
