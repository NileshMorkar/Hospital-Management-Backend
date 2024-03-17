package com.example.HospitalManagementBackend.controller;

import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.ImageRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.FolderResponse;
import com.example.HospitalManagementBackend.model.response.ImageResponse;
import com.example.HospitalManagementBackend.repository.UserRepository;
import com.example.HospitalManagementBackend.service.CloudinaryImageService;
import com.example.HospitalManagementBackend.service.FolderService;
import com.example.HospitalManagementBackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/folder")
@CrossOrigin("*")
public class FolderController {
    @Autowired
    private CloudinaryImageService cloudinaryImageService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FolderService folderService;

    @PostMapping("/{userEmail}/create/{folderName}")
    public ResponseEntity<ApiResponseMessage> createFolder(@PathVariable String userEmail, @PathVariable String folderName) throws GlobalException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

//        PatientEntity patientEntity = userRepository.findById(userId).orElseThrow(() -> new GlobalException("User Not Found!!",HttpStatus.NOT_FOUND));
        folderService.create(userEmail, folderName);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("Folder Created Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);
    }


    @PostMapping("/{userEmail}/{folderId}/create-image")
    ResponseEntity<ApiResponseMessage> uploadFile(@PathVariable String userEmail, @PathVariable Long folderId, @RequestParam(name = "image") MultipartFile multipartFile) throws GlobalException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        Map map = cloudinaryImageService.upload(multipartFile);
        String imageLink = map.get("secure_url").toString();
        ImageRequest imageRequest = ImageRequest
                .builder()
                .email(userEmail)
                .folderId(folderId)
                .link(imageLink)
                .build();

        imageService.save(imageRequest);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("Image Upload Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);
    }


    @GetMapping("/{userEmail}/{folderId}/get-images")
    public List<ImageResponse> getAllImages(@PathVariable String userEmail, @PathVariable Long folderId) throws GlobalException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();
        return imageService.getAllImages(userEmail, folderId);

    }

    @DeleteMapping("/{userEmail}/image/{imageId}")
    public ResponseEntity<ApiResponseMessage> deleteImageById(@PathVariable String userEmail, @PathVariable Long imageId) throws GlobalException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        imageService.delete(userEmail, imageId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("Image Deleted Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);

    }

    @DeleteMapping("/{userEmail}/{folderId}")
    public ResponseEntity<ApiResponseMessage> deleteFolderById(@PathVariable String userEmail, @PathVariable Long folderId) throws GlobalException {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();

        folderService.delete(userEmail, folderId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("Folder Deleted Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);
    }

    @GetMapping("/{userEmail}/get-all")
    public ResponseEntity<List<FolderResponse>> getAllFolders(@PathVariable String userEmail) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();
        List<FolderResponse> folderResponseList = folderService.getAllFolders(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(folderResponseList);
    }

}
