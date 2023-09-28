package io.sniffer.mojang;

import io.sniffer.mojang.requests.PlayerProfilesMojangRequest;
import io.sniffer.mojang.responses.PlayerProfileMojangResponse;
import io.sniffer.mojang.responses.PlayerProfilesMojangResponse;
import io.sniffer.util.web.WebUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MojangAPI {
	
	private static final Set<String> BANNED_PLAYER_PROFILE_NAMES = new HashSet<>();
	
	private static final String PLAYER_PROFILE_NAME_PATH_FORMAT = "https://api.mojang.com/users/profiles/minecraft/%s";
	private static final String PLAYER_PROFILES_NAMES_PATH = "https://api.mojang.com/profiles/minecraft";
	
	private static final int MAXIMAL_PLAYER_PROFILE_REQUEST_AMOUNT = 10;
	
	static {
		BANNED_PLAYER_PROFILE_NAMES.add("anus");
		BANNED_PLAYER_PROFILE_NAMES.add("aryan");
		BANNED_PLAYER_PROFILE_NAMES.add("autobiography");
		BANNED_PLAYER_PROFILE_NAMES.add("bitch");
		BANNED_PLAYER_PROFILE_NAMES.add("bondage");
		BANNED_PLAYER_PROFILE_NAMES.add("cocaine");
		BANNED_PLAYER_PROFILE_NAMES.add("cock");
		BANNED_PLAYER_PROFILE_NAMES.add("consolidation");
		BANNED_PLAYER_PROFILE_NAMES.add("consultations");
		BANNED_PLAYER_PROFILE_NAMES.add("cum");
		BANNED_PLAYER_PROFILE_NAMES.add("damn");
		BANNED_PLAYER_PROFILE_NAMES.add("dick");
	}
	
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
	
	public static boolean playerProfileNameBanned(String name) {
		name = name.toLowerCase();
		
		return BANNED_PLAYER_PROFILE_NAMES.contains(name);
	}
	
	public static int getMaximalPlayerProfileRequestAmount() {
		return MAXIMAL_PLAYER_PROFILE_REQUEST_AMOUNT;
	}
	
}
