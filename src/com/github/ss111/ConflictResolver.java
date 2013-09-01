package com.github.ss111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

/**
 * ConflictResolver is used to help resolve ID conflicts. It's only functionality is the ability to parse Forge configuration files and then solve ID conflicts based on the information given.
 * @author SS111
 */
public class ConflictResolver {
	
	private static BufferedReader configReader;
	private static String configLine;
	private static Boolean blockComingUp = false;
	private static Boolean itemComingUp = false;
	
	/**
	 * Resolves ID conflicts in the configuration files.
	 * @author SS111
	 * @param path
	 * @param unusedBlocks the unused block IDs
	 * @param unusedItems the of unused item IDs
	 * @param conflictingBlocks the conflicting block IDs
	 * @param conflictingItems the conflicting item IDs
	 * @see <a href="http://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/MultiValueMap.html">MultiValueMap</a>
	 * @see ArrayList
	 */
	public static void resolveConflicts(String path, ArrayList<Integer> unusedBlocks, ArrayList<Integer> unusedItems, ArrayList<Integer> conflictingBlocks, ArrayList<Integer> conflictingItems) {
	
		File configDirectory = new File(path);
		
		try {
			
			FileUtils.copyDirectory(configDirectory, new File(path.replace("config", "config_bak")));
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		File[] configFiles = configDirectory.listFiles();
		
		for (File configFile : configFiles) {
			
			ArrayList<String> lines = new ArrayList<String>();
			
			if (configFile.isFile() & configFile.getAbsolutePath().contains(".cfg") || configFile.getAbsolutePath().contains(".txt") || configFile.getAbsolutePath().contains(".conf")) {
				
				try {
					
					configReader = new BufferedReader(new FileReader(configFile.getAbsolutePath()));
					
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				
				try {
					
					while ((configLine = configReader.readLine()) != null) {
						
						if (configLine == null) {
							
							lines.add(configLine);
							continue;
							
						} else if (configLine.contains("}") && blockComingUp == true) {
						
							blockComingUp = false;
							
							lines.add(configLine);
							continue;
							
						} else if (configLine.contains("}") && itemComingUp == true) {
						
							itemComingUp = false;
							
							lines.add(configLine);
							continue;
							
						} else if (configLine.equals("# block")) {
							
							blockComingUp = true;
							
							lines.add(configLine);
							continue;
							
						} else if (configLine.equals("# item")) {
							
							itemComingUp = true;
							
							lines.add(configLine);
							continue;
							
						} else if (configLine.contains("I:") && blockComingUp == true) {
							
							Boolean writeDefault = new Boolean(true);
							
							Integer ID = Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1));
							
							forLoop:
							for (Integer conflictID : conflictingBlocks) {
								
								if (ID.equals(conflictID)) {
									
									String newConfigLine = configLine.split("=")[0];
									
									newConfigLine = newConfigLine + "=" + unusedBlocks.get(0);
									
									lines.add(newConfigLine);
									
									unusedBlocks.remove(0);
									
									writeDefault = false;
									break forLoop;
								}
							}
							
							if (writeDefault == true) {
								
								lines.add(configLine);
							}
							continue;
						
							
						} else if (configLine.contains("I:") && itemComingUp == true) {
							
							Boolean writeDefault = new Boolean(true);
							
							Integer ID = Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1));
							
							forLoop:
							for (Integer conflictID : conflictingItems) {
								
								if (ID.equals(conflictID)) {
									
									String newConfigLine = configLine.split("=")[0];
									
									newConfigLine = newConfigLine + "=" + unusedItems.get(0);
									
									lines.add(newConfigLine);
									
									unusedItems.remove(0);
									
									writeDefault = false;
									break forLoop;
								}
							}
							
							if (writeDefault == true) {
								
								lines.add(configLine);
							}
							continue;
							
							//& or &&?
						} else if (configLine.contains("I:") & blockComingUp == false & itemComingUp == false) {
							
							lines.add(configLine);
							continue;
							
						} else {
							
							lines.add(configLine);
							continue;
						}
					}
					
					configReader.close();
					
					FileUtils.writeLines(configFile.getAbsoluteFile(), lines);
					
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
					
					ArrayList<String> lines = new ArrayList<String>();
					
					if (configFile.isFile() & configFile.getAbsolutePath().contains(".cfg") || configFile.getAbsolutePath().contains(".txt") || configFile.getAbsolutePath().contains(".conf")) {
						
						try {
							
							configReader = new BufferedReader(new FileReader(configFile.getAbsolutePath()));
							
						} catch (FileNotFoundException e) {
							
							e.printStackTrace();
						}
						
						try {
							
							while ((configLine = configReader.readLine()) != null) {
								
								if (configLine == null) {
									
									lines.add(configLine);
									continue;
									
								} else if (configLine.contains("}") && blockComingUp == true) {
								
									blockComingUp = false;
									
									lines.add(configLine);
									continue;
									
								} else if (configLine.contains("}") && itemComingUp == true) {
								
									itemComingUp = false;
									
									lines.add(configLine);
									continue;
									
								} else if (configLine.equals("# block")) {
									
									blockComingUp = true;
									
									lines.add(configLine);
									continue;
									
								} else if (configLine.equals("# item")) {
									
									itemComingUp = true;
									
									lines.add(configLine);
									continue;
									
								} else if (configLine.contains("I:") && blockComingUp == true) {
									
									Boolean writeDefault = new Boolean(true);
									
									Integer ID = Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1));
									
									forLoop:
									for (Integer conflictID : conflictingBlocks) {
										
										if (ID.equals(conflictID)) {
											
											String newConfigLine = configLine.split("=")[0];
											
											newConfigLine = newConfigLine + "=" + unusedBlocks.get(0);
											
											lines.add(newConfigLine);
											
											unusedBlocks.remove(0);
											
											writeDefault = false;
											break forLoop;
										}
									}
									
									if (writeDefault == true) {
										
										lines.add(configLine);
									}
									continue;
									
								} else if (configLine.contains("I:") && itemComingUp == true) {
									
									Boolean writeDefault = new Boolean(true);
									
									Integer ID = Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1));
									
									forLoop:
									for (Integer conflictID : conflictingItems) {
										
										if (ID.equals(conflictID)) {
											
											String newConfigLine = configLine.split("=")[0];
											
											newConfigLine = newConfigLine + "=" + unusedItems.get(0);
											
											lines.add(newConfigLine);
											
											unusedItems.remove(0);
											
											writeDefault = false;
											break forLoop;
										}
									}
									
									if (writeDefault == true) {
										
										lines.add(configLine);
									}
									continue;
									
									//& or &&?
								} else if (configLine.contains("I:") & blockComingUp == false & itemComingUp == false) {
									
									lines.add(configLine);
									continue;
									
								} else {
									
									lines.add(configLine);
									continue;
								}
							}
							
							configReader.close();
							
							FileUtils.writeLines(configFile.getAbsoluteFile(), lines);
							
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
