package edu.jong.board.redis.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

	private final RedisProperties properties;
	
	@Bean
	RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName(properties.getHost());
		config.setPort(properties.getPort());
		return new LettuceConnectionFactory(config);
	}

	@Bean
	RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}

}
