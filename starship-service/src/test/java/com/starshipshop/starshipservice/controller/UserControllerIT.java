package com.starshipshop.starshipservice.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
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
public class UserControllerIT {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static final String BASE_URL = "/api/v1/users";
    private static final String USER_USERNAME = "user@mail.com";
    private static final String USER_PASSWORD = "password";
    private static final String ADMIN_USERNAME = "admin@mail.com";
    private static final String ADMIN_PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Tag("GET /{pseudo}")
    @Order(0)
    @DisplayName("GET user when user exists should return the user info")
    void getAUser_whenUserExists_shouldUserInfo() throws Exception {
        this.mockMvc
                .perform(get(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.pseudo").exists());
    }

    @Test
    @Tag("GET /{pseudo}")
    @DisplayName("GET user when user does not exist as user should throw error")
    @Order(1)
    void getAUser_whenUserDoesNotExists_shouldThrowResourceNotFoundAdvice() throws Exception {
        this.mockMvc
                .perform(get(BASE_URL + "/" + "wrongId").with(httpBasic(USER_USERNAME, USER_PASSWORD)))
                .andExpect(status().isNotFound()).andExpect(content().string(
                        "ResourceNotFoundException: ADVICE: Cannot convert hash to id with the given hash: wrongId"));
    }

    @Test
    @Tag("POST /")
    @DisplayName("create user should works")
    @Order(2)
    void createAUser_whenAuthenticatedAsUser_shouldWorks() throws Exception {
        String json = "{\r\n\"pseudo\": \"test\",\r\n\"addresses\": [\r\n{\r\n\"address\": \"38 Rue Montorgueil\",\r\n\"zipCode\": \"75001\",\r\n\"city\": \"Paris\",\r\n\"state\": \"Ile de France\",\r\n\"country\": \"France\",\r\n\"planet\": \"Earth\"\r\n}\r\n],\r\n\"emails\": [\r\n{\r\n\"email\": \"test@mail.com\"\r\n}\r\n],\r\n\"telephones\": [\r\n{\r\n\"telephoneNumber\": \"9876543210\"\r\n}\r\n    ]\r\n}";

        this.mockMvc
                .perform(post(BASE_URL + "/").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pseudo", is("test")))
                .andExpect(jsonPath("$.emails[0].email", is("test@mail.com")))
                .andExpect(jsonPath("$.telephones[0].telephoneNumber", is("9876543210")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(3)
    void updateAUser_shouldWorks() throws Exception {
        String json = "{\r\n    \"pseudo\": \"john2\",\r\n    \"addresses\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"38 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"email\": \"john2@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"0123456789\"\r\n        }\r\n    ]\r\n}";

        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pseudo", is("john2")))
                .andExpect(jsonPath("$.emails[0].email", is("john2@mail.com")))
                .andExpect(jsonPath("$.telephones[0].telephoneNumber", is("0123456789")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(4)
    void updateAUser_whenAddingAnEmail_shouldWorks() throws Exception {
        String json = "{\r\n    \"pseudo\": \"john2\",\r\n    \"addresses\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"38 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"email\": \"john2@mail.com\"\r\n        },\r\n             {\r\n            \"email\": \"john3@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"0123456789\"\r\n        }\r\n    ]\r\n}";

        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pseudo", is("john2")))
                .andExpect(jsonPath("$.emails", hasSize(2)))
                .andExpect(content().string(containsString("john2@mail.com")))
                .andExpect(content().string(containsString("john3@mail.com")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(4)
    void updateAUser_whenTwoEmailsHaveSameId_shouldThrowError() throws Exception {
        String json = "{\r\n    \"pseudo\": \"john\",\r\n    \"addresses\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"38 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"email\": \"john@mail.com\"\r\n        },\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"email\": \"john2@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"1234567890\"\r\n        }\r\n    ]\r\n}";
        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("IllegalArgumentException: ADVICE: ")))
                .andExpect(content().string(containsString("Multiple emails with the same id: W5pvAw0r given.")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(4)
    void updateAUser_whenAnEmailHasAnIdNotHeldByTheUser_shouldThrowError() throws Exception {
        String json = "{\r\n    \"pseudo\": \"johnr\",\r\n    \"addresses\": [\r\n         {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"37 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\":\"GELQkpdQ\",\r\n            \"email\": \"test@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"1234567890\"\r\n        }\r\n    ]\r\n}";
        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("ResourceNotFoundException: ADVICE: ")))
                .andExpect(content().string(containsString("The email with the given id: GELQkpdQ doesn't exists.")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(4)
    void updateAUser_whenAddingATelephone_shouldWorks() throws Exception {
        String json = "{\r\n    \"pseudo\": \"john2\",\r\n    \"addresses\": [\r\n         {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"37 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\":\"W5pvAw0r\",\r\n            \"email\": \"test@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"1234567890\"\r\n        },\r\n  {\r\n            \"telephoneNumber\": \"0987654321\"\r\n        }\r\n     ]\r\n}";
        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pseudo", is("john2")))
                .andExpect(jsonPath("$.telephones", hasSize(2)))
                .andExpect(content().string(containsString("1234567890")))
                .andExpect(content().string(containsString("0987654321")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(4)
    void updateAUser_whenTwoTelephonesHaveSameId_shouldThrowError() throws Exception {
        String json = "{\r\n    \"pseudo\": \"john\",\r\n    \"addresses\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"38 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"email\": \"john@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"1234567891\"\r\n        },\r\n         {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"1234567890\"\r\n        }\r\n    ]\r\n}";

        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("IllegalArgumentException: ADVICE: ")))
                .andExpect(content().string(containsString("Multiple telephones with the same id: W5pvAw0r given.")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(4)
    void updateAUser_whenATelephoneHasAnIdNotHeldByTheUser_shouldThrowError() throws Exception {
        String json = "{\r\n    \"pseudo\": \"john\",\r\n    \"addresses\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"38 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"email\": \"john@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"1234567891\"\r\n        },\r\n         {\r\n            \"id\": \"GELQkpdQ\",\r\n            \"telephoneNumber\": \"1234567890\"\r\n        }\r\n    ]\r\n}";

        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("ResourceNotFoundException: ADVICE: ")))
                .andExpect(
                        content().string(containsString("The telephone with the given id: GELQkpdQ doesn't exists.")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(4)
    void updateAUser_whenAddingAnAddress_shouldWorks() throws Exception {
        String json = "{\r\n    \"pseudo\": \"john2\",\r\n    \"addresses\": [\r\n         {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"37 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        },\r\n        {\r\n            \"address\": \"38 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\":\"W5pvAw0r\",\r\n            \"email\": \"test@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"1234567890\"\r\n        }\r\n    ]\r\n}";
        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pseudo", is("john2")))
                .andExpect(jsonPath("$.addresses", hasSize(2)))
                .andExpect(content().string(containsString("37 Rue Montorgueil")))
                .andExpect(content().string(containsString("38 Rue Montorgueil")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(4)
    void updateAUser_whenTwoAddressesHaveSameId_shouldThrowError() throws Exception {
        String json = "{\r\n    \"pseudo\": \"john2\",\r\n    \"addresses\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"38 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        },\r\n           {\r\n            \"id\": \"W5pvAw0r\",\r\n             \"address\": \"37 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"email\": \"john@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"1234567890\"\r\n        }\r\n    ]\r\n}";

        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("IllegalArgumentException: ADVICE: ")))
                .andExpect(content().string(containsString("Multiple addresses with the same id: W5pvAw0r given.")));
    }

    @Test
    @Tag("PUT /{userId}")
    @DisplayName("update user should works")
    @Order(4)
    void updateAUser_whenAnAddressHasAnIdNotHeldByTheUser_shouldThrowError() throws Exception {
        String json = "{\r\n    \"pseudo\": \"john\",\r\n    \"addresses\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"address\": \"38 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        },\r\n         {\r\n            \"id\": \"GELQkpdQ\",\r\n            \"address\": \"38 Rue Montorgueil\",\r\n            \"zipCode\": 75001,\r\n            \"city\": \"Paris\",\r\n            \"state\": \"Ile de France\",\r\n            \"country\": \"France\",\r\n            \"planet\": \"Earth\"\r\n        }\r\n    ],\r\n    \"emails\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"email\": \"john@mail.com\"\r\n        }\r\n    ],\r\n    \"telephones\": [\r\n        {\r\n            \"id\": \"W5pvAw0r\",\r\n            \"telephoneNumber\": \"1234567890\"\r\n        }\r\n    ]\r\n}";
        mockMvc
                .perform(put(BASE_URL + "/" + "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD))
                        .content(json).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("ResourceNotFoundException: ADVICE: ")))
                .andExpect(content().string(containsString("The address with the given id: GELQkpdQ doesn't exists.")));
    }

    @Test
    @Tag("POST /{userId}/emails")
    @DisplayName("create an email on the authenticated account and given userId works")
    void givenAnEmail_createUserEmail_shouldWorks() throws Exception {
        String json = "{\r\n    \"email\": \"test@mail.com\"\r\n}";
        this.mockMvc
                .perform(post(BASE_URL + "/" + "W5pvAw0r/emails").with(httpBasic(
                        USER_USERNAME,
                        USER_PASSWORD)).contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email", is("test@mail.com")));
    }

    @Test
    @Tag("POST /{userId}/emails")
    void givenAnEmailAndAUserIdOfNonExistUser_createUserEmail_shouldThrowError() throws Exception {
        String json = "{\r\n    \"email\": \"test@mail.com\"\r\n}";
        this.mockMvc.perform(post(BASE_URL + "/" + "qZpyYpYM/emails")
                .with(httpBasic(USER_USERNAME, USER_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        "ResourceNotFoundException: ADVICE: Cannot retrieve user with the given id: qZpyYpYM"));
    }

    @Test
    @Tag("POST /{userId}/emails")
    void givenAnEmail_whenAccountHasNoUserCreateUserEmail_shouldThrowError() throws Exception {
        String json = "{\r\n    \"email\": \"test@mail.com\"\r\n}";
        this.mockMvc.perform(post(BASE_URL + "/" + "W5pvAw0r/emails")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound()).andExpect(content().string(
                        "ResourceNotFoundException: ADVICE: The authenticated account has no user defined"));
    }

    @Test
    @Tag("POST /{userId}/telephones")
    @DisplayName("create a telephone on the authenticated account and given userId works")
    void givenATelephone_createUserTelephone_shouldWorks() throws Exception {
        String json = "{\r\n\"telephoneNumber\":\"0123456789\"\r\n}";
        this.mockMvc
                .perform(post(BASE_URL + "/" + "W5pvAw0r/telephones").with(httpBasic(
                        USER_USERNAME,
                        USER_PASSWORD)).contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.telephoneNumber", is("0123456789")));
    }

    @Test
    @Tag("POST /{userId}/telephones")
    void givenATelephoneAndAUserIdOfNonExistUser_CreateUserTelephone_shouldThrowError() throws Exception {
        String json = "{\r\n    \"telephoneNumber\": \"0123456789\"\r\n}";
        this.mockMvc.perform(post(BASE_URL + "/" + "qZpyYpYM/telephones")
                .with(httpBasic(USER_USERNAME, USER_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        "ResourceNotFoundException: ADVICE: Cannot retrieve user with the given id: qZpyYpYM"));
    }

    @Test
    @Tag("POST /{userId}/telephones")
    void givenATelephone_whenAccountHasNoUserCreateUserTelephone_shouldThrowError() throws Exception {
        String json = "{\r\n    \"telephoneNumber\": \"0123456789\"\r\n}";
        this.mockMvc.perform(post(BASE_URL + "/" + "W5pvAw0r/telephones")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound()).andExpect(content().string(
                        "ResourceNotFoundException: ADVICE: The authenticated account has no user defined"));
    }

    @Test
    @Tag("POST /{userId}/addresses")
    @DisplayName("create an address on the authenticated account and given userId works")
    void givenAnAddress_createUserAddress_shouldWorks() throws Exception {
        String json = "{\r\n    \"address\": \"38 Rue Montorgueil\",\r\n    \"zipCode\": 75001,\r\n    \"city\": \"Paris\",\r\n    \"state\": \"Ile de France\",\r\n    \"country\": \"France\",\r\n    \"planet\": \"Earth\"\r\n}";
        this.mockMvc.perform(post(BASE_URL + "/" + "W5pvAw0r/addresses")
                .with(httpBasic(USER_USERNAME, USER_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.address", is("38 Rue Montorgueil")));
    }

    @Test
    @Tag("POST /{userId}/addresses")
    void givenAnAddressAndAUserIdOfNonExistUser_CreateUserAddress_shouldThrowError()
            throws Exception {
        String json = "{\r\n    \"address\": \"38 Rue Montorgueil\",\r\n    \"zipCode\": 75001,\r\n    \"city\": \"Paris\",\r\n    \"state\": \"Ile de France\",\r\n    \"country\": \"France\",\r\n    \"planet\": \"Earth\"\r\n}";
        this.mockMvc.perform(post(BASE_URL + "/" + "qZpyYpYM/addresses")
                .with(httpBasic(USER_USERNAME, USER_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound()).andExpect(content().string(
                        "ResourceNotFoundException: ADVICE: Cannot retrieve user with the given id: qZpyYpYM"));
    }

    @Test
    @Tag("POST /{userId}/addresses")
    void givenAnAddress_whenAccountHasNoUserCreateUserAddress_shouldThrowError() throws Exception {
        String json = "{\r\n    \"address\": \"38 Rue Montorgueil\",\r\n    \"zipCode\": 75001,\r\n    \"city\": \"Paris\",\r\n    \"state\": \"Ile de France\",\r\n    \"country\": \"France\",\r\n    \"planet\": \"Earth\"\r\n}";
        this.mockMvc.perform(post(BASE_URL + "/" + "W5pvAw0r/addresses")
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                .contentType(APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound()).andExpect(content().string(
                        "ResourceNotFoundException: ADVICE: The authenticated account has no user defined"));
    }

}
