package com.example.HospitalManagementBackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageRequest {
    Long folderId;
    String email;
    private String link;
}
