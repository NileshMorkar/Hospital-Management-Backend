package com.example.HospitalManagementBackend.repository;

import com.example.HospitalManagementBackend.entity.ImageEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM image_entity WHERE folder_entity_id = ?1 ORDER BY date")
    List<ImageEntity> findAllImagesByUserId(Long folderId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete FROM image_entity WHERE folder_entity_id = ?1")
    void deleteAllImagesByFolderId(Long folderId);

}
