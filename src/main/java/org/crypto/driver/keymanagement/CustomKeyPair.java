package org.crypto.driver.keymanagement;

import java.io.Serializable;

import thep.paillier.PrivateKey;
import thep.paillier.PublicKey;

public final class CustomKeyPair implements Serializable {

	private static final long serialVersionUID = -7565189502268009839L;
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public CustomKeyPair(PublicKey publicKey, PrivateKey privateKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}
	
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}
	
	public PublicKey getPublicKey() {
		return this.publicKey;
	}
	
}