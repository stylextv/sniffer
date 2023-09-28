package io.sniffer.mojang.requests;

import io.sniffer.mojang.MojangRequest;

public class PlayerProfilesMojangRequest extends MojangRequest {
	
	private static final String PROFILE_NAMES_TEXT_PAYLOAD_PREFIX = "[";
	private static final String PROFILE_NAMES_TEXT_PAYLOAD_SUFFIX = "]";
	private static final String PROFILE_NAME_TEXT_PAYLOAD_PREFIX = "\"";
	private static final String PROFILE_NAME_TEXT_PAYLOAD_SUFFIX = "\"";
	private static final String PROFILE_NAME_TEXT_PAYLOAD_SEPARATOR = ", ";
	
	private final String[] names;
	
	public PlayerProfilesMojangRequest(String[] names) {
		this.names = names;
	}
	
	@Override
	protected String textPayload() {
		return textPayloadValues(names, PROFILE_NAMES_TEXT_PAYLOAD_PREFIX, PROFILE_NAMES_TEXT_PAYLOAD_SUFFIX, PROFILE_NAME_TEXT_PAYLOAD_PREFIX, PROFILE_NAME_TEXT_PAYLOAD_SUFFIX, PROFILE_NAME_TEXT_PAYLOAD_SEPARATOR);
	}
	
	public String[] getNames() {
		return names;
	}
	
}
