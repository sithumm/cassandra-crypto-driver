package org.crypto.driver.function;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import junit.framework.Assert;

import org.crypto.driver.function.AESCryptoFunction;
import org.crypto.driver.keymanagement.SymmetricKeyManager;
import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.CryptoInt;
import org.junit.BeforeClass;
import org.junit.Test;

public class AESCryptoFunctionTest {

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

	@Test
	public void testEncrypt() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		config.setIv(SecureRandom.getSeed(16));
		AESCryptoFunction function = new AESCryptoFunction(p.getValue(), config);
		BigInteger encInt = (BigInteger) function.encrypt();
		System.out.println(encInt);
		Assert.assertNotNull(encInt);
		Assert.assertFalse(encInt.equals(p.getValue()));
	}

	@Test
	public void testDecrypt() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		config.setIv(SecureRandom.getSeed(16));
		AESCryptoFunction function = new AESCryptoFunction(p.getValue(), config);
		BigInteger encInt = (BigInteger) function.encrypt();
		System.out.println(encInt);
		
		config.setMode(CryptoConfig.DECRYPT_MODE);
		function = new AESCryptoFunction(encInt, config);
		BigInteger plainValue = (BigInteger) function.decrypt();
		System.out.println(plainValue);
		Assert.assertNotNull(plainValue);
		Assert.assertTrue(plainValue.equals(p.getValue()));
	}

}
