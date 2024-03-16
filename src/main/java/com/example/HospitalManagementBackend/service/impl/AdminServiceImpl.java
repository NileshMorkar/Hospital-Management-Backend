package com.example.HospitalManagementBackend.service.impl;


import com.example.HospitalManagementBackend.entity.AdminEntity;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.AdminRequest;
import com.example.HospitalManagementBackend.model.response.AdminResponse;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.repository.AdminRepository;
import com.example.HospitalManagementBackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminResponse createNewAdmin(AdminRequest adminRequest) throws GlobalException {

        AdminEntity admin = adminRepository.findByEmail(adminRequest.getEmail()).orElse(null);

        if (admin != null) {
            throw new GlobalException("Admin Is Already Present!!", HttpStatus.OK);
        }

        admin = AdminEntity
                .builder()
                .email(adminRequest.getEmail())
                .name(adminRequest.getName())
                .contactNumber(adminRequest.getContactNumber())
                .password(adminRequest.getPassword())
                .build();

        admin = adminRepository.save(admin);

        return AdminResponse
                .builder()
                .adminId(admin.getAdminId())
                .name(admin.getName())
                .email(admin.getEmail())
                .build();
    }

    @Override
    public ApiResponseMessage updateAdminNamePassword(AdminRequest adminRequest) {
        AdminEntity admin = adminRepository.findByEmail(adminRequest.getEmail()).orElse(null);
        assert admin != null;
        admin.setName(adminRequest.getName());
        admin.setPassword(adminRequest.getPassword());
        admin.setContactNumber(adminRequest.getContactNumber());

        adminRepository.save(admin);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Name Password Updated Successfully!!")
                .build();
    }
}
