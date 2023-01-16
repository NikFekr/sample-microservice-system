package com.sample.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@SpringBootTest(classes = LibraryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan({"com.sample.library.*"})
@ActiveProfiles("dev")
public class LibraryApplicationTests {

	@Autowired
	protected TestRestTemplate restTemplate;

	protected HttpHeaders headers = new HttpHeaders();

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	protected void setAuthorizationHeader(String accessToken) {
		headers.remove("Authorization");
		headers.add("Authorization", "Bearer " + accessToken);
	}


	protected <T> T post(String path, Object request, HttpHeaders headers, Class<T> responseType) {
		return restTemplate.exchange(
				path,
				HttpMethod.POST,
				new HttpEntity<>(request, headers),
				responseType
		).getBody();
	}
}
