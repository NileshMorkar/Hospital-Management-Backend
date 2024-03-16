package com.example.HospitalManagementBackend.service;

public interface FolderService {

    void create(String userEmail, String folderName);

    void delete(Long folderId);
}
