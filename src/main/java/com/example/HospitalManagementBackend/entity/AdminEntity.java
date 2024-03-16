package com.example.HospitalManagementBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "admins")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    private String name;
    private String contactNumber;

    @Column(unique = true)
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "admin")
    private HospitalEntity hospital;

}
