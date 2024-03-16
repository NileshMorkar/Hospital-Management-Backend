package com.example.HospitalManagementBackend.service;


import com.example.HospitalManagementBackend.model.request.HospitalRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.DoctorResponse;
import com.example.HospitalManagementBackend.model.response.HospitalResponse;
import com.example.HospitalManagementBackend.model.response.ReviewResponse;

import java.util.List;

public interface HospitalService {
    ApiResponseMessage createNewHospital(String adminId, HospitalRequest hospitalRequest);

    ApiResponseMessage updateHospitalInfo(HospitalRequest hospitalRequest);

    List<HospitalResponse> getAllHospitals();

    List<DoctorResponse> getAllDoctors(Long hospitalId);

    List<ReviewResponse> getAllReviews(Long hospitalId);

    List<HospitalResponse> searchHospitalByAddress(String address);

}
