package io.sniffer.mojang;

import java.util.ArrayList;
import java.util.List;

public class MojangResponse {
	
	private static final int FIRST_STRING_CHARACTER_INDEX = 0;
	private static final int INVALID_STRING_CHARACTER_INDEX = -1;
	
	private final String textPayload;
	
	public MojangResponse(String textPayload) {
		this.textPayload = textPayload;
	}
	
	public String getTextPayload() {
		return textPayload;
	}
	
	public static List<String> textPayloadValues(String textPayload, String valuePrefix, String valueSuffix) {
		String[] splitTextPayload = textPayload.split(valuePrefix);
		List<String> values = new ArrayList<>();
		
		for(int i = 1; i < splitTextPayload.length; i++) {
			String value = splitTextPayload[i];
			int index = value.indexOf(valueSuffix);
			
			if(index == INVALID_STRING_CHARACTER_INDEX) continue;
			value = value.substring(FIRST_STRING_CHARACTER_INDEX, index);
			values.add(value);
		}
		
		return values;
	}
	
	public static String textPayloadValue(String textPayload, String valuePrefix, String valueSuffix) {
		int index = textPayload.indexOf(valuePrefix);
		if(index == INVALID_STRING_CHARACTER_INDEX) return null;
		
		index += valuePrefix.length();
		textPayload = textPayload.substring(index);
		
		index = textPayload.indexOf(valueSuffix);
		if(index == INVALID_STRING_CHARACTER_INDEX) return null;
		
		return textPayload.substring(FIRST_STRING_CHARACTER_INDEX, index);
	}
	
}
