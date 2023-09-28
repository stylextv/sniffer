package io.sniffer.mojang;

import java.util.UUID;

public class MojangPlayerProfile {
	
	private static final UUID EMPTY_ID = null;
	private static final String EMPTY_NAME = null;
	
	public static final MojangPlayerProfile EMPTY = new MojangPlayerProfile(EMPTY_ID, EMPTY_NAME);
	
	private final UUID id;
	private final String name;
	
	public MojangPlayerProfile(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public boolean equals(MojangPlayerProfile profile) {
		if(isEmpty()) return profile.isEmpty();
		
		UUID id2 = profile.getID();
		return id.equals(id2);
	}
	
	public UUID getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isEmpty() {
		return id == EMPTY_ID;
	}
	
}
