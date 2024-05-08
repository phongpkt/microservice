package com.example.roomservice.model;

import com.example.roomservice.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable=false, updatable=false)
    private Long id;
    private Long hotel;
    @ManyToOne
    @JoinColumn(name="type_id", nullable=false)
    private RoomType type;
    @Enumerated(EnumType.STRING)
    private RoomStatus Status;
}
