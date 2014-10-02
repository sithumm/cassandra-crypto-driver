package org.crypto.driver.function;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.crypto.driver.types.CryptoConfig;

public class AESCryptoFunction implements ICryptoFunction {
	
	private CryptoConfig config;
	private Object value;

	public AESCryptoFunction(Object value, CryptoConfig config) {
		this.config = config;
		this.value = value;
	}

	public Object encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		Cipher c = null;
		IvParameterSpec iv = null;
		if (this.config.getAlgorithm() == null || "".equalsIgnoreCase(this.config.getAlgorithm())) {
			c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} else {
//			System.out.println(this.config.getAlgorithm());
			c = Cipher.getInstance(this.config.getAlgorithm());
		}
		if (this.config.getIv() == null || !(this.config.getIv().length > 0)) {
			iv = new IvParameterSpec(new byte[16]);
		} else {
			iv = new IvParameterSpec(this.config.getIv());
//			System.out.println(this.config.getIv());
		}
		c.init(Cipher.ENCRYPT_MODE, this.config.getSymmetricKey(), iv);
		byte [] text = ((BigInteger)this.value).toByteArray();
		byte [] encrypted = c.doFinal(text);
		return new BigInteger(encrypted);
	}

	public Object decrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		Cipher c = null;
		IvParameterSpec iv = null;
		if (this.config.getAlgorithm() == null || "".equalsIgnoreCase(this.config.getAlgorithm())) {
			c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} else {
			c = Cipher.getInstance(this.config.getAlgorithm());
		}
		if (this.config.getIv() == null || !(this.config.getIv().length > 0)) {
			iv = new IvParameterSpec(new byte[16]);
		} else {
//			System.out.println(this.config.getIv());
			iv = new IvParameterSpec(this.config.getIv());
		}
		c.init(Cipher.DECRYPT_MODE, this.config.getSymmetricKey(), iv);
		byte [] encrypted = ((BigInteger)this.value).toByteArray();
		byte [] text = c.doFinal(encrypted);
		return new BigInteger(text);
	}

}
