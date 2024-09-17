package com.example.demo.Controller;

import com.example.demo.DTO.HospitalDTO;
import com.example.demo.Model.Hospital;
import com.example.demo.Model.User;
import com.example.demo.Service.HospitalService;
import com.example.demo.Service.HotLineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospital")
public class HospitalController {
    private final HospitalService hospitalService;

    @PostMapping("/add")//ALL
    public ResponseEntity addHospital(@RequestBody @Valid HospitalDTO hospitalDTO) {
        hospitalService.addHospitalToSystem(hospitalDTO);
        return ResponseEntity.status(200).body("Hospital added successfully");
    }

    @GetMapping("/get-all")//ADMIN
    public ResponseEntity getAllHospitals() {
        return ResponseEntity.status(200).body(hospitalService.getAllHospitals());
    }

    @PutMapping("/update/{hospitalId}")//ADMIN
    public ResponseEntity updateHospital(@PathVariable  Integer hospitalId,@RequestBody @Valid Hospital hospital) {
        hospitalService.updateHospitalInSystem(hospitalId,hospital);
        return ResponseEntity.status(200).body("hospital updated successfully");
    }

    @DeleteMapping("/delete/{hospitalId}")//ADMIN
    public ResponseEntity deleteHospital(@PathVariable  Integer hospitalId) {
        hospitalService.deleteHospital(hospitalId);
        return ResponseEntity.status(200).body("hospital deleted successfully");
    }

    // ----------------------------------- end point --------------


    @GetMapping("/patients-count") // HOSPITAL   x
    public ResponseEntity patientCount(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(hospitalService.getAllPatientsInHospital(user.getId()));

    }


   @PutMapping("/remove-doctor/{doctorId}")//HOSPITAL     +
    public ResponseEntity removeDoctor(@AuthenticationPrincipal User user, @PathVariable  Integer doctorId) {
        hospitalService.removeDoctorFromHospital(user.getId(), doctorId);
        return ResponseEntity.status(200).body("doctor removed successfully");
   }


     @GetMapping("/all-hospital-requests")// HOSPITAL    +
    public ResponseEntity getHospitalRequest(@AuthenticationPrincipal User user) {
       return ResponseEntity.status(200).body(hospitalService.allRequestHospital(user.getId()));

     }




}
