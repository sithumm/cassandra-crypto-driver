package org.crypto.driver.function;

import java.math.BigInteger;
import java.util.UUID;

import junit.framework.Assert;

import org.crypto.driver.function.HECryptoFunction;
import org.crypto.driver.keymanagement.AssymetricKeyManager;
import org.crypto.driver.keymanagement.CustomKeyPair;
import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.CryptoInt;
import org.junit.BeforeClass;
import org.junit.Test;

import thep.paillier.exceptions.BigIntegerClassNotValid;

public class HECryptoFunctionTest {

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

	@Test
	public void testEncrypt() throws BigIntegerClassNotValid {
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		HECryptoFunction function = new HECryptoFunction(p.getValue(), config);
		BigInteger encInt = (BigInteger) function.encrypt();
		System.out.println(encInt);
		Assert.assertNotNull(encInt);
		Assert.assertFalse(encInt.equals(p.getValue()));
	}

	@Test
	public void testDecrypt() throws BigIntegerClassNotValid {
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		HECryptoFunction function = new HECryptoFunction(p.getValue(), config);
		BigInteger encInt = (BigInteger) function.encrypt();
		System.out.println(encInt);
		
		config.setMode(CryptoConfig.DECRYPT_MODE);
		function = new HECryptoFunction(encInt, config);
		BigInteger plainValue = (BigInteger) function.decrypt();
		Assert.assertNotNull(plainValue);
		Assert.assertTrue(plainValue.equals(p.getValue()));
	}

}
