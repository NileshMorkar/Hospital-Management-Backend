package com.example.HospitalManagementBackend.exception;

import com.example.HospitalManagementBackend.model.response.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiResponseMessage> handleGlobalException(GlobalException exception) {

        ApiResponseMessage errorResponse = ApiResponseMessage
                .builder()
                .httpStatus(exception.getHttpStatus())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handelMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(errorObject -> {
            String fieldName = ((FieldError) errorObject).getField();
            String errorMessage = errorObject.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

}
