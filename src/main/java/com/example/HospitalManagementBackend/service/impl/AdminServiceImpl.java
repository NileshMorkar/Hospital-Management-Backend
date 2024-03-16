package com.example.HospitalManagementBackend.service.impl;


import com.example.HospitalManagementBackend.entity.AdminEntity;
import com.example.HospitalManagementBackend.entity.RoleEntity;
import com.example.HospitalManagementBackend.enums.RoleName;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.AdminRequest;
import com.example.HospitalManagementBackend.model.response.AdminResponse;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.repository.AdminRepository;
import com.example.HospitalManagementBackend.repository.RoleRepository;
import com.example.HospitalManagementBackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminResponse createNewAdmin(AdminRequest adminRequest) throws GlobalException {

        AdminEntity admin = adminRepository.findByEmail(adminRequest.getEmail()).orElse(null);


        if (admin != null) {
            throw new GlobalException("Admin Is Already Present!!", HttpStatus.OK);
        }

        //Set Password By Using Encode
        adminRequest.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        RoleEntity roleEntity = roleRepository.findByRoleName(RoleName.ADMIN_ROLE.name()).orElse(null);
        admin = AdminEntity
                .builder()
                .email(adminRequest.getEmail())
                .name(adminRequest.getName())
                .contactNumber(adminRequest.getContactNumber())
                .password(adminRequest.getPassword())
                .role(roleEntity)
                .build();

        admin = adminRepository.save(admin);

        return AdminResponse
                .builder()
                .adminId(admin.getAdminId())
                .name(admin.getName())
                .email(admin.getEmail())
                .role(RoleName.ADMIN_ROLE.name())
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
