package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId; //  رقم الموعد

    @NotNull(message = "appointment Date is Required")
    private LocalDate appointmentDate; // تاريخ الموعد

    @NotEmpty(message = "appointment Reason is Require")
    private String appointmentReason; // سبب الموعد

    @Pattern(regexp = "Scheduled|Cancelled|Completed|Rescheduled" , message = "Status must be: Scheduled , Cancelled , Rescheduled or Completed ")
    private String Status; // حالة الموعد

    @NotEmpty(message = "Appointment Type is Required ")
    @Pattern(regexp = "General|Medical|Emergency|Surgical|Consultation" , message = "appointment Type must be: General , Medical , Emergency , Surgical or Consultation  ")
    private String appointmentType;

    // ---------------------------------- Relations -----
    @ManyToOne
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JsonIgnore
    private Patient patient;


//    @ManyToOne
//    @JsonIgnore
//    private HealthRecord healthRecord;



}
