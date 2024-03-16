package com.example.HospitalManagementBackend.service.impl;

import com.example.HospitalManagementBackend.entity.FolderEntity;
import com.example.HospitalManagementBackend.entity.PatientEntity;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.response.FolderResponse;
import com.example.HospitalManagementBackend.repository.FolderRepository;
import com.example.HospitalManagementBackend.repository.ImageRepository;
import com.example.HospitalManagementBackend.repository.UserRepository;
import com.example.HospitalManagementBackend.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void delete(String userEmail, Long folderId) throws GlobalException {
        List<FolderEntity> folderEntityList = folderRepository.findAllByPatientEmail(userEmail);

        Optional<FolderEntity> folderEntity = folderEntityList.stream().filter(folder -> folder.getId().equals(folderId)).findFirst();

        if (folderEntity.isEmpty()) {
            throw new GlobalException("Folder Not Found", HttpStatus.NOT_FOUND);
        }
        imageRepository.deleteAllImagesByFolderId(folderId);
        folderRepository.deleteById(folderId);
    }

    @Override
    public List<FolderResponse> getAllFolders(String userEmail) {

        List<FolderEntity> folderEntityList = folderRepository.findAllByPatientEmail(userEmail);
        return folderEntityList.stream().map(folderEntity -> FolderResponse
                .builder()
                .date(folderEntity.getDate())
                .id(folderEntity.getId())
                .name(folderEntity.getName())
                .build()).collect(Collectors.toList());
    }
}
