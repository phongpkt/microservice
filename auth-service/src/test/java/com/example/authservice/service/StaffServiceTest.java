package com.example.authservice.service;

import com.example.authservice.model.Role;
import com.example.authservice.model.Staff;
import com.example.authservice.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.example.authservice.specifications.StaffSpecification.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.data.jpa.domain.Specification.where;

@SpringBootTest
class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffService staffService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_ReturnsListOfStaff() {
        // Given
        List<Staff> staffList = createStaffList();
        when(staffRepository.findAll()).thenReturn(staffList);

        // When
        List<Staff> result = staffService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        assertEquals("123456789", result.get(0).getPhone());
    }
    @Test
    void findById_ReturnsStaff() {
        // Given
        Long staffId = 1L;
        List<Staff> staffList = createStaffList();
        Staff staff = staffList.get(0);
        when(staffRepository.findById(staffId)).thenReturn(Optional.of(staff));

        // When
        Optional<Staff> result = staffService.findById(staffId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(staff, result.get());
    }

    private static List<Staff> createStaffList() {
        List<Staff> staffList = new ArrayList<>();

        Set<Role> roles1 = new HashSet<>();
        roles1.add(new Role(1L, "ADMIN"));
        staffList.add(new Staff(1L, 1L, "John", "Doe","123456789", "john.doe@example.com", "1234", roles1));

        Set<Role> roles2 = new HashSet<>();
        roles2.add(new Role(2L, "STAFF"));
        staffList.add(new Staff(2L, 1L, "Jane", "Doe","987654321", "jane.doe@example.com", "1234", roles2));

        return staffList;
    }
}