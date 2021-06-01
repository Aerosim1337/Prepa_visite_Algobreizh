/**
 * 
 */
package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Classes.Account;
import Database.crud_global;

/**
 * @author Arnaud
 *
 */
class crud_global_tests {

	@Test
	//TODO
	void test_read_by_table() throws Exception {
		Account account =new Account();
		account.setId(1);account.setLogin("connectemoi");account.setCommercial_id(1);account.accountInit();
		System.out.println(crud_global.read_all_by_indication(account.getClass()));
		assertEquals("1 connectemoi mdp 1 1",crud_global.read_by_indication("login"));
	}
}
