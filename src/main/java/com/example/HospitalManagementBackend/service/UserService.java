package com.example.HospitalManagementBackend.service;

import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.AppointmentRequest;
import com.example.HospitalManagementBackend.model.request.ReviewRequest;
import com.example.HospitalManagementBackend.model.request.UserRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.AppointmentResponse;
import com.example.HospitalManagementBackend.model.response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<UserResponse> createNormalUser(UserRequest userRequest) throws GlobalException;

    ResponseEntity<ApiResponseMessage> delete(String userEmail) throws GlobalException;


    ResponseEntity<ApiResponseMessage> update(String userEmail, UserRequest newUser) throws GlobalException;

    UserResponse getById(Long userId) throws GlobalException;

    ResponseEntity<UserResponse> getByEmail(String userEmail) throws GlobalException;

    ResponseEntity<List<UserResponse>> search(String keyword);

    ApiResponseMessage createNewAppointment(Long patientId, Long doctorId, AppointmentRequest appointmentRequest);

    List<AppointmentResponse> getAllAppointments(Long patientId);

    ApiResponseMessage deleteAppointmentById(Long patientId, Long appointmentId);


    ApiResponseMessage createReview(Long patientId, Long hospitalId, ReviewRequest reviewRequest);

    ApiResponseMessage deleteReview(Long patientId, Long reviewId);

}
