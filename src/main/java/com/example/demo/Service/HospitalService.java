package com.example.demo.Service;


import com.example.demo.Api.ApiException;
import com.example.demo.DTO.HospitalDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.DoctorRepository;
import com.example.demo.Repository.HospitalRepository;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;


    // ALL
    public void addHospitalToSystem(HospitalDTO hospitalDTO) {
        User user = new User();
        user.setUsername(hospitalDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(hospitalDTO.getPassword()));
        user.setEmail(hospitalDTO.getEmail());
        user.setFullName(hospitalDTO.getHospitalName());
        user.setPhoneNumber(hospitalDTO.getPhoneNumber());
        user.setEmergencyPhoneNumber(hospitalDTO.getEmergencyPhoneNumber());
        user.setRole("HOSPITAL");
        user.setAddress(hospitalDTO.getAddress());
        user.setEmergencyPhoneNumber(hospitalDTO.getEmergencyPhoneNumber());
        user.setCity(hospitalDTO.getCity());

        Hospital hos=new Hospital();
        hos.setHospitalName(hospitalDTO.getHospitalName());
        hos.setUser(user);
        userRepository.save(user);
        hospitalRepository.save(hos);
    }

    // ADMIN
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }



    // ADMIN
    public void updateHospitalInSystem(Integer hospitalId,Hospital hospital) {
        Hospital hospitalToUpdate = hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospitalToUpdate == null) {
            throw new ApiException("Hospital not found");
        }
        hospitalToUpdate.setHospitalName(hospital.getHospitalName());
        hospitalToUpdate.setUser(hospital.getUser());
        hospitalRepository.save(hospital);
    }


    // ADMIN
    public void deleteHospital(Integer hospitalId) {
        Hospital hospital=hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital == null) {
            throw new ApiException("Hospital not found");
        }
        hospitalRepository.delete(hospital);
    }
   // -------------------------------- End point -----------------


    // HOSPITAL
    public List<Patient> getAllPatientsInHospital(Integer hospitalId) {
        Hospital hospital = hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital == null) {
            throw new ApiException("Hospital not found for ID: " + hospitalId);
        }

        List<Patient> results = hospitalRepository.getAllPatientsByHospitalId(hospitalId);
        if (results.isEmpty()) {
            throw new ApiException("No patients found for Hospital ID: " + hospitalId);
        }

        List<Patient> patients = new ArrayList<>();
        for (Patient result : results) {
            patients.add(result); // Adding each Patient object to the patients list
        }

        return patients;
    }


        // HOSPITAL
        public void removeDoctorFromHospital(Integer hospitalId, Integer doctorId) {
        Hospital hospital = hospitalRepository.findHospitalByHospitalId(hospitalId);
        Doctor doctor = doctorRepository.findDoctorByDoctorId(doctorId);

        if (hospital == null) {
            throw new ApiException("Hospital not found");
        }
        if (doctor == null) {
            throw new ApiException("Doctor not found");
        }
        if (!hospital.getDoctors().contains(doctor)) {
            throw new ApiException("Doctor not found in Hospital");
        }

        hospital.getDoctors().remove(doctor);
        doctor.setHospitalName(null); // تعيين المستشفى الخاص بالطبيب إلى قيمة null
        doctorRepository.save(doctor); // حفظ التغييرات على الطبيب
    }

        //HOSPITAL
        public List<Requests> allRequestHospital(Integer hospitalId) {
        Hospital hospital = hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital == null) {
            throw new ApiException("Hospital not found");
        }
        if (hospital.getRequestSet().size() == 0) {
            throw new ApiException("No set requests found");
        }
        return new ArrayList<>(hospital.getRequestSet());
         }




         

}
