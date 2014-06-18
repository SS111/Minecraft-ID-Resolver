package com.github.ss111;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TableRenderer {
	
	private static ArrayList<String> fileExtensions = new ArrayList<String>();
	
	private static DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
		
		private static final long serialVersionUID = 635122024602747098L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			 
			 Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			 
			 if (row == 0 || row == 1 || row == 2) {
				 
				 c.setBackground(Color.GRAY);
				 return c;
			 }
			 else {
				 
				 c.setBackground(Color.WHITE);
				 
				 if (value != null && !value.toString().startsWith(".")) {
					 
					 if (!value.equals("")) {
						 
						 c.setBackground(Color.RED);
					 }
				 }
				 
				 return c;
			 }
		}
	};
	
	private static DefaultTableModel dtm = new DefaultTableModel() {
		
		private static final long serialVersionUID = -5522373491102361200L;

		@Override
		public boolean isCellEditable(int row, int col) {
			
			if (row == 0 || row == 1 || row == 2) {
				
				return false;
			}
			else {
				
				return true;
			}
		}
	};
	
	public static DefaultTableCellRenderer getCellRenderer() {
		
		return cellRenderer;
	}
	
	public static DefaultTableModel getTableModel() {
		
		dtm.setRowCount(8);
		dtm.setColumnCount(1);
		dtm.setValueAt(".cfg", 0, 0);
		dtm.setValueAt(".txt", 1, 0);
		dtm.setValueAt(".conf", 2, 0);
		return dtm;
	}
	
	public static JTable getExtensionsTable() {
		
		return WindowMain.extensionsTable;
	}
	
	public static ArrayList<String> getExtraFileExtensions() {
		
		if (ConfigHelper.isCommandLine == false) {
			
			for (int i = 3; i <= 7; i++) {
				
				Object value = getExtensionsTable().getModel().getValueAt(i, 0);
				
				if (value != null && value.toString().startsWith(".")) {
					
					fileExtensions.add(value.toString().replace(".", ""));
				}
			}
			
		} else {
			
			return fileExtensions;
		}
		
		return fileExtensions;
	}
	
	public static void setExtraFileExtensions(String[] extraExtensions) {
		
		for (String s : extraExtensions) {
			
			if (s != "" && s.startsWith(".")) {
				
				fileExtensions.add(s.replace(".", ""));
			}
		}
		
		ConfigHelper.isCommandLine = true;
	}
}
