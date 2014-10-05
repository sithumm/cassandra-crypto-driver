/**
 * 
 */
package org.crypto.driver.wrapper;

import java.math.BigInteger;
import java.util.UUID;

import org.crypto.driver.keymanagement.AssymetricKeyManager;
import org.crypto.driver.keymanagement.CustomKeyPair;
import org.crypto.driver.keymanagement.SymmetricKeyManager;
import org.crypto.driver.types.CryptoConfig;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author sithum
 *
 */
public class AESPaillierCryptoExecutorTest {
	
	private static CryptoConfig config;

	@BeforeClass
	public static void setupBeforeClass () throws Exception {
		config = new CryptoConfig();
		config.setExecuteSequenceItem(CryptoDecoratorFactory.HE_DECORATOR);
		config.setExecuteSequenceItem(CryptoDecoratorFactory.AES_DECORATOR);
		CustomKeyPair keyPair = AssymetricKeyManager.getInstance().generateKeyPair(256);
		config.setHeKeyPair(keyPair);
		config.setSymmetricKey(SymmetricKeyManager.getInstance().generateSecretKey("AES", 128));
		config.setInstanceId(UUID.randomUUID().toString());
	}

	/**
	 * Test method for {@link org.crypto.driver.wrapper.AESPaillierCryptoExecutor#execute(java.lang.Object)}.
	 */
	@Test
	public void testExecuteEncrypt() throws Exception {
		BigInteger actual = new BigInteger("3000");
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		AESPaillierCryptoExecutor executor = new AESPaillierCryptoExecutor(config);
		Object value = executor.execute(new BigInteger("3000"));
		Assert.assertNotNull(value);
		Assert.assertNotSame(value, actual);
	}
	
	@Test
	public void testExecuteDecrypt() throws Exception {
		BigInteger actual = new BigInteger("3000");
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		AESPaillierCryptoExecutor executor = new AESPaillierCryptoExecutor(config);
		BigInteger enc = (BigInteger) executor.execute(actual);
		
		config.setMode(CryptoConfig.DECRYPT_MODE);
		config.getExecuteSequence().clear();
		config.setExecuteSequenceItem(CryptoDecoratorFactory.AES_DECORATOR);
		config.setExecuteSequenceItem(CryptoDecoratorFactory.HE_DECORATOR);
		executor = new AESPaillierCryptoExecutor(config);
		Object value = executor.execute(enc);
		Assert.assertNotNull(value);
		Assert.assertEquals(value, actual);
	}

}
