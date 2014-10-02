package org.crypto.driver.keymanagement;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.crypto.driver.util.KeyStoreIOHandler;

public class SymmetricKeyManager implements SecureKeyManager {
	
	private static SymmetricKeyManager instance;
	
	private SymmetricKeyManager() {
	}
	
	public static SymmetricKeyManager getInstance() {
		synchronized (SymmetricKeyManager.class) {
			if (instance == null) {
				instance = new SymmetricKeyManager();
			}
			return instance;
		}
	}

	public CustomKeyPair generateKeyPair(int keySize) {
		throw new UnsupportedOperationException("This operation is not supported.");
	}

	public Key generateSecretKey(String algorithm, int keySize) throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
		keyGen.init(keySize);
		return keyGen.generateKey();
	}

	public CustomKeyPair retrieveKeyPair(String alias) {
		throw new UnsupportedOperationException("This operation is not supported.");
	}

	public Key retrieveSecretKey(String alias) {
		Collection<Object> keys = KeyStoreIOHandler.getInstance().getKeys(alias);
		for (Object obj : keys) {
			if (obj instanceof SecretKey) {
				return (SecretKey) obj;
			}
		}
		return null;
	}

	public void persistKeys(String alias, Object obj) {
		KeyStoreIOHandler.getInstance().storeKeys(alias, obj);
	}

	public void removeKey(String alias) {
		KeyStoreIOHandler.getInstance().removeKey(alias);
	}

	public void removeKeys(List<String> aliases) {
		KeyStoreIOHandler.getInstance().removeKeys(aliases);
	}

}