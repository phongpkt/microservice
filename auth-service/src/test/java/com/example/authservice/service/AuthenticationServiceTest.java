package com.example.authservice.service;

import com.example.authservice.dto.LoginUserDto;
import com.example.authservice.dto.RegisterUserDto;
import com.example.authservice.model.Role;
import com.example.authservice.model.Staff;
import com.example.authservice.repository.RoleRepository;
import com.example.authservice.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;


@SpringBootTest
class AuthenticationServiceTest {
    @Mock
    private StaffRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private StaffService staffService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Open mockito annotations
    }

    @Test
    void authenticate() {
        // Given
        LoginUserDto loginUserDto = new LoginUserDto("john.doe@example.com", "1234");
        Staff expectedStaff = new Staff();
        when(userRepository.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.of(expectedStaff));

        // When
        Staff result = authenticationService.authenticate(loginUserDto);

        // Then
        assertNotNull(result);
        assertEquals(expectedStaff, result);
    }

    @Test
    void signup() {
        // Given
        Role role = new Role(1L, "ADMIN");
        RegisterUserDto registerUserDto = new RegisterUserDto("john.doe@example.com", "password", "John", "Doe", "123456789", new HashSet<>(Arrays.asList("ADMIN")));
        when(roleService.findRoleByName("ADMIN")).thenReturn(role);
        when(userRepository.save(any(Staff.class))).thenReturn(new Staff());

        // When
        Staff result = authenticationService.signup(registerUserDto);

        // Then
        assertNotNull(result);
    }

    @Test
    void validateToken() {
        // Given
        String token = "dummyToken";
        doNothing().when(jwtService).validateToken(token);

        // When, Then
        assertDoesNotThrow(() -> authenticationService.validateToken(token));
    }

    @Test
    void findCurrentUser() {
        // Given
        Staff staff = new Staff();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(staff);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // When
        Staff result = authenticationService.findCurrentUser();

        // Then
        assertNotNull(result);
        assertEquals(staff, result);
    }
}