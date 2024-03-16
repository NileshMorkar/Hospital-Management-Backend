package com.example.HospitalManagementBackend.service;

import com.example.HospitalManagementBackend.exception.GlobalException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryImageService {
    public Map upload(MultipartFile multipartFile) throws GlobalException;
}
