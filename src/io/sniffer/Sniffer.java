package io.sniffer;

import io.sniffer.name.NameList;

public class Sniffer {
	
	private static final String NAMES_PATH = "names.txt";
	
	public static void main(String[] args) {
		NameList names = NameList.readFromPath(NAMES_PATH);
		if(names == null) return;
		
		names.updateNameOwners();
		NameList.writeToPath(names, NAMES_PATH);
	}
	
}
