package com.example.HospitalManagementBackend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Cloudinary getCloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", "dgicg7q0u",
                        "api_key", "529389334125279",
                        "api_secret", "alIrB0fo_sdmnKPphyssju1jVf0",
                        "secure", true
                )
        );
    }
}
