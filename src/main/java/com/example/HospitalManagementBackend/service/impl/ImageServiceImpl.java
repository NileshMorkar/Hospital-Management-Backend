package com.example.HospitalManagementBackend.service.impl;

import com.example.HospitalManagementBackend.entity.FolderEntity;
import com.example.HospitalManagementBackend.entity.ImageEntity;
import com.example.HospitalManagementBackend.entity.PatientEntity;
import com.example.HospitalManagementBackend.model.request.ImageRequest;
import com.example.HospitalManagementBackend.model.response.ImageResponse;
import com.example.HospitalManagementBackend.repository.FolderRepository;
import com.example.HospitalManagementBackend.repository.ImageRepository;
import com.example.HospitalManagementBackend.repository.UserRepository;
import com.example.HospitalManagementBackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    FolderRepository folderRepository;

    @Override
    public void save(ImageRequest imageRequest) {
        PatientEntity patientEntity = userRepository.findByEmail(imageRequest.getEmail()).orElse(null);
        FolderEntity folderEntity = folderRepository.findById(imageRequest.getFolderId()).orElse(null);

        ImageEntity imageEntity = ImageEntity.builder()
                .link(imageRequest.getLink())
                .folderEntity(folderEntity)
                .date(new Date())
                .build();
        imageRepository.save(imageEntity);
    }

    @Override
    public void delete(Long imageId) {
        imageRepository.deleteById(imageId);
    }

    @Override
    public List<ImageResponse> getAllImages(Long folderId) {
        List<ImageEntity> images = imageRepository.findAllImagesByUserId(folderId);

        return images.stream().map(imageEntity -> ImageResponse
                .builder()
                .imageId(imageEntity.getId())
                .link(imageEntity.getLink())
                .date(imageEntity.getDate())
                .build()).collect(Collectors.toList());
    }
}
