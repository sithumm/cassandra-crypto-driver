/**
 * 
 */
package org.crypto.driver.keymanagement;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.crypto.driver.util.KeyStoreIOHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sithum
 *
 */
public class SymmetricKeyManagerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
//		KeyStoreIOHandler.getInstance().clear();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.SymmetricKeyManager#getInstance()}.
	 */
	@Test
	public void testGetInstance() throws Exception {
		SymmetricKeyManager instance = SymmetricKeyManager.getInstance();
		Assert.assertNotNull(instance);
		Assert.assertEquals(instance, SymmetricKeyManager.getInstance());
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.SymmetricKeyManager#generateKeyPair(int)}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testGenerateKeyPair() throws Exception {
		SymmetricKeyManager instance = SymmetricKeyManager.getInstance();
		instance.generateKeyPair(128);
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.SymmetricKeyManager#generateSecretKey(java.lang.String, int)}.
	 */
	@Test
	public void testGenerateSecretKey() throws Exception {
		SymmetricKeyManager instance = SymmetricKeyManager.getInstance();
		Object actual = instance.generateSecretKey("AES", 256);
		Assert.assertTrue(actual instanceof Key);
	}
	
	/**
	 * Test method for {@link org.crypto.driver.keymanagement.SymmetricKeyManager#generateSecretKey(java.lang.String, int)}.
	 */
	@Test(expected = NoSuchAlgorithmException.class)
	public void testGenerateSecretKeyException() throws Exception {
		SymmetricKeyManager instance = SymmetricKeyManager.getInstance();
		Object actual = instance.generateSecretKey("TEST", 256);
		Assert.assertTrue(actual instanceof Key);
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.SymmetricKeyManager#retrieveKeyPair(java.lang.String)}.
	 */
	@Test(expected=UnsupportedOperationException.class)
	public void testRetrieveKeyPair() throws Exception {
		SymmetricKeyManager instance = SymmetricKeyManager.getInstance();
		instance.retrieveKeyPair("test");
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.SymmetricKeyManager#persistKeys(java.lang.String, java.lang.Object)}.
	 */
	@Test
	public void testPersistKeys() throws Exception {
		SymmetricKeyManager instance = SymmetricKeyManager.getInstance();
		Key actual = instance.generateSecretKey("AES", 256);
		instance.persistKeys("test1", actual);
		Assert.assertEquals(1, KeyStoreIOHandler.getInstance().getKeyStoreSize());
	}
	
	/**
	 * Test method for {@link org.crypto.driver.keymanagement.SymmetricKeyManager#retrieveSecretKey(java.lang.String)}.
	 */
	@Test
	public void testRetrieveSecretKey() throws Exception {
		SymmetricKeyManager instance = SymmetricKeyManager.getInstance();
		Key key = instance.retrieveSecretKey("test1");
//		Assert.assertNotNull(key);
	}


	/**
	 * Test method for {@link org.crypto.driver.keymanagement.SymmetricKeyManager#removeKey(java.lang.String, java.lang.Object)}.
	 */
	@Test
	public void testRemoveKey() throws Exception {
		SymmetricKeyManager instance = SymmetricKeyManager.getInstance();
		instance.removeKey("test1");
		Assert.assertEquals(0, KeyStoreIOHandler.getInstance().getKeyStoreSize());
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.SymmetricKeyManager#removeKeys(java.util.List)}.
	 */
	@Test
	public void testRemoveKeys() throws Exception {
		SymmetricKeyManager instance = SymmetricKeyManager.getInstance();
		Key actual = instance.generateSecretKey("AES", 256);
		instance.persistKeys("test1", actual);
		instance.persistKeys("test2", instance.generateSecretKey("AES", 192));
		Assert.assertEquals(2, KeyStoreIOHandler.getInstance().getKeyStoreSize());
		List<String> aliases = new ArrayList<String>();
		aliases.add("test1");
		aliases.add("test2");
		instance.removeKeys(aliases);
	}

}
