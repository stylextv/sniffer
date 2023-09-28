package io.sniffer.name;

import io.sniffer.mojang.MojangPlayerProfile;

import java.util.UUID;

public class Name {
	
	private static final String NAME_STRING_FORMAT = "%s, %s, %s, %s, %s, %s, %s";
	private static final String NAME_STRING_VALUE_SEPARATOR = ", ";
	private static final int NAME_STRING_NAME_INDEX = 0;
	private static final int NAME_STRING_POPULARITY_INDEX = 1;
	private static final int NAME_STRING_OWNER_PROFILE_ID_INDEX = 2;
	private static final int NAME_STRING_OWNER_PROFILE_NAME_INDEX = 3;
	private static final int NAME_STRING_LAST_OWNER_UPDATE_TIME_INDEX = 4;
	private static final int NAME_STRING_LAST_OWNER_CHANGE_TIME_INDEX = 5;
	private static final int NAME_STRING_BANNED_INDEX = 6;
	private static final String MISSING_NAME_OWNER_PROFILE_ID_STRING = "null";
	
	private final String name;
	private final int popularity;
	
	private MojangPlayerProfile ownerProfile;
	private long lastOwnerUpdateTime;
	private long lastOwnerChangeTime;
	private boolean banned;
	
	public Name(String name, int popularity, MojangPlayerProfile ownerProfile, long lastOwnerUpdateTime, long lastOwnerChangeTime, boolean banned) {
		this.name = name;
		this.popularity = popularity;
		this.ownerProfile = ownerProfile;
		this.lastOwnerUpdateTime = lastOwnerUpdateTime;
		this.lastOwnerChangeTime = lastOwnerChangeTime;
		this.banned = banned;
	}
	
	@Override
	public String toString() {
		UUID playerProfileID = ownerProfile.getID();
		String playerProfileName = ownerProfile.getName();
		
		return String.format(NAME_STRING_FORMAT, name, popularity, playerProfileID, playerProfileName, lastOwnerUpdateTime, lastOwnerChangeTime, banned);
	}
	
	public void update(MojangPlayerProfile ownerProfile, boolean banned) {
		lastOwnerUpdateTime = System.currentTimeMillis();
		if(!this.ownerProfile.equals(ownerProfile)) lastOwnerChangeTime = System.currentTimeMillis();
		
		this.ownerProfile = ownerProfile;
		this.banned = banned;
	}
	
	public boolean claimable() {
		return !claimed() && !banned;
	}
	
	public boolean claimed() {
		return !ownerProfile.isEmpty();
	}
	
	public String getName() {
		return name;
	}
	
	public int getPopularity() {
		return popularity;
	}
	
	public MojangPlayerProfile getOwnerProfile() {
		return ownerProfile;
	}
	
	public long getLastOwnerUpdateTime() {
		return lastOwnerUpdateTime;
	}
	
	public boolean isBanned() {
		return banned;
	}
	
	public static Name ofString(String nameString) {
		String[] valueStrings = nameString.split(NAME_STRING_VALUE_SEPARATOR);
		
		String name = valueStrings[NAME_STRING_NAME_INDEX];
		String popularityString = valueStrings[NAME_STRING_POPULARITY_INDEX];
		String playerProfileIDString = valueStrings[NAME_STRING_OWNER_PROFILE_ID_INDEX];
		String playerProfileName = valueStrings[NAME_STRING_OWNER_PROFILE_NAME_INDEX];
		String lastOwnerUpdateTimeString = valueStrings[NAME_STRING_LAST_OWNER_UPDATE_TIME_INDEX];
		String lastOwnerChangeTimeString = valueStrings[NAME_STRING_LAST_OWNER_CHANGE_TIME_INDEX];
		String bannedString = valueStrings[NAME_STRING_BANNED_INDEX];
		
		int popularity = Integer.parseInt(popularityString);
		UUID playerProfileID = playerProfileIDString.equals(MISSING_NAME_OWNER_PROFILE_ID_STRING) ? MojangPlayerProfile.EMPTY.getID() : UUID.fromString(playerProfileIDString);
		long lastOwnerUpdateTime = Long.parseLong(lastOwnerUpdateTimeString);
		long lastOwnerChangeTime = Long.parseLong(lastOwnerChangeTimeString);
		boolean banned = Boolean.parseBoolean(bannedString);
		
		MojangPlayerProfile playerProfile = new MojangPlayerProfile(playerProfileID, playerProfileName);
		return new Name(name, popularity, playerProfile, lastOwnerUpdateTime, lastOwnerChangeTime, banned);
	}
	
}
