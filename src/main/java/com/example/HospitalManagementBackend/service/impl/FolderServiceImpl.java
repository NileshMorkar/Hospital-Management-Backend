package com.example.HospitalManagementBackend.service.impl;

import com.example.HospitalManagementBackend.entity.FolderEntity;
import com.example.HospitalManagementBackend.entity.PatientEntity;
import com.example.HospitalManagementBackend.repository.FolderRepository;
import com.example.HospitalManagementBackend.repository.ImageRepository;
import com.example.HospitalManagementBackend.repository.UserRepository;
import com.example.HospitalManagementBackend.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FolderServiceImpl implements FolderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    FolderRepository folderRepository;

    @Override
    public void create(String userEmail, String folderName) {

        PatientEntity patient = userRepository.findByEmail(userEmail).orElse(null);

        FolderEntity folderEntity = FolderEntity
                .builder()
                .name(folderName)
                .date(new Date())
                .patient(patient)
                .build();

        folderRepository.save(folderEntity);

    }

    @Override
    public void delete(Long folderId) {
        imageRepository.deleteAllImagesByFolderId(folderId);
        folderRepository.deleteById(folderId);
    }
}
