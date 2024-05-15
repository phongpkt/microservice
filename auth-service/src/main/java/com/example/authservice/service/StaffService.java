package com.example.authservice.service;

import com.example.authservice.enums.StaffRole;
import com.example.authservice.model.Staff;
import com.example.authservice.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.authservice.specifications.StaffSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> findAll(){
        return staffRepository.findAll();
    }

    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }
    public Optional<Staff> findByFirstnameAndLastname(String firstName, String lastName) {
        return staffRepository.findOne(where(hasFirstName(firstName).and(hasLastName(lastName))));
    }
    public Optional<Staff> findByEmail(String email) {
        return staffRepository.findOne(where(hasEmail(email)));
    }
    public Optional<Staff> findByPhone(String phone) {
        return staffRepository.findOne(where(hasPhone(phone)));
    }
    public List<Staff> findByStaffRole(String roleString) {
        StaffRole role;
        try {
            role = StaffRole.valueOf(roleString);
        } catch (IllegalArgumentException ex) {
            return Collections.emptyList();
        }
        return staffRepository.findAll(hasRole(role));
    }
    public boolean findDuplication(Staff staff){
        Optional<Staff> foundResource = staffRepository.findByEmail(staff.getEmail().trim());
        return foundResource.isPresent();
    }
    public Staff update(Staff newStaff, Long id){
        Staff updatedStaff = staffRepository.findById(id).map(staff -> {
            staff.setFirstName(newStaff.getFirstName());
            staff.setLastName(newStaff.getLastName());
            staff.setEmail(newStaff.getEmail());
            staff.setPhone(newStaff.getPhone());
            staff.setPassword(newStaff.getPassword());
            return staffRepository.save(staff);
        }).orElseGet(() -> {
            newStaff.setId(id);
            return staffRepository.save(newStaff);
        });
        return updatedStaff;
    }

    public boolean delete(Long id) {
        boolean exists = staffRepository.existsById(id);
        if(exists) {
            staffRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
