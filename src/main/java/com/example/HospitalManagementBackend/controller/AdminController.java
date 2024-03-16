package com.example.HospitalManagementBackend.controller;

import com.example.HospitalManagementBackend.entity.AdminEntity;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.AdminRequest;
import com.example.HospitalManagementBackend.model.request.HospitalRequest;
import com.example.HospitalManagementBackend.model.response.AdminResponse;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.repository.AdminRepository;
import com.example.HospitalManagementBackend.service.AdminService;
import com.example.HospitalManagementBackend.service.CloudinaryImageService;
import com.example.HospitalManagementBackend.service.HospitalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    @PostMapping("/create-admin")
    public ResponseEntity<AdminResponse> createNewPatient(@RequestBody AdminRequest adminRequest) throws GlobalException {
        AdminResponse adminResponse = adminService.createNewAdmin(adminRequest);
        return ResponseEntity.status(HttpStatus.OK).body(adminResponse);
    }

    @PutMapping("/update-admin")
    public ResponseEntity<ApiResponseMessage> updatePatientNamePassword(@RequestBody AdminRequest adminRequest) {
        ApiResponseMessage responseMessage = adminService.updateAdminNamePassword(adminRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/create-hospital")
    public ResponseEntity<ApiResponseMessage> createNewHospital(@RequestBody HospitalRequest hospitalRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        ApiResponseMessage responseMessage = hospitalService.createNewHospital(userEmail, hospitalRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/update-hospital")
    public ResponseEntity<ApiResponseMessage> updateHospitalInfo(@RequestBody HospitalRequest hospitalRequest) {
        ApiResponseMessage responseMessage = hospitalService.updateHospitalInfo(hospitalRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }


    @PostMapping("/upload-profile-image")
    public ResponseEntity<AdminResponse> uploadProfileImage(@RequestParam(name = "image") MultipartFile multipartFile) throws GlobalException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        Map map = cloudinaryImageService.upload(multipartFile);
        String imageLink = map.get("secure_url").toString();

        AdminEntity adminEntity = adminRepository.findByEmail(userEmail).orElse(null);
        assert adminEntity != null;
        adminEntity.setImage(imageLink);
        adminRepository.save(adminEntity);

        AdminResponse adminResponse = modelMapper.map(adminEntity, AdminResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(adminResponse);
    }
}
