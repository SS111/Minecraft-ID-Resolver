package com.github.ss111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class IdDumpHelper {
	
	private static BufferedReader dumpReader;
	private static ArrayList<Integer> unusedBlockIDs = new ArrayList<Integer>(5000);
	private static ArrayList<Integer> unusedItemIDs = new ArrayList<Integer>(5000);
	private static String dumpLine;
	
	public static void populateUnusedIDs(String path) {
		
		File idMap = new File(path);
		
		try {
			
			dumpReader = new BufferedReader(new FileReader(idMap));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		try {
			
			while ((dumpLine = dumpReader.readLine()) != null) {
				
				if (dumpLine == null) {
					
					continue;
					
				} else if (dumpLine.contains("Name:")) {
					
					continue;
					
				} else if (dumpLine.contains("Block")) {
						
						if (Integer.valueOf(dumpLine.replace("Block. ", "").replace("Unused ID: ", "")) <= 421) {
							
							continue;
							
						} else {
							
							unusedBlockIDs.add(Integer.valueOf(dumpLine.replace("Block. ", "").replace("Unused ID: ", "")));
							continue;
						}
						
				} else {
					
					if (Integer.valueOf(dumpLine.replace("Item. ", "").replace("Unused ID: ", "")) <= 421) {
						
						continue;
						
					} else {
						
						unusedItemIDs.add(Integer.valueOf(dumpLine.replace("Item. ", "").replace("Unused ID: ", "")));
						continue;
					}
				}
			}
						
			dumpReader.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Integer> getUnusedBlockIDs() {
		
		return unusedBlockIDs;
	}
	
	public static ArrayList<Integer> getUnusedItemIDs() {
		
		if (unusedItemIDs.size() == 0) {
			
			JOptionPane.showMessageDialog(null, "There are no unused item IDs. Are you sure you dumped them? The program cannot continue and will now close.", "Information", JOptionPane.ERROR_MESSAGE);
		}
		
		return unusedItemIDs;
	}

}
