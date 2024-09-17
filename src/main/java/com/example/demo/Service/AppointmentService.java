package com.example.demo.Service;


import com.example.demo.Api.ApiException;
import com.example.demo.Model.Appointment;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.HealthRecord;
import com.example.demo.Model.Patient;
import com.example.demo.Repository.AppointmentRepository;
import com.example.demo.Repository.DoctorRepository;
import com.example.demo.Repository.HealthRecordRepository;
import com.example.demo.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final HealthRecordRepository healthRecordRepository;


    // DOCTOR
    public void addAppointment(Integer doctorId, Integer patientId, Appointment appointment) {
        Doctor doctor = doctorRepository.findDoctorByDoctorId(doctorId);
        if (doctor == null) {
            throw new ApiException("Doctor not found");
        }
        Patient patient = patientRepository.findPatientByPatientId(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }
        HealthRecord healthRecord = healthRecordRepository.findHealthRecordByHealthRecordId(patient.getHealthRecord().getHealthRecordId());
        if (healthRecord == null) {
            throw new ApiException("HealthRecord not found");
        }
        appointment.setDoctor(doctor); // set app to doctor
        //appointment.setPatient(patient); // تعيين المريض للموعد
        Appointment savedAppointment = appointmentRepository.save(appointment);
        healthRecord.getPatient().getAppointments().add(savedAppointment);
        healthRecordRepository.save(healthRecord);
    }



    // ADMIN
    public List<Appointment> getAllAppointment() {
        return appointmentRepository.findAll();
    }


    // DOCTOR
    public void updateAppointment(Integer patientId,Integer appointmentId,Appointment appointment) {
        Appointment appointment1 = appointmentRepository.findAppointmentByAppointmentId(appointmentId);
        Patient patient = patientRepository.findPatientByPatientId(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }
        if (appointment1 == null) {
            throw new ApiException("Appointment not found");
        }
      //  appointment1.setPatient(patient);
        appointmentRepository.save(appointment1);
    }


    // DOCTOR
    public void deleteAppointment(Integer patientId,Integer appointmentId) {
        Appointment appointment1 = appointmentRepository.findAppointmentByAppointmentId(appointmentId);
        Patient patient = patientRepository.findPatientByPatientId(patientId);
        if (appointment1 == null) {
            throw new ApiException("Appointment not found");
        }
        if(patient ==null){
            throw new ApiException("Patient not found");
        }
        appointmentRepository.delete(appointment1);
    }


   // ---------------------------- End point -----------------


    // PATIENT
    public List<Appointment> getAllAppointmentByDate(Integer doctorId,Date searchDate) {
        Doctor doctor = doctorRepository.findDoctorByDoctorId(doctorId);
        if (doctor == null) {
            throw new ApiException("Doctor not found");
        }
        List<Appointment> appointmentSearch=appointmentRepository.findAppointmentByAppointmentDate(searchDate);
        if(appointmentSearch==null){
            throw new ApiException("Appointment not found");
        }
        return appointmentSearch;
    }



      // PATIENT
     public void RerescheduleAppointment(Integer appointmentId,String newStatus , LocalDate newDate) {
        Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(appointmentId);
          if(appointment==null){
             throw new ApiException("Appointment not found with id: " + appointmentId);
          }
          if(appointment.getStatus().equalsIgnoreCase("canceled")){
              throw new ApiException(" Sorry can't Rescheduled appointment because it is canceled");
          }
          appointment.setStatus(newStatus);
          appointment.setAppointmentDate(newDate);
          appointmentRepository.save(appointment);
     }

     // PATIENT
    public void cancelAppointment(Integer patientId,Integer appointmentId) {
        Patient patient=patientRepository.findPatientByPatientId(patientId);
        if (patient==null){
            throw new ApiException("Patient not found");
        }
        Appointment appointment1 = appointmentRepository.findAppointmentByAppointmentId(appointmentId);
        if(appointment1==null){
            throw new ApiException("Appointment not found with id: " + appointmentId);
        }
        if(appointment1.getStatus().equalsIgnoreCase("canceled")){
            throw new ApiException("Appointment already canceled");
        }
        appointment1.setStatus("cancelled");
        appointment1.setAppointmentDate(null);
        appointmentRepository.save(appointment1);
    }




   // PATIENT
   public List<Appointment> findAppointmentsByDoctorName(Integer patientId, String doctorName) {
       Patient patient = patientRepository.findPatientByPatientId(patientId);
       if (patient == null) {
           throw new ApiException("Patient not found");
       }
       List<Appointment> appointments = appointmentRepository.findByDoctorUserFullName(doctorName);
       if (appointments.isEmpty()) {
           throw new ApiException("No appointments found with doctor name: " + doctorName);
       }
       return appointments;
   }



    //PATIENT
    public List<Appointment> pastAppointment(Integer patientId) {
        Patient patient = patientRepository.findPatientByPatientId(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }
        LocalDate currentDate = LocalDate.now();
        List<Appointment> pastAppointments = new ArrayList<>();
        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.getAppointmentDate().isBefore(currentDate)) {
                pastAppointments.add(appointment);
            }
        }
        return pastAppointments;
    }



    // PATIENT
    public List<Appointment> futureAppointment (Integer patientId){
        Patient patient = patientRepository.findPatientByPatientId(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }
        LocalDate currentDate = LocalDate.now();
        List<Appointment> futureAppointments = new ArrayList<>();

        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.getAppointmentDate().isBefore(currentDate)) {
                futureAppointments.add(appointment);
            }
        }
        return futureAppointments;
    }




    // DOCTOR
    public void rescheduleAppointment(Integer doctorId,Integer appointmentsId, LocalDate newDate) {
        Doctor doctor = doctorRepository.findDoctorByDoctorId(doctorId);
        if (doctor == null) {
            throw new ApiException("Doctor not found");
        }
        Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(appointmentsId);
        if (appointment == null) {
            throw new ApiException("Appointment not found with id: " + appointmentsId);
        }
        if (newDate.isBefore(LocalDate.now())) {
            throw new ApiException("New appointment date can't be in the past");
        }
        if (appointment.getStatus().equalsIgnoreCase("cancelled") || appointment.getStatus().equalsIgnoreCase("Scheduled")) {
            if (newDate.isAfter(appointment.getAppointmentDate()) && appointment.getAppointmentDate().isAfter(LocalDate.now())) {
                if (!newDate.equals(appointment.getAppointmentDate())) {
                    appointment.setAppointmentDate(newDate);
                    appointment.setStatus("Rescheduled");
                    appointmentRepository.save(appointment);
                    System.out.println("Appointment rescheduled successfully to: " + newDate);
                }
            }
            else {
                throw new ApiException("New appointment date should be after the current appointment date");
            }
        }
        else if (appointment.getStatus().equalsIgnoreCase("completed")) {
            throw new ApiException("Appointment already completed");
        } else {
            System.out.println("New appointment date is the same as the current appointment date. No changes made.");
        }
    }



    // PATIENT
    public Set<Appointment> getAllAppointmentsByUserId(Integer userId) {
        Patient patient = patientRepository.findPatientByPatientId(userId);
        if(patient == null) {
            throw new ApiException("Patient not found with id: " + userId);
        }
        Set<Appointment> myAppointments = patient.getAppointments();
        if(myAppointments.isEmpty()) {
            throw new ApiException("No appointments found for patient with id: " + userId);
        }
        return myAppointments;
    }




}
