package com.budget_app.controllers;

import com.budget_app.dto.JwtRequest;
import com.budget_app.dto.JwtResponse;
import com.budget_app.exceptions.AppError;
import com.budget_app.services.UserService;
import com.budget_app.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;


    @PostMapping("api/v1/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        //проверка через userdetailsservice правильности лог/пасс из запроса
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException e ) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED );
        }
        //если ок то получаем userdetails, формируем токен и отправляем клиенту
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
