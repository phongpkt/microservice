package com.example.hotelservice.repository;

import com.example.hotelservice.model.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
* Cấu hình một bài kiểm tra JPA tập trung vào các thành phần JPA
* Nó sẽ cấu hình một cơ sở dữ liệu trong bộ nhớ (in-memory database) để chạy các bài kiểm tra.
*/

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void findByName() {
        // Given
        createHotels();

        // When
        List<Hotel> hotels = hotelRepository.findByName("Hotel California");

        // Then
        // Xác nhận rằng kết quả trả về đúng với mong đợi.
        assertEquals(2, hotels.size());
        assertTrue(hotels.stream().anyMatch(hotel -> hotel.getPhone_number().equals("123456789")));
        assertTrue(hotels.stream().anyMatch(hotel -> hotel.getPhone_number().equals("987654321")));
    }

    @Test
    void findByName_EmptyName() {
        // Given
        createHotels();

        // When
        List<Hotel> hotels = hotelRepository.findByName("");

        // Then
        assertTrue(hotels.isEmpty());
    }

    @Test
    void findByName_NotFound() {
        // Given
        createHotels();

        // When
        List<Hotel> hotels = hotelRepository.findByName("Non-existent Hotel");

        // Then
        assertTrue(hotels.isEmpty());
    }

    @Test
    void findByName_WrongInfo() {
        // Given
        createHotels();

        // When
        List<Hotel> hotels = hotelRepository.findByName("Hotel California");

        // Then
        assertEquals(2, hotels.size());
        assertTrue(hotels.stream().anyMatch(hotel -> hotel.getPhone_number().equals("123456789")));
        assertFalse(hotels.stream().anyMatch(hotel -> hotel.getPhone_number().equals("111111111"))); // số điện thoại không tồn tại trong danh sách
    }

    private void createHotels() {
        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel California");
        hotel1.setPhone_number("123456789");
        hotel1.setAddress("Address 1");
        hotelRepository.save(hotel1);

        Hotel hotel2 = new Hotel();
        hotel2.setName("Hotel California");
        hotel2.setPhone_number("987654321");
        hotel2.setAddress("Address 2");
        hotelRepository.save(hotel2);

        Hotel hotel3 = new Hotel();
        hotel3.setName("Other Hotel");
        hotel3.setPhone_number("555555555");
        hotel3.setAddress("Address 3");
        hotelRepository.save(hotel3);
    }
}