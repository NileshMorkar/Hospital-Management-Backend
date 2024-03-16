package com.example.HospitalManagementBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hospitals")
public class HospitalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalId;

    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    @Column(unique = true)
    private String email;

    private Integer capacity;
    private Date establishedDate;
    private String contactNumber;

    private String description;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DoctorEntity> doctors;

}
