package com.example.authservice.repository;

import com.example.authservice.model.Staff;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StaffRepositoryTest {

    @Autowired
    private StaffRepository staffRepository;

    @Test
    public void testFindByEmail() {
        // Given
        Staff staff = new Staff();
        staff.setEmail("john.doe@example.com");
        staffRepository.save(staff);

        // When
        Optional<Staff> foundStaff = staffRepository.findByEmail("john.doe@example.com");

        // Then
        assertTrue(foundStaff.isPresent());
        assertEquals("john.doe@example.com", foundStaff.get().getEmail());
    }

    @Test
    public void testFindByEmail_NotFound() {
        // When
        Optional<Staff> foundStaff = staffRepository.findByEmail("nonexistent@example.com");

        // Then
        assertFalse(foundStaff.isPresent());
    }
}