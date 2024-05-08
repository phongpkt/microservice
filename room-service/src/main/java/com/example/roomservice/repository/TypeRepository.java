package com.example.roomservice.repository;

import com.example.roomservice.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<RoomType, Long> {
}
