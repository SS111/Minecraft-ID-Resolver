package com.github.ss111;

public class ColoredString {
	
	private static String htmlStart = "<html><font color=red>";
	private static String htmlEnd = "</font></html>";
	
	public static String getColoredString(String input) {
		
		return htmlStart + input + htmlEnd;
		
	}

}
