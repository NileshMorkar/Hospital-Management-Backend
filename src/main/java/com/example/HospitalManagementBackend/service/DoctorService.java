package com.example.HospitalManagementBackend.service;


import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.DoctorRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.AppointmentResponse;
import com.example.HospitalManagementBackend.model.response.DoctorResponse;

import java.util.List;

public interface DoctorService {

    DoctorResponse createNewDoctor(DoctorRequest doctorRequest, Long hospitalId) throws GlobalException, GlobalException;

    ApiResponseMessage updateDoctorNamePassword(DoctorRequest doctorRequest);

    ApiResponseMessage updateStatusOfAppointment(String userEmail, Long appointmentId);

    List<AppointmentResponse> getAllAppointments(String userEmail);

    List<DoctorResponse> searchDoctorByName(String name);
}
