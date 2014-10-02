package org.crypto.driver.types;

public class CryptoInt extends BaseInt {
	
	private Object value;
	
	public CryptoInt(Object value) {
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

}
