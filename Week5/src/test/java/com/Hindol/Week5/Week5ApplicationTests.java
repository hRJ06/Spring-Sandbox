package com.Hindol.Week5;

import com.Hindol.Week5.Entity.UserEntity;
import com.Hindol.Week5.Service.Implementation.JWTServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Week5ApplicationTests {

	@Autowired
	private JWTServiceImplementation jwtServiceImplementation;
	@Test
	void contextLoads() {
		UserEntity userEntity = new UserEntity(4L, "Hindol Roy", "hindol.roy@gmail.com", "1234");
		String token = jwtServiceImplementation.generateAccessToken(userEntity);
		System.out.println(token);
		Long id = jwtServiceImplementation.getUserIdFromToken(token);
		System.out.println(id);
	}

}
