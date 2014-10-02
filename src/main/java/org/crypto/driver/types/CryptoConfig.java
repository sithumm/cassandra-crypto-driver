package org.crypto.driver.types;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import org.crypto.driver.keymanagement.CustomKeyPair;

public class CryptoConfig {
	
	public static final String ENCRYPT_MODE = "ENCRYPT_MODE";
	public static final String DECRYPT_MODE = "DECRYPT_MODE";
	
	private String algorithm;
	private String instanceId;
	private int keySize;
	private byte[] iv;
	private CustomKeyPair heKeyPair;
	private Key symmetricKey;
	private String mode;
	private List<String> executeSequence;
	
	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	
	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}
	
	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	/**
	 * @return the keySize
	 */
	public int getKeySize() {
		return keySize;
	}
	
	/**
	 * @param keySize the keySize to set
	 */
	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}
	
	/**
	 * @return the iv
	 */
	public byte[] getIv() {
		return iv;
	}
	
	/**
	 * @param iv the iv to set
	 */
	public void setIv(byte[] iv) {
		this.iv = iv;
	}

	/**
	 * @return the heKeyPair
	 */
	public CustomKeyPair getHeKeyPair() {
		return heKeyPair;
	}

	/**
	 * @param heKeyPair the heKeyPair to set
	 */
	public void setHeKeyPair(CustomKeyPair heKeyPair) {
		this.heKeyPair = heKeyPair;
	}

	/**
	 * @return the symmetricKey
	 */
	public Key getSymmetricKey() {
		return symmetricKey;
	}

	/**
	 * @param symmetricKey the symmetricKey to set
	 */
	public void setSymmetricKey(Key symmetricKey) {
		this.symmetricKey = symmetricKey;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the executeSequence
	 */
	public List<String> getExecuteSequence() {
		return executeSequence;
	}

	/**
	 * @param executeSequence the executeSequence to set
	 */
	public void setExecuteSequence(List<String> executeSequence) {
		this.executeSequence = executeSequence;
	}
	
	public void setExecuteSequenceItem(String decoratorType) {
		if (this.executeSequence == null) {
			this.executeSequence = new ArrayList<String>();
		}
		this.executeSequence.add(decoratorType);
	}

}