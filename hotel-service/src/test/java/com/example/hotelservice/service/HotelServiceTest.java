package com.example.hotelservice.service;

import com.example.hotelservice.model.Hotel;
import com.example.hotelservice.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceTest {
    //Tạo một mock của HotelRepository
    @Mock
    private HotelRepository hotelRepository;
    //Tạo một đối tượng HotelService và inject các mock vào đó.
    @InjectMocks
    private HotelService hotelService;
    //Lhởi tạo các mock
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Hotel hotel1 = new Hotel();
        Hotel hotel2 = new Hotel();
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel1, hotel2));

        List<Hotel> hotels = hotelService.findAll();

        assertEquals(2, hotels.size());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void save() {
        Hotel hotel = new Hotel();
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel savedHotel = hotelService.save(hotel);

        assertEquals(hotel, savedHotel);
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void findById() {
        Hotel hotel = new Hotel();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Optional<Hotel> foundHotel = hotelService.findById(1L);

        assertTrue(foundHotel.isPresent());
        assertEquals(hotel, foundHotel.get());
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    void update_existingHotel() {
        Hotel existingHotel = new Hotel();
        existingHotel.setHotel_id(1L);
        existingHotel.setName("Old Name");

        Hotel newHotel = new Hotel();
        newHotel.setName("New Name");
        newHotel.setPhone_number("123456789");
        newHotel.setAddress("New Address");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(existingHotel));
        when(hotelRepository.save(existingHotel)).thenReturn(existingHotel);

        Hotel updatedHotel = hotelService.update(newHotel, 1L);

        assertEquals("New Name", updatedHotel.getName());
        assertEquals("123456789", updatedHotel.getPhone_number());
        assertEquals("New Address", updatedHotel.getAddress());
        verify(hotelRepository, times(1)).findById(1L);
        verify(hotelRepository, times(1)).save(existingHotel);
    }

    @Test
    void update_newHotel() {
        Hotel newHotel = new Hotel();
        newHotel.setName("New Name");
        newHotel.setPhone_number("123456789");
        newHotel.setAddress("New Address");

        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
        when(hotelRepository.save(newHotel)).thenReturn(newHotel);

        Hotel savedHotel = hotelService.update(newHotel, 1L);

        assertEquals("New Name", savedHotel.getName());
        assertEquals("123456789", savedHotel.getPhone_number());
        assertEquals("New Address", savedHotel.getAddress());
        verify(hotelRepository, times(1)).findById(1L);
        verify(hotelRepository, times(1)).save(newHotel);
    }

    @Test
    void delete_existingHotel() {
        when(hotelRepository.existsById(1L)).thenReturn(true);

        boolean result = hotelService.delete(1L);

        assertTrue(result);
        verify(hotelRepository, times(1)).existsById(1L);
        verify(hotelRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_nonExistingHotel() {
        when(hotelRepository.existsById(1L)).thenReturn(false);

        boolean result = hotelService.delete(1L);

        assertFalse(result);
        verify(hotelRepository, times(1)).existsById(1L);
        verify(hotelRepository, times(0)).deleteById(1L);
    }

}