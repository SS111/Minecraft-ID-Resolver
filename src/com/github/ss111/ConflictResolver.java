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
				
			} else if (configFile.isDirectory()) {
				
				File[] configFilesDir1 = configFile.listFiles();
				
				for (File configFile1 : configFilesDir1) {
					
					ArrayList<String> lines1 = new ArrayList<String>();
					
					if (configFile1.isFile() & configFile1.getAbsolutePath().contains(".cfg") || configFile1.getAbsolutePath().contains(".txt") || configFile1.getAbsolutePath().contains(".conf")) {
						
						try {
							
							configReader = new BufferedReader(new FileReader(configFile1.getAbsolutePath()));
							
						} catch (FileNotFoundException e) {
							
							e.printStackTrace();
						}
						
						try {
							
							while ((configLine = configReader.readLine()) != null) {
								
								if (configLine == null) {
									
									lines1.add(configLine);
									continue;
									
								} else if (configLine.contains("}") && blockComingUp == true) {
								
									blockComingUp = false;
									
									lines1.add(configLine);
									continue;
									
								} else if (configLine.contains("}") && itemComingUp == true) {
								
									itemComingUp = false;
									
									lines1.add(configLine);
									continue;
									
								} else if (configLine.equals("# block")) {
									
									blockComingUp = true;
									
									lines1.add(configLine);
									continue;
									
								} else if (configLine.equals("# item")) {
									
									itemComingUp = true;
									
									lines1.add(configLine);
									continue;
									
								} else if (configLine.contains("I:") && blockComingUp == true) {
									
									Boolean writeDefault = new Boolean(true);
									
									Integer ID = Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1));
									
									forLoop:
									for (Integer conflictID : conflictingBlocks) {
										
										if (ID.equals(conflictID)) {
											
											String newConfigLine = configLine.split("=")[0];
											
											newConfigLine = newConfigLine + "=" + unusedBlocks.get(0);
											
											lines1.add(newConfigLine);
											
											unusedBlocks.remove(0);
											
											writeDefault = false;
											break forLoop;
										}
									}
									
									if (writeDefault == true) {
										
										lines1.add(configLine);
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
											
											lines1.add(newConfigLine);
											
											unusedItems.remove(0);
											
											writeDefault = false;
											break forLoop;
										}
									}
									
									if (writeDefault == true) {
										
										lines1.add(configLine);
									}
									continue;
									
									//& or &&?
								} else if (configLine.contains("I:") & blockComingUp == false & itemComingUp == false) {
									
									lines1.add(configLine);
									continue;
									
								} else {
									
									lines1.add(configLine);
									continue;
								}
							}
							
							configReader.close();
							
							FileUtils.writeLines(configFile1.getAbsoluteFile(), lines1);
							
						} catch (IOException e) {

							e.printStackTrace();
						}
						
					} else if (configFile1.isDirectory()) {
						
						File[] configFilesDir2 = configFile1.listFiles();
						
						for (File configFile2 : configFilesDir2) {
							
							ArrayList<String> lines2 = new ArrayList<String>();
							
							if (configFile2.isFile() & configFile2.getAbsolutePath().contains(".cfg") || configFile2.getAbsolutePath().contains(".txt") || configFile2.getAbsolutePath().contains(".conf")) {
								
								try {
									
									configReader = new BufferedReader(new FileReader(configFile2.getAbsolutePath()));
									
								} catch (FileNotFoundException e) {
									
									e.printStackTrace();
								}
								
								try {
									
									while ((configLine = configReader.readLine()) != null) {
										
										if (configLine == null) {
											
											lines2.add(configLine);
											continue;
											
										} else if (configLine.contains("}") && blockComingUp == true) {
										
											blockComingUp = false;
											
											lines2.add(configLine);
											continue;
											
										} else if (configLine.contains("}") && itemComingUp == true) {
										
											itemComingUp = false;
											
											lines2.add(configLine);
											continue;
											
										} else if (configLine.equals("# block")) {
											
											blockComingUp = true;
											
											lines2.add(configLine);
											continue;
											
										} else if (configLine.equals("# item")) {
											
											itemComingUp = true;
											
											lines2.add(configLine);
											continue;
											
										} else if (configLine.contains("I:") && blockComingUp == true) {
											
											Boolean writeDefault = new Boolean(true);
											
											Integer ID = Integer.valueOf(configLine.substring(configLine.lastIndexOf("=") + 1));
											
											forLoop:
											for (Integer conflictID : conflictingBlocks) {
												
												if (ID.equals(conflictID)) {
													
													String newConfigLine = configLine.split("=")[0];
													
													newConfigLine = newConfigLine + "=" + unusedBlocks.get(0);
													
													lines2.add(newConfigLine);
													
													unusedBlocks.remove(0);
													
													writeDefault = false;
													break forLoop;
												}
											}
											
											if (writeDefault == true) {
												
												lines2.add(configLine);
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
													
													lines2.add(newConfigLine);
													
													unusedItems.remove(0);
													
													writeDefault = false;
													break forLoop;
												}
											}
											
											if (writeDefault == true) {
												
												lines2.add(configLine);
											}
											continue;
											
											//& or &&?
										} else if (configLine.contains("I:") & blockComingUp == false & itemComingUp == false) {
											
											lines2.add(configLine);
											continue;
											
										} else {
											
											lines2.add(configLine);
											continue;
										}
									}
									
									configReader.close();
									
									FileUtils.writeLines(configFile2.getAbsoluteFile(), lines2);
									
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
}
