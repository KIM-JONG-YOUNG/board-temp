package edu.jong.board.role.type;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

import edu.jong.board.type.CodeEnum;

public enum APIMethod implements CodeEnum<String> {

	ALL,
	GET,
	POST,
	PUT,
	DELETE;

	private static final Map<HttpMethod, APIMethod> HTTP_METHODS = new HashMap<>();
	static {
		HTTP_METHODS.put(HttpMethod.GET, GET);
		HTTP_METHODS.put(HttpMethod.POST, POST);
		HTTP_METHODS.put(HttpMethod.PUT, PUT);
		HTTP_METHODS.put(HttpMethod.DELETE, DELETE);
	}
	
	@Override
	public String getCode() {
		return this.name();
	}
	
	public static APIMethod convert(HttpMethod method) {
		return HTTP_METHODS.get(method);
	}
}
