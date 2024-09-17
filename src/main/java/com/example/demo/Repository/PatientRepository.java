package com.example.demo.Repository;

import com.example.demo.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findPatientByPatientId(int id);
    List<Patient> findByHospitalHospitalName(String hospitalName);
}
