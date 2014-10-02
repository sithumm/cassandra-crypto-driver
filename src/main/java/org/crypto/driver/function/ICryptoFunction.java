package org.crypto.driver.function;

import java.math.BigInteger;

public interface ICryptoFunction {
	
	public final static BigInteger THRESHOLD = new BigInteger("100000000000000000000000000000000000000");

	public Object encrypt() throws Exception;
	
	public Object decrypt() throws Exception;
	
}
