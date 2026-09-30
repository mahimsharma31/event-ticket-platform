package com.example.Ticket.Booking.App.config;

import com.example.Ticket.Booking.App.filter.userProvisioningFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    public SecurityFilterChain FilterChain(
            HttpSecurity http,
            userProvisioningFilter userProvisioningFilter) throws Exception{

        http.
                authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(
                                Customizer.withDefaults()
                        ))
                .addFilterAt(userProvisioningFilter, BasicAuthenticationFilter.class);

        return http.build();
    }
}
