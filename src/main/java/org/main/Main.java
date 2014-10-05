package org.main;

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
import org.crypto.driver.wrapper.AESDecorator;
import org.crypto.driver.wrapper.PaillierDecorator;

import thep.paillier.EncryptedInteger;
import thep.paillier.exceptions.BigIntegerClassNotValid;
import thep.paillier.exceptions.PublicKeysNotEqualException;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, BigIntegerClassNotValid, PublicKeysNotEqualException {
		BaseInt int1 = new CryptoInt(new BigInteger("8000"));
		BaseInt int2 = new CryptoInt(new BigInteger("13000"));
		
		System.out.println("Calling Wrapper for encryption");
		
		CustomKeyPair keyPair = AssymetricKeyManager.getInstance().generateKeyPair(256);
		AssymetricKeyManager.getInstance().persistKeys("test_he_pair1", keyPair);
		Key key = SymmetricKeyManager.getInstance().generateSecretKey("AES", 128);
		SymmetricKeyManager.getInstance().persistKeys("test_aes_key", key);
		
		System.out.println("Key generation complete.\n");
		
		System.out.println("====================================================================================\n");
		
		System.out.println("Start encrypting int1");
		BaseInt encrypted1 = encrypt(int1);
		System.out.println("Finished encrypting int1 :: " + encrypted1.getValue());
		
		System.out.println("\n------------------------------------------------------------------------------------\n");
		save();
		System.out.println("\n------------------------------------------------------------------------------------\n");
				
		System.out.println("Start encrypting int2");
		BaseInt encrypted2 = encrypt(int2);
		System.out.println("Finished encrypting int2 :: " + encrypted2.getValue());
		
		System.out.println("\n------------------------------------------------------------------------------------\n");
		save();
		System.out.println("\n------------------------------------------------------------------------------------\n");
		
		System.out.println("====================================================================================\n");
		
		System.out.println("Calling Wrapper for decryption");
		
		System.out.println("Start decrypting int1");
		BaseInt decrypted1 = decrypt(encrypted1);
		System.out.println("Finished decrypting int1 :: " + decrypted1.getValue());
		
		System.out.println("Start decrypting int2");
		BaseInt decrypted2 = decrypt(encrypted2);
		System.out.println("Finished decrypting int2 :: " + decrypted2.getValue());
		
		System.out.println("====================================================================================\n");
		System.out.println("Calling calculation server....");
		System.out.println("Performing :: " + decrypted1.getValue() + " + " + decrypted2.getValue());
		EncryptedInteger integer1 = new EncryptedInteger(keyPair.getPublicKey());
		integer1.setCipherVal((BigInteger) decrypted1.getValue());
		
		EncryptedInteger integer2 = new EncryptedInteger(keyPair.getPublicKey());
		integer2.setCipherVal((BigInteger) decrypted2.getValue());
		
		EncryptedInteger added = integer1.add(integer2);
		System.out.println("Added encrypted result :: " + added.getCipherVal());
		
		System.out.println("Encrypted calculation finished. Returnning encrypted result.");
	
		System.out.println("====================================================================================\n");
		System.out.println("Decrypted result :: " + added.decrypt(keyPair.getPrivateKey()));
	}
	
	private static void save() {
		System.out.println("Saving to database completed.");
	}

	public static BaseInt encrypt (BaseInt int1) throws NoSuchAlgorithmException {
		
		CryptoConfig config = new CryptoConfig();
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		config.setAlgorithm("HE/Paillier");
		config.setHeKeyPair(AssymetricKeyManager.getInstance().retrieveKeyPair("test_he_pair1"));
		config.setInstanceId(UUID.randomUUID().toString());
		
		System.out.println("HE :: Encrypting int1");
		BaseInt decorator = new PaillierDecorator(int1, config);
		System.out.println("HE :: Encrypted int1 :: " + decorator.getValue());
		
		config = null;
		config = new CryptoConfig();
		config.setMode(CryptoConfig.ENCRYPT_MODE);
		config.setAlgorithm("AES/CBC/PKCS5Padding");
		config.setKeySize(128);
		config.setSymmetricKey(SymmetricKeyManager.getInstance().retrieveSecretKey("test_aes_key"));
		config.setInstanceId(UUID.randomUUID().toString());
		
		System.out.println("AES :: Encrypting int1");
		decorator = new AESDecorator(decorator, config);
		System.out.println("AES :: Encrypted int1 :: " + decorator.getValue());
		
		return decorator;
	}
	
	public static BaseInt decrypt (BaseInt int1) {
		CryptoConfig config = new CryptoConfig();
		config.setMode(CryptoConfig.DECRYPT_MODE);
		config.setAlgorithm("AES/CBC/PKCS5Padding");
		config.setKeySize(128);
		System.out.println("Retrieving keys for layer 1 decryption");
		config.setSymmetricKey(SymmetricKeyManager.getInstance().retrieveSecretKey("test_aes_key"));
		config.setInstanceId(UUID.randomUUID().toString());
		
		System.out.println("AES :: Decrypting int1");
		BaseInt  decorator = new AESDecorator(int1, config);
		System.out.println("AES :: Decrypted int1 :: " + decorator.getValue());
		
		/*config = null;
		config = new CryptoConfig();
		config.setMode(CryptoConfig.DECRYPT_MODE);
		config.setAlgorithm("HE/Paillier");
		config.setHeKeyPair(AssymetricKeyManager.getInstance().retrieveKeyPair("test_he_pair1"));
		config.setInstanceId(UUID.randomUUID().toString());
		
		System.out.println("HE :: Decrypting int1");
		decorator = new HEDecorator(decorator, config);
		System.out.println("HE :: Decrypted int1 :: " + decorator.getValue());*/
		
		return decorator;
	}

}
