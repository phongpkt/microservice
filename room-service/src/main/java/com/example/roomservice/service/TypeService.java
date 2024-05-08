package com.example.roomservice.service;

import com.example.roomservice.model.RoomType;
import com.example.roomservice.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public List<RoomType> findAll(){
        return typeRepository.findAll();
    }
    public Optional<RoomType> findById(Long id){
        return typeRepository.findById(id);
    }
    public RoomType save(RoomType newType) {
        return typeRepository.save(newType);
    }
    public RoomType update(RoomType newType, Long id){
        RoomType updatedType = typeRepository.findById(id).map(type -> {
            type.setName(newType.getName());
            type.setDescription(newType.getDescription());
            type.setPricePerNight(newType.getPricePerNight());
            type.setCapacity(newType.getCapacity());
            type.setPricePerNight(newType.getPricePerNight());
            return typeRepository.save(type);
        }).orElseGet(() -> {
            newType.setId(id);
            return typeRepository.save(newType);
        });
        return updatedType;
    }
    public boolean delete(Long id) {
        boolean exists = typeRepository.existsById(id);
        if(exists) {
            typeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
