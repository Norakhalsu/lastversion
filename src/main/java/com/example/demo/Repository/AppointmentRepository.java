package com.example.demo.Repository;


import com.example.demo.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Appointment findAppointmentByAppointmentId(Integer id);
    List<Appointment> findAppointmentByAppointmentDate(Date date);
    List<Appointment> findByDoctorUserFullName(String doctorName);
}
