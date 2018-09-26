package org.openstates.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

import org.openstates.data.Legislator;

/**
 * Holds a {@link TreeMap} of {@link Legislator} objects.
 *
 */
public class Legislators {
	private static TreeMap<String, Legislator> legislators = new TreeMap<String, Legislator>();

	/**
	 * Get a Legislator from the map by its id.
	 * 
	 * @param id String
	 * @return legislator Legislator
	 */
	public static Legislator get(String id) {
		return legislators.get(id);
	}
	
	/**
	 * Put a Legislator into the map.
	 * 
	 * @param id String
	 * @param legislator Legislator
	 */
	public static void put(String id, Legislator legislator ) {
		legislators.put(id, legislator);
	}

	/**
	 * Get the KeySet of the Legislator map.
	 * 
	 * @return Set of Strings
	 */
	public static Set<String> keySet() {
		return legislators.keySet();
	}
	
	/**
	 * Get the values of the Legislator map.
	 * 
	 * @return Collection of Legislators
	 */
	public static Collection<Legislator> values() {
		return legislators.values();
	}
	
	/**
	 * Clear the legislator map.
	 * 
	 */
	public static void clear() {
		legislators.clear();
	}
	
	/**
	 * Access the Legislators map directly.
	 * 
	 * @return TreeMap of String by Legislator
	 */
	public static TreeMap<String, Legislator> legislators() {
		return legislators;
	}

}
