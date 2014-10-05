/**
 * 
 */
package org.crypto.driver.wrapper;

import java.math.BigInteger;
import java.util.UUID;

import junit.framework.Assert;

import org.crypto.driver.keymanagement.AssymetricKeyManager;
import org.crypto.driver.keymanagement.CustomKeyPair;
import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.CryptoInt;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author sithum
 *
 */
public class PaillierDecoratorTest {

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
	 * Test method for {@link org.crypto.driver.wrapper.PaillierDecorator#getValue()}.
	 */
	@Test
	public void testGetEncryptedValue() throws Exception {
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		PaillierDecorator function = new PaillierDecorator(p, config);
		BigInteger encInt = (BigInteger) function.getValue();
		Assert.assertNotNull(encInt);
		Assert.assertFalse(encInt.equals(p.getValue()));
	}

	/**
	 * Test method for {@link org.crypto.driver.wrapper.PaillierDecorator#HEDecorator(org.crypto.driver.types.BaseInt, org.crypto.driver.types.BaseInt.CryptoConfig)}.
	 */
	@Test
	public void testGetDecryptedValue() throws Exception {
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		PaillierDecorator decorator = new PaillierDecorator(p, config);
		BigInteger encInt = (BigInteger) decorator.getValue();
		
		config.setMode(CryptoConfig.DECRYPT_MODE);
		p = new CryptoInt(encInt);
		decorator = new PaillierDecorator(p, config);
		BigInteger plainValue = (BigInteger) decorator.getValue();
		Assert.assertNotNull(plainValue);
		Assert.assertTrue(plainValue.equals(new BigInteger("3000")));
	}

}
