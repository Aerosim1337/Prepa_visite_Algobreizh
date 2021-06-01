package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import Classes.Client;
import Classes.Commercial;

class Commercial_tests {
	Commercial commercial1 = new Commercial();
	Client client1 = new Client(1,"Test","Jean",27,"rue du bon son", 69420, "XLR");

	@Test
	void testIds() {
		commercial1.setId(1);
		assertEquals(1,commercial1.getId());
	}
	@Test
	void testNames() {
		commercial1.setName("Test");
		assertEquals("Test",commercial1.getName());
	}
	@Test
	void testSurnames() {
		commercial1.setSurname("Pierre");
		assertEquals("Pierre",commercial1.getSurname());
	}
	@Test
	void testGeo() {
		commercial1.setGeographic_zone(69420);
		assertEquals(69420,commercial1.getGeographic_zone());
	}
	@Test
	void testClientList() throws SQLException {
		commercial1.setClientList();
		assertEquals(false,commercial1.getClientList().isEmpty());
	}
	@Test
	void testAddClient() throws Exception {
		commercial1.addClient(client1);
		assertEquals(client1,commercial1.getClientList().get(1));
	}
	@Test
	void testAddCommercial() {
		commercial1.setSurname("Pierre");
		assertEquals("Pierre",commercial1.getSurname());
	}
	@Test
	void testAddRDV() {
		commercial1.setSurname("Pierre");
		assertEquals("Pierre",commercial1.getSurname());
	}
}
