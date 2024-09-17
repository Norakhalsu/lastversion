package com.example.demo.Controller;


import com.example.demo.Api.ApiResponse;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.HealthRecord;
import com.example.demo.Model.User;
import com.example.demo.Service.HealthRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/healthRecord")
public class HealthRecordController {

     private final HealthRecordService healthRecordService;


    @PostMapping("/add/to/patient/{patientId}")//HOSPITAL
    public ResponseEntity addMedicalHistory(@AuthenticationPrincipal User user,@PathVariable Integer patientId, @RequestBody @Valid HealthRecord healthRecord){
        healthRecordService.addHealthRecord(user.getId(), patientId, healthRecord);
        return ResponseEntity.ok(new ApiResponse("add Patient's Health Record successfully"));
    }

    @GetMapping("/get-all") //ADMIN
    public ResponseEntity allHealthRecord(){
        return ResponseEntity.status(200).body(healthRecordService.getAllHealthRecords());
    }

    @PutMapping("/update/{recordId}") //HOSPITAL
    public ResponseEntity updateMedicalHistory(@AuthenticationPrincipal User user,@PathVariable Integer recordId,@RequestBody @Valid HealthRecord healthRecord){
        healthRecordService.updateHealthRecord(user.getId(),recordId,healthRecord);
        return ResponseEntity.status(200).body(new ApiResponse("update Patient's Health Record Successful"));
    }

    @DeleteMapping("/delete/{healthRecordId}") //HOSPITAL
    public ResponseEntity deleteHealthRecord(@AuthenticationPrincipal User user,@PathVariable Integer healthRecordId){
        healthRecordService.deleteHealthRecord(user.getId(), healthRecordId);
        return ResponseEntity.ok(new ApiResponse("Health Record deleted"));
    }


    // ----------------------- end point -----------------

    @GetMapping("/get-Health-Record/{healthRecordId}") // PATIENT
    public ResponseEntity healthRecordByPatientId(@AuthenticationPrincipal User user,@PathVariable Integer healthRecordId){
        return ResponseEntity.status(200).body(healthRecordService.getHealthRecordByPatientId(user.getId(),healthRecordId));
    }


    @PutMapping("/new-healthHabits")//PATIENT
    public ResponseEntity updatePatientHealthHabits(@AuthenticationPrincipal User user, @Valid @RequestBody List<String> newHealthHabits) {
        healthRecordService.addNewHabits(user.getId(), newHealthHabits);
        return ResponseEntity.status(200).body("update Patient's Health Habits successfully");
    }


    @PutMapping("/new-pastIllness")// PATIENT -- وين ال healthreorcd id
    public ResponseEntity addNewPastIllness(@AuthenticationPrincipal User use , List<String> pastIllness){
        healthRecordService.addNewPastIllness(use.getId(), pastIllness);
        return ResponseEntity.ok(new ApiResponse("add new PastIllness successfully"));
    }

    @PutMapping("/new-medications")// PATIENT
    public ResponseEntity updatePastMedicationsForHealthRecord(@AuthenticationPrincipal User user, @RequestBody @Valid List<String> pastMedications) {
        healthRecordService.addNewMedication(user.getId(), pastMedications);
        return ResponseEntity.status(200).body("update Patient's Health Medications successfully");
    }



    @PutMapping("/new-Surgery")//PATIENT
    public ResponseEntity addNewSurgery(@AuthenticationPrincipal User user , List<String> newSurgery){
        healthRecordService.addNewSurgery(user.getId(),newSurgery);
        return ResponseEntity.ok(new ApiResponse("add new Surgery successfully"));
    }


    @GetMapping("/{patientId}/health-records/{healthRecordId}")//ADMIN
    public ResponseEntity adminGetHealthRecord(@PathVariable Integer patientId, @PathVariable Integer healthRecordId) {
        return ResponseEntity.status(200).body(healthRecordService.getHealthRecordByPatientId(patientId, healthRecordId));
    }





}
