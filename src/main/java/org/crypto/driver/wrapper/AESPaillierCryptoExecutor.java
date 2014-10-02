package org.crypto.driver.wrapper;

import org.crypto.driver.types.BaseInt;
import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.CryptoInt;

public class AESPaillierCryptoExecutor implements ICryptoExecutor {
	
	private CryptoConfig config;

	public AESPaillierCryptoExecutor(CryptoConfig config) {
		this.config = config;
	}

	public Object execute(Object value) {
		BaseInt baseInt = new CryptoInt(value);
		for (String type: config.getExecuteSequence()) {
			baseInt = CryptoDecoratorFactory.getBaseIntDecorator(type, baseInt, config);
		}
		return baseInt.getValue();
	}

}
