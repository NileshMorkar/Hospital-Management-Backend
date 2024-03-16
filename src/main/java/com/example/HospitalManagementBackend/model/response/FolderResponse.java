package com.example.HospitalManagementBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FolderResponse {
    private Long id;
    private String name;
    private Date date;
}
