package com.example.authservice.specifications;

import com.example.authservice.enums.StaffRole;
import com.example.authservice.model.Staff;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StaffSpecification {
    public static Specification<Staff> hasRole(StaffRole role) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("role"), role);
        });
    }

    public static Specification<Staff> hasFirstName(String firstName) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("FirstName"), firstName);
        });
    }

    public static Specification<Staff> hasLastName(String lastName) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("LastName"), lastName);
        });
    }

    public static Specification<Staff> hasEmail(String email) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("email"), email);
        });
    }

    public static Specification<Staff> hasPhone(String phone) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("phone"), phone);
        });
    }
}
