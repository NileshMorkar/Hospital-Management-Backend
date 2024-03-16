package com.example.HospitalManagementBackend.service.impl;

import com.cloudinary.Cloudinary;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.service.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile multipartFile) throws GlobalException {

        try {
            Map map = cloudinary.uploader().upload(multipartFile.getBytes(), Map.of());
            return map;
        } catch (IOException e) {
            throw new GlobalException("Image Uploading Fail!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
