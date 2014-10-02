/**
 * 
 */
package org.crypto.driver.wrapper;

import java.math.BigInteger;
import java.security.Key;
import java.security.SecureRandom;
import java.util.UUID;

import junit.framework.Assert;

import org.crypto.driver.keymanagement.SymmetricKeyManager;
import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.CryptoInt;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author sithum
 *
 */
public class AESDecoratorTest {

	private static CryptoConfig config;
	private static CryptoInt p;
	
	@BeforeClass
	public static void setupBeforeClass () throws Exception {
		p = new CryptoInt(new BigInteger("3000"));
		config = new CryptoConfig();
		config.setAlgorithm("AES/CBC/PKCS5Padding");
		Key key = SymmetricKeyManager.getInstance().generateSecretKey("AES", 128);
		config.setKeySize(128);
		config.setSymmetricKey(key);
		config.setInstanceId(UUID.randomUUID().toString());
	}

	/**
	 * Test method for {@link org.crypto.driver.wrapper.AESDecorator#getValue()}.
	 */
	@Test
	public void testGetEncryptedValue() throws Exception {
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		config.setIv(SecureRandom.getSeed(16));
		AESDecorator decorator = new AESDecorator(p, config);
		BigInteger encInt = (BigInteger) decorator.getValue();
		Assert.assertNotNull(encInt);
		Assert.assertFalse(encInt.equals(p.getValue()));
	}
	
	@Test
	public void testGetDecryptedValue() throws Exception {
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		config.setIv(SecureRandom.getSeed(16));
		AESDecorator decorator = new AESDecorator(p, config);
		BigInteger encInt = (BigInteger) decorator.getValue();
		System.out.println(encInt);
		
		config.setMode(CryptoConfig.DECRYPT_MODE);
		p = new CryptoInt(encInt);
		decorator = new AESDecorator(p, config);
		BigInteger plainValue = (BigInteger) decorator.getValue();
		Assert.assertNotNull(plainValue);
		Assert.assertTrue(plainValue.equals(new BigInteger("3000")));
	}

}
