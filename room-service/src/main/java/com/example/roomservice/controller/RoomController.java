package com.example.roomservice.controller;

import com.example.roomservice.dto.responseDTO;
import com.example.roomservice.dto.roomDTO;
import com.example.roomservice.dto.roomTypeDTO;
import com.example.roomservice.dto.statusRequest;
import com.example.roomservice.exceptions.ResponseObject;
import com.example.roomservice.model.Room;
import com.example.roomservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @GetMapping("/findAll")
    public ResponseEntity<List<Room>> findAll() {
        List<Room> roomList = roomService.findAll();
        if(roomList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Optional<Room> foundResource = roomService.findById(id);
        if(foundResource.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find room with id = " + id, "")
            );
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getRoom(@PathVariable("id") Long id) {
        responseDTO foundResource = roomService.getRoom(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "successfully", foundResource)
        );
    }
    @GetMapping("/findByType")
    public ResponseEntity<ResponseObject> findByRoomType(@RequestBody roomTypeDTO roomType) {
        List<Room> foundResource = roomService.findByRoomType(roomType.getRoomType());
        if(!foundResource.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", foundResource)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find room with type = " + roomType.getRoomType(), "")
            );
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> save(@RequestBody roomDTO newRoom) {
        try {
            Room newRoomData = roomService.save(newRoom);
            if(newRoomData!=null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "successfully", newRoomData)
                );
            }else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new ResponseObject("ok", "Invalid request - Please check your input", "")
                );
            }

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while saving", "")
            );
        }
    }

    @PatchMapping("/update/status/{id}")
    public ResponseEntity<ResponseObject> updateStatus(@RequestBody statusRequest status, @PathVariable Long id) {
        try {
            Room updatedRoom = roomService.updateRoomStatus(id, status.getStatus());
            if (updatedRoom==null){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                        new ResponseObject("invalid", "Status is invalid", "")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", updatedRoom)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating", "")
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        boolean deleted = roomService.delete(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find room to delete", "")
        );
    }

}
