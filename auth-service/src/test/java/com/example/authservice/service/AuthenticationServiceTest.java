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
import org.springframework.security.core.context.SecurityContext;
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
    @Mock
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;
    @InjectMocks
    private StaffService staffService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate() {
        LoginUserDto loginUserDto = new LoginUserDto("staff@example.com", "1234");
        Staff expectedStaff = new Staff();
        expectedStaff.setEmail("staff@example.com");
        // Các thuộc tính khác của expectedStaff có thể được thiết lập tương tự

        // Thiết lập hành vi cho mock staffService
        when(staffService.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.of(expectedStaff));

        // Gọi phương thức authenticate
        Staff result = authenticationService.authenticate(loginUserDto);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(expectedStaff, result);
    }


    @Test
    void signup() {
        // Given
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        RegisterUserDto registerUserDto = new RegisterUserDto(
                "john.doe@example.com", "password", "John", "Doe", "123456789",
                new HashSet<>(Arrays.asList("ADMIN")), 1L
        );

        when(userRepository.save(any(Staff.class))).thenReturn(new Staff());

        // When
        Staff result = authenticationService.signup(registerUserDto);

        // Then
        assertNotNull(result);
    }

    @Test
        void findCurrentUser() {
            // Given
            Staff staff = new Staff();
            Authentication authentication = mock(Authentication.class);
            when(authentication.getPrincipal()).thenReturn(staff);

            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

            // When
            Staff result = authenticationService.findCurrentUser();

            // Then
            assertEquals(staff, result);
        }
}