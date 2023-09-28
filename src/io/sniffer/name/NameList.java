package io.sniffer.name;

import io.sniffer.mojang.MojangAPI;
import io.sniffer.mojang.MojangPlayerProfile;
import io.sniffer.mojang.MojangPlayerProfileList;
import io.sniffer.util.file.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NameList {
	
	private static final boolean OWNED_NAME_BANNED = false;
	
	private final List<Name> names = new ArrayList<>();
	private final Map<String, Name> mappedNames = new HashMap<>();
	
	public void updateNames() {
		synchronized(names) {
			
			int nameAmount = names.size();
			int nameIndex = 0;
			while(nameIndex < nameAmount) {
				
				List<Name> names = new ArrayList<>();
				List<String> profileNames = new ArrayList<>();
				int profileNameAmount = 0;
				
				while(nameIndex < nameAmount && profileNameAmount < MojangAPI.getMaximalPlayerProfileRequestAmount()) {
					
					Name name = this.names.get(nameIndex);
					String n = name.getName();
					
					names.add(name);
					profileNames.add(n);
					profileNameAmount++;
					nameIndex++;
				}
				
				MojangPlayerProfileList profiles = MojangAPI.playerProfiles(profileNames);
				
				for(Name name : names) {
					String n = name.getName();
					
					if(profiles.containsProfile(n)) {
						
						MojangPlayerProfile profile = profiles.getProfile(n);
						name.update(profile, OWNED_NAME_BANNED);
						
					} else {
						
						boolean nameBanned = MojangAPI.playerProfileNameBanned(n);
						name.update(MojangPlayerProfile.EMPTY, nameBanned);
					}
				}
			}
		}
	}
	
	public void forEachName(Consumer<Name> nameConsumer) {
		synchronized(names) {
			names.forEach(nameConsumer);
		}
	}
	
	public void addName(Name name) {
		String n = name.getName();
		n = n.toLowerCase();
		
		synchronized(names) {
			names.add(name);
			mappedNames.put(n, name);
		}
	}
	
	public Name getName(String name) {
		name = name.toLowerCase();
		
		synchronized(names) {
			return mappedNames.get(name);
		}
	}
	
	public Name getName(int index) {
		synchronized(names) {
			return names.get(index);
		}
	}
	
	public int nameAmount() {
		synchronized(names) {
			return names.size();
		}
	}
	
	public static NameList readFromPath(String path) {
		List<String> textLines = FileUtil.readTextLinesFromPath(path);
		if(textLines == null) return null;
		
		NameList names = new NameList();
		
		for(String textLine : textLines) {
			
			Name name = Name.ofString(textLine);
			names.addName(name);
		}
		
		return names;
	}
	
	public static void writeToPath(NameList names, String path) {
		List<String> textLines = new ArrayList<>();
		
		names.forEachName((name -> {
			
			String textLine = name.toString();
			textLines.add(textLine);
			
		}));
		
		FileUtil.writeTextLinesToPath(textLines, path);
	}
	
}
