package com.example.HospitalManagementBackend.service.login;


import com.example.HospitalManagementBackend.entity.AdminEntity;
import com.example.HospitalManagementBackend.entity.DoctorEntity;
import com.example.HospitalManagementBackend.entity.PatientEntity;
import com.example.HospitalManagementBackend.repository.AdminRepository;
import com.example.HospitalManagementBackend.repository.DoctorRepository;
import com.example.HospitalManagementBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        DoctorEntity doctor = doctorRepository.findByEmail(username).orElse(null);
        if (doctor != null) {
            return new CustomUserDetails(doctor.getEmail(), doctor.getPassword(), AuthorityUtils.createAuthorityList("DOCTOR_ROLE"));
        }

        PatientEntity patient = userRepository.findByEmail(username).orElse(null);
        if (patient != null) {
            return new CustomUserDetails(patient.getEmail(), patient.getPassword(), AuthorityUtils.createAuthorityList("PATIENT_ROLE"));
        }

        AdminEntity admin = adminRepository.findByEmail(username).orElse(null);
        if (admin != null) {
            return new CustomUserDetails(admin.getEmail(), admin.getPassword(), AuthorityUtils.createAuthorityList("ADMIN_ROLE"));
        }

        throw new UsernameNotFoundException("User Not Found With Username: " + username);
    }
}
