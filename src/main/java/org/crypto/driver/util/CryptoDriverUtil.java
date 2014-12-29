package org.crypto.driver.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.crypto.driver.keymanagement.AssymetricKeyManager;
import org.crypto.driver.keymanagement.CustomKeyPair;
import org.crypto.driver.keymanagement.SymmetricKeyManager;
import org.crypto.driver.types.BaseInt;
import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.CryptoInt;
import org.crypto.driver.wrapper.AESCBCDecorator;
import org.crypto.driver.wrapper.PaillierDecorator;

public class CryptoDriverUtil {
	
	public CryptoDriverUtil() {
		CustomKeyPair keyPair = AssymetricKeyManager.getInstance().generateKeyPair(256);
		AssymetricKeyManager.getInstance().persistKeys("he_pair", keyPair);
		Key key = null;
		try {
			key = SymmetricKeyManager.getInstance().generateSecretKey("AES", 256);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		SymmetricKeyManager.getInstance().persistKeys("aes_key", key);
	}
	
	public String encrypt(final BigInteger plain) {
		CryptoConfig config = new CryptoConfig();
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		config.setAlgorithm("HE/Paillier");
		config.setHeKeyPair(AssymetricKeyManager.getInstance().retrieveKeyPair("he_pair"));
		config.setInstanceId(UUID.randomUUID().toString());
		
		System.out.println("HE :: Encrypting int1");
		BaseInt decorator = new PaillierDecorator(new CryptoInt(plain), config);
		System.out.println("HE :: Encrypted int1 :: " + decorator.getValue());
		
		config = null;
		config = new CryptoConfig();
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		config.setAlgorithm("AES/CBC/PKCS5Padding");
		config.setKeySize(128);
		config.setSymmetricKey(SymmetricKeyManager.getInstance().retrieveSecretKey("aes_key"));
		config.setInstanceId(UUID.randomUUID().toString());
		
		System.out.println("AES :: Encrypting int1");
		decorator = new AESCBCDecorator(decorator, config);
		System.out.println("AES :: Encrypted int1 :: " + decorator.getValue());
//		System.out.println("AES :: Encrypted int1 :: B64 :: " + DatatypeConverter.printHexBinary(decorator.getValue().toString().getBytes()));
		
		return decorator.getValue().toString();
	}
	
	public BigInteger decrypt(final String encrypted) {
		BigInteger ei = new BigInteger(encrypted);
		
		CryptoConfig config = new CryptoConfig();
		config.setMode(CryptoConfig.DECRYPT_MODE);
		config.setAlgorithm("AES/CBC/PKCS5Padding");
		config.setKeySize(128);
		System.out.println("Retrieving keys for layer 1 decryption");
		config.setSymmetricKey(SymmetricKeyManager.getInstance().retrieveSecretKey("aes_key"));
		config.setInstanceId(UUID.randomUUID().toString());
		
		System.out.println("AES :: Decrypting int1");
		BaseInt  decorator = new AESCBCDecorator(new CryptoInt(ei), config);
		System.out.println("AES :: Decrypted int1 :: " + decorator.getValue());
		
		return (BigInteger)decorator.getValue();
	}

}
