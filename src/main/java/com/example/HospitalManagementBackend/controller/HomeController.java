package com.example.HospitalManagementBackend.controller;

import com.example.HospitalManagementBackend.exception.GlobalException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class HomeController {


    @RequestMapping("/")
    public void homePage(HttpServletResponse httpServletResponse) throws GlobalException {
        try {
            httpServletResponse.sendRedirect("/swagger-ui/index.html");
        } catch (Exception exception) {
            throw new GlobalException("Page Not Found Exception!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK !!!";
    }

}
