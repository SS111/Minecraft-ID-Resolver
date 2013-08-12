package com.github.ss111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * IdDumpHelper is used to help with NEI ID dumps. It's main functionality is the ability to parse NEI ID dumps and extract both the unused block IDs and the unused item IDs.
 * @author SS111
 */
public class IdDumpHelper {
	
	private static BufferedReader dumpReader;
	private static ArrayList<Integer> unusedBlockIDs = new ArrayList<Integer>();
	private static ArrayList<Integer> unusedItemIDs = new ArrayList<Integer>();
	private static String dumpLine;
	
	/**
	 * Populates 2 {@link ArrayList}s; one for unused block IDs and one for unused item IDs.
	 * @author SS111
	 * @param path path to an NEI ID dump
	 * @see ArrayList
	 */
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
	
	/**
	 * Returns an {@link ArrayList} of unused block IDs.
	 * @return ArrayList<Integer>
	 */
	public static ArrayList<Integer> getUnusedBlockIDs() {
		
		return unusedBlockIDs;
	}
	
	/**
	 * Returns an {@link ArrayList} of unused item IDs.
	 * <p>
	 * This will also check to see if the unused items array is empty (indicating that the user forget to dump the unused item IDs) and inform the user.
	 * @author SS111
	 * @return ArrayList<Integer>
	 * @see ArrayList
	 */
	public static ArrayList<Integer> getUnusedItemIDs() {
		
		if (unusedItemIDs.size() == 0) {
			
			JOptionPane.showMessageDialog(null, "There are no unused item IDs. Are you sure you dumped them? The program cannot continue and will now close.", "Information", JOptionPane.ERROR_MESSAGE);
			
			WindowMain.frmMain.dispose();
		}
				
		return unusedItemIDs;
	}

}
