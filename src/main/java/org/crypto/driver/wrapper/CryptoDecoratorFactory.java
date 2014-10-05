package org.crypto.driver.wrapper;

import org.crypto.driver.types.BaseInt;
import org.crypto.driver.types.CryptoConfig;

public class CryptoDecoratorFactory {
	
	public final static String AES_DECORATOR = "AES_DECORATOR";
	public final static String HE_DECORATOR = "HE_DECORATOR";

	public static BaseIntDecorator getBaseIntDecorator(String type, BaseInt baseInt, CryptoConfig config) {
		if (AES_DECORATOR.equalsIgnoreCase(type)) {
			return new AESDecorator(baseInt, config);
		} else {
			return new PaillierDecorator(baseInt, config);
		}
	}

}
