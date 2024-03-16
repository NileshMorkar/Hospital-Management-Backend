package com.example.HospitalManagementBackend.controller;

import com.example.HospitalManagementBackend.exception.GlobalException;
import com.example.HospitalManagementBackend.model.jwt.JwtRequest;
import com.example.HospitalManagementBackend.model.jwt.JwtResponse;
import com.example.HospitalManagementBackend.model.response.UserResponse;
import com.example.HospitalManagementBackend.security.JwtHelper;
import com.example.HospitalManagementBackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper helper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws GlobalException {
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = helper.generateToken(userDetails);
        UserResponse userResponse = modelMapper.map(userDetails, UserResponse.class);
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .user(userResponse).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) throws GlobalException {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new GlobalException("Invalid Username And Password !!", HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/user")
    public ResponseEntity<Map.Entry<String, String>> getCurrentUserName(Principal principal) {
        Map.Entry<String, String> m = Map.entry("User", principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(m);
    }

    @GetMapping
    public ResponseEntity<UserDetails> getCurrentUser(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsService.loadUserByUsername(principal.getName()));
    }


    //Exception Handling
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }


}
