package com.example.demo.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
public class HospitalDTO {

    @NotEmpty(message = "Username Require")
    @Size(min = 4,max = 20 , message = "Username length must be 4-15 characters ")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must contain only letters and numbers")
    private String username;

    @NotEmpty(message = "Password Required")
    @Size(min = 4, max = 20, message = "Password length must be 4-20 characters ")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain symbols, numbers, and uppercase and lowercase characters")
    private String password;


    @NotEmpty(message = "Phone Number is Required ")
    @Pattern(regexp="\\d+", message="Phone number must contain only integers")
    // @Size(max = 10 , message = "PhoneNumber must be 10 characters ")
    private String phoneNumber;

    @NotEmpty(message = "Emergency Phone Number is Required ")
    @Pattern(regexp="\\d+", message="Emergency Phone number must contain only integers")
    @Size(max = 10 , message = "PhoneNumber must be 10 characters ")
    private String emergencyPhoneNumber;


    @Email(message = "Email must be valid format ")
    // @Size(max = 30 , message = "Email length max 30 characters ")
    private String email;

    @NotEmpty(message = "Address is Required")
    private String address;

    @Pattern(regexp = "^(patient|DOCTOR|admin|HOSPITAL)$", message = "Role must be either Patient , Doctor or Admin")
    @NotEmpty(message = "Role is Required")
    private String role;


    @NotEmpty(message = "city is Required")
    private String city;


    // --------------
    @NotEmpty(message = "Hospital Name is Required")
    private String hospitalName;

    private int patientCount;


}
