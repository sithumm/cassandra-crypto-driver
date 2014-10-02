package org.crypto.driver.wrapper;

import org.crypto.driver.types.CryptoConfig;
import org.crypto.driver.types.BaseInt;

public class BaseIntDecorator extends BaseInt {

	protected final BaseInt baseInt;
	protected final CryptoConfig config;
	
	
	public BaseIntDecorator(BaseInt baseInt, CryptoConfig config) {
		this.baseInt = baseInt;
		this.config = config;
	}
	
	@Override
	public Object getValue() {
		return this.baseInt.getValue();
	}
	
}
