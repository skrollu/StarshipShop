package com.starshipshop.starshipservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test-mysql")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomeControllerIT {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    public static final String BASE_URL = "/";
   
   @Autowired
	private MockMvc mockMvc;
    
	// HOME
    @Test
	@DisplayName("GET home message")
	void getHomeMessage_shouldReturnWelcomeMessage() throws Exception {
		this.mockMvc.perform(get(BASE_URL))
		.andExpect(status().isOk())
		.andExpect(content().string(is("Welcome, home !")));
	}
	
	// USER
	@Test
	@DisplayName("GET user message authenticated as User should return user message")
	void getUserMessage_whenAuthenticatedAsUser_shouldWorks() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "user").with(httpBasic("user@mail.com","password")))
		.andExpect(status().isOk())
		.andExpect(content().string(is("Welcome, user !")));
	}

	@Test
	@DisplayName("GET user message when not authenticated should response 401")
	void getUserMessage_whenNotAuthenticated_shouldResponse401() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "user"))
		.andExpect(status().isUnauthorized());
	}

	// ADMIN
	@Test
	@DisplayName("GET admin message authenticated as admin should return admin message")
	void getAdminMessage_whenAuthenticatedAsAdmin_shouldWorks() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "admin").with(httpBasic("admin@mail.com","password")))
		.andExpect(status().isOk())
		.andExpect(content().string(is("Welcome, admin !")));
	}

	@Test
	@DisplayName("GET admin message authenticated as user should response 403")
	void getAdminMessage_whenAuthenticatedAsUser_shouldResponse403() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "admin").with(httpBasic("user@mail.com","password")))
		.andExpect(status().isForbidden());
	}

    @Test
	@DisplayName("GET admin message when not authenticated should response 401: Unauthorized message")
	void getAdminMessage_whenNotAuthenticated_shouldResponse401Message() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "admin"))
		.andExpect(status().isUnauthorized());
	}

	// Writer
	@Test
	@DisplayName("GET writer message authenticated as user should response 403: Forbidden message")
	void getWriterMessage_whenAuthenticatedAsUser_shouldResponse403() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "write").with(httpBasic("user@mail.com","password")))
		.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("GET writer message authenticated as admin should works")
	void getWriterMessage_whenAuthenticatedAsAdmin_shouldWorks() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "write").with(httpBasic("admin@mail.com","password")))
		.andExpect(status().isOk())
		.andExpect(content().string(is("Welcome, writer !")));
	}

	// Reader
	@Test
	@DisplayName("GET reader message authenticated as admin should works")
	void getReaderMessage_whenAuthenticatedAsAdmin_shouldWorks() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "read").with(httpBasic("admin@mail.com","password")))
		.andExpect(status().isOk())
		.andExpect(content().string(is("Welcome, reader !")));
	}

	@Test
	@DisplayName("GET reader message authenticated as user should works")
	void getReaderMessage_whenAuthenticatedAsUser_shouldWorks() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "read").with(httpBasic("user@mail.com","password")))
		.andExpect(status().isOk())
		.andExpect(content().string(is("Welcome, reader !")));
	}
}
