package com.Hindol.Test;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class TestApplicationTests {

	@BeforeEach
	void setUp() {
		log.info("Starting the method, setting up config");
	}

	@AfterEach
	void tearDown() {
		log.info("Tearing down the method");
	}

	@BeforeAll
	static void setUpOnce() {
		log.info("Setup once");
	}

	@AfterAll
	static void tearDownOnce() {
		log.info("Tear Down once");
	}

	@Test
	void contextLoads() {
	}

	@Test
	/* @Disabled */
	void testNumberOne() {
		log.info("Test 1 is run");
		int a = 5;
		int b = 3;
		int result = addTwoNumbers(a, b);
		/* Assertions.assertEquals(8, result); */
		assertThat(result).isEqualTo(8).isCloseTo(9, Offset.offset(1));
		assertThat("Apple").isEqualTo("Apple").startsWith("App").endsWith("le").hasSize(5);
	}

	@Test
	void testDivideTwoNumber_whenDenominatorIsZero_ThenArithmeticException() {
		int a = 5;
		int b = 0;
		assertThatThrownBy(() -> divideTwoNumbers(a, b))
				.isInstanceOf(ArithmeticException.class)
				.hasMessage("Tried to divide by zero");
	}

	@Test
	@DisplayName("Test Number 2")
	void testNumberTwo() {
		log.info("Test 2 is run");
	}

	int addTwoNumbers(int a, int b) {
		return a + b;
	}

	double divideTwoNumbers(int a, int b) {
		try {
			return a / b;
		}
		catch (ArithmeticException e) {
			log.error("Arithmetic Exception : {}", e.getLocalizedMessage());
			throw new ArithmeticException("Tried to divide by zero");
		}
	}
}
