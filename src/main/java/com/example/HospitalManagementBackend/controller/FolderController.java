package com.example.HospitalManagementBackend.controller;

import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.request.ImageRequest;
import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import com.example.HospitalManagementBackend.model.response.ImageResponse;
import com.example.HospitalManagementBackend.repository.UserRepository;
import com.example.HospitalManagementBackend.service.CloudinaryImageService;
import com.example.HospitalManagementBackend.service.FolderService;
import com.example.HospitalManagementBackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/folder")
public class FolderController {
    @Autowired
    private CloudinaryImageService cloudinaryImageService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FolderService folderService;

    @PostMapping("/create/{folderName}")
    public ResponseEntity<ApiResponseMessage> createFolder(@PathVariable String folderName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        folderService.create(userEmail, folderName);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("Folder Created Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);
    }


    @PostMapping("/{folderId}/create-image")
    ResponseEntity<ApiResponseMessage> uploadFile(@PathVariable Long folderId, @RequestParam(name = "image") MultipartFile multipartFile) throws GlobalException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

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


    @GetMapping("/{folderId}/get-images")
    public List<ImageResponse> getAllImages(@PathVariable Long folderId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();
//        PatientEntity patient = userRepository.findByEmail(userEmail).orElse(null);

//        assert patient != null;
        return imageService.getAllImages(folderId);

    }

    @DeleteMapping("/image/{imageId}")
    public ResponseEntity<ApiResponseMessage> deleteImageById(@PathVariable Long imageId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();
//        PatientEntity patient = userRepository.findByEmail(userEmail).orElse(null);

//        assert patient != null;
        imageService.delete(imageId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("Image Deleted Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);

    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<ApiResponseMessage> deleteFolderById(@PathVariable Long folderId) {

        folderService.delete(folderId);

        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("Folder Deleted Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseMessage);


    }

}
