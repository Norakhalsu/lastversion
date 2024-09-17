package com.example.demo.Controller;


import com.example.demo.Api.ApiResponse;
import com.example.demo.DTO.DoctorDTO;
import com.example.demo.Model.Appointment;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.User;
import com.example.demo.Service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/registration/{hospitalId}")// ALL
    public ResponseEntity creatDoctor(@PathVariable Integer hospitalId,@RequestBody @Valid DoctorDTO doctorDTO ){
        doctorService.addDoctor( hospitalId,doctorDTO);
        return new ResponseEntity("Doctor registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-all")// ADMIN
    public ResponseEntity getDoctors(){
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }


    @PutMapping("/update")//Doctor
    public ResponseEntity updatePatient(@AuthenticationPrincipal User user, @RequestBody @Valid Doctor doctor){
        doctorService.updateDoctor(user.getId(),doctor);
        return new ResponseEntity("Doctor updated successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{doctorId}") //ADMIN
    public ResponseEntity deletePatient(@PathVariable Integer doctorId){
        doctorService.deleteDoctor(doctorId);
        return new ResponseEntity("Doctor deleted successfully", HttpStatus.OK);
    }
   // --------------------------- End Point ---------------


    @GetMapping("/appointments-count")// DOCTOR
    public ResponseEntity doctorAppointmentsCount(@AuthenticationPrincipal User user) {
       return ResponseEntity.status(200).body(doctorService.countDoctorAppointments(user.getId()));
    }

    @GetMapping("/all-patient")//DOCTOR
    public ResponseEntity getPatientAppointments(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(doctorService.allPatientsWithAppointments(user.getId()));
    }
    @GetMapping("/show-doctor/{doctorId}")//HOSPITAL
    public ResponseEntity getDoctor(@AuthenticationPrincipal User user ,@PathVariable Integer doctorId) {
        return ResponseEntity.status(200).body(doctorService.getDoctorById(user.getId(),doctorId));
    }

    @GetMapping("/major/{major}")//PATIENT
    public ResponseEntity searchDoctorsByMajor(@AuthenticationPrincipal User user,@PathVariable String major) {
       return ResponseEntity.status(200).body(doctorService.searchDoctorsByMajor(user.getId(),major));
    }

    @PostMapping("/add/{doctorId}/appointment")//PATIENT
    public ResponseEntity addAppointmentToDoctor(@AuthenticationPrincipal User user,@PathVariable Integer doctorId, @RequestBody @Valid Set<Appointment> appointment) {
        doctorService.addAppointmentToDoctor(user.getId(),doctorId,appointment);
        return ResponseEntity.status(200).body("Appointment added successfully");
    }

        @PutMapping("/update-degree")//DOCTOR
       public ResponseEntity updateDoctorDegree(@AuthenticationPrincipal User user, @RequestBody @Valid String degree) {
            doctorService.updateDegree(user.getId(),degree);
         return ResponseEntity.status(200).body("Doctor's Degree updated successfully");
    }

}
