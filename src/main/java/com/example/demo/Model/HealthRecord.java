package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class HealthRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer healthRecordId;

    @ElementCollection
    @NotEmpty(message = "past Illnesses is required")
    @Column(nullable = false)

    private List<String> pastIllnesses;

    @ElementCollection
    @Column(nullable = false)
    private List<String> pastSurgeries;

    @ElementCollection
    @Column(nullable = false)
    private List<String> pastMedications;

    @ElementCollection
    @Column(nullable = false)
    private List<String> healthHabits;



    // ----------------------- Relations-----------

    @OneToOne
    @JsonIgnore
    private Patient patient;



//
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Appointment> appointments; // قائمة المواعيد
//

//    @ManyToMany
//    private Set<Emergencies> emergencies; // عدة سجلات صحية تحتوي على عدة طلبات طارئة

//    @ManyToMany
//    private Set<BillingRecord> BillingRecords; //
//
//    @ManyToMany
//    private Set<Requests> requests;



}
