package com.example.HospitalManagementBackend.service.impl;


import com.example.HospitalManagementBackend.entity.AdminEntity;
import com.example.HospitalManagementBackend.entity.DoctorEntity;
import com.example.HospitalManagementBackend.entity.HospitalEntity;
import com.example.HospitalManagementBackend.entity.ReviewEntity;
import com.example.HospitalManagementBackend.helper.Helper;
import com.example.HospitalManagementBackend.model.request.HospitalRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.DoctorResponse;
import com.example.HospitalManagementBackend.model.response.HospitalResponse;
import com.example.HospitalManagementBackend.model.response.ReviewResponse;
import com.example.HospitalManagementBackend.repository.AdminRepository;
import com.example.HospitalManagementBackend.repository.HospitalRepository;
import com.example.HospitalManagementBackend.repository.ReviewRepository;
import com.example.HospitalManagementBackend.service.HospitalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponseMessage createNewHospital(Long adminId, HospitalRequest hospitalRequest) {

        HospitalEntity hospital = hospitalRepository.findByEmail(hospitalRequest.getEmail()).orElse(null);

        AdminEntity admin = adminRepository.findById(adminId).orElse(null);

        if (hospital != null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message("Hospital Is Already Present!!")
                    .build();
        }
        if (Objects.requireNonNull(admin).getHospital() != null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message("Admin Can Create Only One Hospital!")
                    .build();
        }
        hospital = Helper.getHospitalEntityFromHospitalRequest(hospitalRequest, admin);
        hospitalRepository.save(hospital);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Hospital Created Successfully!!")
                .build();

    }

    @Override
    public ApiResponseMessage updateHospitalInfo(HospitalRequest hospitalRequest) {

        HospitalEntity hospital = hospitalRepository.findByEmail(hospitalRequest.getEmail()).orElse(null);

        if (hospital == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.CREATED)
                    .message("Hospital Not Found!!")
                    .build();
        }
        hospital.setAddress(hospitalRequest.getAddress());
        hospital.setCapacity(hospitalRequest.getCapacity());
        hospital.setName(hospitalRequest.getName());
        hospital.setCity(hospitalRequest.getCity());
        hospital.setState(hospitalRequest.getState());
        hospital.setZipCode(hospitalRequest.getZipCode());
        hospital.setContactNumber(hospitalRequest.getContactNumber());
        hospital.setDescription(hospitalRequest.getDescription());
        hospital.setEstablishedDate(hospitalRequest.getEstablishedDate());

        hospitalRepository.save(hospital);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Hospital Updated Successfully!!")
                .build();

    }

    @Override
    public List<HospitalResponse> getAllHospitals() {

        List<HospitalEntity> hospitalEntityList = hospitalRepository.findAll();

        return hospitalEntityList.stream().map(Helper::getHospitalResponseFromHospitalEntity).collect(Collectors.toList());
    }

    @Override
    public List<DoctorResponse> getAllDoctors(Long hospitalId) {

        HospitalEntity hospital = hospitalRepository.findById(hospitalId).orElse(null);
        if (hospital == null) {
            return new ArrayList<>();
        }

        List<DoctorEntity> doctorEntityList = hospital.getDoctors();

        return doctorEntityList.stream().map(Helper::getDoctorResponseFromDoctorEntity).collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getAllReviews(Long hospitalId) {
        HospitalEntity hospital = hospitalRepository.findById(hospitalId).orElse(null);
        if (hospital == null) {
            return new ArrayList<>();
        }

        List<ReviewEntity> reviewEntityList = reviewRepository.findByHospitalId(hospitalId);

        return reviewEntityList.stream().map(Helper::getReviewResponseFromReviewEntity).collect(Collectors.toList());
    }

    @Override
    public List<HospitalResponse> searchHospitalByAddress(String address) {
        List<HospitalEntity> hospitalEntityList = hospitalRepository.searchByAddress(address);

        return hospitalEntityList.stream().map(Helper::getHospitalResponseFromHospitalEntity).collect(Collectors.toList());
    }
}
