package com.example.Ticket.Booking.App.config;

import com.example.Ticket.Booking.App.filter.userProvisioningFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain FilterChain(
            HttpSecurity http,
            userProvisioningFilter userProvisioningFilter,
            JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception{

        http.
                authorizeHttpRequests(authorize ->

                        authorize
                                .requestMatchers(HttpMethod.GET, "/api/v1/published-events/**").permitAll()
                                .requestMatchers("/api/v1/event").hasRole("ORGANIZER")
                                .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
                        ))
                .addFilterAt(userProvisioningFilter, BasicAuthenticationFilter.class);

        return http.build();
    }
}
