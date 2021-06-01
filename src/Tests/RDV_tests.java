package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.Test;

import Classes.RDV;
import Classes.Client;

class RDV_tests {
	Client client0 = new Client();
	RDV rdv0 = new RDV(1,new Date(01,06,2018),new Time(15,30,00),"au PMU du coin de la rue", client0);
	
	@Test
	void id_test() {
		assertEquals(1,rdv0.getId());
		rdv0.setId(2);
		assertEquals(2,rdv0.getId());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void date_test() {
		assertEquals(new Date(01,06,2018), rdv0.getDate_rdv());
		rdv0.setDate_rdv(new Date(01,06,1995));
		assertEquals(new Date(01,06,1995), rdv0.getDate_rdv());
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void hour_test() {
		assertEquals(new Time(15,30,0),rdv0.getHour());
		rdv0.setHour(new Time(12,45,00));
		assertEquals(new Time(12,45,00),rdv0.getHour());
	}
	@Test
	void spot_test() {
		assertEquals("au PMU du coin de la rue",rdv0.getRdv_spot());
		rdv0.setRdv_spot("au sommet des Alpes");
		assertEquals((String)"au sommet des Alpes", rdv0.getRdv_spot());
	}
	@Test
	void client_test() {
		assertEquals(client0, rdv0.getClient());
		client0 = new Client(1, "Cuffy", "Michelle", 1, "voie 9 3/4", 12345, "Londre");
		rdv0.setClient(client0);
		assertEquals(client0, rdv0.getClient());
	}

}
