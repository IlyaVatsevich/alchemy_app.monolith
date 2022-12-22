package com.example.alchemy_app.controller;

import com.example.alchemy_app.extension.GMailCustomExtension;
import com.example.alchemy_app.generator.IngredientDtoGeneratorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GMailCustomExtension().withConfiguration();

    private final String userDetailsNameAdmin = "admin_1";

    private final String userDetailsNameUser = "user_1";

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testCreateIngredientShouldCreateIngredient() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(IngredientDtoGeneratorUtil.createRecipeIngredientDto()))).
                andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(userDetailsNameUser)
    void testCreateIngredientShouldThrowCauseForbiddenForUserRole() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(IngredientDtoGeneratorUtil.createRecipeIngredientDto()))).
                andExpect(status().isForbidden());
    }

    @Test
    void testCreateIngredientShouldThrowCauseUserAnonymous() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(IngredientDtoGeneratorUtil.createRecipeIngredientDto()))).
                andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testCreateIngredientShouldThrowCauseInvalidIngredientData() throws Exception {
        mockMvc.perform(post("/api/v1/ingredient").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(IngredientDtoGeneratorUtil.createIngredientWithInvalidPrice()))).
                andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testGetAllIngredientsShouldReturnAllIngredients() throws Exception {
        mockMvc.perform(get("/api/v1/ingredient").
                        content(objectToJson(Pageable.unpaged()))).
                andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testGetIngredientByIdShouldReturnIngredient() throws Exception {
        mockMvc.perform(get("/api/v1/ingredient/{id}",1000)).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(userDetailsNameAdmin)
    void testGetIngredientByIdShouldThrowCauseIngredientNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/ingredient/{id}",2000)).andExpect(status().isBadRequest());
    }

    private String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
