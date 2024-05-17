package com.example.authservice.controller;

import com.example.authservice.dto.AuthenticationResponse;
import com.example.authservice.dto.LoginUserDto;
import com.example.authservice.dto.RegisterUserDto;
import com.example.authservice.exceptions.ResponseObject;
import com.example.authservice.model.Role;
import com.example.authservice.model.Staff;
import com.example.authservice.service.AuthenticationService;
import com.example.authservice.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationControllerTest {
    @Mock
    private AuthenticationService mockAuthService;

    @Mock
    private JwtService mockJwtService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        Role role = new Role(1L, "ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        RegisterUserDto registerUserDto = new RegisterUserDto(
                "admin@example.com", "password", "admin", "Admin", "123456789",
                new HashSet<>(List.of("ADMIN")), 1L
        );

        Staff staff = new Staff(1L, 1L, "admin@example.com", "password", "admin", "Admin", "123456789",
                roles);

        when(mockAuthService.signup(registerUserDto)).thenReturn(staff);

        ResponseEntity<ResponseObject> responseEntity = authenticationController.register(registerUserDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("User registered", responseEntity.getBody().getMessage());
        assertEquals(staff, responseEntity.getBody().getData());

        verify(mockAuthService).signup(registerUserDto);
    }

    @Test
    void authenticate() {
        Role role = new Role(1L, "STAFF");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        LoginUserDto loginUserDto = new LoginUserDto("staff@example.com", "1234");
        Staff staff = new Staff(1L, "John", "Doe", "123456789", "staff@example.com", "$2a$10$irGjGywh32lYKlyX1hJ63uUDS7Ng/BocdSt.brcDEjfZ/9WOCv6v.", roles);
        when(mockAuthService.authenticate(loginUserDto)).thenReturn(staff);
        when(mockJwtService.generateToken(staff)).thenReturn("mocked_jwt_token");

        ResponseEntity<ResponseObject> responseEntity = authenticationController.authenticate(loginUserDto);

        // Kiểm tra các giá trị trả về từ phương thức authenticate
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("User logged in", responseEntity.getBody().getMessage());
        assertEquals("mocked_jwt_token", ((AuthenticationResponse)responseEntity.getBody().getData()).getToken());

        // Kiểm tra xem phương thức authenticate của service đã được gọi không
        verify(mockAuthService).authenticate(loginUserDto);
        verify(mockJwtService).generateToken(staff);
    }

    @Test
    void userProfile() {
        Role role = new Role(1L, "STAFF");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        Staff staff = new Staff(1L, "John", "Doe", "123456789", "staff@example.com", "$2a$10$irGjGywh32lYKlyX1hJ63uUDS7Ng/BocdSt.brcDEjfZ/9WOCv6v.", roles);
        when(mockAuthService.findCurrentUser()).thenReturn(staff);

        ResponseEntity<Object> responseEntity = authenticationController.userProfile();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", ((ResponseObject)responseEntity.getBody()).getStatus());
        assertEquals("User logged in", ((ResponseObject)responseEntity.getBody()).getMessage());
        assertEquals(staff, ((ResponseObject)responseEntity.getBody()).getData());

        verify(mockAuthService).findCurrentUser();
    }
}