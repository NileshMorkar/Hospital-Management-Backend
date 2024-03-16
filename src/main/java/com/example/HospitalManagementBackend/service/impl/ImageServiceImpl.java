package com.example.HospitalManagementBackend.service.impl;

import com.example.HospitalManagementBackend.entity.FolderEntity;
import com.example.HospitalManagementBackend.entity.ImageEntity;
import com.example.HospitalManagementBackend.entity.PatientEntity;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.ImageRequest;
import com.example.HospitalManagementBackend.model.response.ImageResponse;
import com.example.HospitalManagementBackend.repository.FolderRepository;
import com.example.HospitalManagementBackend.repository.ImageRepository;
import com.example.HospitalManagementBackend.repository.UserRepository;
import com.example.HospitalManagementBackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public void delete(String userEmail, Long imageId) throws GlobalException {

        ImageEntity imageEntity = imageRepository.findById(imageId).orElseThrow(() -> new GlobalException("Image Not Found!", HttpStatus.NOT_FOUND));

        List<FolderEntity> folderEntityList = folderRepository.findAllByPatientEmail(userEmail);

        Optional<FolderEntity> folderEntity = folderEntityList.stream().filter(folder -> folder.getId().equals(imageEntity.getFolderEntity().getId())).findFirst();

        if (folderEntity.isEmpty()) {
            throw new GlobalException("Image Not Found", HttpStatus.NOT_FOUND);
        }
        imageRepository.deleteById(imageId);
    }

    @Override
    public List<ImageResponse> getAllImages(String userEmail, Long folderId) throws GlobalException {

        List<FolderEntity> folderEntityList = folderRepository.findAllByPatientEmail(userEmail);

        Optional<FolderEntity> folderEntity = folderEntityList.stream().filter(folder -> folder.getId().equals(folderId)).findFirst();

        if (folderEntity.isEmpty()) {
            throw new GlobalException("Folder Not Found", HttpStatus.NOT_FOUND);
        }

        List<ImageEntity> images = imageRepository.findAllImagesByUserId(folderId);

        return images.stream().map(imageEntity -> ImageResponse
                .builder()
                .imageId(imageEntity.getId())
                .link(imageEntity.getLink())
                .date(imageEntity.getDate())
                .build()).collect(Collectors.toList());
    }
}
