package com.github.ss111;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.map.MultiValueMap;

/**
 * ConflictHelper is used to help with conflicts. It's main functionality includes the ability determine if an ID is conflicting, and to generate sentences stating the conflicts and where the conflicts are located.
 * @author SS111
 */
public class ConflictHelper {
	
	private static ArrayList<Integer> conflictingBlocks = new ArrayList<Integer>();
	private static ArrayList<Integer> conflictingItems = new ArrayList<Integer>();
	
	/**
	 * Determines if a block/item is has a conflicting ID.
	 * <p>
	 * Optionally, by passing the type argument (either BLOCK or ITEM), ConflictHelper will store conflicting block/item IDs in their respective ArrayLists which can then be retrieved by calling ConfigHelper.getConflictingBlocks() or ConfigHelper.getConflictingItems().
	 * @author SS111
	 * @param map a <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a> populated by ConfigHelper
	 * @param key the block/item ID
	 * @param type the map type (BLOCK or ITEM)
	 * @return Boolean
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see com.github.ss111.ConfigHelper
	 * @see ConflictHelper#getConflictingBlocks()
	 * @see ConflictHelper#getConflictingItems()
	 */
	public static Boolean isConflicting(MultiValueMap map, Integer key, String type) {
		
		@SuppressWarnings("unchecked")
		Collection<ArrayList<String>> names = map.getCollection(key);
	
		if (names.size() == 1) {
			
			return false;
			
		} else {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
			
			if (namesArray.toArray()[0].toString().equals(namesArray.toArray()[1].toString())) {
				
				return false;
				
			} else {
				
				if (type == "BLOCK") {
					
					conflictingBlocks.add(key);
						
				} else if (type == "ITEM") {
					
					conflictingItems.add(key);
				}
				
				return true;
			}
		}
	}
	
	/**
	 * Determines if a block/item is has a conflicting ID.
	 * @author SS111
	 * @param map a <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a> populated by ConfigHelper
	 * @param key the block/item ID
	 * @return Boolean
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see com.github.ss111.ConfigHelper
	 */
	public static Boolean isConflicting(MultiValueMap map, Integer key) {
		
		@SuppressWarnings("unchecked")
		Collection<ArrayList<String>> names = map.getCollection(key);
	
		if (names.size() == 1) {
			
			return false;
			
		} else {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
			
			if (namesArray.toArray()[0].toString().equals(namesArray.toArray()[1].toString())) {
				
				return false;
				
			} else {
				
				return true;
			}
		}
	}
	
	/**
	 * Returns the name of the block/item that corresponds to its ID.
	 * @author SS111
	 * @param map a <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a> populated by ConfigHelper
	 * @param key the block/item ID
	 * @return String
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see com.github.ss111.ConfigHelper
	 */
	public static String getName(MultiValueMap map, Integer key) {
		
		@SuppressWarnings("unchecked")
		Collection<ArrayList<String>> names = map.getCollection(key);
		
		String name = new String();
		
		for (ArrayList<String> combo : names) {
			
			name = combo.toArray()[0].toString();
			break;
		}
		
		return name;
		
	}
	
	/**
	 * Returns a sentence that tells what each block/item conflicts with.
	 * @author SS111
	 * @param map a <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a> populated by ConfigHelper
	 * @param key key the block/item ID
	 * @return String
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see com.github.ss111.ConfigHelper
	 */
	public static String getConflictString(MultiValueMap map, Integer key) {
		
		@SuppressWarnings("unchecked")
		Collection<ArrayList<String>> names = map.getCollection(key);
		
		String conflictString = "";
		
		ArrayList<String> namesArray = new ArrayList<String>();
		
		for (ArrayList<String> combo : names) {
			
			namesArray.add(combo.toArray()[0].toString());
		}
		
		for (int i = 0; i <= names.size() - 1; i++) {
			
			if (i == 0) {
				
				conflictString += namesArray.toArray()[i] + " conflicts with ";
				
			} else if (i == names.size() - 1) {
				
				conflictString += namesArray.toArray()[i] + ".";
				
			} else if (i == names.size() - 2) {
				
				conflictString += namesArray.toArray()[i] + ", and ";
				
			} else {
		
				conflictString += namesArray.toArray()[i] + ", ";
			}
		}
		
		return conflictString;
	}
	
	/**
	 * Returns a sentence that states the configuration files that have conflicts
	 * <p>
	 * This is to be used in conjunction with ConfigHelper.getConflictString(MultiValueMap map, Integer key)
	 * @author SS111
	 * @param map a <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a> populated by ConfigHelper
	 * @param key the block/item ID
	 * @param type the map type (BLOCK or ITEM)
	 * @return String
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see com.github.ss111.ConfigHelper
	 * @see ConifgHelper#getConflictString(MultiValueMap map, Integer key) getConflictString
	 */
	public static String getConfigConflictString(MultiValueMap map, Integer key) {
		
		@SuppressWarnings("unchecked")
		Collection<ArrayList<String>> names = map.getCollection(key);
		
		String conflictString = "These conflicts are located in ";
		
		ArrayList<String> namesArray = new ArrayList<String>();
		
		for (ArrayList<String> combo : names) {
			
			namesArray.add(combo.toArray()[1].toString());
		}
		
		for (int i = 0; i <= names.size() - 1; i++) {
			
			if (i == names.size() - 1) {
				
				conflictString += namesArray.toArray()[i] + ".";
				
			} else if (i == names.size() - 2) {
				
				conflictString += namesArray.toArray()[i] + ", and ";
				
			} else {
				
				conflictString += namesArray.toArray()[i] + ", ";
			}
		}
		
		return conflictString;
	}
	
	/**
	 * Returns an {@link ArrayList} of block IDs that are conflicting.
	 * @return ArrayList<Integer>
	 * @see ArrayList
	 */
	public static ArrayList<Integer> getConflictingBlocks() {
		
		return conflictingBlocks;
	}
	
	/**
	 * Returns an {@link ArrayList} of item IDs that are conflicting.
	 * @return ArrayList<Integer>
	 * @see ArrayList
	 */
	public static ArrayList<Integer> getConflictingItems() {
				
		return conflictingItems;
	}
}
