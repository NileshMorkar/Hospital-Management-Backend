package com.example.HospitalManagementBackend.service;


import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.AdminRequest;
import com.example.HospitalManagementBackend.model.response.AdminResponse;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;

public interface AdminService {
    AdminResponse createNewAdmin(AdminRequest adminRequest) throws GlobalException;

    ApiResponseMessage updateAdminNamePassword(AdminRequest adminRequest);

}
