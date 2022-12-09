package com.example.starshipshop.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.hamcrest.Matchers;
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

import com.example.starshipshop.domain.manufacturer.ManufacturerInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ActiveProfiles("test-mysql")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManufacturerControllerIT {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private static final String BASE_URL = "/api/v1/manufacturers";
	private static final String USER_USERNAME = "user@mail.com";
	private static final String USER_PASSWORD = "password";
	private static final String ADMIN_USERNAME = "admin@mail.com";
	private static final String ADMIN_PASSWORD = "password";

	@Autowired
	private MockMvc mockMvc;

	// GET /manufacturers
	@Test
	@Order(1)
	@Tag("GET /manufacturers")
	@DisplayName("GET all manufacturers when not authenticated should works")
	void getManufacturers_whenNotAuthenticated_shouldWorks() throws Exception {
		this.mockMvc.perform(get(BASE_URL))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("ManufacturerToGet1")))
				.andExpect(jsonPath("$._embedded.manufacturers[0]").exists())
				.andExpect(jsonPath("$._embedded.manufacturers[0]._links.self.href").exists())
				.andExpect(jsonPath("$._embedded.manufacturers[0]._links.self.href",
						is(Matchers.containsString(BASE_URL + "/" + "W5pvAw0r"))))
				.andExpect(jsonPath("$._links.manufacturers.href").value(Matchers.containsString(BASE_URL)));
	}

	@Test
	@Order(1)
	@Tag("GET /manufacturers")
	@DisplayName("GET all manufacturers when authenticated as User should works")
	void getManufacturers_whenAuthenticatedAsUser_shouldWorks() throws Exception {
		this.mockMvc.perform(get(BASE_URL).with(httpBasic(USER_USERNAME, USER_PASSWORD)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("ManufacturerToGet1")))
				.andExpect(jsonPath("$._embedded.manufacturers[0]").exists())
				.andExpect(jsonPath("$._embedded.manufacturers[0]._links.self.href").exists())
				.andExpect(jsonPath("$._embedded.manufacturers[0]._links.self.href",
						is(Matchers.containsString(BASE_URL + "/" + "W5pvAw0r"))))
				.andExpect(jsonPath("$._links.manufacturers.href").value(Matchers.containsString(BASE_URL)));
	}

	// GET /manufacturers/{id}
	@Test
	@Order(2)
	@Tag("GET /manufacturers/{id}")
	@DisplayName("GET one manufacturer when not authenticated should works throught all layers")
	void getManufacturerById_whenNotAuthenticated_shouldWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r"))

				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.name", is("ManufacturerToGet1")))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id", not("test")))
				.andExpect(jsonPath("$.id", is("W5pvAw0r")))
				.andExpect(jsonPath("$._links.self.href", is(Matchers.containsString(BASE_URL + "/" + "W5pvAw0r"))))
				.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	@Tag("GET /manufacturers/{id}")
	@DisplayName("GET one manufacturer when authenticated as user should works throught all layers")
	void getManufacturerById_whenAuthenticatedAsUser_shouldWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "/{id}", "W5pvAw0r").with(httpBasic(USER_USERNAME, USER_PASSWORD)))

				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.name", is("ManufacturerToGet1")))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id", not("test")))
				.andExpect(jsonPath("$.id", is("W5pvAw0r")))
				.andExpect(jsonPath("$._links.self.href", is(Matchers.containsString(BASE_URL + "/" + "W5pvAw0r"))))
				.andExpect(status().isOk());
	}

	// POST /manufacturers/{id}
	@Test
	@Tag("POST /manufacturers/{id}")
	@DisplayName("POST manufacturer when authenticated as Admin should works throught all layers")
	public void createManufacturer_whenAuthenticatedAsAdmin_shouldWorksThroughAllLayers() throws Exception {
		ManufacturerInput requestObj = new ManufacturerInput("ManufacturerCreated");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(post(BASE_URL).with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(jsonPath("$.name").value("ManufacturerCreated"))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(status().isCreated());
	}

	@Test
	@Tag("POST /manufacturers/{id}")
	@DisplayName("POST manufacturer when authenticated as user should return isForbiddent 403")
	public void createManufacturer_whenAuthenticatedAsUser_shouldReturn403() throws Exception {
		ManufacturerInput requestObj = new ManufacturerInput("ManufacturerCreated");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(post(BASE_URL).with(httpBasic(USER_USERNAME, USER_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isForbidden());
	}

	@Test
	@Tag("POST /manufacturers/{id}")
	@DisplayName("POST manufacturer when not authenticated should return isForbidden 401")
	public void createManufacturer_whenNotAuthenticated_shouldReturn401() throws Exception {
		ManufacturerInput requestObj = new ManufacturerInput("ManufacturerCreated");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(post(BASE_URL).contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@Tag("POST /manufacturers/{id}")
	@DisplayName("POST manufacturer with empty name when authenticated as Admin should throw Illegal Argument Exception")
	public void createManufacturerWithEmptyName_whenAuthenticatedAsAdmin_shouldThrowIllegalArgumentException()
			throws Exception {
		ManufacturerInput requestObj = new ManufacturerInput("");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(post(BASE_URL).with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("MethodArgumentNotValidException: ADVICE:")));
	}

	@Test
	@Tag("POST /manufacturers/{id}")
	@DisplayName("POST manufacturer with null name when authenticated as Admin should throw Illegal Argument Exception")
	public void createManufacturerWithNullName_whenAuthenticatedAsAdmin_shouldThrowIllegalArgumentException()
			throws Exception {
		ManufacturerInput requestObj = new ManufacturerInput();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(post(BASE_URL).with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("MethodArgumentNotValidException: ADVICE: ")));
	}

	// PUT /manufacturers/{id}
	@Test
	@Tag("PUT /manufacturers/{id}")
	@DisplayName("PUT manufacturer works when authenticated as Admin should works")
	public void updateManufacturer_whenAuthenticatedAsAdmin_shouldWorks() throws Exception {
		final String name = "ManufacturerToUpdate3";

		ManufacturerInput requestObj = new ManufacturerInput();
		requestObj.setName(name);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(put(BASE_URL + "/mbLbXp35").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value("mbLbXp35"))
				.andExpect(jsonPath("$.name").value(name))
				.andExpect(status().isCreated());
	}

	@Test
	@Tag("PUT /manufacturers/{id}")
	@DisplayName("PUT manufacturer works when authenticated as User should response 403")
	public void updateManufacturer_whenAuthenticatedAsUser_shouldResponse403() throws Exception {
		final String name = "ManufacturerToUpdate3";

		ManufacturerInput requestObj = new ManufacturerInput();
		requestObj.setName(name);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(put(BASE_URL + "/mbLbXp35").with(httpBasic(USER_USERNAME, USER_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isForbidden());
	}

	@Test
	@Tag("PUT /manufacturers/{id}")
	@DisplayName("PUT manufacturer works when not authenticated should response 401")
	public void updateManufacturer_whenNotAuthenticated_shouldResponse401() throws Exception {
		final String name = "ManufacturerToUpdate3";

		ManufacturerInput requestObj = new ManufacturerInput();
		requestObj.setName(name);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(put(BASE_URL + "/mbLbXp35")
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@Tag("PUT /manufacturers/{id}")
	@DisplayName("PUT manufacturer with empty name when authenticated as Admin should throw Illegal Argument Exception")
	public void updateManufacturerWithEmptyName_whenAuthenticatedAsAdmin_shouldThrowIllegalArgumentException()
			throws Exception {
		final String name = "";

		ManufacturerInput requestObj = new ManufacturerInput();
		requestObj.setName(name);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(put(BASE_URL + "/mbLbXp35").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("MethodArgumentNotValidException: ADVICE: ")));
	}

	@Test
	@Tag("PUT /manufacturers/{id}")
	@DisplayName("PUT manufacturer with null name when authenticated as Admin should throw Illegal Argument Exception")
	public void updateManufacturerWithNullName_whenAuthenticatedAsAdmin_shouldThrowIllegalArgumentException()
			throws Exception {
		ManufacturerInput requestObj = new ManufacturerInput();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(put(BASE_URL + "/mbLbXp35").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isBadRequest())
				.andExpect(content()
						.string(containsString("MethodArgumentNotValidException: ADVICE: ")));
	}

	@Test
	@Tag("PUT /manufacturers/{id}")
	@DisplayName("PUT manufacturer with wrong id when authenticated as Admin should throw Resource not found exception by the HashToIdConverter")
	public void updateManufacturer_whenAuthenticatedAsAdmin_shouldThrowResourceNotFoundException() throws Exception {
		final String name = "ManufacturerToUpdate3";

		ManufacturerInput requestObj = new ManufacturerInput();
		requestObj.setName(name);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
				.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc.perform(put(BASE_URL + "/wrongId").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(content().string(Matchers.startsWith("ResourceNotFoundException: ADVICE: ")))
				.andExpect(content().string(containsString("Cannot convert hash to id with the given hash:")))
				.andExpect(content().string(Matchers.endsWith("wrongId")))
				.andExpect(status().isNotFound());
	}

	// DELETE /manufacturers/{id}
	@Test
	@Tag("DELETE /manufacturers/{id}")
	@DisplayName("DELETE manufacturer when authenticated as Admin should works throught all layers")
	public void deleteManufacturer_whenAuthenticatedAsAdmin_shouldWorksThroughAllLayers() throws Exception {
		// Id of a not joined Manufacturer to avoid nested errors or errors caused by
		// delete before other tests ended.
		mockMvc.perform(delete(BASE_URL + "/{id}", "qZpyYpYM").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.deleted").exists())
				.andExpect(jsonPath("$.deleted").value(true))
				.andExpect(status().isOk());
	}

	@Test
	@Tag("DELETE /manufacturers/{id}")
	@DisplayName("DELETE manufacturer when authenticated as User should response 403")
	public void deleteManufacturer_whenAuthenticatedAsUser_shouldResponse403() throws Exception {
		// Id of a not joined Manufacturer to avoid nested errors or errors caused by
		// delete before other tests ended.
		mockMvc.perform(delete(BASE_URL + "/{id}", "qZpyYpYM").with(httpBasic(USER_USERNAME, USER_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isForbidden());
	}

	@Test
	@Tag("DELETE /manufacturers/{id}")
	@DisplayName("DELETE manufacturer with wrong id when authenticated as Admin should throw Resource not found exception by the HashToIdConverter")
	public void deleteManufacturer_whenAuthenticatedAsAdmin_shouldThrowResourceNotFoundException() throws Exception {
		mockMvc.perform(delete(BASE_URL + "/{id}", "wrongId").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
				.contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().string(Matchers.startsWith("ResourceNotFoundException: ADVICE: ")))
				.andExpect(content().string(containsString("Cannot convert hash to id with the given hash:")))
				.andExpect(content().string(Matchers.endsWith("wrongId")))
				.andExpect(status().isNotFound());
	}
}
