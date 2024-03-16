package com.example.HospitalManagementBackend.service;

import com.example.HospitalManagementBackend.model.request.ImageRequest;
import com.example.HospitalManagementBackend.model.response.ImageResponse;

import java.util.List;

public interface ImageService {
    void save(ImageRequest imageRequest);

    void delete(Long imageId);

    List<ImageResponse> getAllImages(Long folderId);

}
