package com.github.ss111;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import org.apache.commons.collections.map.MultiValueMap;

public class ConflictHelper {
	
	private static ArrayList<Integer> conflictingBlocks = new ArrayList<Integer>(1000);
	private static ArrayList<Integer> conflictingItems = new ArrayList<Integer>(1000);
	
	public static Boolean isConflicting(MultiValueMap map, Integer key, String type) {
		
		@SuppressWarnings("unchecked")
		Collection<String> names = map.getCollection(key);
	
		if (names.size() == 1) {
			
			return false;
			
		} else {
			
			String[] namesArray = (String[]) names.toArray(new String[0]);
			
			if (namesArray[0].equals(namesArray[1])) {
				
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
	
	public static String getName(MultiValueMap map, Integer key) {
		
		@SuppressWarnings("unchecked")
		Collection<String> names = map.getCollection(key);
		
		String[] namesArray = (String[]) names.toArray(new String[0]);
		
		return namesArray[0];
		
	}
	
	public static String getConflictString(MultiValueMap map, Integer key) {
		
		@SuppressWarnings("unchecked")
		Collection<String> names = map.getCollection(key);
		
		if (names.size() == 2) {
			
			String[] namesArray = (String[]) names.toArray(new String[0]);
			
			return "(" + namesArray[0] + " conflicts with " + namesArray[1] + ")";
			
		} else if (names.size() == 3) {
			
			String[] namesArray = (String[]) names.toArray(new String[0]);
			
			return "(" + namesArray[0] + " conflicts with " + namesArray[1] + " and " + namesArray[2] + ")";
			
		} else if (names.size() == 4) {
			
			String[] namesArray = (String[]) names.toArray(new String[0]);
			
			return "(" + namesArray[0] + " conflicts with " + namesArray[1] + ", " + namesArray[2] + ", and " + namesArray[3] + ")";
			
		} else if (names.size() == 5) {
			
			String[] namesArray = (String[]) names.toArray(new String[0]);
			
			return "(" + namesArray[0] + " conflicts with " + namesArray[1] + ", " + namesArray[2] + ", " + namesArray[3] + ", and " + namesArray[4] + ")";
			
		} else if (names.size() == 6) {
			
			String[] namesArray = (String[]) names.toArray(new String[0]);
			
			return "(" + namesArray[0] + " conflicts with " + namesArray[1] + ", " + namesArray[2] + ", " + namesArray[3] + ", " + namesArray[4] + ", and " + namesArray[5] + ")";
			
		} else if (names.size() == 7) {
			
            String[] namesArray = (String[]) names.toArray(new String[0]);
			
			return "(" + namesArray[0] + " conflicts with " + namesArray[1] + ", " + namesArray[2] + ", " + namesArray[3] + ", " + namesArray[4] + ", " + namesArray[5] + ", and" + namesArray[6] + ")";
			
		} else if (names.size() == 8) {
			
			 String[] namesArray = (String[]) names.toArray(new String[0]);
				
		     return "(" + namesArray[0] + " conflicts with " + namesArray[1] + ", " + namesArray[2] + ", " + namesArray[3] + ", " + namesArray[4] + ", " + namesArray[5] + ", " + namesArray[6] + ", and" + namesArray[7] + ")";
		     
		} else if (names.size() == 9) {
			
			 String[] namesArray = (String[]) names.toArray(new String[0]);
				
		     return "(" + namesArray[0] + " conflicts with " + namesArray[1] + ", " + namesArray[2] + ", " + namesArray[3] + ", " + namesArray[4] + ", " + namesArray[5] + ", " + namesArray[6] + ", " + namesArray[7] + ", and" + namesArray[8] + ")";
		     
		} else if (names.size() == 10) {
			
			 String[] namesArray = (String[]) names.toArray(new String[0]);
				
		     return "(" + namesArray[0] + " conflicts with " + namesArray[1] + ", " + namesArray[2] + ", " + namesArray[3] + ", " + namesArray[4] + ", " + namesArray[5] + ", " + namesArray[6] + ", " + namesArray[7] + ", " + namesArray[8] + ", and" + namesArray[9] + ")";
		     
		} else {
			
			String[] namesArray = (String[]) names.toArray(new String[0]);
			
			return namesArray[0] + " conflicts with 10+ blocks/items. If you see this message on the block or item tab, let me know on the forum!";
		}
	}
	
	public static ArrayList<Integer> getConflictingBlocks() {
		
		if (conflictingBlocks.size() == 0) {
			
			JOptionPane.showMessageDialog(null, "There are no unused block IDs! Did you forget to dump them? The program cannot continue and will now close.", "Information", JOptionPane.ERROR_MESSAGE);
			
			WindowMain.frmMain.dispose();
		}
		
		return conflictingBlocks;
	}
	
	public static ArrayList<Integer> getConflictingItems() {
		
		if (conflictingItems.size() == 0) {
			
			JOptionPane.showMessageDialog(null, "There are no unused item IDs! Did you forget to dump them? The program cannot continue and will now close.", "Information", JOptionPane.ERROR_MESSAGE);
			
			WindowMain.frmMain.dispose();
		}
		
		return conflictingItems;
	}
}
