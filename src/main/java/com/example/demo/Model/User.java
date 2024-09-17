package com.example.demo.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username Require")
    @Size(min = 4, max = 15, message = "Username length must be 4-15 characters ")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must contain only letters and numbers")
    @Column(unique = true, nullable = false)
    private String username;

    //@NotEmpty(message = "Password Required")
    //@Size(min = 4, max = 20, message = "Password length must be 4-20 characters ")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain symbols, numbers, and uppercase and lowercase characters")
    @Column(unique = true, nullable = false)
    private String password;

    @NotEmpty(message = "Full name is Required")
    @Size(min = 3, max = 35, message = "Full name length must be between 3-35 characters ")
    @Column(unique = true, nullable = false)
    private String fullName;

    //@NotNull(message = "Birthday Date is Required")
    private LocalDate birthDate;

    @NotEmpty(message = "Phone Number is Required ")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only integers")
    @Size(max = 10 , message = "PhoneNumber must be 10 characters ")
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @NotEmpty(message = "Emergency Phone Number is Required ")
    @Pattern(regexp = "\\d+", message = "Emergency Phone number must contain only integers")
    @Size(max = 11, message = "PhoneNumber must be 10 characters ")
    private String emergencyPhoneNumber;

    //@NotNull(message = "Age is Required")
    private int age;

    @Email(message = "Email must be valid format ")
    @Size(max = 30 , message = "Email length max 30 characters ")
    @Column(unique = true, nullable = false)
    private String email;

   //@NotEmpty(message = "gender is Required")
    @Pattern(regexp = "^(male|female)$", message = "Gender must be either Male or Female")
    private String gender;

    @NotEmpty(message = "Address is Required")
    private String address;

    @Pattern(regexp = "^(PATIENT|DOCTOR|ADMIN|HOSPITAL)$", message = "Role must be either PATIENT , DOCTOR or ADMIN")
    @NotEmpty(message = "Role is Required")
    private String role;

    @NotEmpty(message = "City is Required")
    private String city;




    // ------------------ Relations -----------

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Patient patient;


    @OneToOne(cascade = CascadeType.ALL , mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Doctor doctor;

    @OneToOne( cascade = CascadeType.ALL,mappedBy = "user")
    private Hospital hospital;

}
