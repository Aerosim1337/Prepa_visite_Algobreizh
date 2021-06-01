package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Classes.Account;
import Classes.Commercial;

class AccountTests {
	Commercial com = new Commercial();
	Account account_test = new Account();
	
	@Test
	void testGetAccount() throws Exception {
		account_test.setLogin("connectemoi");
		assertEquals(true, account_test.getAccount("connectemoi", "mdp"));
	}
	
	@Test
	void testIds() {
		account_test.setId(1);
		assertEquals(1,account_test.getId());
	}
	
	@Test
	void testLogins() {
		account_test.setLogin("connectemoi");
		assertEquals("connectemoi",account_test.getLogin());
	}
	
	@Test
	void testCIds() {
		account_test.setCommercial_id(1);
		assertEquals(1,account_test.getCommercial_id());
	}
	@Test
	void testCommercial() throws Exception {
		account_test.setCommercial_id(1);
		account_test.accountInit();
		System.out.println(com.getClass().getSimpleName());
		System.out.println("Test__________________");
		System.out.println(account_test.getCommercial().getClass().getSimpleName());
		assertEquals(com.getClass().getSimpleName(),account_test.getCommercial().getClass().getSimpleName());
	}
}
