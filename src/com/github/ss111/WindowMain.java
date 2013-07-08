package com.github.ss111;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		frmMain.setResizable(false);
		frmMain.setTitle("Minecraft ID Resolver V1.0 - By SS111");
		frmMain.setBounds(100, 100, 450, 345);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.getContentPane().setLayout(new MigLayout("", "[95px][10px][210px,grow][10px][99px]", "[23px][23px][179px][][][]"));
		
		JLabel lblConfigDirectory = new JLabel("Config directory:");
		frmMain.getContentPane().add(lblConfigDirectory, "cell 0 0,alignx center,aligny center");
		
		final JTextField txtFieldPath = new JTextField();
		txtFieldPath.setEnabled(false);
		frmMain.getContentPane().add(txtFieldPath, "cell 2 0,growx,aligny center");
		txtFieldPath.setColumns(10);
		
		JLabel lblIdDump = new JLabel("NEI ID dump:");
		frmMain.getContentPane().add(lblIdDump, "cell 0 4,alignx left,aligny center");
		
		txtFieldPath2 = new JTextField();
		txtFieldPath2.setEnabled(false);
		frmMain.getContentPane().add(txtFieldPath2, "cell 1 4 2 1,growx");
		txtFieldPath2.setColumns(10);
		
		final JButton btnResolve = new JButton("Resolve conflicts!");
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
		btnResolve.setEnabled(false);
		frmMain.getContentPane().add(btnResolve, "cell 0 5 5 1,growx");
		
		final JButton btnBrowse2 = new JButton("Browse");
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
		btnBrowse2.setEnabled(false);
		frmMain.getContentPane().add(btnBrowse2, "cell 4 4,growx,aligny top");
				
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
							
							listModelBlocks.addElement(ColoredString.getColoredString("Block ID: " + ID + " | " + ConflictHelper.getConflictString(ConfigHelper.getBlockIDs(), ID)));
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
							
							listModelItems.addElement(ColoredString.getColoredString("Item ID: " + ID + " | " + ConflictHelper.getConflictString(ConfigHelper.getItemIDs(), ID)));
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
    						
    						listModelUnknown.addElement(ColoredString.getColoredString("ID: " + ID + " | " + ConflictHelper.getConflictString(ConfigHelper.getUnknownIDs(), ID)));
    						conflicts++;
    						
    					} else {
    						
    						listModelUnknown.addElement("ID: " + ID + " | " + "Name: " + ConflictHelper.getName(ConfigHelper.getUnknownIDs(), ID));
    					}
    				}
                }
                
                btnSearch.setText("Done!");
                
                if (conflicts == 0) {
                	
                	JOptionPane.showMessageDialog(null, "No conflicts were found! Hooray!", "Information", JOptionPane.INFORMATION_MESSAGE);
                	
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
		panelBlocks.setLayout(new MigLayout("", "[407px]", "[132px]"));
		panelBlocks.add(scrollPaneBlocks, "cell 0 0,grow");
		panelBlocks.setOpaque(false);
		
		JPanel panelItems = new JPanel();
		panelItems.setLayout(new MigLayout("", "[407px]", "[132px]"));
		panelItems.add(scrollPaneItems, "cell 0 0,grow");
		panelItems.setOpaque(false);
		
		JPanel panelUnknown = new JPanel();
		panelUnknown.setLayout(new MigLayout("", "[407px]", "[132px]"));
		panelUnknown.add(scrollPaneUnknown, "cell 0 0,grow");
		panelUnknown.setOpaque(false);
		
		tabbedPaneIDs = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneIDs.addTab("Block IDs", panelBlocks);
		tabbedPaneIDs.addTab("Item IDs", panelItems);
		tabbedPaneIDs.addTab("Unknown IDs", panelUnknown);
		tabbedPaneIDs.setEnabled(false);
		frmMain.getContentPane().add(tabbedPaneIDs, "cell 0 2 5 2,grow");
		
	}
}
