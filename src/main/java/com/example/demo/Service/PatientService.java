package com.example.demo.Service;

import com.example.demo.Api.ApiException;
import com.example.demo.DTO.PatientDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    private final HealthRecordRepository healthRecordRepository;
    private  final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final RequestRepository requestRepository;


    // ADMIN
    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }


    // ALL
    public void addPatientToHospital(Integer hospitalId, PatientDTO patientDTO) {
        // 2 object [ user , Patient]
        Hospital hospital = hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital == null) {
            throw new ApiException("hospital not found");
        }
        User user = new User();
        user.setUsername(patientDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(patientDTO.getPassword()));
        user.setEmail(patientDTO.getEmail());
        user.setRole("PATIENT");
        user.setFullName(patientDTO.getFullName());
        user.setAge(patientDTO.getAge());
        user.setAddress(patientDTO.getAddress());
        user.setBirthDate(patientDTO.getBirthDate());
        user.setPhoneNumber(patientDTO.getPhoneNumber());
        user.setEmergencyPhoneNumber(patientDTO.getEmergencyPhoneNumber());
        user.setGender(patientDTO.getGender());
        user.setCity(patientDTO.getCity());

        Patient patient = new Patient();
        patient.setPatientCurrentDiet(patientDTO.getPatientCurrentDiet());
        patient.setSensitivy(patientDTO.isSensitivy());
        patient.setHealthStatus(patientDTO.getHealthStatus());
        patient.setHospital(hospital);

        patient.setUser(user);
        hospital.getPatients().add(patient);
        userRepository.save(user);
        patientRepository.save(patient);
    }


    // ADMIN
    public void updatePatient(Integer id, Patient patient) {
        Patient patient1 = patientRepository.findPatientByPatientId(id);
        if (patient1 == null) {
            throw new ApiException("not found");
        }
        //  patient1.setHealthStatus(patient.getHealthStatus());
        //   patient1.setMedicalHistory(patient.getMedicalHistory());
        patientRepository.save(patient1);
    }


    // ADMIN
    public void deletePatient(Integer patientId) {
        Patient patient = patientRepository.findPatientByPatientId(patientId);
        if (patient == null) {
            throw new ApiException("not found");
        }
        patientRepository.delete(patient);
    }

    //---------------------------  end point  ---------------------------------


    // ADMIN
    public List<User> allPatientsInCity(String city) {
        List<User> patientsInCity = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        if (allUsers.size() == 0) {
            throw new ApiException("No Patients found");
        }
        for (User user : allUsers) {
            if ("PATIENT".equalsIgnoreCase(user.getRole()) && user.getPatient() != null && user.getPatient().getUser() != null &&
                    user.getPatient().getUser().getCity() != null && user.getPatient().getUser().getCity().equalsIgnoreCase(city)) {
                patientsInCity.add(user);
            }
        }
        return patientsInCity;
    }




         // احصاية للحالات العاجلة في مدينة معينه
        public double getEmergencyPatientsPercentageInCity(String city) {
        List<User> allUsers = userRepository.findAll();
        int totalPatientsInCity = 0;
        int emergencyPatientsCount = 0;

        for (User user : allUsers) {
            if ("PATIENT".equalsIgnoreCase(user.getRole()) && user.getPatient() != null &&
                    user.getPatient().getRequests().getTypeRequest().equalsIgnoreCase("EMERGENCY") &&
                    user.getPatient().getUser().getCity() != null &&
                    user.getPatient().getUser().getCity().equalsIgnoreCase(city)) {
                totalPatientsInCity++;
                emergencyPatientsCount++;
            }
        }
        if (totalPatientsInCity == 0) {
            return 0.0; // لتجنب القسمة على صفر
        }
        return ((double) emergencyPatientsCount / totalPatientsInCity) * 100;
    }





    // ADMIN
    public List<Patient> findPatientsInHospital(String hospitalName) {
        List<Patient> patientsInHospital = new ArrayList<>();
        List<Patient> allPatients = patientRepository.findAll(); // افتراضياً، يجب أن يكون لديك repository لكائن Patient

        for (Patient patient : allPatients) {
            if (patient.getHospital() != null && patient.getHospital().getHospitalName().equalsIgnoreCase(hospitalName)) {
                patientsInHospital.add(patient);
            }
        }
        return patientsInHospital;
    }




    public void ratingToDoctor(Integer patientId,Integer requestId, Integer doctorId, int rating) {
        Patient patient=patientRepository.findPatientByPatientId(patientId);
        if (patient == null) {
            throw new ApiException("not found");
        }
        Requests requests = requestRepository.findRequestByRequestId(requestId);
        if (requests == null) {
            throw new ApiException("Request not found");
        }
        Doctor doctor = doctorRepository.findDoctorByDoctorId(doctorId);
        if (doctor == null) {
            throw new ApiException("Doctor not found");
        }
        if (!requests.getHospital().getDoctors().contains(doctor)) {
            throw new ApiException("Doctor is not associated with this request");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating should be between 1 and 5");
        }
         patient = requests.getPatient(); // تحديد المريض المرتبط بالطلب
        patient.setRatingToDoctor(rating); // تعيين التقييم للمريض
        doctorRepository.save(doctor); // حفظ الطبيب بعد إضافة التقييم
    }



}