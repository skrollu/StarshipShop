package com.starshipshop.starshipservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
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
public class AccountControllerIT {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static final String BASE_URL = "/api/v1/account";
    private static final String USER_USERNAME = "user@mail.com";
    private static final String USER_PASSWORD = "password";
    private static final String ADMIN_USERNAME = "admin@mail.com";
    private static final String ADMIN_PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Tag("GET /myAccount")
    @DisplayName("GET My account information when authenticated should return my info")
    void getMyAccountInformation_whenAuthenticatedAsUser_shouldReturnMyInfo() throws Exception {
        this.mockMvc
                .perform(get(BASE_URL + "/" + "myAccount")
                        .with(httpBasic(USER_USERNAME, USER_PASSWORD)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.username", is("user@mail.com")))
                .andExpect(jsonPath("$.users").isNotEmpty());
    }

    @Test
    @Tag("GET /myAccount")
    @DisplayName("GET My account information when not authenticated should return 401")
    void getMyAccountInformation_whenNotAuthenticated_shouldReturn401() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/" + "myAccount")).andExpect(status().isBadRequest());
    }

    @Test
    @Tag("GET /myAccount")
    @DisplayName("GET My account information when authenticated as admin should return my info")
    void getMyAccountInformation_whenAuthenticatedAsAdmin_shouldReturnMyInfo() throws Exception {
        this.mockMvc
                .perform(get(BASE_URL + "/" + "myAccount")
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.username", is("admin@mail.com")))
                .andExpect(jsonPath("$.users").isEmpty());
    }

    // Register
    @Test
    @Tag("POST /register")
    @DisplayName("register should return my registration info")
    void register_whenNotAuthenticated_shouldReturnMyInfo() throws Exception {
        String json = "{\r\n\"username\":\"newUser@mail.com\",\r\n\"password\":\"password\",\r\n\"matchingPassword\":\"password\"\r\n}";

        this.mockMvc
                .perform(post(BASE_URL + "/" + "register").contentType(APPLICATION_JSON_UTF8)
                        .content(json))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("newUser@mail.com")))
                .andExpect(jsonPath("$.users").isEmpty());
    }

    @Test
    @Tag("POST /register")
    @DisplayName("register without matchingPassword should return an error")
    void register_withoutMatchingPassword_shouldReturAnError() throws Exception {
        String json = "{\r\n\"username\":\"user@mail.com\",\r\n\"password\":\"password\",\r\n\"matchingPassword\":\"notMatchingPassword\"\r\n}";

        this.mockMvc
                .perform(post(BASE_URL + "/" + "register").contentType(APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isBadRequest()).andExpect(content().string(
                        "NonMatchingPasswordException: ADVICE: The given passwords and matching password are different."));
    }

    @Test
    @Tag("POST /register")
    @DisplayName("register without matchingPassword should return an error")
    void register_withAnAlreadyTakenEmail_shouldReturAnError() throws Exception {
        String json = "{\r\n\"username\":\"user@mail.com\",\r\n\"password\":\"password\",\r\n\"matchingPassword\":\"password\"\r\n}";

        this.mockMvc
                .perform(post(BASE_URL + "/" + "register").contentType(APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isBadRequest()).andExpect(content().string(
                        "AccountUsernameAlreadyExistException: ADVICE: There is an account with that email address: user@mail.com"));

    }

    // Check Email is available
    @Test
    @Tag("POST /emailExists")
    @DisplayName("register without matchingPassword should return an error")
    void checkIfEmailExists_shouldWorks() throws Exception {
        String json = "{\r\n\"email\":\"test@mail.com\"\r\n}";

        this.mockMvc
                .perform(post(BASE_URL + "/" + "emailExists").contentType(APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isOk()).andExpect(jsonPath("$.exist", is(false)));

    }

    @Test
    @Tag("POST /emailExists")
    @DisplayName("emailExists when Email has wrong format should not works")
    void checkIfEmailExists_withWrongFormat_shouldNotWorks() throws Exception {
        String json = "{\r\n\"email\":\"wrongFormat\"\r\n}";

        this.mockMvc
                .perform(post(BASE_URL + "/" + "emailExists").contentType(APPLICATION_JSON_UTF8)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString()
                .contains("MethodArgumentNotValidException: ADVICE: Validation failed");
    }

    @Test
    @Tag("PUT /update")
    @DisplayName("update Account Should Works")
    void updateAccountShouldWorks() throws Exception {
        String json = "{\r\n    \"username\": \"updated1@mail.com\",\r\n    \"password\": \"password123\",\r\n    \"matchingPassword\": \"password123\"\r\n}";

        this.mockMvc
                .perform(put(BASE_URL + "/" + "update").contentType(APPLICATION_JSON_UTF8)
                        .content(json).with(httpBasic("update1@mail.com", USER_PASSWORD)))

                .andExpect(jsonPath("$.username", is("updated1@mail.com")))
                .andExpect(jsonPath("$.users").isEmpty())
                .andExpect(status().isCreated());
    }

    @Test
    @Tag("PUT /update")
    @DisplayName("update Account With The Same Previous Data Should Works")
    void updateAccountWithTheSamePreviousDataShouldWorks() throws Exception {
        String json = "{\r\n    \"username\": \"update2@mail.com\",\r\n    \"password\": \"password\",\r\n    \"matchingPassword\": \"password\"\r\n}";

        this.mockMvc
                .perform(put(BASE_URL + "/" + "update").contentType(APPLICATION_JSON_UTF8)
                        .content(json).with(httpBasic("update2@mail.com", USER_PASSWORD)))
                .andExpect(jsonPath("$.username", is("update2@mail.com")))
                .andExpect(jsonPath("$.users").isEmpty())
                .andExpect(status().isCreated());
    }

    @Test
    @Tag("PUT /update")
    @DisplayName("update Account With An Existing Username Should Throw Error")
    void updateAccountWithAnExistingUsernameShouldThrowError() throws Exception {
        String json = "{\r\n    \"username\": \"user@mail.com\",\r\n    \"password\": \"password\",\r\n    \"matchingPassword\": \"password\"\r\n}";

        this.mockMvc
                .perform(put(BASE_URL + "/" + "update").contentType(APPLICATION_JSON_UTF8)
                        .content(json).with(httpBasic("update3@mail.com", USER_PASSWORD)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString()
                .contains("There is an account with that email address: user@mail.com");
    }

    @Test
    @Tag("PUT /update")
    @DisplayName("update Account With Non Matching Password Should Throw Error")
    void updateAccountWithNonMatchingPasswordShouldThrowError() throws Exception {
        String json = "{\r\n    \"username\": \"user@mail.com\",\r\n    \"password\": \"wrongPassword\",\r\n    \"matchingPassword\": \"password123\"\r\n}";

        this.mockMvc
                .perform(put(BASE_URL + "/" + "update").contentType(APPLICATION_JSON_UTF8)
                        .content(json).with(httpBasic("update3@mail.com", USER_PASSWORD)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString()
                .contains("The given passwords and matching password are different.");
    }
}