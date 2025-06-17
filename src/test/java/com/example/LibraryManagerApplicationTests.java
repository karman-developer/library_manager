package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
class LibraryManagerApplicationTests {

	@Test
	void contextLoads() {
	}
	
	public static void main(String[] args) {
        String rawPassword = "user1";
        String encodedPassword = "$2a$10$jxJ4n0RBskX2QpevKy3GBOraPVvi9vUBzLkRTtqRJe7tIxN6/LmZa";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches(rawPassword, encodedPassword));
    }

}

