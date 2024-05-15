package com.example.authservice.controller;

import com.example.authservice.dto.AuthenticationResponse;
import com.example.authservice.dto.RegisterUserDto;
import com.example.authservice.dto.LoginUserDto;
import com.example.authservice.exceptions.ResponseObject;
import com.example.authservice.model.Staff;
import com.example.authservice.service.AuthenticationService;
import com.example.authservice.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto("John", "Doe", "john.doe@example.com", "password", "123456789", Collections.singleton("Admin"), null);
        Staff staff = new Staff();
        when(authenticationService.signup(any(RegisterUserDto.class))).thenReturn(staff);
        ResponseEntity<ResponseObject> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "User registered", staff));

        // Convert object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(registerUserDto);

        // Mocking the controller
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse.getBody())));
    }

    @Test
    void testAuthenticate() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto("john.doe@example.com", "password");
        Staff authenticatedUser = new Staff();
        String jwtToken = "jwtToken";
        when(authenticationService.authenticate(any(LoginUserDto.class))).thenReturn(authenticatedUser);
        when(jwtService.generateToken(authenticatedUser)).thenReturn(jwtToken);
        ResponseEntity<ResponseObject> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "User logged in", new AuthenticationResponse(jwtToken, jwtService.getExpirationTime())));

        // Convert object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(loginUserDto);

        // Mocking the controller
        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse.getBody())));
    }

    @Test
    void testUserProfile() throws Exception {
        Staff staff = new Staff();
        when(authenticationService.findCurrentUser()).thenReturn(staff);
        ResponseEntity<Object> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "User logged in", staff));

        // Mocking the controller
        mockMvc.perform(get("/api/auth/profile"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse.getBody())));
    }
}