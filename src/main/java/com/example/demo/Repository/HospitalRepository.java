package com.example.demo.Repository;

import com.example.demo.Model.Hospital;
import com.example.demo.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository  extends JpaRepository<Hospital, Integer> {
    Hospital findHospitalByHospitalId(Integer hospitalId);
    int getPatientCountByHospitalId(Integer patientId);
    List<Patient> getAllPatientsByHospitalId(Integer hospitalId);
}
