package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;

    @Column(nullable = false)
    private String hospitalName;
    @Column(nullable = false)
    private String major; // تخصص الطبيب
    @Column(nullable = false)
    private String degree; // الدرجة العلمية
    private int ratingToPatient;

    // ------------------- Relations ---------
    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Appointment> appointments;


    @ManyToOne
    @JsonIgnore
    private Hospital hospital;

}
