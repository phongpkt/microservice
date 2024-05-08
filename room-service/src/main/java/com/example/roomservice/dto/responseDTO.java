package com.example.roomservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class responseDTO {
    private Long roomId;
    private String hotel;
    private String roomType;
    private String roomStatus;
}
