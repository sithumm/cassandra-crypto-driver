package org.crypto.driver.wrapper;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.crypto.driver.function.AESCryptoFunction;
import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.BaseInt;

public class AESDecorator extends BaseIntDecorator {

	public AESDecorator(BaseInt baseInt, CryptoConfig config) {
		super(baseInt, config);
	}
	
	@Override
	public Object getValue() {
		AESCryptoFunction function = new AESCryptoFunction(super.getValue(), config);
		if (CryptoConfig.ENCRYPT_MODE.equalsIgnoreCase(config.getMode())) {
			try {
				return function.encrypt();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (CryptoConfig.DECRYPT_MODE.equalsIgnoreCase(config.getMode())) {
			try {
				return function.decrypt();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.getValue();
	}

}
