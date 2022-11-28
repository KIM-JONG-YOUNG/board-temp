package edu.jong.board.redis.service;

import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface RedisService {

	void changeObjectMapper(ObjectMapper mapper);
	
	void caching(String key, String value, long expireSeconds);
	
	void caching(String key, Object value, long expireSeconds);

	Optional<String> get(String key);
	
	<T> Optional<T> get(String key, TypeReference<T> type);

	boolean has(String key);
	
	boolean remove(String key);
	
}
