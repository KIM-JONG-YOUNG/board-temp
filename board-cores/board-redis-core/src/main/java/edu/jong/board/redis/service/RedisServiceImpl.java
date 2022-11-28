package edu.jong.board.redis.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

	private final RedisTemplate<String, String> redisTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void changeObjectMapper(ObjectMapper mapper) {
		this.objectMapper = mapper;
	}

	@Override
	public void caching(String key, String value, long expireSeconds) {
		redisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
	}

	@Override
	public void  caching(String key, Object value, long expireSeconds) {

		String json = null;

		try {
			json = objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		if (json != null) redisTemplate.opsForValue().set(key, json, expireSeconds, TimeUnit.SECONDS);
	}

	@Override
	public Optional<String> get(String key) {
		return Optional.ofNullable(redisTemplate.opsForValue().get(key));
	}

	@Override
	public <T> Optional<T> get(String key, TypeReference<T> type) {

		String json = redisTemplate.opsForValue().get(key);

		if (StringUtils.isBlank(json)) return Optional.empty(); 
					
		T value = null;
		try {
			value = objectMapper.readValue(json, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		return Optional.ofNullable(value);
	}

	@Override
	public boolean has(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public boolean remove(String key) {
		return redisTemplate.delete(key);
	}

}
