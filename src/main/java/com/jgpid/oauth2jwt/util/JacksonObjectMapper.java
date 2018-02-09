package com.jgpid.oauth2jwt.util;

import org.springframework.integration.support.json.Jackson2JsonObjectMapper;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonObjectMapper {
	public static Jackson2JsonObjectMapper getJacksonJsonObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(Feature.ALLOW_COMMENTS, true);

		return new Jackson2JsonObjectMapper(objectMapper);
	}
}
