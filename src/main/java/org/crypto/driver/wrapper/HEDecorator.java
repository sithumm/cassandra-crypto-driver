package org.crypto.driver.wrapper;

import org.crypto.driver.function.HECryptoFunction;
import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.BaseInt;

import thep.paillier.exceptions.BigIntegerClassNotValid;

public class HEDecorator extends BaseIntDecorator {

	public HEDecorator(BaseInt baseInt, CryptoConfig config) {
		super(baseInt, config);
	}
	
	@Override
	public Object getValue() {
		HECryptoFunction function = new HECryptoFunction(super.getValue(), config);
		if (CryptoConfig.ENCRYPT_MODE.equalsIgnoreCase(config.getMode())) {
			try {
				return function.encrypt();
			} catch (BigIntegerClassNotValid e) {
				e.printStackTrace();
			}			
		} else if (CryptoConfig.DECRYPT_MODE.equalsIgnoreCase(config.getMode())) {
			try {
				return function.decrypt();
			} catch (BigIntegerClassNotValid e) {
				e.printStackTrace();
			}
		}
		return super.getValue();
	}

}
