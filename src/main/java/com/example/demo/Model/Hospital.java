package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hospitalId;

     @Column(unique = true, nullable = false)
     private String hospitalName;

     private int patientCount;

     // --------------------- Relations -------
    @OneToOne
    @JsonIgnore
    private User user;


    @OneToMany(cascade = CascadeType.ALL)
    private Set<Patient> patients; // المسفشى فيها عدة مرضى ةالمرضى يرحو لعدة مستشفيات

    @OneToMany(cascade = CascadeType.ALL)
    private List<Requests> requestSet; // المستشفى تطلب عدة طلبات والطلبات تكون من عدة مستشفيات


    @OneToMany(cascade = CascadeType.ALL)
    private Set<Doctor> doctors; // المستشفى فيها غدة اطباء والاطباء يعملون في مستشفى واحد

}
