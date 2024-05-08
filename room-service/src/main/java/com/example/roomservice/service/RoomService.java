package com.example.roomservice.service;

import com.example.roomservice.dto.hotelDTO;
import com.example.roomservice.dto.responseDTO;
import com.example.roomservice.dto.roomDTO;
import com.example.roomservice.enums.RoomStatus;
import com.example.roomservice.model.Room;
import com.example.roomservice.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TypeService typeService;
    @Autowired
    private RestTemplate restTemplate;
    public List<Room> findAll(){
        return roomRepository.findAll();
    }
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public responseDTO getRoom(Long id) {
        responseDTO response = new responseDTO();
        Optional<Room> room = roomRepository.findById(id);

        if (room.isPresent()){
            ResponseEntity<hotelDTO> responseEntity = restTemplate
                    .getForEntity("http://localhost:8081/api/hotel/find/" + room.get().getHotel(),
                            hotelDTO.class);
            hotelDTO hotel = responseEntity.getBody();

            response.setRoomId(room.get().getId());
            response.setHotel(hotel.getName());
            response.setRoomType(room.get().getType().getName());
            response.setRoomStatus(String.valueOf(room.get().getStatus()));
            return response;
        }
        return null;
    }


    public List<Room> findByRoomType(String roomType) {
        return roomRepository.findRoomByType(roomType);
    }
    public Room save(roomDTO newRoom){
        Room room = new Room();
        try {
            RoomStatus status = RoomStatus.valueOf(newRoom.getStatus());
            room.setStatus(status);
        } catch (IllegalArgumentException e) {
            return null;
        }
        typeService.findById(newRoom.getType_id()).ifPresent(room::setType);
        return roomRepository.save(room);
    }
    public Room updateRoomStatus(Long id, String statusString){
        Room updatedRoom = roomRepository.getRoomById(id);
        for (RoomStatus status : RoomStatus.values()){
            if (status.name().equalsIgnoreCase(statusString)){
                updatedRoom.setStatus(status);
                return roomRepository.save(updatedRoom);
            }
        }
        return null;
    }
    public boolean delete(Long id) {
        boolean exists = roomRepository.existsById(id);
        if(exists) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
