package com.example.authservice.service;

import com.example.authservice.model.Role;
import com.example.authservice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void allRoles_ReturnsListOfRoles() {
        // Given
        List<Role> roles = createRoleList();
        when(roleRepository.findAll()).thenReturn(roles);

        // When
        List<Role> result = roleService.allRoles();

        // Then
        assertEquals(2, result.size());
        assertEquals(roles, result);
    }
    @Test
    void findRoleByName_ReturnsRole() {
        // Given
        String roleName = "ADMIN";
        Role role = new Role(1L, roleName);
        when(roleRepository.findRoleByName(roleName)).thenReturn(role);

        // When
        Role result = roleService.findRoleByName(roleName);

        // Then
        assertNotNull(result);
        assertEquals(role, result);
    }
    private static List<Role> createRoleList() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "ADMIN"));
        roles.add(new Role(2L, "STAFF"));
        return roles;
    }
}