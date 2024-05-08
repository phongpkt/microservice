package com.example.hotelservice.service;

import com.example.hotelservice.model.Hotel;
import com.example.hotelservice.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> findAll(){
        return hotelRepository.findAll();
    }
    public Hotel save(Hotel newHotel){
        return hotelRepository.save(newHotel);
    }
    public Optional<Hotel> findById(Long id){
        return hotelRepository.findById(id);
    }
    public Hotel update(Hotel newHotel, Long id){
        Hotel updatedHotel = hotelRepository.findById(id).map(hotel -> {
            hotel.setName(newHotel.getName());
            hotel.setPhone_number(newHotel.getPhone_number());
            hotel.setAddress(newHotel.getAddress());
            return hotelRepository.save(hotel);
        }).orElseGet(() -> {
            newHotel.setHotel_id(id);
            return hotelRepository.save(newHotel);
        });
        return updatedHotel;
    }
    public boolean delete(Long id) {
        boolean exists = hotelRepository.existsById(id);
        if(exists) {
            hotelRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
