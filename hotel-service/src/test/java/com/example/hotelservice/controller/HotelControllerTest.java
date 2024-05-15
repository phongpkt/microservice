package com.example.hotelservice.controller;

import com.example.hotelservice.model.Hotel;
import com.example.hotelservice.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HotelController.class)
@AutoConfigureMockMvc
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Test
    void findById_WhenFound() throws Exception {
        // Given
        Hotel hotel = new Hotel();
        hotel.setHotel_id(1L);
        hotel.setName("Hotel California");

        // định nghĩa hành vi của hotelService
        when(hotelService.findById(1L)).thenReturn(Optional.of(hotel));

        // When and Then
        // gửi yêu cầu HTTP
        mockMvc.perform(get("/api/hotel/find/1"))
                // kiểm tra kết quả
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hotel_id").value(1))
                .andExpect(jsonPath("$.name").value("Hotel California"));
    }

    @Test
    void findById_WhenNotFound() throws Exception {
        // Given
        when(hotelService.findById(1L)).thenReturn(Optional.empty());

        // When and Then
        mockMvc.perform(get("/api/hotel/find/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll() throws Exception{
        // Given
        List<Hotel> hotels = new ArrayList<>();
        Hotel hotel1 = new Hotel();
        hotel1.setHotel_id(1L);
        hotel1.setName("Hotel 1");
        Hotel hotel2 = new Hotel();
        hotel2.setHotel_id(2L);
        hotel2.setName("Hotel 2");
        hotels.add(hotel1);
        hotels.add(hotel2);

        when(hotelService.findAll()).thenReturn(hotels);

        // When and Then
        mockMvc.perform(get("/api/hotel/find"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].hotel_id").value(1))
                .andExpect(jsonPath("$[0].name").value("Hotel 1"))
                .andExpect(jsonPath("$[1].hotel_id").value(2))
                .andExpect(jsonPath("$[1].name").value("Hotel 2"));
    }

    @Test
    void save_WhenSuccess() throws Exception {
        // Given
        Hotel newHotel = new Hotel();
        newHotel.setName("New Hotel");
        newHotel.setPhone_number("123456789");
        newHotel.setAddress("New Address");

        Hotel savedHotel = new Hotel();
        savedHotel.setHotel_id(1L);
        savedHotel.setName("New Hotel");
        savedHotel.setPhone_number("123456789");
        savedHotel.setAddress("New Address");

        when(hotelService.save(any(Hotel.class))).thenReturn(savedHotel);

        // When and Then
        mockMvc.perform(post("/api/hotel/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newHotel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.message").value("successfully"))
                .andExpect(jsonPath("$.data.hotel_id").value(1))
                .andExpect(jsonPath("$.data.name").value("New Hotel"))
                .andExpect(jsonPath("$.data.phone_number").value("123456789"))
                .andExpect(jsonPath("$.data.address").value("New Address"));
    }

    @Test
    void save_WhenError() throws Exception {
        // Given
        Hotel newHotel = new Hotel();
        newHotel.setName("New Hotel");
        newHotel.setPhone_number("123456789");
        newHotel.setAddress("New Address");

        when(hotelService.save(any(Hotel.class))).thenThrow(new RuntimeException("Error occurred"));

        // When and Then
        mockMvc.perform(post("/api/hotel/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newHotel)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("An error occurred while saving"));
    }

    @Test
    void update_WhenSuccess() throws Exception {
        // Given
        Hotel updatedHotel = new Hotel();
        updatedHotel.setHotel_id(1L);
        updatedHotel.setName("Updated Hotel");
        updatedHotel.setPhone_number("987654321");
        updatedHotel.setAddress("Updated Address");

        when(hotelService.update(any(Hotel.class), eq(1L))).thenReturn(updatedHotel);

        // When and Then
        mockMvc.perform(put("/api/hotel/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedHotel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.message").value("successfully"))
                .andExpect(jsonPath("$.data.hotel_id").value(1))
                .andExpect(jsonPath("$.data.name").value("Updated Hotel"))
                .andExpect(jsonPath("$.data.phone_number").value("987654321"))
                .andExpect(jsonPath("$.data.address").value("Updated Address"));
    }

    @Test
    void update_WhenError() throws Exception {
        // Given
        Hotel updatedHotel = new Hotel();
        updatedHotel.setHotel_id(1L);
        updatedHotel.setName("Updated Hotel");
        updatedHotel.setPhone_number("987654321");
        updatedHotel.setAddress("Updated Address");

        when(hotelService.update(any(Hotel.class), eq(1L))).thenThrow(new RuntimeException("Error occurred"));

        // When and Then
        mockMvc.perform(put("/api/hotel/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedHotel)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("An error occurred while updating - Please check your input"));
    }

    @Test
    void delete_WhenSuccess() throws Exception {
        // Given
        when(hotelService.delete(1L)).thenReturn(true);

        // When and Then
        mockMvc.perform(delete("/api/hotel/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.message").value("Delete successfully"));
    }

    @Test
    void delete_WhenHotelNotFound() throws Exception {
        // Given
        when(hotelService.delete(1L)).thenReturn(false);

        // When and Then
        mockMvc.perform(delete("/api/hotel/delete/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("failed"))
                .andExpect(jsonPath("$.message").value("Cannot find hotel to delete"));
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}