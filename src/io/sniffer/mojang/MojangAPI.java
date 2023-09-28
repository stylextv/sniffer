package io.sniffer.mojang;

import io.sniffer.mojang.requests.PlayerProfilesMojangRequest;
import io.sniffer.mojang.responses.PlayerProfileMojangResponse;
import io.sniffer.mojang.responses.PlayerProfilesMojangResponse;
import io.sniffer.util.web.WebUtil;

import java.util.Collection;

public class MojangAPI {
	
	private static final String PLAYER_PROFILE_NAME_PATH_FORMAT = "https://api.mojang.com/users/profiles/minecraft/%s";
	private static final String PLAYER_PROFILES_NAMES_PATH = "https://api.mojang.com/profiles/minecraft";
	
	private static final int MAXIMAL_PLAYER_PROFILE_REQUEST_AMOUNT = 10;
	
	public static MojangPlayerProfileList playerProfiles(Collection<String> names) {
		String[] ns = names.toArray(String[]::new);
		
		return playerProfiles(ns);
	}
	
	public static MojangPlayerProfileList playerProfiles(String... names) {
		MojangRequest request = new PlayerProfilesMojangRequest(names);
		String textPayload = request.getTextPayload();
		textPayload = WebUtil.readTextFromPath(PLAYER_PROFILES_NAMES_PATH, textPayload);
		
		PlayerProfilesMojangResponse response = new PlayerProfilesMojangResponse(textPayload);
		return response.asProfiles();
	}
	
	public static MojangPlayerProfile playerProfile(String name) {
		String path = PLAYER_PROFILE_NAME_PATH_FORMAT.formatted(name);
		String textPayload = WebUtil.readTextFromPath(path);
		
		PlayerProfileMojangResponse response = new PlayerProfileMojangResponse(textPayload);
		return response.asProfile();
	}
	
	public static int getMaximalPlayerProfileRequestAmount() {
		return MAXIMAL_PLAYER_PROFILE_REQUEST_AMOUNT;
	}
	
}
