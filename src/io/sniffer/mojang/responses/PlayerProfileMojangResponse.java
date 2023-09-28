package io.sniffer.mojang.responses;

import io.sniffer.mojang.MojangPlayerProfile;
import io.sniffer.mojang.MojangResponse;

import java.util.UUID;

public class PlayerProfileMojangResponse extends MojangResponse {
	
	private static final String PROFILE_ID_TEXT_PAYLOAD_VALUE_PREFIX = "\"id\" : \"";
	private static final String PROFILE_NAME_TEXT_PAYLOAD_VALUE_PREFIX = "\"name\" : \"";
	private static final String TEXT_PAYLOAD_VALUE_SUFFIX = "\"";
	
	private static final String STRIPPED_PROFILE_ID_PATTERN = "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)";
	private static final String PROFILE_ID_PATTERN = "$1-$2-$3-$4-$5";
	
	private final UUID profileID;
	private final String profileName;
	
	public PlayerProfileMojangResponse(String textPayload) {
		super(textPayload);
		
		String profileIDString = textPayloadValue(textPayload, PROFILE_ID_TEXT_PAYLOAD_VALUE_PREFIX, TEXT_PAYLOAD_VALUE_SUFFIX);
		profileIDString = profileIDString.replaceFirst(STRIPPED_PROFILE_ID_PATTERN, PROFILE_ID_PATTERN);
		
		this.profileID = UUID.fromString(profileIDString);
		this.profileName = textPayloadValue(textPayload, PROFILE_NAME_TEXT_PAYLOAD_VALUE_PREFIX, TEXT_PAYLOAD_VALUE_SUFFIX);
	}
	
	public MojangPlayerProfile asProfile() {
		return new MojangPlayerProfile(profileID, profileName);
	}
	
	public UUID getProfileID() {
		return profileID;
	}
	
	public String getProfileName() {
		return profileName;
	}
	
}
