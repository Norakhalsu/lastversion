package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
public class HotLine {

    // نظام مسول عن الحالات ال urgent
    // solution to urgent cases
    // توفير طياره اخلاء
    // وسيط

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotlineId;
    @NotEmpty(message = "Title is required")
    private String title;
    @NotEmpty(message = "description is re  required")
    private String description;


    // ------------------------ Relations ----

    @OneToMany
    @JsonIgnore
    private Set<Requests> requestSet;





//    private boolean facility;
//    private String tools;
//    private String accept;
//    private boolean emergencyRequest;
//    private boolean urgentRequest;
//    private int hoursRequired;
    // sender hospital
    // recevier hospital ;
    // less than one hour ;
    // relations with urgent






}
