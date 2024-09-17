package com.example.demo.Service;


import com.example.demo.Api.ApiException;
import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthRecordService {
    private final HealthRecordRepository healthRecordRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;


    // HOSPITAL
    public void addHealthRecord(Integer hospitalId,Integer patientId, HealthRecord healthRecord) {
        Hospital hospital=hospitalRepository.findHospitalByHospitalId(hospitalId);
        if(hospital==null) {
            throw new ApiException("Hospital not found");
        }
        Patient patient = patientRepository.findPatientByPatientId(patientId);
        if (patient == null) {
            throw new ApiException("Patient not found");
        }
        healthRecord.setPatient(patient);
        healthRecordRepository.save(healthRecord);
    }


    // ADMIN
    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordRepository.findAll();
    }

    // HOSPITAL
    public void updateHealthRecord(Integer hospitalId, Integer recordId, HealthRecord healthRecord) {
        HealthRecord healthRecord1 = healthRecordRepository.findHealthRecordByHealthRecordId(recordId);
        if (healthRecord1 == null) {
            throw new ApiException("HealthRecord not found");
        }

        Hospital hospital = hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital == null) {
            throw new ApiException("Hospital not found");
        }

        Patient patient = healthRecord1.getPatient(); // Retrieve the patient associated with the existing HealthRecord

        healthRecord.setHealthHabits(patient.getHealthRecord().getHealthHabits());
        healthRecord.setPastSurgeries(patient.getHealthRecord().getPastSurgeries());
        healthRecord.setPastMedications(patient.getHealthRecord().getPastMedications());
        healthRecord.setPatient(patient);

        healthRecordRepository.save(healthRecord);
    }

    // HOSPITAL
    public void deleteHealthRecord(Integer hospitalId,Integer healthRecordId) {
        Hospital hospital=hospitalRepository.findHospitalByHospitalId(hospitalId);
        if(hospital==null) {
            throw new ApiException("Hospital not found");
        }
        Patient patient=new Patient();
        HealthRecord healthRecord=healthRecordRepository.findHealthRecordByHealthRecordId(healthRecordId);
        if(patient==null) {
            throw new ApiException("Patient Not Found");
        }
        if(healthRecord==null) {
            throw new ApiException("Health Record Not Found");
        }
        healthRecordRepository.delete(healthRecord);
    }



// ------------------------------- end point ---------


    //Patient -- DONE
    public HealthRecord getHealthRecordByPatientId(Integer patientId,Integer healthRecordId) {
        Patient patient = patientRepository.findPatientByPatientId(patientId);
        if(patient==null) {
            throw new ApiException("Patient Not Found");
        }
        HealthRecord healthRecord = healthRecordRepository.findHealthRecordByHealthRecordId(healthRecordId);
        if (healthRecord == null) {
            throw new ApiException("Health Record Not Found");
        }
        if (healthRecord.getPatient().getPatientId().equals(patient.getPatientId())) {
            return healthRecord;
        } else {
            throw new ApiException("Patient Not Found");
        }

    }


    // PATIENT
    public void addNewHabits(Integer patientId, List<String> newHealthHabits) {
        Patient patient = patientRepository.findPatientByPatientId(patientId);
        if (patient == null) {
            throw new ApiException("Patient Not Found");
        }
        HealthRecord healthRecord = healthRecordRepository.findHealthRecordByHealthRecordId(patientId);
        if (healthRecord == null) {
            throw new ApiException("Health Record Not Found");
        }
        healthRecord.getHealthHabits().clear();
        healthRecord.getHealthHabits().addAll(newHealthHabits);
        healthRecordRepository.save(healthRecord);
    }



        public void addNewPastIllness(Integer healthRecordId, List<String> illness) {
        HealthRecord healthRecord = healthRecordRepository.findHealthRecordByHealthRecordId(healthRecordId);
                   if(healthRecord==null){
                       throw new ApiException("Health Record Not Found");
                   }
        healthRecord.setPastIllnesses(illness);
        healthRecordRepository.save(healthRecord);
    }


    public void addNewMedication(Integer healthRecordId, List<String> medication) {
        HealthRecord healthRecord = healthRecordRepository.findHealthRecordByHealthRecordId(healthRecordId);
        if (healthRecord == null) {
            throw new ApiException("HealthRecord not found");
        }
        healthRecord.setPastMedications(medication);
        healthRecordRepository.save(healthRecord);
    }



    public void addNewSurgery(Integer healthRecordId, List<String> surgery) {
        HealthRecord healthRecord = healthRecordRepository.findHealthRecordByHealthRecordId(healthRecordId);
        if (healthRecord == null) {
            throw new ApiException("HealthRecord not found");
        }
        healthRecord.setPastSurgeries(surgery);
        healthRecordRepository.save(healthRecord);
    }


//    public void addNewHealthHabit(Integer healthRecordId, List<String> habit) {
//        HealthRecord healthRecord = healthRecordRepository.findHealthRecordByHealthRecordId(healthRecordId);
//        if (healthRecord == null) {
//            throw new ApiException("HealthRecord not found");
//        }
//        healthRecord.setHealthHabits(habit);
//        healthRecordRepository.save(healthRecord);
//    }

    public void searchAndUpdatePastIllness(Integer healthRecordId, List<String> illnesses) {
        HealthRecord healthRecord = healthRecordRepository.findHealthRecordByHealthRecordId(healthRecordId);
        if (healthRecord == null) {
            throw new ApiException("HealthRecord not found");
        }
        List<String> currentIllnesses = healthRecord.getPastIllnesses();
        if (currentIllnesses.equals(illnesses)) {
            return;
        }
        healthRecord.setPastIllnesses(illnesses);
        healthRecordRepository.save(healthRecord);
    }



//    public void updatePastMedicationsForHealthRecord(Integer patientId,  Integer healthRecordId,  List<String> pastMedications) {
//        // Logic to update past medications for health record
//    }




//    public HealthRecord getHealthRecordByPatientId( Integer patientId,  Integer healthRecordId) {
//        return healthRecordService.getHealthRecordByPatientId(patientId, healthRecordId);
//    }


}

    









