/**
 * 
 */
package org.crypto.driver.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import thep.paillier.PrivateKey;
import thep.paillier.PublicKey;

/**
 * @author sithum
 *
 */
public class KeyStoreIOHandlerTest {
	
	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	private KeyStoreIOHandler instance; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		instance = KeyStoreIOHandler.getInstance();
		instance.clear();
		instance.storeKeys("testKey1", publicKey);
		instance.storeKeys("testKey1", privateKey);
		instance.storeKeys("testKey2", privateKey);
		instance.storeKeys("testKey3", privateKey);
		instance.storeKeys("testKey4", new PrivateKey(256));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@BeforeClass
	public static void setUpBeforeClass() {
		privateKey = new PrivateKey(128);
		publicKey = privateKey.getPublicKey();
	}

	/**
	 * Test method for {@link org.crypto.driver.util.KeyStoreIOHandler#getInstance()}.
	 */
	@Test
	public void testGetInstance() throws Exception {
		KeyStoreIOHandler instance = KeyStoreIOHandler.getInstance();
		Assert.assertNotNull(instance);
		Assert.assertTrue(instance instanceof KeyStoreIOHandler);
		Assert.assertEquals(instance.hashCode(), KeyStoreIOHandler.getInstance().hashCode());
	}

	/**
	 * Test method for {@link org.crypto.driver.util.KeyStoreIOHandler#storeKeys(java.lang.String, java.lang.Object)}.
	 */
	@Test
	public void testStoreKeys() throws Exception {
		instance.storeKeys("testKey5", privateKey);
		instance.storeKeys("testKey6", privateKey);
		Assert.assertEquals(7, instance.getKeyStoreSize());
	}

	/**
	 * Test method for {@link org.crypto.driver.util.KeyStoreIOHandler#getKeys(java.lang.String)}.
	 */
	@Test
	public void testGetKeys() throws Exception {
		KeyStoreIOHandler ioHandler = KeyStoreIOHandler.getInstance();
		Assert.assertEquals(2, ioHandler.getKeys("testKey1").size());
	}

	/**
	 * Test method for {@link org.crypto.driver.util.KeyStoreIOHandler#removeKeys(java.util.List)}.
	 */
	@Test
	public void testRemoveKeys() throws Exception {
		KeyStoreIOHandler ioHandler = KeyStoreIOHandler.getInstance();
		List<String> aliasList = new ArrayList<String>();
		aliasList.add("testKey6");
		aliasList.add("testKey5");
		ioHandler.removeKeys(aliasList);
		Assert.assertEquals(5, ioHandler.getKeyStoreSize());
	}

	/**
	 * Test method for {@link org.crypto.driver.util.KeyStoreIOHandler#removeKey(java.lang.String, java.lang.Object)}.
	 */
	@Test
	public void testRemoveKey() throws Exception {
		KeyStoreIOHandler ioHandler = KeyStoreIOHandler.getInstance();
		ioHandler.removeKey("testKey4");
		Assert.assertEquals(4, ioHandler.getKeyStoreSize());
	}

}
