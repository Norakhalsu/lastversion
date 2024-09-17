package com.example.demo.DTO;

import com.example.demo.Model.Patient;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HealthRecordDTO  extends Patient {

//    @ElementCollection
//    @NotEmpty(message = "past Illnesses is required")
//    private List<String> pastIllnesses; // ماضي الأمراض
//
//    @ElementCollection
//    @NotEmpty(message = "past Surgeries is required")
//    private List<String> pastSurgeries; // العمليات الجراحية الماضيه
//
//    @ElementCollection
//    @NotEmpty(message = "past Medications is required")
//    private List<String> pastMedications;  // الادوية الماضية
//
//    @ElementCollection
//    @NotEmpty(message = "past Medications is required")
//    private List<String> healthHabits; // العادات الصحية

}
