package org.crypto.driver.util;

import java.security.KeyStoreException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MultiMap;

public class KeyStoreIOHandler {
	
	private static HazelcastInstance hazelcast;
	private static KeyStoreIOHandler instance;
	private final static Logger logger = Logger.getLogger(KeyStoreIOHandler.class.getName());
	public final static String KEY_STORE_NAME = "keystore.multimap";
	
	private KeyStoreIOHandler() throws KeyStoreException {
		if (hazelcast == null) {
			Config cfg = new Config();
			cfg.setInstanceName("cassandra-keystore");
			hazelcast = Hazelcast.getOrCreateHazelcastInstance(cfg);			
		}
	}
	
	public static KeyStoreIOHandler getInstance() {
		synchronized (KeyStoreIOHandler.class) {
			if (instance == null) {
				try {
					instance = new KeyStoreIOHandler();
					logger.info(MessageFormat.format("{0}:Instantiated.", KeyStoreIOHandler.class.getName()));
				} catch (KeyStoreException e) {
					logger.log(Level.SEVERE, MessageFormat.format("{0}"+e.getMessage(), KeyStoreIOHandler.class.getName()), e);
				}
			}
			return instance;
		}
	}
	
	private MultiMap<String, Object> loadKeyStore() {
		return hazelcast.getMultiMap(KEY_STORE_NAME);
	}
	
	public void storeKeys(String alias, Object key) {
		MultiMap<String, Object> keystore = this.loadKeyStore();
		keystore.put(alias, key);
	}
	
	public Collection<Object> getKeys(String alias) {
		return hazelcast.getMultiMap(KEY_STORE_NAME).get(alias);
	}
	
	public void removeKeys(List<String> aliasList) {
		MultiMap<String, Object> keystore = hazelcast.getMultiMap(KEY_STORE_NAME);
		for (String alias : aliasList) {
			keystore.remove(alias);
		}
	}
	
	public void removeKey(String alias) {
		MultiMap<String, Object> keystore = hazelcast.getMultiMap(KEY_STORE_NAME);
		keystore.remove(alias);
	}
	
	public void clear() {
		MultiMap<String, Object> keystore = hazelcast.getMultiMap(KEY_STORE_NAME);
		keystore.clear();
	}
	
	public int getKeyStoreSize() {
		return hazelcast.getMultiMap(KEY_STORE_NAME).size();
	}

}
