package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import Classes.Planning;
import Classes.RDV;


class Planning_tests {
	Planning plan = new Planning();
	RDV rdv1 = new RDV();
	RDV rdv2 = new RDV();
	ArrayList<RDV> rdvs = new ArrayList<RDV>();

	@Test
	void testIds() {
		plan.setId(1);
		assertEquals(1,plan.getId());
	}
	@Test
	void testCommercialIds() {
		plan.setId_commercial(1);
		assertEquals(1,plan.getId_commercial());
	}
	@Test
	void testRdv() {
		rdvs.add(rdv1);rdvs.add(rdv2);
		plan.setRdv_list(rdvs);
		assertEquals(rdvs,plan.getRdvList());
	}
}
