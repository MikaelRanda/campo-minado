package br.com.mikaelmiranda.cm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Teste {

	@Test
	void testarIgualDois() {
		int a = 1 + 1;
		
		assertEquals(2, a);
	}

	@Test
	void testarIgualATres() {
		int x = 2 + 10 - 9;
		
		assertEquals(3, x);
	}
}
