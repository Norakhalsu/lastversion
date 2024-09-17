package com.example.demo.Controller;


import com.example.demo.Model.Requests;
import com.example.demo.Model.User;
import com.example.demo.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/request")
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/new-urgent-request/{patientId}")//HOSPITAL
    public ResponseEntity newRequest(@AuthenticationPrincipal User user, @PathVariable Integer patientId , @RequestBody @Valid Requests request) {
        requestService.addUrgentRequest(user.getId(), patientId, request);
        return ResponseEntity.status(200).body("Request has been created");
    }

    @GetMapping("/get-all")//ADMIN
    public ResponseEntity getAllRequests() {
        return ResponseEntity.status(200).body(requestService.getAllRequests());
    }

    @PutMapping("/update/{requestId}")//HOSPITAL
    public ResponseEntity updateRequest(@AuthenticationPrincipal User user, @PathVariable Integer requestId, @RequestBody @Valid Requests request) {
        requestService.updateRequest(user.getId(), requestId, request);
        return ResponseEntity.status(200).body("Request has been updated");
    }


    @DeleteMapping("/delete/{requestId}")//HOSPITAL
    public ResponseEntity deleteRequest(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        requestService.deleteRequest(user.getId(), requestId);
        return ResponseEntity.status(200).body("Request has been deleted");
    }

    // ------------------------------ end point ------------------

    @GetMapping("/requests/{requestId}/hoursRequired")//HOSPITAL
    public ResponseEntity calculateHoursRequired(@AuthenticationPrincipal User user,@PathVariable Integer requestId) {
        return ResponseEntity.status(200).body(requestService.calculateTotalHoursRequired(user.getId(),requestId));
    }

    @GetMapping("/priority/{requestId}")// HOSPITAL
    public ResponseEntity priorityRequests(@AuthenticationPrincipal User user,@PathVariable Integer requestId) {
        return ResponseEntity.status(200).body(requestService.priorityRequest(user.getId(),requestId));
    }

    @GetMapping("/all-emergency")//HOSPITAL
    public ResponseEntity getAllEmergencyRequests(@AuthenticationPrincipal User user ) {
        return ResponseEntity.status(200).body(requestService.allEmergencyRequest(user.getId()));
    }

    @PostMapping("/add-emergencyRequest/{patientId}")// HOSPITAL
    public ResponseEntity<String> emergencyRequest(@AuthenticationPrincipal User user, @PathVariable Integer patientId, @RequestBody @Valid Requests request) {
        requestService.addEmergencyRequest(user.getId(), patientId, request);
        return ResponseEntity.status(200).body("Request has been added");
    }

    @GetMapping("/get-request/{requestId}")//HOSPITAL
    public ResponseEntity getRequest(@AuthenticationPrincipal User user, @PathVariable Integer requestId) {
        return  ResponseEntity.status(200).body(requestService.getRequestById(requestId));
    }



}

