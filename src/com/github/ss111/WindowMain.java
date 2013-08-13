package com.github.ss111;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.collections.map.MultiValueMap;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import java.awt.BorderLayout;

public class WindowMain {
	
	public static JFrame frmMain;
	
	private JButton btnBrowse;
	private JButton btnSearch;
	
	private JTabbedPane tabbedPaneIDs;
	
	private DefaultListModel<String> listModelBlocks = new DefaultListModel<String>();
	private DefaultListModel<String> listModelItems = new DefaultListModel<String>();
	private DefaultListModel<String> listModelUnknown = new DefaultListModel<String>();
	
	private Integer conflicts = 0;
	
	private JTextField txtFieldPath2;
	
	public static void main(String[] args) {
		
		if (args.length != 0) {
			
			FlaggedOption optionOne = new FlaggedOption("config dir").setStringParser(com.martiansoftware.jsap.stringparsers.FileStringParser.getParser()).setRequired(true).setShortFlag('c').setLongFlag("config");
			optionOne.setHelp("The path to the Minecraft configuration directory.");
			
			FlaggedOption optionTwo = new FlaggedOption("nei id dump").setStringParser(com.martiansoftware.jsap.stringparsers.FileStringParser.getParser()).setRequired(true).setShortFlag('n').setLongFlag("dump");
			optionTwo.setHelp("The path to the NEI ID dump file.");
			
			FlaggedOption optionThree = new FlaggedOption("show conflicts").setStringParser(JSAP.BOOLEAN_PARSER).setRequired(false).setDefault("false").setShortFlag('s').setLongFlag("show");
			optionThree.setHelp("Requests that conflicts be displayed before being resolved.");
			
			JSAP argsParser = new JSAP();
			
			try {
				
				argsParser.registerParameter(optionOne);
				argsParser.registerParameter(optionTwo);
				argsParser.registerParameter(optionThree);
				
			} catch (JSAPException e) {
				
				e.printStackTrace();
			}
			
			JSAPResult argsResult = argsParser.parse(args);
			
			if (argsResult.success() != true) {
				
				System.err.println("");
				
	            for (@SuppressWarnings("rawtypes")
				Iterator errs = argsResult.getErrorMessageIterator();
	                    errs.hasNext();) {
	            	
	                System.err.println("Error: " + errs.next());
	            }
				
	            System.err.println("");
				System.err.println("Usage: java -jar Minecraft.ID.Resolver.v1.0.4.jar ");
				System.err.println("                " + argsParser.getUsage());
				System.err.println("");
				System.err.println(argsParser.getHelp());
				System.exit(1);
				
			}
			
			System.out.println("");
			System.out.println("Minecraft ID Resolver v1.0.4 - By SS111");
			System.out.println("Console mode activated.");
			System.out.println("");
			System.out.print("\rValidating arguments...");
			
			File configDirectory = argsResult.getFile("config dir");
			File idDump = argsResult.getFile("nei id dump");
			
			if (configDirectory.exists() == true && idDump.exists() == true) {
				
				System.out.print("done");
				System.out.println("");
				System.out.println("");
				System.out.print("\rParsing configs...");
				
				ConfigHelper.populateMaps(configDirectory.getAbsolutePath());
				
				System.out.print("done");
				System.out.println("");
				System.out.println("");
				
				if (argsResult.getBoolean("show conflicts") == true) {
					
					System.out.println("Finding conflicts...");
					System.out.println("");
					System.out.println("Block IDs:");
					System.out.println("");
					
					for (Object key : ConfigHelper.getBlockIDs().keySet()) {
						
						Integer ID = Integer.valueOf(key.toString());
						
						if (ConflictHelper.isConflicting(ConfigHelper.getBlockIDs(), ID, "BLOCK") == true) {
							
							System.out.println(ConflictHelper.getConflictString(ConfigHelper.getBlockIDs(), ID));
							System.out.println(ConflictHelper.getConfigConflictString(ConfigHelper.getBlockIDs(), ID, "BLOCK"));
							System.out.println("");
							
						}

					}
					
					System.out.println("Item IDs:");
					System.out.println("");
					
					for (Object key : ConfigHelper.getItemIDs().keySet()) {
						
						Integer ID = Integer.valueOf(key.toString());
						
						if (ConflictHelper.isConflicting(ConfigHelper.getItemIDs(), ID, "ITEM") == true) {
							
							System.out.println(ConflictHelper.getConflictString(ConfigHelper.getItemIDs(), ID));
							System.out.println(ConflictHelper.getConfigConflictString(ConfigHelper.getItemIDs(), ID, "ITEM"));
							System.out.println("");
							
						}

					}
					
					System.out.println("Unknown IDs:");
					System.out.println("");
					
					for (Object key : ConfigHelper.getUnknownIDs().keySet()) {
						
						Integer ID = Integer.valueOf(key.toString());
						
						if (ConflictHelper.isConflicting(ConfigHelper.getUnknownIDs(), ID) == true) {
							
							System.out.println(ConflictHelper.getConflictString(ConfigHelper.getUnknownIDs(), ID));
							System.out.println(ConflictHelper.getConfigConflictString(ConfigHelper.getUnknownIDs(), ID, "UNKNOWN"));
							System.out.println("");
							
						}

					}
					
					System.out.println("Done finding conflicts!");
					System.out.println("");
					System.out.print("\rPopulating unused IDs...");
					
					IdDumpHelper.populateUnusedIDs(idDump.getAbsolutePath());
					
					System.out.print("done");
					System.out.println("");
					System.out.println("");
					System.out.println("Resolve the above conflicts? (Y/N)");
					System.out.println("");
					
					BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
					
					String result = new String();
					
					try {
						
						result = consoleReader.readLine();

						consoleReader.close();
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					if (result.equals("Y") == false && result.equals("y") == false && result.equals("N") == false && result.equals("n") == false) {
						
						System.err.println("");
						System.err.println("ERROR: Invalid input. Exiting.");
						System.exit(1);
						
					} else {
						
						if (result.equals("Y") || result.equals("y")) {
							
							System.out.println("");
							System.out.print("\rResolving conflicts...");
							
							ConflictResolver.resolveConflicts(configDirectory.getAbsolutePath(), IdDumpHelper.getUnusedBlockIDs(), IdDumpHelper.getUnusedItemIDs(), ConflictHelper.getConflictingBlocks(), ConflictHelper.getConflictingItems());
							
							System.out.print("done");
							System.out.println("");
							
							System.exit(0);
								
						} else {
							
							System.out.println("");
							System.out.println("Not resolving. Exiting");
							System.out.println("");
							
							System.exit(0);
						}
					}
					
				} else {
					
					System.out.print("\rFinding conflicts...");
					
					for (Object key : ConfigHelper.getBlockIDs().keySet()) {
						
						Integer ID = Integer.valueOf(key.toString());
						
						if (ConflictHelper.isConflicting(ConfigHelper.getBlockIDs(), ID, "BLOCK") == true) {}

					}
					
					for (Object key : ConfigHelper.getItemIDs().keySet()) {
						
						Integer ID = Integer.valueOf(key.toString());
						
						if (ConflictHelper.isConflicting(ConfigHelper.getItemIDs(), ID, "ITEM") == true) {}

					}
					
					System.out.print("done");
					System.out.println("");
					System.out.println("");
					System.out.print("\rPopulating unused IDs...");
					
					IdDumpHelper.populateUnusedIDs(idDump.getAbsolutePath());
					
					System.out.print("done");
					System.out.println("");
					System.out.println("");
					System.out.print("\rResolving conflicts...");
					
					ConflictResolver.resolveConflicts(configDirectory.getAbsolutePath(), IdDumpHelper.getUnusedBlockIDs(), IdDumpHelper.getUnusedItemIDs(), ConflictHelper.getConflictingBlocks(), ConflictHelper.getConflictingItems());
					
					System.out.print("done");
					System.out.println("");
					
					System.exit(0);
				}
				
			} else {
				
				System.out.print("failed!");
				System.err.println("");
				System.out.println("");
				System.err.println("ERROR: The configuration directory or NEI ID dump doesn't exist!");
				
				System.exit(1);
			}
		}
		
		EventQueue.invokeLater(new Runnable() {
			
			@SuppressWarnings("static-access")
			public void run() {
				
				try {
									
					WindowMain window = new WindowMain();
					window.frmMain.setVisible(true);
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	
	private WindowMain() {
		
		initialize();
	}

	private void initialize() {
		
		frmMain = new JFrame();
		frmMain.setTitle("Minecraft ID Resolver V1.0.4 - By SS111");
		frmMain.setBounds(100, 100, 450, 345);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.getContentPane().setLayout(new MigLayout("", "[95px][10px][210px,grow][10px][99px]", "[23px][23px][179px][grow][][]"));
		
		JLabel lblConfigDirectory = new JLabel("Config directory:");
		
		frmMain.getContentPane().add(lblConfigDirectory, "cell 0 0,alignx center,aligny center");
		
		final JTextField txtFieldPath = new JTextField();
		txtFieldPath.setEnabled(false);
		txtFieldPath.setColumns(10);
		
		frmMain.getContentPane().add(txtFieldPath, "cell 2 0,growx,aligny center");
		
		
		JLabel lblIdDump = new JLabel("NEI ID dump:");
		
		frmMain.getContentPane().add(lblIdDump, "cell 0 4,alignx left,aligny center");
		
		txtFieldPath2 = new JTextField();
		txtFieldPath2.setEnabled(false);
		txtFieldPath2.setColumns(10);
		
		frmMain.getContentPane().add(txtFieldPath2, "cell 1 4 2 1,growx");
		
		final JButton btnResolve = new JButton("Resolve conflicts!");
		btnResolve.setEnabled(false);
		
		frmMain.getContentPane().add(btnResolve, "cell 0 5 5 1,growx");
		
		btnResolve.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				tabbedPaneIDs.setEnabled(false);
				
				btnResolve.setText("Resolving conflicts...please wait");
				btnResolve.setEnabled(false);
				
				ConflictResolver.resolveConflicts(txtFieldPath.getText(), IdDumpHelper.getUnusedBlockIDs(), IdDumpHelper.getUnusedItemIDs(), ConflictHelper.getConflictingBlocks(), ConflictHelper.getConflictingItems());
				
				JOptionPane.showMessageDialog(null, "All conflicts were resolved!", "Information", JOptionPane.INFORMATION_MESSAGE);
				
				frmMain.dispose();
			}
		});
		
		final JButton btnBrowse2 = new JButton("Browse");
		btnBrowse2.setEnabled(false);
		
		frmMain.getContentPane().add(btnBrowse2, "cell 4 4,growx,aligny top");
		
		btnBrowse2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser dumpChooser = new JFileChooser();
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("NEI ID Dump", "txt");
				
				dumpChooser.setFileFilter(filter);
				dumpChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				int returnValue = dumpChooser.showOpenDialog(frmMain);
				
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					
					txtFieldPath2.setText(dumpChooser.getSelectedFile().getAbsolutePath());
					
					IdDumpHelper.populateUnusedIDs(txtFieldPath2.getText());
					
					btnBrowse2.setEnabled(false);
					
					btnResolve.setEnabled(true);
					btnResolve.requestFocus();
				}
				
			}
		});
						
