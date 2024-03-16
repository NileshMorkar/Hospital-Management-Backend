package com.example.HospitalManagementBackend.service;

import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.ImageRequest;
import com.example.HospitalManagementBackend.model.response.ImageResponse;

import java.util.List;

public interface ImageService {
    void save(ImageRequest imageRequest);

    void delete(String userEmail, Long imageId) throws GlobalException;

    List<ImageResponse> getAllImages(String userEmail, Long folderId) throws GlobalException;

}
