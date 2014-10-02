package org.crypto.driver.function;

import java.math.BigInteger;

import org.crypto.driver.types.CryptoConfig;

import thep.paillier.EncryptedInteger;
import thep.paillier.exceptions.BigIntegerClassNotValid;

public class HECryptoFunction implements ICryptoFunction {
	
	private CryptoConfig config;
	private Object value;

	public HECryptoFunction(Object value, CryptoConfig config) {
		this.config = config;
		this.value = value;
	}

	public Object encrypt() throws BigIntegerClassNotValid {
		EncryptedInteger encInt = new EncryptedInteger((BigInteger)value, config.getHeKeyPair().getPublicKey());
		return encInt.getCipherVal();
	}

	public Object decrypt() throws BigIntegerClassNotValid {
		EncryptedInteger encryptedInteger = new EncryptedInteger(config.getHeKeyPair().getPublicKey());
		encryptedInteger.setCipherVal((BigInteger) this.value);
		BigInteger decInt = encryptedInteger.decrypt(this.config.getHeKeyPair().getPrivateKey());
		if (decInt.compareTo(ICryptoFunction.THRESHOLD) == 1) {
	    	decInt = decInt.subtract(this.config.getHeKeyPair().getPublicKey().getN());
	    }
		return decInt;
	}

}
