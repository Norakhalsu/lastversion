package com.example.demo.Controller;


import com.example.demo.Api.ApiResponse;
import com.example.demo.DTO.PatientDTO;
import com.example.demo.Model.Patient;
import com.example.demo.Model.User;
import com.example.demo.Service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientService PatientService;
    private final PatientService patientService;


    @PostMapping("/registration/{hospitalId}")// ALL
    public ResponseEntity creatPatient(@PathVariable Integer hospitalId, @RequestBody @Valid PatientDTO patientDTO) {
        PatientService.addPatientToHospital(hospitalId, patientDTO);
        return ResponseEntity.status(200).body("Patient Registered successfully");
    }

    @GetMapping("/get-all")//ADMIN
    public ResponseEntity getPatient() {
        return ResponseEntity.ok(PatientService.getAllPatient());
    }


    @PutMapping("/update")// Patient تحديث البيانات الشخصيه
    public ResponseEntity updatePatient(@AuthenticationPrincipal User pateintUser, @RequestBody @Valid Patient patient) {
        PatientService.updatePatient(pateintUser.getId(), patient);
        return ResponseEntity.status(200).body(new ApiResponse("Patient Data updated successfully"));
    }

    @DeleteMapping("/delete/{patientId}") //ADMIN
    public ResponseEntity deletePatient(@PathVariable Integer patientId) {
        PatientService.deletePatient(patientId);
        return ResponseEntity.ok(new ApiResponse("Patient deleted successfully"));
    }

    // ----------------- End Point -------------------

    @GetMapping("/all-patients-city/{city}")//ADMIN
    public ResponseEntity getAllPatientsByCity(@PathVariable String city) {
        return ResponseEntity.status(200).body(patientService.allPatientsInCity(city));
    }

    @GetMapping("/emergency-Percentage/{city}")//ADMIN
    public ResponseEntity getEmergencyPatientsPercentageInCity(@PathVariable String city) {
        return ResponseEntity.status(200).body(patientService.getEmergencyPatientsPercentageInCity(city));
    }

    @GetMapping("/patients-In-Hospital/{hospitalName}")//ADMIN
    public ResponseEntity findPatientsInHospital(@PathVariable String hospitalName) {
          return ResponseEntity.status(200).body(patientService.findPatientsInHospital(hospitalName));
    }

    @PostMapping("/rateDoctor/{requestId}/{doctorId}/{rating}")//PATIENT
    public ResponseEntity ratingToDoctor(@AuthenticationPrincipal User user,@PathVariable Integer requestId, @PathVariable Integer doctorId, @PathVariable int rating) {
         patientService.ratingToDoctor(user.getId(), requestId,doctorId,rating);
         return ResponseEntity.status(200).body(new ApiResponse("add Doctor's Rating successfully"));
    }

}