package com.example.authservice.repository;

import com.example.authservice.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindRoleByName() {
        // Given
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);

        // When
        Role foundRole = roleRepository.findRoleByName("ADMIN");

        // Then
        assertNotNull(foundRole);
        assertEquals("ADMIN", foundRole.getName());
    }

    @Test
    public void testFindRoleByName_NotFound() {
        // When
        Role foundRole = roleRepository.findRoleByName("NON_EXISTENT_ROLE");

        // Then
        assertNull(foundRole);
    }
}