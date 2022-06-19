package com.example.starShipShop.manufacturer;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.starshipShop.dto.ManufacturerDto;
import com.example.starshipShop.requestDto.ManufacturerRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@ActiveProfiles("test-h2")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ManufacturerIntegrationTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	public static final String BASE_URL = "/api/v1/manufacturers";

	@Autowired
	private MockMvc mockMvc;

	// TODO Change the hashids salt to have static test
	@Test
	@DisplayName("GET all manufacturers works throught all layers")
	void getManufacturersWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get(BASE_URL))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("TestManufacturer")))
					.andExpect(jsonPath("$._embedded.manufacturers[0]").exists())
					.andExpect(jsonPath("$._embedded.manufacturers[0]._links.self.href").exists())
					.andExpect(jsonPath("$._embedded.manufacturers[0]._links.self.href",
							is(Matchers.containsString(BASE_URL + "/" + "3R45bLd8"))))
					.andExpect(jsonPath("$._links.manufacturers.href").value(Matchers.containsString(BASE_URL)));
	}

	@Test
	void getManufacturerByIdWorksThroughAllLayers() throws Exception {
		this.mockMvc.perform(get(BASE_URL + "/{id}", "pAGJNGay"))
					.andDo(print())
					.andExpect(jsonPath("$.name").exists())
					.andExpect(jsonPath("$.name", is("TestManufacturer")))
					.andExpect(jsonPath("$.id").exists())
					.andExpect(jsonPath("$.id", not("test")))
					.andExpect(jsonPath("$.id", is("pAGJNGay")))
					.andExpect(jsonPath("$._links.self.href", is(Matchers.containsString(BASE_URL + "/" + "pAGJNGay"))))
					.andExpect(status().isOk());
	}

	@Test
	public void createManufacturerWorksThroughAllLayers() throws Exception {
		ManufacturerRequestDTO requestObj = new ManufacturerRequestDTO();
		requestObj.setName("Renault");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
								.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
										.content(requestJson))
				.andExpect(jsonPath("$.name").value("Renault"))
				.andExpect(jsonPath("$.id").exists())
//				.andExpect(jsonPath("$.id").value("PR4WXLep"))
				.andExpect(status().isOk());
	}

	@Test
	public void createManufacturerWithEmptyNameThrowIllegalArgumentException() throws Exception {
		ManufacturerRequestDTO requestObj = new ManufacturerRequestDTO();
		requestObj.setName("");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
								.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
										.content(requestJson))
				.andExpect(content().string(containsString("Name cannot be empty.")))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createManufacturerWithNullNameThrowIllegalArgumentException() throws Exception {
		ManufacturerRequestDTO requestObj = new ManufacturerRequestDTO();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
								.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc	.perform(post(BASE_URL)	.contentType(APPLICATION_JSON_UTF8)
										.content(requestJson))
				.andExpect(content().string(containsString("Name cannot be null.")))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updateManufacturerWorksThroughAllLayers() throws Exception {
		final String name = "Peugeot";

		ManufacturerDto requestObj = new ManufacturerDto();
		requestObj.setName(name);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
								.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc	.perform(put(BASE_URL + "/3R45bLd8").contentType(APPLICATION_JSON_UTF8)
													.content(requestJson))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value("3R45bLd8"))
				.andExpect(jsonPath("$.name").value(name))
				.andExpect(status().isOk());
	}

	@Test
	public void updateManufacturerWithEmptyNameThrowIllegalArgumentException() throws Exception {
		final String name = "";

		ManufacturerRequestDTO requestObj = new ManufacturerRequestDTO();
		requestObj.setName(name);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
								.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc	.perform(put(BASE_URL + "/3R45bLd8").contentType(APPLICATION_JSON_UTF8)
													.content(requestJson))
				.andExpect(content().string(containsString("Name cannot be empty.")))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updateManufacturerWithNullNameThrowIllegalArgumentException() throws Exception {
		ManufacturerRequestDTO requestObj = new ManufacturerRequestDTO();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
								.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc	.perform(put(BASE_URL + "/3R45bLd8").contentType(APPLICATION_JSON_UTF8)
													.content(requestJson))
				.andExpect(content().string(containsString("Name cannot be null.")))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updateManufacturerThrowResourceNotFoundException() throws Exception {
		final String name = "Peugeot";

		ManufacturerRequestDTO requestObj = new ManufacturerRequestDTO();
		requestObj.setName(name);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer()
								.withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(requestObj);

		mockMvc	.perform(put(BASE_URL + "/1000").contentType(APPLICATION_JSON_UTF8)
												.content(requestJson))
				.andExpect(content().string(containsString("Cannot find resource with the given id:")))
				.andExpect(content().string(containsString("1000")))
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteManufacturerWorksThroughAllLayers() throws Exception {
		mockMvc	.perform(delete(BASE_URL + "/{id}", "PR4WXLep").contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.deleted").exists())
				.andExpect(jsonPath("$.deleted").value(true))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteManufacturerThrowResourceNotFoundException() throws Exception {
		mockMvc	.perform(delete(BASE_URL + "/{id}", "11111").contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().string(containsString("Cannot find resource with the given id:")))
				.andExpect(content().string(containsString("11111")))
				.andExpect(status().isNotFound());
	}

}
