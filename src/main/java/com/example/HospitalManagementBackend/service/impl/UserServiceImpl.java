package com.example.HospitalManagementBackend.service.impl;

import com.example.HospitalManagementBackend.entity.*;
import com.example.HospitalManagementBackend.enums.RoleName;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.helper.Helper;
import com.example.HospitalManagementBackend.model.request.AppointmentRequest;
import com.example.HospitalManagementBackend.model.request.ReviewRequest;
import com.example.HospitalManagementBackend.model.request.UserRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.AppointmentResponse;
import com.example.HospitalManagementBackend.model.response.UserResponse;
import com.example.HospitalManagementBackend.repository.*;
import com.example.HospitalManagementBackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserResponse> createNormalUser(UserRequest userRequest) throws GlobalException {

        Optional<PatientEntity> userEntity = userRepository.findByEmail(userRequest.getEmail());

        if (userEntity.isPresent()) {
            throw new GlobalException("User Is Already Present!", HttpStatus.OK);
        }

        //Set Password By Using Encode
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        PatientEntity newUser = modelMapper.map(userRequest, PatientEntity.class);

        RoleEntity role = roleRepository.findByRoleName(RoleName.PATIENT_ROLE.name()).orElse(null);

        newUser.setRole(role);

        newUser = userRepository.save(newUser);

        UserResponse userResponse = UserResponse.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .name(newUser.getName())
                .role(RoleName.PATIENT_ROLE.name())
                .gender(newUser.getGender())
                .image(newUser.getImage())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @Override
    public ResponseEntity<ApiResponseMessage> delete(String userEmail) throws GlobalException {

        PatientEntity userEntity = userRepository.findByEmail(userEmail).orElseThrow(() -> new GlobalException("User Not Found!", HttpStatus.NOT_FOUND));

        //First delete Image Here

        userRepository.delete(userEntity);

        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User Deleted Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @Override
    public ResponseEntity<ApiResponseMessage> update(String userEmail, UserRequest newUser) throws GlobalException {

        PatientEntity userEntity = userRepository.findByEmail(userEmail).orElseThrow(() -> new GlobalException("User Not Found!", HttpStatus.NOT_FOUND));

        userEntity.setGender(newUser.getGender());
        userEntity.setPassword(userEntity.getPassword());
        userEntity.setName(newUser.getName());
        userEntity.setImage(newUser.getImage());

        userRepository.save(userEntity);

        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User Updated Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @Override
    public UserResponse getById(Long userId) throws GlobalException {
        PatientEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new GlobalException("UserId Not Present!", HttpStatus.BAD_REQUEST));

        return modelMapper.map(userEntity, UserResponse.class);
    }

    @Override
    public ResponseEntity<UserResponse> getByEmail(String userEmail) throws GlobalException {
        PatientEntity userEntity = userRepository.findByEmail(userEmail).orElseThrow(() -> new GlobalException("Email Not Present!", HttpStatus.BAD_REQUEST));
        UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Override
    public ResponseEntity<List<UserResponse>> search(String keyword) {
        List<PatientEntity> usersEntity = userRepository.findByNameContaining(keyword);
        List<UserResponse> usersResponse = usersEntity.stream().map(userEntity -> modelMapper.map(userEntity, UserResponse.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(usersResponse);

    }


    @Override
    public ApiResponseMessage createNewAppointment(Long patientId, Long doctorId, AppointmentRequest appointmentRequest) {

        PatientEntity patient = userRepository.findById(patientId).orElse(null);
        DoctorEntity doctor = doctorRepository.findById(doctorId).orElse(null);

        if (doctor == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Doctor Not Found!!")
                    .build();
        }

        AppointmentEntity appointment = AppointmentEntity
                .builder()
                .appointmentDate(appointmentRequest.getAppointmentDate())
                .patient(patient)
                .status("PENDING")
                .doctor(doctor)
                .appointmentTime(appointmentRequest.getAppointmentTime())
                .description(appointmentRequest.getDescription())
                .build();

        assert patient != null;
        patient.getAppointments().add(appointment);
        userRepository.save(patient);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Appointment Created Successfully!!")
                .build();
    }

    @Override
    public List<AppointmentResponse> getAllAppointments(Long patientId) {
        PatientEntity patient = userRepository.findById(patientId).orElse(null);
        assert patient != null;
        return patient.getAppointments().stream().map(Helper::getAppointmentResponseFromAppointmentEntity).toList();
    }

    @Override
    public ApiResponseMessage deleteAppointmentById(Long patientId, Long appointmentId) {

        PatientEntity patient = userRepository.findById(patientId).orElse(null);
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElse(null);

        if (appointment == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Appointment Not Found !!")
                    .build();
        }
        assert patient != null;
        patient.getAppointments().remove(appointment);
        userRepository.save(patient);
        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Appointment Deleted Successfully!!")
                .build();
    }

    @Override
    public ApiResponseMessage createReview(Long patientId, Long hospitalId, ReviewRequest reviewRequest) {

        HospitalEntity hospital = hospitalRepository.findById(hospitalId).orElse(null);

        if (hospital == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Hospital Not Found!!")
                    .build();
        }

        PatientEntity patient = userRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Patient Not Found!!")
                    .build();
        }

        ReviewEntity review = ReviewEntity
                .builder()
                .star(reviewRequest.getStar())
                .hospital(hospital)
                .patient(patient)
                .description(reviewRequest.getDescription())
                .build();
        reviewRepository.save(review);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Review Created Successfully!!")
                .build();
    }

    @Override
    public ApiResponseMessage deleteReview(Long patientId, Long reviewId) {

        PatientEntity patient = userRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Patient Not Found!!")
                    .build();
        }

        ReviewEntity review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null && Objects.equals(review.getPatient().getId(), patient.getId())) {
            reviewRepository.delete(review);
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Review Deleted Successfully!!")
                    .build();
        }
        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("Review Not Found!!")
                .build();


    }
}
