package com.example.hotelservice.controller;

import com.example.hotelservice.exceptions.ResponseObject;
import com.example.hotelservice.model.Hotel;
import com.example.hotelservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/find/{id}")
    public ResponseEntity<Optional<Hotel>> findById(@PathVariable("id") Long id) {
        Optional<Hotel> foundResource = hotelService.findById(id);
        if(foundResource.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(foundResource);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<List<Hotel>> findAll() {
        List<Hotel> hotelList = hotelService.findAll();
        if(hotelList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hotelList, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> save(@RequestBody Hotel newHotel) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", hotelService.save(newHotel))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while saving", "")
            );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody Hotel newHotel, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfully", hotelService.update(newHotel, id))
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("error", "An error occurred while updating - Please check your input", "")
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        boolean deleted = hotelService.delete(id);
        if(deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find hotel to delete", "")
        );
    }
}
