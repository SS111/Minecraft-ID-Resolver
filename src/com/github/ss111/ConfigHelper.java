package com.github.ss111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.collections.map.MultiValueMap;

public class ConfigHelper {
	
	private static BufferedReader configReader;
	private static String configLine;
	private static MultiValueMap blockIDs = new MultiValueMap();
	private static MultiValueMap itemIDs = new MultiValueMap();
	private static MultiValueMap unknownIDs = new MultiValueMap();
	private static Boolean blockComingUp = false;
	private static Boolean itemComingUp = false;
		
	public static void populateMaps(String path) {
		
		File configDirectory = new File(path);
		
		File[] configFiles = configDirectory.listFiles();
		
		for (File configFile : configFiles) {
			
			if (configFile.isFile() & configFile.getAbsolutePath().contains(".cfg") || configFile.getAbsolutePath().contains(".txt")) {
				
				try {
					
					configReader = new BufferedReader(new FileReader(configFile.getAbsolutePath()));
					
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				
				try {
					
					while ((configLine = configReader.readLine()) != null) {
						
						if (configLine == null) {
							
							continue;
							
						} else if (configLine.contains("}") && blockComingUp == true) {
						
							blockComingUp = false;
							continue;
							
						} else if (configLine.contains("}") && itemComingUp == true) {
						
							itemComingUp = false;
							continue;
							
						} else if (configLine.equals("# block")) {
							
							blockComingUp = true;
							continue;
							
						} else if (configLine.equals("# item")) {
							
							itemComingUp = true;
							continue;
							
						} else if (configLine.contains("I:") && blockComingUp == true) {
							
							String[] blockNameSplit = configLine.split("=");
							
							ArrayList<String> blockAndConfig = new ArrayList<String>();
							blockAndConfig.add(blockNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
							blockAndConfig.add(getPrettyName(configFile.getAbsolutePath()));
							
							blockIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), blockAndConfig);
							continue;
							
						} else if (configLine.contains("I:") && itemComingUp == true) {
							
							String[] itemNameSplit = configLine.split("=");
							
							ArrayList<String> itemAndConfig = new ArrayList<String>();
							itemAndConfig.add(itemNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
							itemAndConfig.add(getPrettyName(configFile.getAbsolutePath()));
							
							itemIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), itemAndConfig);
							continue;
							
							//& or &&?
						} else if (configLine.contains("I:") & blockComingUp == false & itemComingUp == false) {
							
							if (configLine.contains("block") || configLine.contains("Block") || configLine.contains("BLOCK") || configLine.contains("item") || configLine.contains("Item") || configLine.contains("ITEM")) {
								
								String[] nameSplit = configLine.split("=");
								
								ArrayList<String> unknownAndConfig = new ArrayList<String>();
								unknownAndConfig.add(nameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
								unknownAndConfig.add(getPrettyName(configFile.getAbsolutePath()));
								
								unknownIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), unknownAndConfig);
								continue;
								
							} else {
								
								continue;
							}
							
						} else {
							
							continue;
						}
					}
					
					configReader.close();
					
				} catch (IOException e) {

					e.printStackTrace();
				}
			}	
		}
		
		File[] configDirectories = configDirectory.listFiles();
		
		for (File configDir : configDirectories) {
			
			if (configDir.isDirectory()) {
				
				File[] configFilesDir = configDir.listFiles();
				
				for (File configFile : configFilesDir) {
					
					if (configFile.isFile() & configFile.getAbsolutePath().contains(".cfg") || configFile.getAbsolutePath().contains(".txt")) {
						
						try {
							
							configReader = new BufferedReader(new FileReader(configFile.getAbsolutePath()));
							
						} catch (FileNotFoundException e) {
							
							e.printStackTrace();
						}
						
						try {
							
							while ((configLine = configReader.readLine()) != null) {
								
								if (configLine == null) {
									
									continue;
									
								} else if (configLine.contains("}") && blockComingUp == true) {
								
									blockComingUp = false;
									continue;
									
								} else if (configLine.contains("}") && itemComingUp == true) {
								
									itemComingUp = false;
									continue;
									
								} else if (configLine.equals("# block")) {
									
									blockComingUp = true;
									continue;
									
								} else if (configLine.equals("# item")) {
									
									itemComingUp = true;
									
								} else if (configLine.contains("I:") && blockComingUp == true) {
									
									String[] blockNameSplit = configLine.split("=");
									
									ArrayList<String> blockAndConfig = new ArrayList<String>();
									blockAndConfig.add(blockNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
									blockAndConfig.add(getPrettyName(configFile.getAbsolutePath()));
									
									blockIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), blockAndConfig);
									continue;
									
								} else if (configLine.contains("I:") && itemComingUp == true) {
									
									String[] itemNameSplit = configLine.split("=");
									
									ArrayList<String> itemAndConfig = new ArrayList<String>();
									itemAndConfig.add(itemNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
									itemAndConfig.add(getPrettyName(configFile.getAbsolutePath()));
									
									itemIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), itemAndConfig);
									continue;
									
									//& or &&?
								} else if (configLine.contains("I:") & blockComingUp == false & itemComingUp == false) {
									
									if (configLine.contains("block") || configLine.contains("Block") || configLine.contains("BLOCK") || configLine.contains("item") || configLine.contains("Item") || configLine.contains("ITEM")) {
										
										String[] nameSplit = configLine.split("=");
										
										ArrayList<String> unknownAndConfig = new ArrayList<String>();
										unknownAndConfig.add(nameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
										unknownAndConfig.add(getPrettyName(configFile.getAbsolutePath()));
										
										unknownIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), unknownAndConfig);
										continue;
										
									} else {
										
										continue;
									}
									
								} else {
									
									continue;
								}
							}
							
							configReader.close();
							
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private static String getPrettyName(String input) {
		
		if (input.contains("\\")) {
			
			return input.substring(input.lastIndexOf("\\") + 1);
			
		} else {
			
			return input.substring(input.lastIndexOf("/") + 1);
		}
	}
	
	public static MultiValueMap getBlockIDs() {
		
		return blockIDs;
	}
	
	public static MultiValueMap getItemIDs() {
		
		return itemIDs;
	}
	
	public static MultiValueMap getUnknownIDs() {
		
		return unknownIDs;
	}
}
