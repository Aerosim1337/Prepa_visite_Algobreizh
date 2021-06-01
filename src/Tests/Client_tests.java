package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Classes.Client;

class Client_tests {
	Client client1 = new Client(1,"Test","Jean",27,"rue du bon son", 69420, "XLR");
	
	@Test
	void testIds() {
		client1.setId(2);
		assertEquals(2,client1.getId());
	}
	
	@Test
	void testNames() {
		client1.setName("Random");
		assertEquals("Random",client1.getName());
	}
	
	@Test
	void testSurnames() {
		client1.setSurname("Pierre");
		assertEquals("Pierre",client1.getSurname());
	}

	@Test
	void testStreet_numbers() {
		client1.setStreet_number(2);
		assertEquals(2,client1.getStreetNb());
	}

	@Test
	void testStreetName() {
		client1.setStreet_name("StreetName");
		assertEquals("StreetName",client1.getStreetName());
	}

	@Test
	void testCP() {
		client1.setPostcode(2);
		assertEquals(2,client1.getPostCode());
	}
	
	@Test
	void testCity() {
		client1.setCity("Aupif");
		assertEquals("Aupif",client1.getCity());
	}
}
