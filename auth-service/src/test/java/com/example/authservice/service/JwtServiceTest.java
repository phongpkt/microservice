package com.example.authservice.service;

import com.example.authservice.model.Staff;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtServiceTest {

    @Mock
    private Staff userDetails;

    @Autowired
    private JwtService jwtService;

    @Test
    public void testGenerateToken() {
        // Given
        String email = "john.doe@example.com";
        String firstName = "John";
        String lastName = "Doe";
        long expirationTime = jwtService.getExpirationTime();
        when(userDetails.getEmail()).thenReturn(email);
        when(userDetails.getFirstName()).thenReturn(firstName);
        when(userDetails.getLastName()).thenReturn(lastName);

        // When
        String token = jwtService.generateToken(userDetails);

        // Then
        assertNotNull(token);
        Claims claims = extractAllClaims(token);
        assertEquals(email, claims.getSubject());
        assertEquals(email, claims.get("email", String.class));
        assertEquals(firstName, claims.get("firstName", String.class));
        assertEquals(lastName, claims.get("lastName", String.class));
        assertTrue(claims.getIssuedAt().before(new Date()));
        assertTrue(claims.getExpiration().before(new Date(System.currentTimeMillis() + expirationTime)));
    }

    @Test
    public void testExtractUsername() {
        // Given
        String email = "john.doe@example.com";
        String firstName = "John";
        String lastName = "Doe";
        when(userDetails.getEmail()).thenReturn(email);
        when(userDetails.getFirstName()).thenReturn(firstName);
        when(userDetails.getLastName()).thenReturn(lastName);

        // When
        String token = jwtService.generateToken(userDetails);

        // When
        String extractedEmail = jwtService.extractUsername(token);

        // Then
        assertEquals(email, extractedEmail);
    }

    private Claims extractAllClaims(String token) {
        try {
            Method method = JwtService.class.getDeclaredMethod("extractAllClaims", String.class);
            method.setAccessible(true);
            return (Claims) method.invoke(jwtService, token);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}