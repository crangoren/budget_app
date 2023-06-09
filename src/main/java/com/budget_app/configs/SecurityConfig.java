package com.budget_app.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
@Configuration
public class SecurityConfig{
    private final JwtRequestFilter jwtRequestFilter;


    //конфигурируем фильтры
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("api/v1/users/**")
                        .authenticated()
                        .requestMatchers("api/v1/profile")
                        .authenticated()
                        .requestMatchers("api/v1/categories/**")
                        .authenticated()
                        .requestMatchers("/h2-console")
                        .permitAll()
                        .requestMatchers("/api/v1/auth").permitAll()
                        .anyRequest().authenticated());
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        // http....;

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }


}
