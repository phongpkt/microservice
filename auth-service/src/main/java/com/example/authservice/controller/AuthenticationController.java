package com.example.authservice.controller;

import com.example.authservice.dto.LoginUserDto;
import com.example.authservice.dto.RegisterUserDto;
import com.example.authservice.exceptions.ResponseObject;
import com.example.authservice.model.Staff;
import com.example.authservice.dto.AuthenticationResponse;
import com.example.authservice.service.JwtService;
import com.example.authservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody RegisterUserDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "User registered", service.signup(request))
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseObject> authenticate(@RequestBody LoginUserDto request) {
        Staff authenticatedUser = service.authenticate(request);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        AuthenticationResponse loginResponse = new AuthenticationResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "User logged in", loginResponse)
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> userProfile() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "User logged in", service.findCurrentUser())
        );
    }
}
