package com.example.starshipShop.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.nio.charset.Charset;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test-mysql")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountControllerIT {
    
    public static final MediaType APPLICATION_JSON_UTF8 =
    new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(),
    MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    public static final String BASE_URL = "/";
    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWORD = "password";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @Tag("GET /myAccount")
    @DisplayName("GET My account information when authenticated should return my info")
    void getMyAccountInformation_whenAthenticatedAsUser_shouldReturnMyInfo() throws Exception {
        this.mockMvc
        .perform(get(BASE_URL + "myAccount").with(httpBasic(USER_USERNAME, USER_PASSWORD)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is("user")))
        .andExpect(jsonPath("$.users").isNotEmpty());
    }

    @Test
    @Tag("GET /myAccount")
    @DisplayName("GET My account information when not authenticated should return 401")
    void getMyAccountInformation_whenNotAthenticated_shouldReturn401() throws Exception {
        this.mockMvc
                .perform(get(BASE_URL + "myAccount")).andExpect(status().isBadRequest());
    }
    
    @Test
    @Tag("GET /myAccount")
    @DisplayName("GET My account information when authenticated as admin should return my info")
    void getMyAccountInformation_whenAthenticatedAsAdmin_shouldReturnMyInfo() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "myAccount")).andExpect(status().isUnauthorized());
    }
}
