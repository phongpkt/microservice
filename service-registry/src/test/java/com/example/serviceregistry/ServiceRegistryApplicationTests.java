package com.example.serviceregistry;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class ServiceRegistryApplicationTests {

	@Test
	void contextLoads() {
		ConfigurableApplicationContext context = SpringApplication.run(ServiceRegistryApplication.class);
		assertNotNull(context);
		context.close();
	}

}
