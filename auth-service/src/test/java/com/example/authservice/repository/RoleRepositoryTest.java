package com.example.authservice.repository;

import com.example.authservice.model.Role;
import com.example.authservice.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void findRoleByName_WhenRoleExists() {
        // Arrange
        String roleName = "ADMIN";
        Role mockRole = new Role(1L, "ADMIN");

        // Mock the behavior of the roleRepository
        when(roleRepository.findRoleByName(roleName)).thenReturn(mockRole);

        // Act
        Role foundRole = roleService.findRoleByName(roleName);

        // Assert
        assertEquals(mockRole, foundRole);
    }

    @Test
    void findRoleByName_WhenRoleDoesNotExist() {
        // Arrange
        String roleName = "NON_EXISTING_ROLE";

        // Mock the behavior of the roleRepository
        when(roleRepository.findRoleByName(roleName)).thenReturn(null);

        // Act
        Role foundRole = roleRepository.findRoleByName(roleName);

        // Assert
        assertEquals(null, foundRole);
    }
}