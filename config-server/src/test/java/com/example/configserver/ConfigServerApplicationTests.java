package com.example.configserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ConfigServerApplicationTests {

	@Test
	void contextLoads() {
		// Kiểm tra xem ứng dụng Spring Boot có thể khởi động thành công hay không
		ConfigurableApplicationContext context = SpringApplication.run(ConfigServerApplication.class);
		assertNotNull(context);
		context.close();
	}

}
