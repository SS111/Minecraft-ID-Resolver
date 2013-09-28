package com.github.ss111;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;

public class SortingHelper {
	
	@SuppressWarnings("unchecked")
	public static void sortListModel(DefaultListModel<String> input) {
		
		Object[] unsortedArray = input.toArray();
		
		List<Object> listToBeSorted = Arrays.asList(unsortedArray);
		
		Collections.sort(listToBeSorted, new AlphanumComparator());
		
		input.clear();
		
		for (Object o : listToBeSorted) {
			
			input.addElement(o.toString());
		}
	}
}
