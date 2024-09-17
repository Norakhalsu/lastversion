package com.example.demo.Service;


import com.example.demo.Api.ApiException;
import com.example.demo.Model.*;
import com.example.demo.Repository.HospitalRepository;
import com.example.demo.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.AopInvocationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final HospitalRepository hospitalRepository;



    // HOSPITAL
    public void addUrgentRequest(Integer hospitalId, Integer patientId, Requests request) {
        Hospital hospital = hospitalRepository.findHospitalByHospitalId(hospitalId);

        if (hospital == null) {
            throw new ApiException("Hospital not found");
        }
        if (hospital.getPatients().contains(patientId)) {
            if (request.getTypeRequest().equalsIgnoreCase("urgent")) {
                if (request.getOnset().equalsIgnoreCase("core")
                        && request.getHoursCase() >= 3) {
                    saveRequest(request);
                } else if (request.getOnset().equalsIgnoreCase("pinmpra")
                        && request.getHoursCase() >= 5) {
                    saveRequest(request);
                } else if (request.getOnset().equalsIgnoreCase("unknown")
                        && request.getHoursCase() >= 6) {
                    saveRequest(request);
                }
            } else {
                throw new ApiException("Request must be urgent");
            }
        }
    }


        // Hospital
    public List<Requests> getAllRequests() {
        return requestRepository.findAll();
    }


    // HOSPITAL - HOTLINE
    public void updateRequest(Integer hospitalId,Integer requestId, Requests request) {
        Hospital hospital=hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital == null) {
            throw new ApiException("Hospital Not Found");
        }
        Requests requests=requestRepository.findRequestByRequestId(requestId);
        if (requests == null) {
            throw new ApiException("Request Not Found");
        }
        requests.setOnset(request.getOnset());
        requests.setHoursCase(request.getHoursCase());
        request.setDescription(request.getDescription());
        request.setTypeRequest(request.getTypeRequest());
            request.setHotLine(request.getHotLine());
            request.setCity(request.getCity());
            request.setAddress(request.getAddress());

        request.setHotLine(request.getHotLine());
        request.setPatient(request.getPatient());
        request.setHospital(hospital);
        requestRepository.save(request);

    }


    public void deleteRequest(Integer hospitalId,Integer requestId) {
        Hospital hospital=hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital == null) {
            throw new ApiException("Hospital Not Found");
        }
        Requests request=requestRepository.findRequestByRequestId(requestId);
        if (request == null) {
            throw new ApiException("Request Not Found");
        }
        requestRepository.delete(request);
    }


    // -------- end point -----------


    public int calculateTotalHoursRequired(Integer hospitalId,int requestId) {
        Hospital hospital=hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital == null) {
            throw new ApiException("Hospital Not Found");
        }
        Requests request = requestRepository.findRequestByRequestId(requestId);

        if (request == null) {
            throw new ApiException("Request Not Found");
        }

        int totalHoursRequired = request.getHoursCase();
        if (totalHoursRequired == 0) {
            throw new ApiException("Request: Hours equal zero");
        } else if (totalHoursRequired >= 2 && totalHoursRequired <= 6) {
            return totalHoursRequired;
        } else {
            throw new ApiException("Hours Required Not within Range");
        }
    }




    public String priorityRequest(Integer hospitalId,Integer requestId) {
        Hospital hospital=hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital==null){
            throw new ApiException("Hospital Not Found");
        }
        Requests request = requestRepository.findRequestByRequestId(requestId);
        if (request == null) {
            throw new ApiException("Request Not Found");
        }
        int hoursRequired = request.getHoursCase();
        if (hoursRequired < 7) {
            return "هام جدًا";
        } else if (hoursRequired > 8 && hoursRequired < 20) {
            return "عاجل";
        } else {
            return "عادي";
        }
    }




    public List<Requests> allEmergencyRequest(Integer hospitalId) {
        Hospital hospital=hospitalRepository.findHospitalByHospitalId(hospitalId);
        if (hospital == null) {
            throw new ApiException("Hospital Not Found");
        }
        Requests request=new Requests();
        List<Requests> emergencyRequest = new ArrayList<>();
        if (request.getTypeRequest().equalsIgnoreCase("emergency")) {
            emergencyRequest.add(request);
            requestRepository.save(request);
        }
        return emergencyRequest;
    }





    public Patient getPatientById(Integer patientId) {
        HealthRecord healthRecord = new HealthRecord();
        Patient patient = healthRecord.getPatient(); // تأكد من أن الطريقة تعيد المريض بناءً على المعرف
        if (patient == null) {
            throw new ApiException("Patient Not Found");
        }
        if (patient.getPatientId().equals(patientId)) {
            return patient;
        } else {
            throw new ApiException("Patient ID does not match");
        }
    }


    public void addEmergencyRequest(Integer hospitalId, Integer patientId, Requests request) {
        Hospital hospital = hospitalRepository.findHospitalByHospitalId(hospitalId);

        if (hospital == null) {
            throw new ApiException("Hospital not found");
        }
        if (hospital.getPatients().contains(patientId)) {
            if (request.getTypeRequest().equalsIgnoreCase("emergency")) {
                saveRequest(request);
            } else {
                throw new ApiException("Request must be urgent");
            }
        }
    }

    public Requests getRequestById(Integer requestId) {
        Requests request = requestRepository.findRequestByRequestId(requestId);
        if (request == null) {
            throw new ApiException("Request not found with id: " + requestId);
        }
        return request;
    }


    private void saveRequest(Requests request) {
        requestRepository.save(request);
        request.setHospital(request.getHospital());
        request.setHotLine(request.getHotLine());
    }




//    public Patient getPatientById(Integer patientId) {
//        HealthRecord healthRecord = new HealthRecord();
//        Patient patient = healthRecord.getPatient();
//
//        if (patient != null) {
//            if (patient.getPatientId().equals(patientId)) {
//                return patient;
//            } else {
//                throw new ApiException("Patient Not Found");
//            }
//        } else {
//            throw new ApiException("Patient Not Found");
//        }
//    }




}
