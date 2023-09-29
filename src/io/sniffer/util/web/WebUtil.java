package io.sniffer.util.web;

import io.sniffer.util.system.ThreadUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class WebUtil {
	
	private static final String MISSING_REQUEST_HEADER = null;
	private static final String MISSING_TEXT_PAYLOAD = null;
	private static final boolean DEFAULT_RETRY_ON_EXCEPTION = true;
	
	private static final String REQUEST_HEADER_VALUE_SEPARATOR = ": ";
	
	private static final HttpClient CLIENT;
	private static final Duration CLIENT_CONNECT_TIMEOUT_DURATION = Duration.ofSeconds(5);
	private static final Duration CLIENT_SEND_TIMEOUT_DURATION = Duration.ofSeconds(5);
	
	private static final int INITIAL_DEPTH = 1;
	private static final int TERMINAL_DEPTH = 5;
	private static final int TERMINAL_DEPTH_SLEEP_DURATION = 10000;
	private static final int EXCEPTION_SLEEP_DURATION = 100;
	
	private static final int SMALLEST_ERROR_STATUS_CODE = 400;
	
	static {
		HttpClient.Builder builder = HttpClient.newBuilder();
		builder.connectTimeout(CLIENT_CONNECT_TIMEOUT_DURATION);
		CLIENT = builder.build();
	}
	
	public static String readTextFromPath(String path) {
		return readTextFromPath(path, MISSING_TEXT_PAYLOAD, DEFAULT_RETRY_ON_EXCEPTION);
	}
	
	public static String readTextFromPath(String path, boolean retryOnException) {
		return readTextFromPath(path, MISSING_TEXT_PAYLOAD, retryOnException);
	}
	
	public static String readTextFromPath(String path, String textPayload) {
		return readTextFromPath(path, textPayload, DEFAULT_RETRY_ON_EXCEPTION);
	}
	
	public static String readTextFromPath(String path, String textPayload, boolean retryOnException) {
		return readTextFromPath(path, MISSING_REQUEST_HEADER, textPayload, retryOnException);
	}
	
	public static String readTextFromPath(String path, String requestHeader, String textPayload, boolean retryOnException) {
		return readTextFromPath(path, requestHeader, textPayload, retryOnException, INITIAL_DEPTH);
	}
	
	private static String readTextFromPath(String path, String requestHeader, String textPayload, boolean retryOnException, int depth) {
		String text = null;
		
		try {
			
			HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
			if(textPayload == MISSING_TEXT_PAYLOAD) {
				
				requestBuilder.GET();
				
			} else {
				
				HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(textPayload);
				requestBuilder.POST(bodyPublisher);
			}
			
			URI uri = new URI(path);
			
			requestBuilder.uri(uri);
			if(requestHeader != MISSING_REQUEST_HEADER) requestBuilder.headers(requestHeader.split(REQUEST_HEADER_VALUE_SEPARATOR));
			requestBuilder.timeout(CLIENT_SEND_TIMEOUT_DURATION);
			HttpRequest request = requestBuilder.build();
			
			HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
			HttpResponse<String> response = CLIENT.send(request, bodyHandler);
			
			int statusCode = response.statusCode();
			if(statusCode < SMALLEST_ERROR_STATUS_CODE) text = response.body();
			
		} catch(IOException | URISyntaxException | InterruptedException exception) {
			
			String message = String.format("An error occurred while reading text from path: %s", path);
			System.out.println(message);
		}
		
		if(text == null && retryOnException) {
			ThreadUtil.sleep(EXCEPTION_SLEEP_DURATION);
			
			if(depth == TERMINAL_DEPTH) {
				depth = INITIAL_DEPTH;
				
				ThreadUtil.sleep(TERMINAL_DEPTH_SLEEP_DURATION);
				
			} else {
				
				depth++;
			}
			
			text = readTextFromPath(path, requestHeader, textPayload, retryOnException, depth);
		}
		
		return text;
	}
	
}
