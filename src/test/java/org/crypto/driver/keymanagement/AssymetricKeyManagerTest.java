/**
 * 
 */
package org.crypto.driver.keymanagement;

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
public class AssymetricKeyManagerTest {

	private AssymetricKeyManager instance;
	private KeyStoreIOHandler ioh;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		instance = AssymetricKeyManager.getInstance();
		Object keyPair = instance.generateKeyPair(128);
		ioh = KeyStoreIOHandler.getInstance();
		ioh.storeKeys("1", keyPair);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		instance = null;
		ioh.clear();
	}
	
	/**
	 * Test method for {@link org.crypto.driver.keymanagement.AssymetricKeyManager#getInstance()}.
	 */
	@Test
	public void testGetInstance() throws Exception {
		Assert.assertNotNull(instance);
		Assert.assertEquals(instance, AssymetricKeyManager.getInstance());
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.AssymetricKeyManager#generateKeyPair(int)}.
	 */
	@Test
	public void testGenerateKeyPair() throws Exception {
		Object keyPair = instance.generateKeyPair(128);
		Assert.assertNotNull(keyPair);
		Assert.assertTrue(keyPair instanceof CustomKeyPair);
		
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.AssymetricKeyManager#generateSecretKey(java.lang.String, int)}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testGenerateSecretKey() throws Exception {
		instance.generateSecretKey("AES", 256);
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.AssymetricKeyManager#retrieveSecretKey(java.lang.String)}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testRetrieveSecretKey() throws Exception {
		instance.retrieveSecretKey("test1");
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.AssymetricKeyManager#persistKeys(java.lang.String, java.lang.Object)}.
	 */
	@Test
	public void testPersistKeys() throws Exception {
		CustomKeyPair actual = instance.generateKeyPair(128);
		instance.persistKeys("test1", actual);
		Assert.assertEquals(2, KeyStoreIOHandler.getInstance().getKeyStoreSize());
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.AssymetricKeyManager#retrieveKeyPair(java.lang.String)}.
	 */
	@Test
	public void testRetrieveKeyPair() throws Exception {
		CustomKeyPair actual = instance.retrieveKeyPair("1");
		Assert.assertNotNull(actual);
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.AssymetricKeyManager#removeKey(java.lang.String)}.
	 */
	@Test
	public void testRemoveKey() throws Exception {
		Object actual = instance.generateKeyPair(128);
		instance.persistKeys("test1", actual);
		System.out.println(KeyStoreIOHandler.getInstance().getKeyStoreSize());
		Assert.assertEquals(2, KeyStoreIOHandler.getInstance().getKeyStoreSize());
		instance.removeKey("test1");
		Assert.assertEquals(1, KeyStoreIOHandler.getInstance().getKeyStoreSize());
	}

	/**
	 * Test method for {@link org.crypto.driver.keymanagement.AssymetricKeyManager#removeKeys(java.util.List)}.
	 */
	@Test
	public void testRemoveKeys() throws Exception {
		AssymetricKeyManager instance = AssymetricKeyManager.getInstance();
		Object actual = instance.generateKeyPair(128);
		instance.persistKeys("test1", actual);
		instance.persistKeys("test2", instance.generateKeyPair(256));
		Assert.assertEquals(3, KeyStoreIOHandler.getInstance().getKeyStoreSize());
		List<String> aliases = new ArrayList<String>();
		aliases.add("test1");
		aliases.add("test2");
		instance.removeKeys(aliases);
		Assert.assertEquals(1, KeyStoreIOHandler.getInstance().getKeyStoreSize());
	}
}
