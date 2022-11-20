package com.example.starshipshop.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    
    public static final MediaType APPLICATION_JSON_UTF8 =
    new MediaType(MediaType.APPLICATION_JSON.getType(),
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
    @DisplayName("GET user when user exists should return the user info")
    void getAUser_whenUserExists_shouldThrowResourceNotFoundAdvice() throws Exception {
        this.mockMvc
        .perform(get(BASE_URL + "/" + "john").with(httpBasic(USER_USERNAME, USER_PASSWORD)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.pseudo", is("john")));
    }
    
    
    @Test
    @Tag("GET /{pseudo}")
    @DisplayName("GET user when user does not exist as user should throw error")
    @Order(1)
    void getAUser_whenUserDoesNotExists_shouldThrowResourceNotFoundAdvice() throws Exception {
        this.mockMvc
        .perform(get(BASE_URL + "/" + "test")
        .with(httpBasic(USER_USERNAME, USER_PASSWORD)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("ResourceNotFoundException: ADVICE: No user found with the given pseudo"));
    }
    
    @Test
    @Tag("POST /")
    @DisplayName("create user should works")
    @Order(2)
    void createAUser_whenAuthenticatedAsUser_shouldWorks()
    throws Exception {
        String json =  "{\r\n\"pseudo\": \"test\",\r\n\"addresses\": [\r\n{\r\n\"address\": \"38 Rue Montorgueil\",\r\n\"zipCode\": \"75001\",\r\n\"city\": \"Paris\",\r\n\"state\": \"Ile de France\",\r\n\"country\": \"France\",\r\n\"planet\": \"Earth\"\r\n}\r\n],\r\n\"emails\": [\r\n{\r\n\"email\": \"test@mail.com\"\r\n}\r\n],\r\n\"telephones\": [\r\n{\r\n\"telephoneNumber\": \"9876543210\"\r\n}\r\n    ]\r\n}";
        
        this.mockMvc
        .perform(post(BASE_URL + "/").with(httpBasic(USER_USERNAME, USER_PASSWORD))
        .content(json)
        .contentType(APPLICATION_JSON_UTF8))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.pseudo", is("test")))
        .andExpect(jsonPath("$.emails[0].email",  is("test@mail.com")))
        .andExpect(jsonPath("$.telephones[0].telephoneNumber",  is("9876543210")))
        ;
    }
    
    
    // @Test
    // @Tag("POST /")
    // @DisplayName("create user should works")
    // @Order(2)
    // void createAUser_whenAuthenticatedAsUser_shouldWorks() throws Exception {
        //     String json =
        //             "{\r\n\"pseudo\": \"john\",\r\n\"addresses\": [\r\n{\r\n\"address\": \"38 Rue Montorgueil\",\r\n\"zipCode\": \"75001\",\r\n\"city\": \"Paris\",\r\n\"state\": \"Ile de France\",\r\n\"country\": \"France\",\r\n\"planet\": \"Earth\"\r\n}\r\n],\r\n\"emails\": [\r\n{\r\n\"email\": \"test@mail.com\"\r\n} \r\n],\r\n\"telephones\": [\r\n{\r\n\"telephoneNumber\": \"0123456789\"\r\n}\r\n]\r\n}";
        
        //     this.mockMvc
        //             .perform(post(BASE_URL + "/").with(httpBasic(USER_USERNAME, USER_PASSWORD))
        //                     .content(json))
        //             .andExpect(status().isCreated()).andExpect(jsonPath("$.pseudo", is("john")))
        //             .andExpect(jsonPath("$.emails[0].email", "test@mail.com"));
        // }
    }
