package com.example.demo.Config;


import com.example.demo.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MyUserDetailsService userDetailsService;


    // Authentication : check Log in  user in database
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }


    // Athurisation
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()

                // User
                .authenticationProvider(daoAuthenticationProvider()).authorizeRequests().
                requestMatchers("/api/v1/user/add").permitAll()


                // Hospital
                .requestMatchers("/api/v1/hospital/add").permitAll()
                .requestMatchers("/api/v1/hospital/get-all").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/hospital/update/{hospitalId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/hospital/delete/{hospitalId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/hospital/patients-count").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/hospital/remove-doctor/{doctorId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/hospital/all-hospital-requests").hasAuthority("HOSPITAL")

                //DOCTOR
                .requestMatchers("/api/v1/doctor/registration/{hospitalId}").permitAll()
                .requestMatchers("/api/v1/doctor/get-all").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/doctor/update").hasAuthority("DOCTOR")
                .requestMatchers("/api/v1/doctor/delete/{doctorId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/doctor/appointments-count").hasAuthority("DOCTOR")
                .requestMatchers("/api/v1/doctor/all-patient").hasAuthority("DOCTOR")
                .requestMatchers("/api/v1/doctor/show-doctor/{doctorId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/doctor/major/{major}").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/doctor/add/{doctorId}/appointment").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/doctor/update-degree").hasAuthority("DOCTOR")



                // Patients
                .requestMatchers("/api/v1/patient/registration/{hospitalId}").permitAll()
                .requestMatchers("/api/v1/patient/get-all").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/patient/update").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/patient/delete/{patientId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/patient/all-patients-city/{city}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/patient/emergency-Percentage/{city}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/patient/patients-In-Hospital/{hospitalName}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/patient/rateDoctor/{requestId}/{doctorId}/{rating}").hasAuthority("PATIENT")


                // health record
                .requestMatchers("/api/v1/healthRecord/add/to/patient/{patientId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/healthRecord/get-all").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/healthRecord/update/{recordId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/healthRecord/delete/{healthRecordId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/healthRecord/get-Health-Record/{healthRecordId}").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/healthRecord/new-healthHabits").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/healthRecord/new-medications").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/healthRecord/new-pastIllness").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/healthRecord/new-Surgery").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/healthRecord/{patientId}/health-records/{healthRecordId}").hasAuthority("ADMIN")


                // Hot Line
                .requestMatchers("/api/v1/hotline/add").permitAll()
                .requestMatchers("/api/v1/hotline/get-all").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/hotline/update/{hotlineId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/hotline/delete/{hotlineId}").hasAuthority("ADMIN")


                 // Appointment
                .requestMatchers("/api/v1/appointment/add-appointment/{patientId}").hasAuthority("DOCTOR")
                .requestMatchers("/api/v1/appointment/get-all").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/appointment/update/{appointmentId}").hasAuthority("DOCTOR")
                .requestMatchers("/api/v1/appointment/delete/{appointmentId}").hasAuthority("DOCTOR")
                .requestMatchers("/api/v1/appointment/byDate/{date}").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/appointment/cancel/{appointmentId}").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/appointment/all-appointments-by-name/{doctorName}").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/appointment/all-past-appointments").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/appointment/all-future-appointments").hasAuthority("PATIENT")
                .requestMatchers("/api/v1/appointment/reschedule-appointment/{appointmentId}").hasAuthority("DOCTOR")
                .requestMatchers("/api/v1/appointment/all-patient-appointment").hasAuthority("PATIENT")



                // requests
                .requestMatchers("/api/v1/request/new-urgent-request/{patientId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/request/get-all").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/request/update/{requestId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/request/delete/{requestId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/request/requests/{requestId}/hoursRequired").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/request/priority/{requestId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/request/all-emergency").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/request/add-emergencyRequest/{patientId}").hasAuthority("HOSPITAL")
                .requestMatchers("/api/v1/request/get-request/{requestId}").hasAuthority("HOSPITAL")



                //.requestMatchers("/api/v1/appointment/add-appointment/{patientId}").hasAuthority("DOCTOR")

                .and()
                .logout().logoutUrl("/api/v1/user/logout").
                deleteCookies("JSESSIONID").invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

    }
}
