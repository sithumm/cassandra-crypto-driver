package org.crypto.driver.keymanagement;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface SecureKeyManager {
	
	public CustomKeyPair generateKeyPair(int keySize);
	
	public Key generateSecretKey(String algorithm, int keySize) throws NoSuchAlgorithmException;
	
	public CustomKeyPair retrieveKeyPair(String alias);
	
	public Key retrieveSecretKey(String alias);
	
	public void persistKeys(String alias, Object obj);
	
	public void removeKey(String alias);
	
	public void removeKeys(List<String> aliases);

}
