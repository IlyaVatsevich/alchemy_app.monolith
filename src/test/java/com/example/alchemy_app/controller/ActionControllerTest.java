package com.example.alchemy_app.controller;


import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.extension.GMailCustomExtension;
import com.example.alchemy_app.generator.IngredientGeneratorUtil;
import com.example.alchemy_app.generator.MixSellBuyGeneratorUtil;
import com.example.alchemy_app.repository.IngredientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
class ActionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IngredientRepository ingredientRepository;

    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GMailCustomExtension().withConfiguration();

    private final String userDetailsName = "admin_1";

    @Test
    @WithUserDetails(userDetailsName)
    void testMixIngredientsShouldMixIngredients() throws Exception {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        validIngredient.setIngredients(List.of(ingredientRepository.findIngredientById(1000L),ingredientRepository.findIngredientById(1200L)));
        ingredientRepository.save(validIngredient);
        mockMvc.perform(post("/api/v1/action/mix").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(MixSellBuyGeneratorUtil.generateMixDto()))).
                andExpect(status().isCreated());
    }

    @Test
    void testMixIngredientsShouldThrowCauseUserAnonymous() throws Exception {
        mockMvc.perform(post("/api/v1/action/mix").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(MixSellBuyGeneratorUtil.generateMixDto()))).
                andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testMixIngredientsShouldDontMixIngredients() throws Exception {
        mockMvc.perform(post("/api/v1/action/mix").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(MixSellBuyGeneratorUtil.generateMixDto()))).
                andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testBuyIngredientShouldBuyIngredient() throws Exception {
        mockMvc.perform(post("/api/v1/action/buy").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(MixSellBuyGeneratorUtil.generateBuyDto()))).
                andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testBuyIngredientShouldThrowCauseNoEnoughMoney() throws Exception {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        validIngredient.setIngredients(List.of(ingredientRepository.findIngredientById(1000L),ingredientRepository.findIngredientById(1200L)));
        validIngredient.setPrice(1000);
        ingredientRepository.save(validIngredient);
        mockMvc.perform(post("/api/v1/action/buy").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(MixSellBuyGeneratorUtil.generateBuyDto(validIngredient.getId(),1)))).
                andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testSellIngredientShouldSellIngredient() throws Exception {
        mockMvc.perform(post("/api/v1/action/sell").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(MixSellBuyGeneratorUtil.generateSellDto()))).
                andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testSellIngredientShouldThrowCauseNotEnoughCount() throws Exception {
        mockMvc.perform(post("/api/v1/action/sell").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectToJson(MixSellBuyGeneratorUtil.generateSellDto(1000L,2)))).
                andExpect(status().isBadRequest());
    }

    private String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
