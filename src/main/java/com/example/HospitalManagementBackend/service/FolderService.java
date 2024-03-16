package com.example.HospitalManagementBackend.service;

import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.response.FolderResponse;

import java.util.List;

public interface FolderService {

    void create(String userEmail, String folderName);

    void delete(String userEmail, Long folderId) throws GlobalException;

    List<FolderResponse> getAllFolders(String userEmail);
}
