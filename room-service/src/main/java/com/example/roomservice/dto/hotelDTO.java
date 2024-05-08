package com.example.roomservice.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class hotelDTO {
        private Long hotel_id;
        private String name;
        private String address;
        private String phone_number;
}
