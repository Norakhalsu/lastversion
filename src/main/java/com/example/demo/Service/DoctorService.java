package com.example.demo.Service;


import com.example.demo.Api.ApiException;
import com.example.demo.DTO.DoctorDTO;
import com.example.demo.DTO.PatientDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.DoctorRepository;
import com.example.demo.Repository.HospitalRepository;
import com.example.demo.Repository.PatientRepository;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;


    // ADMIN
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }


    // ALL
    public void addDoctor( Integer hospitalId ,DoctorDTO doctorDTO ) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new ApiException("Hospital not found"));
        // 2 object [ user , Doctor]
        User user = new User();
        user.setUsername(doctorDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(doctorDTO.getPassword()));
        user.setEmail(doctorDTO.getEmail());
        user.setRole("DOCTOR");
        user.setFullName(doctorDTO.getFullName());
        user.setAge(doctorDTO.getAge());
        user.setAddress(doctorDTO.getAddress());
        user.setBirthDate(doctorDTO.getBirthDate());
        user.setPhoneNumber(doctorDTO.getPhoneNumber());
        user.setEmergencyPhoneNumber(doctorDTO.getEmergencyPhoneNumber());
        user.setGender(doctorDTO.getGender());
        user.setCity(doctorDTO.getCity());


        Doctor doctor = new Doctor();
        doctor.setMajor(doctorDTO.getMajor());
        doctor.setHospitalName(doctorDTO.getHospitalName());
        doctor.setDegree(doctorDTO.getDegree());
        doctor.setHospital(hospital); // ربط الطبيب بالمستشفى

        doctor.setUser(user);
        hospital.getDoctors().add(doctor); // إضافة الطبيب إلى قائمة الأطباء في المستشفى

        userRepository.save(user);
        doctorRepository.save(doctor);
        hospitalRepository.save(hospital);
    }



    // DOCTOR
    public void updateDoctor(Integer id,Doctor doctor) {
        Doctor doctor1 =doctorRepository.findDoctorByDoctorId(id);
        if(doctor1==null){
            throw new ApiException("not found");
        }
        //  patient1.setHealthStatus(patient.getHealthStatus());
        //   patient1.setMedicalHistory(patient.getMedicalHistory());
        doctorRepository.save(doctor1);
    }


    // ADMIN
    public void deleteDoctor(Integer id) {
        Doctor doctor =doctorRepository.findDoctorByDoctorId(id);
        if(doctor==null){
            throw new ApiException("not found");
        }
        doctorRepository.delete(doctor);
    }

   // ------------------------- End point -------------------------------

    // DOCTOR
   public int countDoctorAppointments(Integer doctorId) {
      Doctor doctor = doctorRepository.findDoctorByDoctorId(doctorId);

       if (doctor==null) {
           throw new ApiException("Doctor with ID " + doctorId + " not found");
       }
           Set<Appointment> appointments = doctor.getAppointments();
           return appointments.size();
          }




          //DOCTOR
        public List<Patient> allPatientsWithAppointments(Integer doctorId) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (!doctor.isPresent()) {
            throw new ApiException("Doctor with ID " + doctorId + " not found");
        }
            Doctor doctor11 = doctor.get();
            Set<Appointment> appointments = doctor11.getAppointments();
            List<Patient> patientNames = new ArrayList<>();

            for (Appointment appointment : appointments) {
                patientNames.add(appointment.getPatient());
            }
            return patientNames;
        }


      // HOSPITAL
        public Doctor getDoctorById(Integer hospitalId, Integer doctorId) {
        Hospital hospital=hospitalRepository.findHospitalByHospitalId(hospitalId);
        if(hospital==null){
            throw new ApiException("Hospital with ID " + hospitalId + " not found");
        }
        Doctor doctor = doctorRepository.findDoctorByDoctorId(doctorId);
        if(doctor==null){
            throw new ApiException("Doctor with ID " + doctorId + " not found");
        }
        if(hospital.getDoctors().contains(doctor)){
            return doctor;
        }
        throw new ApiException("Doctor with ID " + doctorId + " not found");
    }

    //PATIENT
    public List<Doctor> searchDoctorsByMajor(Integer patientId , String major ) {
        Patient patient=patientRepository.findPatientByPatientId(patientId);
        if(patient==null){
            throw new ApiException("Patient with ID " + patientId + " not found");
        }
            List<Doctor> doctors = doctorRepository.findDoctorByMajor(major);
            if (doctors.isEmpty()) {
                throw new ApiException("No doctors found with major: " + major);
            }
            return doctors;
        }


        // PATIENT
        public void addAppointmentToDoctor(Integer patientId , Integer doctorId , Set<Appointment> appointment){
           Patient patient=patientRepository.findPatientByPatientId(patientId);
           Doctor doctor=doctorRepository.findDoctorByDoctorId(doctorId);
           if (patient==null) {
               throw new ApiException("Patient with ID " + patientId + " not found");
           }
           if(doctor==null){
               throw new ApiException("Doctor with ID " + doctorId + " not found");
           }
                doctor.setAppointments(appointment); // إضافة الموعد لقائمة مواعيد الطبيب
                doctorRepository.save(doctor); // حفظ التغييرات في قاعدة البيانات
            }



            //DOCTOR
           public void updateDegree(Integer doctorId , String degree ){
            Doctor doctor=doctorRepository.findDoctorByDoctorId(doctorId);
            if(doctor==null){
                throw new ApiException("Doctor with ID " + doctorId + " not found");
            }
            doctor.setDegree(degree);
            doctorRepository.save(doctor);
           }




//    public void addRatingToDoctor( Integer patientId,Integer doctorId,  int rating) {
//        Doctor doctor = doctorRepository.findDoctorByDoctorId(doctorId);
//        if(doctor==null){
//            throw new ApiException("Doctor with ID " + doctorId + " not found");
//        }
//        Patient patient=patientRepository.findPatientByPatientId(patientId);
//        if (patient==null) {
//            throw new ApiException("Patient with ID " + patientId + " not found");
//        }
//        Requests requests=new Requests();
//        if(requests.getHospital().getDoctors())
//        doctor.setRatingToPatient(rating);
//        doctorRepository.save(doctor);
//    }







//    public void deleteRatingFromDoctor( Integer id) {
//        Doctor doctor = doctorRepository.findDoctorByDoctorId(id);
//        doctor.setRatingToPatient(0);
//        doctorRepository.save(doctor);
//    }





//    @GetMapping("/doctors/sorted")
//    public List<Doctor> getDoctorsSortedByRating() {
//        return doctorRepository.findAll(Sort.by(Sort.Direction.DESC, "ratingToPatient"));
//    }
//






















          // تفاصيل الموعيد بين الدكنور والمريض
//        public  Appointment getAppointmentWithPatient(Integer doctorId, Integer appointmentId) {}
//    Appointment appointment = doctorService.getPatientAppointment(doctorId, patientId);
//
//    if(appointment != null) {
//        return ResponseEntity.status(200).body(appointment);
//    }



}
