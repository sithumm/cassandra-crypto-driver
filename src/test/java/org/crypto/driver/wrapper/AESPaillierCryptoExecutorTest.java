/**
 * 
 */
package org.crypto.driver.wrapper;

import java.math.BigInteger;
import java.util.UUID;

import org.crypto.driver.keymanagement.AssymetricKeyManager;
import org.crypto.driver.keymanagement.CustomKeyPair;
import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.CryptoInt;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author sithum
 *
 */
public class AESPaillierCryptoExecutorTest {
	
	private static CryptoConfig config;
	private static CryptoInt p;

	@BeforeClass
	public static void setupBeforeClass () throws Exception {
		p = new CryptoInt(new BigInteger("3000"));
		config = new CryptoConfig();
		config.setAlgorithm("HE/Paillier");
		CustomKeyPair keyPair = AssymetricKeyManager.getInstance().generateKeyPair(256);
		config.setHeKeyPair(keyPair);
		config.setInstanceId(UUID.randomUUID().toString());
	}

	/**
	 * Test method for {@link org.crypto.driver.wrapper.AESPaillierCryptoExecutor#execute(java.lang.Object)}.
	 */
	@Test
	public void testExecute() throws Exception {
		
	}

}
