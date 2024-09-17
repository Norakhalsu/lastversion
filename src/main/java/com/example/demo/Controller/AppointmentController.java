package com.example.demo.Controller;


import com.example.demo.Model.Appointment;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.User;
import com.example.demo.Service.AppointmentService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/add-appointment/{patientId}")//DOCTOR
    public ResponseEntity addAppointment(@AuthenticationPrincipal User user, @PathVariable Integer patientId, @RequestBody @Valid Appointment appointment) {
        appointmentService.addAppointment(user.getId(), patientId, appointment);
        return ResponseEntity.status(200).body("Appointment added successfully");
    }

    @GetMapping("/get-all")//ADMIN
    public ResponseEntity getAllAppointments() {
        return ResponseEntity.status(200).body(appointmentService.getAllAppointment());
    }
       @PutMapping("/update/{appointmentId}")//DOCTOR
        public ResponseEntity updateAppointment(@AuthenticationPrincipal User patientUser, @PathVariable int appointmentId, @RequestBody @Valid Appointment appointment) {
           appointmentService.updateAppointment(patientUser.getId(), appointmentId, appointment);
           return ResponseEntity.status(200).body("Appointment updated successfully");
       }
        @DeleteMapping("/delete/{appointmentId}")// DOCTOR
           public ResponseEntity deleteAppointment(@AuthenticationPrincipal User patientUser, @PathVariable int appointmentId) {
            appointmentService.deleteAppointment(patientUser.getId(), appointmentId);
            return ResponseEntity.status(200).body("Appointment deleted successfully");
        }
        // ----------------------------- End point --------------------

    @GetMapping("/byDate/{date}")//PATIENT
    public List<Appointment> getAppointmentsByDate(@AuthenticationPrincipal User user,@PathVariable Date date) {
        return appointmentService.getAllAppointmentByDate(user.getId(),date);
    }


//    @PutMapping("/status-update/{appointmentId}")//PATIENT
//    public ResponseEntity updateAppointmentStatus(@PathVariable Integer appointmentId, @RequestBody @Valid String newStatus , @RequestBody @Valid LocalDate newDate) {
//        appointmentService.rescheduleAppointment(appointmentId, newStatus, newDate);
//        return ResponseEntity.status(200).body("Appointment Rescheduled successfully");
//    }

    @PutMapping("/cancel/{appointmentId}")//PATIENT
    public ResponseEntity cancelAppointment(@AuthenticationPrincipal User user,@PathVariable Integer appointmentId) {
        appointmentService.cancelAppointment(user.getId() ,appointmentId);
        return ResponseEntity.status(200).body("Appointment Cancelled successfully");
    }

    @GetMapping("/all-appointments-by-name/{doctorName}")//PATIENT
    public ResponseEntity getAllAppointmentsByName(@AuthenticationPrincipal User user,@PathVariable String doctorName) {
        return ResponseEntity.status(200).body(appointmentService.findAppointmentsByDoctorName(user.getId(), doctorName));
    }

    @GetMapping("/all-past-appointments")//PATIENT
    public ResponseEntity getAllPastAppointments(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(appointmentService.pastAppointment(user.getId()));
    }

    @GetMapping("/all-future-appointments")//PATIENT
    public ResponseEntity getAllFutureAppointments(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(appointmentService.futureAppointment(user.getId()));
    }

    @PutMapping("/reschedule-appointment/{appointmentId}")//DOCTOR
    public ResponseEntity rescheduleAppointment(@AuthenticationPrincipal User user, @PathVariable Integer appointmentId, @RequestBody @Valid LocalDate newDate) {
        appointmentService.rescheduleAppointment(user.getId(), appointmentId, newDate);
        return ResponseEntity.status(200).body("Appointment rescheduled successfully");
    }

    @GetMapping("/all-patient-appointment")//PATIENT
    public ResponseEntity getAllPatientAppointments(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(appointmentService.getAllAppointmentsByUserId(user.getId()));
    }

//    @GetMapping("/byDoctor/{doctorName}")
//    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable String doctorName) {
//        List<Appointment> list=appointmentService.findAppointmentByDoctorName(doctorName);
//        return ResponseEntity.status(200).body(list);
//    }



}
