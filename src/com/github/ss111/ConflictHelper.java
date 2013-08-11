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
			ArrayList<String> configArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
				configArray.add(combo.toArray()[1].toString());
			}
			
			if (namesArray.toArray()[0].toString().equals(namesArray.toArray()[1].toString()) || configArray.toArray()[0].toString().equals(configArray.toArray()[1].toString())) {
				
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
	 * <p>
	 * This will only generate a sentence if there is less than 11 conflicts. Otherwise, it just states that there is 10+ conflicts.
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
		
		if (names.size() == 2) {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
			
			return "(" + namesArray.toArray()[0].toString() + " conflicts with " + namesArray.toArray()[1].toString() + ")";
			
		} else if (names.size() == 3) {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
			
			return "(" + namesArray.toArray()[0].toString() + " conflicts with " + namesArray.toArray()[1].toString() + " and " + namesArray.toArray()[2].toString() + ")";
			
		} else if (names.size() == 4) {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
			
			return "(" + namesArray.toArray()[0].toString() + " conflicts with " + namesArray.toArray()[1].toString() + ", " + namesArray.toArray()[2].toString() + ", and " + namesArray.toArray()[3].toString() + ")";
			
		} else if (names.size() == 5) {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
			
			return "(" + namesArray.toArray()[0].toString() + " conflicts with " + namesArray.toArray()[1].toString() + ", " + namesArray.toArray()[2].toString() + ", " + namesArray.toArray()[3].toString() + ", and " + namesArray.toArray()[4].toString() + ")";
			
		} else if (names.size() == 6) {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
			
			return "(" + namesArray.toArray()[0].toString() + " conflicts with " + namesArray.toArray()[1].toString() + ", " + namesArray.toArray()[2].toString() + ", " + namesArray.toArray()[3].toString() + ", " + namesArray.toArray()[4].toString() + ", and " + namesArray.toArray()[5].toString() + ")";
			
		} else if (names.size() == 7) {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
			
			return "(" + namesArray.toArray()[0].toString() + " conflicts with " + namesArray.toArray()[1].toString() + ", " + namesArray.toArray()[2].toString() + ", " + namesArray.toArray()[3].toString() + ", " + namesArray.toArray()[4].toString() + ", " + namesArray.toArray()[5].toString() + ", and" + namesArray.toArray()[6].toString() + ")";
			
		} else if (names.size() == 8) {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
				
		     return "(" + namesArray.toArray()[0].toString() + " conflicts with " + namesArray.toArray()[1].toString() + ", " + namesArray.toArray()[2].toString() + ", " + namesArray.toArray()[3].toString() + ", " + namesArray.toArray()[4].toString() + ", " + namesArray.toArray()[5].toString() + ", " + namesArray.toArray()[6].toString() + ", and" + namesArray.toArray()[7].toString() + ")";
		     
		} else if (names.size() == 9) {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
				
		     return "(" + namesArray.toArray()[0].toString() + " conflicts with " + namesArray.toArray()[1].toString() + ", " + namesArray.toArray()[2].toString() + ", " + namesArray.toArray()[3].toString() + ", " + namesArray.toArray()[4].toString() + ", " + namesArray.toArray()[5].toString() + ", " + namesArray.toArray()[6].toString() + ", " + namesArray.toArray()[7].toString() + ", and" + namesArray.toArray()[8].toString() + ")";
		     
		} else if (names.size() == 10) {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
				
		     return "(" + namesArray.toArray()[0].toString() + " conflicts with " + namesArray.toArray()[1].toString() + ", " + namesArray.toArray()[2].toString() + ", " + namesArray.toArray()[3].toString() + ", " + namesArray.toArray()[4].toString() + ", " + namesArray.toArray()[5].toString() + ", " + namesArray.toArray()[6].toString() + ", " + namesArray.toArray()[7].toString() + ", " + namesArray.toArray()[8].toString() + ", and" + namesArray.toArray()[9].toString() + ")";
		     
		} else {
			
			ArrayList<String> namesArray = new ArrayList<String>();
			
			for (ArrayList<String> combo : names) {
				
				namesArray.add(combo.toArray()[0].toString());
			}
			
			return namesArray.toArray()[0].toString() + " conflicts with 10+ blocks. If you see this message in the block or item tab, let me know on the forum!";
		}
	}
	
	/**
	 * Returns a sentence that states the configuration files that have conflicts
	 * <p>
	 * This is to be used in conjunction with ConfigHelper.getConflictString(MultiValueMap map, Integer key)
	 * <p>
	 * This will only generate a sentence if there is less than 11 conflicts. Otherwise, it just states that there is 10+ configuration files.
	 * @author SS111
	 * @param map a <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a> populated by ConfigHelper
	 * @param key the block/item ID
	 * @param type the map type (BLOCK or ITEM)
	 * @return String
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see com.github.ss111.ConfigHelper
	 * @see ConifgHelper#getConflictString(MultiValueMap map, Integer key) getConflictString
	 */
	public static String getConfigConflictString(MultiValueMap map, Integer key, String type) {
		
		@SuppressWarnings("unchecked")
		Collection<ArrayList<String>> names = map.getCollection(key);
		
		if (type == "BLOCK") {
			
			if (names.size() == 2) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks are located in " + configArray.toArray()[0].toString() +  " and " + configArray.toArray()[1].toString() + ".";
				
			} else if (names.size() == 3) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", and " + configArray.toArray()[2].toString() + ".";
				
			} else if (names.size() == 4) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", and " + configArray.toArray()[3].toString() + ".";
				
			} else if (names.size() == 5) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + ", and " + configArray.toArray()[4].toString() + ".";
				
			} else if (names.size() == 6) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", and " + configArray.toArray()[5].toString() + ".";
				
			} else if (names.size() == 7) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", and " + configArray.toArray()[6].toString() + ".";
				
			} else if (names.size() == 8) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", " + configArray.toArray()[6].toString() + ", and " + configArray.toArray()[7].toString() + ".";
				
			} else if (names.size() == 9) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", " + configArray.toArray()[6].toString() + ", " + configArray.toArray()[7].toString() + ", and " + configArray.toArray()[8].toString() + ".";
				
			} else if (names.size() == 10) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", " + configArray.toArray()[6].toString() + ", " + configArray.toArray()[7].toString() + ", " + configArray.toArray()[8].toString() + ", and " + configArray.toArray()[9].toString() + ".";
				
			} else {
				
				return "The above conflict's blocks are located in 10+ configuration files. If you see this message in the block or item tab, let me know on the forum!";
			}
			
		} else if (type == "ITEM") {
			
			if (names.size() == 2) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's items are located in " + configArray.toArray()[0].toString() +  " and " + configArray.toArray()[1].toString() + ".";
				
			} else if (names.size() == 3) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", and " + configArray.toArray()[2].toString() + ".";
				
			} else if (names.size() == 4) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", and " + configArray.toArray()[3].toString() + ".";
				
			} else if (names.size() == 5) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + ", and " + configArray.toArray()[4].toString() + ".";
				
			} else if (names.size() == 6) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", and " + configArray.toArray()[5].toString() + ".";
				
			} else if (names.size() == 7) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", and " + configArray.toArray()[6].toString() + ".";
				
			} else if (names.size() == 8) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", " + configArray.toArray()[6].toString() + ", and " + configArray.toArray()[7].toString() + ".";
				
			} else if (names.size() == 9) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", " + configArray.toArray()[6].toString() + ", " + configArray.toArray()[7].toString() + ", and " + configArray.toArray()[8].toString() + ".";
				
			} else if (names.size() == 10) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", " + configArray.toArray()[6].toString() + ", " + configArray.toArray()[7].toString() + ", " + configArray.toArray()[8].toString() + ", and " + configArray.toArray()[9].toString() + ".";
				
			} else {
				
				return "The above conflict's items are located in 10+ configuration files. If you see this message in the block or item tab, let me know on the forum!";
			}
			
		} else {
			
			if (names.size() == 2) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks/items are located in " + configArray.toArray()[0].toString() +  " and " + configArray.toArray()[1].toString() + ".";
				
			} else if (names.size() == 3) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks/items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", and " + configArray.toArray()[2].toString() + ".";
				
			} else if (names.size() == 4) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks/items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", and " + configArray.toArray()[3].toString() + ".";
				
			} else if (names.size() == 5) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks/items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + ", and " + configArray.toArray()[4].toString() + ".";
				
			} else if (names.size() == 6) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks/items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", and " + configArray.toArray()[5].toString() + ".";
				
			} else if (names.size() == 7) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks/items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", and " + configArray.toArray()[6].toString() + ".";
				
			} else if (names.size() == 8) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks/items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", " + configArray.toArray()[6].toString() + ", and " + configArray.toArray()[7].toString() + ".";
				
			} else if (names.size() == 9) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks/items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", " + configArray.toArray()[6].toString() + ", " + configArray.toArray()[7].toString() + ", and " + configArray.toArray()[8].toString() + ".";
				
			} else if (names.size() == 10) {
				
				ArrayList<String> configArray = new ArrayList<String>();
				
				for (ArrayList<String> combo : names) {
					
					configArray.add(combo.toArray()[1].toString());
				}
				
				return "The above conflict's blocks/items are located in " + configArray.toArray()[0].toString() + ", " + configArray.toArray()[1].toString() + ", " + configArray.toArray()[2].toString() + ", " + configArray.toArray()[3].toString() + "," + configArray.toArray()[4].toString() + ", " + configArray.toArray()[5].toString() + ", " + configArray.toArray()[6].toString() + ", " + configArray.toArray()[7].toString() + ", " + configArray.toArray()[8].toString() + ", and " + configArray.toArray()[9].toString() + ".";
				
			} else {
				
				return "The above conflict's blocks/items are located in 10+ configuration files. If you see this message in the block or item tab, let me know on the forum!";
			}
		}
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
