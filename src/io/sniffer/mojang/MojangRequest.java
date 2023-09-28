package io.sniffer.mojang;

public abstract class MojangRequest {
	
	private String textPayload;
	
	protected abstract String textPayload();
	
	public String getTextPayload() {
		if(textPayload == null) textPayload = textPayload();
		return textPayload;
	}
	
	public static String textPayloadValues(String[] values, String valuesPrefix, String valuesSuffix, String valuePrefix, String valueSuffix, String valueSeparator) {
		StringBuilder builder = new StringBuilder();
		builder.append(valuesPrefix);
		
		boolean firstValue = true;
		
		for(String value : values) {
			
			if(!firstValue) builder.append(valueSeparator);
			builder.append(valuePrefix);
			builder.append(value);
			builder.append(valueSuffix);
			
			firstValue = false;
		}
		
		builder.append(valuesSuffix);
		return builder.toString();
	}
	
}
