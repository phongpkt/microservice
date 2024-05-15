package com.example.authservice.config;

import com.example.authservice.model.Staff;
import com.example.authservice.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppConfigTest {
    @Mock
    private StaffRepository staffRepository;

    @Test
    void userDetailsService() {
        // Arrange
        AppConfig appConfig = new AppConfig();
        appConfig.repository = staffRepository;

        // Stub the repository's behavior
        when(staffRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new Staff()));

        // Act
        UserDetailsService userDetailsService = appConfig.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");

        // Assert
        assertNotNull(userDetails);
    }

    @Test
    void authenticationManager() throws Exception {
        // Arrange
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        AppConfig appConfig = new AppConfig();

        // Act
        AuthenticationManager result = appConfig.authenticationManager(authenticationConfiguration);

        // Assert
        assertNotNull(result);
        assertEquals(authenticationManager, result);
    }
}