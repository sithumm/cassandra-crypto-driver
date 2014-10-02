package org.crypto.driver.keymanagement;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

import org.crypto.driver.util.KeyStoreIOHandler;

import thep.paillier.PrivateKey;
import thep.paillier.PublicKey;

public class AssymetricKeyManager implements SecureKeyManager {
	
	private static AssymetricKeyManager instance;
	
	private AssymetricKeyManager(){
		
	}
	
	public static AssymetricKeyManager getInstance() {
		synchronized (AssymetricKeyManager.class) {
			if (instance == null) {
				instance = new AssymetricKeyManager();
			}
			return instance;
		}
	}

	public CustomKeyPair generateKeyPair(int keySize) {
		PrivateKey privateKey = new PrivateKey(keySize);
		PublicKey publicKey = privateKey.getPublicKey();
		CustomKeyPair kp = new CustomKeyPair(publicKey, privateKey);
		return kp;
	}

	public Key generateSecretKey(String algorithm, int keySize)
			throws NoSuchAlgorithmException {
		throw new UnsupportedOperationException("This operation is not supported.");
	}

	public Key retrieveSecretKey(String alias) {
		throw new UnsupportedOperationException("This operation is not supported.");
	}

	public void persistKeys(String alias, Object obj) {
		KeyStoreIOHandler.getInstance().storeKeys(alias, obj);			
	}

	public CustomKeyPair retrieveKeyPair(String alias) {
		Collection<Object> keys = KeyStoreIOHandler.getInstance().getKeys(alias);
		for (Object obj : keys) {
			if (obj instanceof CustomKeyPair) {
				return (CustomKeyPair) obj;
			}
		}
		return null;
	}

	public void removeKey(String alias) {
		KeyStoreIOHandler.getInstance().removeKey(alias);
	}

	public void removeKeys(List<String> aliases) {
		KeyStoreIOHandler.getInstance().removeKeys(aliases);
	}

}
