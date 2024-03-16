package com.example.HospitalManagementBackend.helper;


import com.example.HospitalManagementBackend.entity.*;
import com.example.HospitalManagementBackend.model.request.HospitalRequest;
import com.example.HospitalManagementBackend.model.response.*;

import java.util.ArrayList;

public class Helper {

    public static HospitalEntity getHospitalEntityFromHospitalRequest(HospitalRequest hospitalRequest, AdminEntity admin) {
        return HospitalEntity
                .builder()
                .admin(admin)
                .email(hospitalRequest.getEmail())
                .name(hospitalRequest.getName())
                .city(hospitalRequest.getCity())
                .zipCode(hospitalRequest.getZipCode())
                .capacity(hospitalRequest.getCapacity())
                .establishedDate(hospitalRequest.getEstablishedDate())
                .contactNumber(hospitalRequest.getContactNumber())
                .state(hospitalRequest.getState())
                .doctors(new ArrayList<>())
                .description(hospitalRequest.getDescription())
                .address(hospitalRequest.getAddress())
                .build();
    }

    public static HospitalResponse getHospitalResponseFromHospitalEntity(HospitalEntity hospital) {
        return HospitalResponse
                .builder()
                .hospitalId(hospital.getHospitalId())
                .contactNumber(hospital.getContactNumber())
                .state(hospital.getState())
                .email(hospital.getEmail())
                .name(hospital.getName())
                .city(hospital.getCity())
                .zipCode(hospital.getZipCode())
                .capacity(hospital.getCapacity())
                .establishedDate(hospital.getEstablishedDate())
                .doctorsCount(hospital.getDoctors().size())
                .description(hospital.getDescription())
                .address(hospital.getAddress())
                .build();
    }

    public static DoctorResponse getDoctorResponseFromDoctorEntity(DoctorEntity doctor) {

        HospitalResponse hospitalResponse = getHospitalResponseFromHospitalEntity(doctor.getHospital());
        return DoctorResponse
                .builder()
                .doctorId(doctor.getDoctorId())
                .specialization(doctor.getSpecialization())
                .contactNumber(doctor.getContactNumber())
                .qualification(doctor.getQualification())
                .email(doctor.getEmail())
                .name(doctor.getName())
                .description(doctor.getDescription())
                .experience(doctor.getExperience())
                .hospital(hospitalResponse)
                .build();
    }

    public static ReviewResponse getReviewResponseFromReviewEntity(ReviewEntity review) {

        PatientResponse patientResponse = getPatientResponseFromPatientEntity(review.getPatient());
        return ReviewResponse
                .builder()
                .reviewId(review.getReviewId())
                .patient(patientResponse)
                .star(review.getStar())
                .description(review.getDescription())
                .build();
    }

    public static PatientResponse getPatientResponseFromPatientEntity(PatientEntity patient) {

        return PatientResponse
                .builder()
                .patientId(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
//                .contactNumber(patient.getContactNumber())
//                .address(patient.getAddress())
                .build();
    }

    public static AppointmentResponse getAppointmentResponseFromAppointmentEntity(AppointmentEntity appointment) {

        DoctorEntity doctor = appointment.getDoctor();
        HospitalEntity hospital = doctor.getHospital();
        PatientEntity patient = appointment.getPatient();

        HospitalResponse hospitalResponse = getHospitalResponseFromHospitalEntity(hospital);
        DoctorResponse doctorResponse = getDoctorResponseFromDoctorEntity(doctor);
        PatientResponse patientResponse = getPatientResponseFromPatientEntity(patient);

        return AppointmentResponse
                .builder()
                .patient(patientResponse)
                .appointmentId(appointment.getAppointmentId())
//                .appointmentTime(appointment.getAppointmentTime())
                .appointmentDate(appointment.getAppointmentDate())
                .description(appointment.getDescription())
                .doctor(doctorResponse)
                .status(appointment.getStatus())
                .build();
    }

}
