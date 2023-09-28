package io.sniffer.mojang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MojangPlayerProfileList {
	
	private final List<MojangPlayerProfile> playerProfiles = new ArrayList<>();
	private final Map<String, MojangPlayerProfile> mappedPlayerProfiles = new HashMap<>();
	
	public void forEachProfile(Consumer<MojangPlayerProfile> profileConsumer) {
		playerProfiles.forEach(profileConsumer);
	}
	
	public void addProfile(MojangPlayerProfile profile) {
		String profileName = profile.getName();
		profileName = profileName.toLowerCase();
		
		playerProfiles.add(profile);
		mappedPlayerProfiles.put(profileName, profile);
	}
	
	public boolean containsProfile(String name) {
		return getProfile(name) != null;
	}
	
	public MojangPlayerProfile getProfile(String name) {
		name = name.toLowerCase();
		return mappedPlayerProfiles.get(name);
	}
	
	public MojangPlayerProfile getProfile(int index) {
		return playerProfiles.get(index);
	}
	
	public int profileAmount() {
		return playerProfiles.size();
	}
	
}
