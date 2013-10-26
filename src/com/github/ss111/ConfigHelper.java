package com.github.ss111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.collections.map.MultiValueMap;

/**
 * ConfigHelper is used to help with configuration files. It's main funtionality is the ability to parse Forge configuration files and store information about conflicting blocks/items.
 * @author SS111
 */
public class ConfigHelper {
	
	private static BufferedReader configReader;
	private static String configLine;
	private static MultiValueMap blockIDs = new MultiValueMap();
	private static MultiValueMap itemIDs = new MultiValueMap();
	private static MultiValueMap unknownIDs = new MultiValueMap();
	private static Boolean blockComingUp = false;
	private static Boolean itemComingUp = false;
	
	/**
	 * Populates 3 <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>s with block/item information by parsing Forge configuration files. There will be a separate MultiValueMap for blocks, items, and unknown (IDs that may be block or items in an attempt to support non-supported configuration files).
	 * <p>
	 * The keys of the map will be the item/block ID.
	 * <p>
	 * The value(s) of the map will be {@link ArrayList}s. Each ArrayList will contain the name of the block/item, and the "pretty name" of the configuration file that the name came from.
	 * @author SS111
	 * @param path the path to the configuration directory
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see ArrayList
	 * @see ConfigHelper#getPrettyName(String)
	 */
	public static void populateMaps(String path) {
		
		File configDirectory = new File(path);
		
		File[] configFiles = configDirectory.listFiles();
		
		for (File configFile : configFiles) {
			
			if (configFile.isFile() & configFile.getAbsolutePath().contains(".cfg") || configFile.getAbsolutePath().contains(".txt") || configFile.getAbsolutePath().contains(".conf")) {
				
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
							
						} else if (configLine.contains("I:") && blockComingUp == true && configLine.matches(".*\\d.*")) {
							
							String[] blockNameSplit = configLine.split("=");
							
							ArrayList<String> blockAndConfig = new ArrayList<String>();
							blockAndConfig.add(blockNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
							blockAndConfig.add(getPrettyName(configFile.getAbsolutePath()));
							
							blockIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), blockAndConfig);
							continue;
							
						} else if (configLine.contains("I:") && itemComingUp == true && configLine.matches(".*\\d.*")) {
							
							String[] itemNameSplit = configLine.split("=");
							
							ArrayList<String> itemAndConfig = new ArrayList<String>();
							itemAndConfig.add(itemNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
							itemAndConfig.add(getPrettyName(configFile.getAbsolutePath()));
							
							itemIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), itemAndConfig);
							continue;
							
							//& or &&?
						} else if (configLine.contains("I:") & configLine.matches(".*\\d.*") & blockComingUp == false & itemComingUp == false) {
							
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
				
			} else if (configFile.isDirectory()) {
				
				File[] configFilesDir1 = configFile.listFiles();
				
				for (File configFile1 : configFilesDir1) {
					
					if (configFile1.isFile() & configFile1.getAbsolutePath().contains(".cfg") || configFile1.getAbsolutePath().contains(".txt") || configFile1.getAbsolutePath().contains(".conf")) {
						
						try {
							
							configReader = new BufferedReader(new FileReader(configFile1.getAbsolutePath()));
							
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
									
								} else if (configLine.contains("I:") && blockComingUp == true && configLine.matches(".*\\d.*")) {
									
									String[] blockNameSplit = configLine.split("=");
									
									ArrayList<String> blockAndConfig = new ArrayList<String>();
									blockAndConfig.add(blockNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
									blockAndConfig.add(getPrettyName(configFile1.getAbsolutePath()));
									
									blockIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), blockAndConfig);
									continue;
									
								} else if (configLine.contains("I:") && itemComingUp == true && configLine.matches(".*\\d.*")) {
									
									String[] itemNameSplit = configLine.split("=");
									
									ArrayList<String> itemAndConfig = new ArrayList<String>();
									itemAndConfig.add(itemNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
									itemAndConfig.add(getPrettyName(configFile1.getAbsolutePath()));
									
									itemIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), itemAndConfig);
									continue;
									
									//& or &&?
								} else if (configLine.contains("I:") & configLine.matches(".*\\d.*") & blockComingUp == false & itemComingUp == false) {
									
									if (configLine.contains("block") || configLine.contains("Block") || configLine.contains("BLOCK") || configLine.contains("item") || configLine.contains("Item") || configLine.contains("ITEM")) {
										
										String[] nameSplit = configLine.split("=");
										
										ArrayList<String> unknownAndConfig = new ArrayList<String>();
										unknownAndConfig.add(nameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
										unknownAndConfig.add(getPrettyName(configFile1.getAbsolutePath()));
										
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
						
					} else if (configFile1.isDirectory()) {
						
						File[] configFilesDir2 = configFile1.listFiles();
						
						for (File configFile2 : configFilesDir2) {
							
							if (configFile2.isFile() & configFile2.getAbsolutePath().contains(".cfg") || configFile2.getAbsolutePath().contains(".txt") || configFile2.getAbsolutePath().contains(".conf")) {
								
								try {
									
									configReader = new BufferedReader(new FileReader(configFile2.getAbsolutePath()));
									
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
											
										} else if (configLine.contains("I:") && blockComingUp == true && configLine.matches(".*\\d.*")) {
											
											String[] blockNameSplit = configLine.split("=");
											
											ArrayList<String> blockAndConfig = new ArrayList<String>();
											blockAndConfig.add(blockNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
											blockAndConfig.add(getPrettyName(configFile2.getAbsolutePath()));
											
											blockIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), blockAndConfig);
											continue;
											
										} else if (configLine.contains("I:") && itemComingUp == true && configLine.matches(".*\\d.*")) {
											
											String[] itemNameSplit = configLine.split("=");
											
											ArrayList<String> itemAndConfig = new ArrayList<String>();
											itemAndConfig.add(itemNameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
											itemAndConfig.add(getPrettyName(configFile2.getAbsolutePath()));
											
											itemIDs.put(Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1)), itemAndConfig);
											continue;
											
											//& or &&?
										} else if (configLine.contains("I:") & configLine.matches(".*\\d.*") & blockComingUp == false & itemComingUp == false) {
											
											if (configLine.contains("block") || configLine.contains("Block") || configLine.contains("BLOCK") || configLine.contains("item") || configLine.contains("Item") || configLine.contains("ITEM")) {
												
												String[] nameSplit = configLine.split("=");
												
												ArrayList<String> unknownAndConfig = new ArrayList<String>();
												unknownAndConfig.add(nameSplit[0].replace("I:", "").replace(" ", "").replace("\"", ""));
												unknownAndConfig.add(getPrettyName(configFile2.getAbsolutePath()));
												
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
		}
	}
	
	/**
	 * Returns the "pretty name" of a configuration file combined with it's absolute path. For example, calling this method on "C:\Users\Test\AppData\Roaming\.minecraft\config\test.cfg" will return "test.cfg".
	 * @author SS111
	 * @param input the input path
	 * @return String
	 * @see com.github.ss111.ConfigHelper
	 */
	public static String getPrettyName(String input) {
		
		return input.substring(input.lastIndexOf(File.separator) + 1);
	}
	
	/**
	 * Returns a <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a> of all the block IDs and their corresponding name(s) and configuration file(s).
	 * @return <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 */
	public static MultiValueMap getBlockIDs() {
		
		return blockIDs;
	}
	
	/**
	 * Returns a <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a> of all the item IDs and their corresponding name(s) and configuration file(s).
	 * @return <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 */
	public static MultiValueMap getItemIDs() {
		
		return itemIDs;
	}
	
	/**
	 * Returns a <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a> of all the unknown IDs and their corresponding name(s) and configuration file(s).
	 * @return <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 */
	public static MultiValueMap getUnknownIDs() {
		
		return unknownIDs;
	}
}
