package com.example.HospitalManagementBackend.service.impl;


import com.example.HospitalManagementBackend.entity.PatientEntity;
import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PatientEntity userEntity = null;

//        PatientEntity user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

//        if (user == null) {
//            AnotherUser anotherUser = anotherUserRepository.findByUsername(username);
//            if (anotherUser == null) {
//                throw new UsernameNotFoundException("User not found with username: " + username);
//            }
//            return anotherUser;
//        }

        try {
            userEntity = userRepository.findByEmail(username).orElseThrow(() -> new GlobalException("User Not Found!!", HttpStatus.NOT_FOUND));
        } catch (GlobalException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        return userEntity;
    }
}