		btnBrowse = new JButton("Browse");
		
		frmMain.getContentPane().add(btnBrowse, "cell 4 0,growx,aligny top");
		
		btnBrowse.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser dirChooser = new JFileChooser();
				dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dirChooser.setDialogTitle("Browse");
				
				int returnValue = dirChooser.showOpenDialog(frmMain);
				
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					
					txtFieldPath.setText(dirChooser.getSelectedFile().getAbsolutePath());
					
					btnSearch.setEnabled(true);
					btnSearch.requestFocus();
					
				}
			}	
		});
		
		btnSearch = new JButton("Search for conflicts!");
		btnSearch.setEnabled(false);
		
		frmMain.getContentPane().add(btnSearch, "cell 0 1 5 1,growx,aligny top");
		
		btnSearch.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				btnBrowse.setEnabled(false);
				
				btnSearch.setEnabled(false);
				btnSearch.setText("Searching...please wait");
				
				ConfigHelper.populateMaps(txtFieldPath.getText());
				
				MultiValueMap blocks = ConfigHelper.getBlockIDs();
				
				if (blocks.isEmpty() != true) {
					
					for (Object key : blocks.keySet()) {
						
						Integer ID = Integer.valueOf(key.toString());
						
						if (ConflictHelper.isConflicting(ConfigHelper.getBlockIDs(), ID, "BLOCK") == true) {
							
							listModelBlocks.addElement(getColoredString("Block ID: " + ID + " | " + ConflictHelper.getConflictString(ConfigHelper.getBlockIDs(), ID)));
							listModelBlocks.addElement(getColoredString(ConflictHelper.getConfigConflictString(ConfigHelper.getBlockIDs(), ID, "BLOCK")));
							conflicts++;
							
						} else {
							
							listModelBlocks.addElement("Block ID: " + ID + " | " + "Block name: " + ConflictHelper.getName(ConfigHelper.getBlockIDs(), ID));
						}
					}
				}
				
				MultiValueMap items = ConfigHelper.getItemIDs();
				
				if (items.isEmpty() != true) {
					
					for (Object key : items.keySet()) {
						
						Integer ID = Integer.valueOf(key.toString());
						
						if (ConflictHelper.isConflicting(ConfigHelper.getItemIDs(), ID, "ITEM") == true) {
							
							listModelItems.addElement(getColoredString("Item ID: " + ID + " | " + ConflictHelper.getConflictString(ConfigHelper.getItemIDs(), ID)));
							listModelItems.addElement(getColoredString(ConflictHelper.getConfigConflictString(ConfigHelper.getItemIDs(), ID, "ITEM")));
							conflicts++;
							
						} else {
							
							listModelItems.addElement("Item ID: " + ID + " | " + "Item name: " + ConflictHelper.getName(ConfigHelper.getItemIDs(), ID));
						}
					}
				}
				
                MultiValueMap unknown = ConfigHelper.getUnknownIDs();
                
                
                 if (unknown.isEmpty() != true) {
                	
                    for (Object key : unknown.keySet()) {
    					
    					Integer ID = Integer.valueOf(key.toString());
    					
    					if (ConflictHelper.isConflicting(ConfigHelper.getUnknownIDs(), ID, "UNKNOWN") == true) {
    						
    						listModelUnknown.addElement(getColoredString("ID: " + ID + " | " + ConflictHelper.getConflictString(ConfigHelper.getUnknownIDs(), ID)));
    						listModelUnknown.addElement(getColoredString(ConflictHelper.getConfigConflictString(ConfigHelper.getUnknownIDs(), ID, "UNKNOWN")));
    						conflicts++;
    						
    					} else {
    						
    						listModelUnknown.addElement("ID: " + ID + " | " + "Name: " + ConflictHelper.getName(ConfigHelper.getUnknownIDs(), ID));
    					}
    				}
                }
                
                btnSearch.setText("Done!");
                
                if (conflicts == 0) {
                	
                	JOptionPane.showMessageDialog(null, "No conflicts were found! Hooray!", "Information", JOptionPane.INFORMATION_MESSAGE);
                	
                	frmMain.dispose();
                	
                } else if (conflicts == 1) {
                	
                	JOptionPane.showMessageDialog(null, conflicts.toString() + " conflict was found!", "Information", JOptionPane.INFORMATION_MESSAGE);
                	
                } else {
                	
                	JOptionPane.showMessageDialog(null, conflicts.toString() + " conflicts were found!", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
                                
                tabbedPaneIDs.setEnabled(true);
                tabbedPaneIDs.requestFocus();
                
                btnBrowse2.setEnabled(true);
			}
		});
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList listBlocks = new JList(listModelBlocks);
		listBlocks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList listItems = new JList(listModelItems);
		listItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList listUnknown = new JList(listModelUnknown);
		listUnknown.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPaneBlocks = new JScrollPane(listBlocks);
		
		JScrollPane scrollPaneItems = new JScrollPane(listItems);
		
		JScrollPane scrollPaneUnknown = new JScrollPane(listUnknown);
				
		JPanel panelBlocks = new JPanel();
		panelBlocks.setLayout(new BorderLayout(0, 0));
		panelBlocks.add(scrollPaneBlocks);
		panelBlocks.setOpaque(false);
		
		JPanel panelItems = new JPanel();
		panelItems.setLayout(new BorderLayout(0, 0));
		panelItems.add(scrollPaneItems);
		panelItems.setOpaque(false);
		
		JPanel panelUnknown = new JPanel();
		panelUnknown.setLayout(new BorderLayout(0, 0));
		panelUnknown.add(scrollPaneUnknown);
		panelUnknown.setOpaque(false);
		
		tabbedPaneIDs = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneIDs.addTab("Block IDs", panelBlocks);
		tabbedPaneIDs.addTab("Item IDs", panelItems);
		tabbedPaneIDs.addTab("Unknown IDs", panelUnknown);
		tabbedPaneIDs.setEnabled(false);
		
		frmMain.getContentPane().add(tabbedPaneIDs, "cell 0 2 5 2,grow");
		
	}
	
	private static String getColoredString(String input) {
		
		return "<html><font color=red>" + input + "</font></html>";
	}
}
