package io.sniffer.mojang.responses;

import io.sniffer.mojang.MojangResponse;

public class PlayerProfileNameBannedMojangResponse extends MojangResponse {
	
	private static final String PROFILE_NAME_BANNED_TEXT_PAYLOAD_VALUE_PREFIX = "\"status\" : \"";
	private static final String PROFILE_NAME_BANNED_TEXT_PAYLOAD_VALUE_SUFFIX = "\"";
	
	private static final String BANNED_PROFILE_NAME_BANNED_STRING = "NOT_ALLOWED";
	
	private final boolean profileNameBanned;
	
	public PlayerProfileNameBannedMojangResponse(String textPayload) {
		super(textPayload);
		
		String profileNameBannedString = textPayloadValue(textPayload, PROFILE_NAME_BANNED_TEXT_PAYLOAD_VALUE_PREFIX, PROFILE_NAME_BANNED_TEXT_PAYLOAD_VALUE_SUFFIX);
		this.profileNameBanned = profileNameBannedString.equals(BANNED_PROFILE_NAME_BANNED_STRING);
	}
	
	public boolean isProfileNameBanned() {
		return profileNameBanned;
	}
	
}
