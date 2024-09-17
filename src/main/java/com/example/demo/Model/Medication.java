package com.example.demo.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Medical Name us Required ")
    private String medicalName;

    @NotEmpty(message = "Medical Name us Required ")
    private String description;

    @NotEmpty(message = "Medical Name us Required ")
    private String medicalType;

    @NotNull(message = "price is Required") //السعر
    private int price; // =0

    @NotNull(message = "Quantity is Required")// علبه او علبتين
    private int quantity;

    @NotEmpty(message = "Medication Doses Name us Required ")
    private String medicationDoses; // جرعات الدواء
}
