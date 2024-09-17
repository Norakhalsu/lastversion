package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId ;

    @Column(nullable = false)
    private String HealthStatus; // الحالة الصحية

    //@Column(name = "sensitive", columnDefinition = "BIT")
    private boolean sensitivy; // الحساسية

    @Column(nullable = false)
    private String patientCurrentDiet; // نوع الروتين الغذائي للمريض
    private int ratingToDoctor;

    // ----------------------------------------- Relations ----------------
    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;


    @ManyToOne
    @JsonIgnore
    private Hospital hospital;


    @OneToOne(cascade = CascadeType.ALL , mappedBy = "patient") // السجل الطبي للمريض
    private HealthRecord healthRecord ;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Appointment> appointments;


    @OneToOne(cascade = CascadeType.ALL , mappedBy = "patient")
    private Requests requests;


}
