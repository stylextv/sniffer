package io.sniffer.mojang.responses;

import io.sniffer.mojang.MojangPlayerProfile;
import io.sniffer.mojang.MojangPlayerProfileList;
import io.sniffer.mojang.MojangResponse;

import java.util.List;
import java.util.UUID;

public class PlayerProfilesMojangResponse extends MojangResponse {
	
	private static final String PROFILE_ID_TEXT_PAYLOAD_VALUE_PREFIX = "\"id\" : \"";
	private static final String PROFILE_NAME_TEXT_PAYLOAD_VALUE_PREFIX = "\"name\" : \"";
	private static final String TEXT_PAYLOAD_VALUE_SUFFIX = "\"";
	
	private static final String STRIPPED_PROFILE_ID_PATTERN = "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)";
	private static final String PROFILE_ID_PATTERN = "$1-$2-$3-$4-$5";
	
	private final UUID[] profileIDs;
	private final String[] profileNames;
	
	private final int profileAmount;
	
	public PlayerProfilesMojangResponse(String textPayload) {
		super(textPayload);
		
		List<String> playerProfileIDStrings = textPayloadValues(textPayload, PROFILE_ID_TEXT_PAYLOAD_VALUE_PREFIX, TEXT_PAYLOAD_VALUE_SUFFIX);
		List<String> playerProfileNames = textPayloadValues(textPayload, PROFILE_NAME_TEXT_PAYLOAD_VALUE_PREFIX, TEXT_PAYLOAD_VALUE_SUFFIX);
		
		this.profileAmount = playerProfileIDStrings.size();
		this.profileIDs = new UUID[profileAmount];
		this.profileNames = new String[profileAmount];
		
		for(int i = 0; i < profileAmount; i++) {
			
			String profileIDString = playerProfileIDStrings.get(i);
			String profileName = playerProfileNames.get(i);
			
			profileIDString = profileIDString.replaceFirst(STRIPPED_PROFILE_ID_PATTERN, PROFILE_ID_PATTERN);
			UUID profileID = UUID.fromString(profileIDString);
			
			profileIDs[i] = profileID;
			profileNames[i] = profileName;
		}
	}
	
	public MojangPlayerProfileList asProfiles() {
		MojangPlayerProfileList profiles = new MojangPlayerProfileList();
		
		for(int i = 0; i < profileAmount; i++) {
			UUID profileID = profileIDs[i];
			String profileName = profileNames[i];
			
			MojangPlayerProfile profile = new MojangPlayerProfile(profileID, profileName);
			profiles.addProfile(profile);
		}
		
		return profiles;
	}
	
	public UUID getProfileID(int index) {
		return profileIDs[index];
	}
	
	public String getProfileName(int index) {
		return profileNames[index];
	}
	
	public int getProfileAmount() {
		return profileAmount;
	}
	
}
