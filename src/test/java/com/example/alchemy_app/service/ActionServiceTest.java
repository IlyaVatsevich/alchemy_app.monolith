package com.example.alchemy_app.service;

import com.example.alchemy_app.dto.ingredient.BuyDto;
import com.example.alchemy_app.dto.ingredient.MixDto;
import com.example.alchemy_app.dto.ingredient.MixResponse;
import com.example.alchemy_app.dto.ingredient.SellDto;
import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.UserIngredient;
import com.example.alchemy_app.extension.GMailCustomExtension;
import com.example.alchemy_app.generator.IngredientGeneratorUtil;
import com.example.alchemy_app.generator.MixSellBuyGeneratorUtil;
import com.example.alchemy_app.repository.IngredientRepository;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@SpringBootTest
@ActiveProfiles("postgres")
@Transactional
class ActionServiceTest {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private UserIngredientService userIngredientService;
    @Autowired
    private ActionService actionService;
    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GMailCustomExtension().withConfiguration();
    private final String userDetailsName = "admin_1";

    @Test
    @WithUserDetails(userDetailsName)
    void testMixIngredientsShouldSaveNewIngredientToUserIngredient() {
        Ingredient recipeIngredient = createRecipeIngredientOne();
        MixDto mixDto = MixSellBuyGeneratorUtil.generateMixDto();
        MixResponse mixResponse = actionService.mixIngredients(mixDto);
        Assertions.assertTrue(mixResponse.isSuccessful());
        UserIngredient userIngredient = userIngredientService.findUserIngredientByIngredient(recipeIngredient);
        Assertions.assertNotNull(userIngredient);
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testMixIngredientsShouldNotSaveNewIngredientToUserIngredient() {
        MixDto mixDto = MixSellBuyGeneratorUtil.generateMixDto();
        MixResponse mixResponse = actionService.mixIngredients(mixDto);
        Assertions.assertFalse(mixResponse.isSuccessful());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testMixIngredientsShouldThrowCauseNoIngredientsInUserIngredient() {
        Ingredient recipeIngredientOne = createRecipeIngredientOne();
        Ingredient recipeIngredientTwo = createRecipeIngredientTwo();
        MixDto mixDto = MixSellBuyGeneratorUtil.generateMixDto(List.of(recipeIngredientOne.getId(), recipeIngredientTwo.getId()));
        Assertions.assertThrows(ValidationException.class,()-> actionService.mixIngredients(mixDto));
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testMixIngredientsShouldDeleteIngredientsFromUserIngredientCauseOfUnsuccessfulMix() {
        Ingredient ingredientWith100LossProbability = createIngredientWith100LossProbability();
        userIngredientService.saveUserIngredient(ingredientWith100LossProbability,1);
        MixDto mixDto = MixSellBuyGeneratorUtil.generateMixDto(List.of(ingredientWith100LossProbability.getId(), 1000L));
        MixResponse mixResponse = actionService.mixIngredients(mixDto);
        UserIngredient userIngredient = userIngredientService.findUserIngredientByIngredient(ingredientWith100LossProbability);
        Assertions.assertEquals(1,mixResponse.getDeletedIngredientIds().size());
        Assertions.assertEquals(ingredientWith100LossProbability.getId(),mixResponse.getDeletedIngredientIds().get(0));
        Assertions.assertEquals(0,userIngredient.getCount());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testSellIngredientShouldSellIngredient() {
        SellDto sellDto = MixSellBuyGeneratorUtil.generateSellDto();
        Assertions.assertDoesNotThrow(()->actionService.sellIngredients(sellDto));
        Ingredient ingredientById = ingredientRepository.findIngredientById(sellDto.getIngredientId());
        UserIngredient userIngredient = userIngredientService.findUserIngredientByIngredient(ingredientById);
        Assertions.assertEquals(0,userIngredient.getCount());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testSellIngredientShouldThrowCauseNoIngredientInUserIngredient() {
        Ingredient recipeIngredientOne = createRecipeIngredientOne();
        SellDto sellDto = MixSellBuyGeneratorUtil.generateSellDto(recipeIngredientOne.getId(), 1);
        Assertions.assertThrows(ValidationException.class,()->actionService.sellIngredients(sellDto));
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testSellIngredientShouldThrowCauseNoEnoughCountInUserIngredient() {
        SellDto sellDto = MixSellBuyGeneratorUtil.generateSellDto(1000L, 2);
        Assertions.assertThrows(IllegalArgumentException.class,()->actionService.sellIngredients(sellDto));
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testSellIngredientShouldThrowCauseCountLessOrEqualZero() {
        SellDto sellDto = MixSellBuyGeneratorUtil.generateSellDto(1000L, 0);
        Assertions.assertThrows(ValidationException.class,()->actionService.sellIngredients(sellDto));
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testBuyIngredientShouldBuyNewIngredient() {
        BuyDto buyDto = MixSellBuyGeneratorUtil.generateBuyDto();
        Assertions.assertDoesNotThrow(()->actionService.buyIngredients(buyDto));
        Ingredient ingredientById = ingredientRepository.findIngredientById(buyDto.getIngredientId());
        UserIngredient userIngredient = userIngredientService.findUserIngredientByIngredient(ingredientById);
        Assertions.assertEquals(2,userIngredient.getCount());
    }

    @Test
    @WithUserDetails(userDetailsName)
    void testBuyIngredientShouldThrowCauseNoEnoughMoney() {
        Ingredient ingredientWithBigPrice = createIngredientWithBigPrice();
        BuyDto buyDto = MixSellBuyGeneratorUtil.generateBuyDto(ingredientWithBigPrice.getId(), 1);
        Assertions.assertThrows(IllegalArgumentException.class,()->actionService.buyIngredients(buyDto));
    }

    private Ingredient createRecipeIngredientOne() {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        validIngredient.setIngredients(ingredientRepository.findAllById(List.of(1000L, 1200L)));
        return ingredientRepository.save(validIngredient);
    }

    private Ingredient createRecipeIngredientTwo() {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        validIngredient.setIngredients(ingredientRepository.findAllById(List.of(1000L, 1300L)));
        return ingredientRepository.save(validIngredient);
    }

    private Ingredient createIngredientWith100LossProbability() {
        Ingredient ingredientWith100LossProbability = createRecipeIngredientOne();
        ingredientWith100LossProbability.setLossProbability(((short) 100));
        return ingredientRepository.save(ingredientWith100LossProbability);
    }

    private Ingredient createIngredientWithBigPrice() {
        Ingredient ingredientWithBigPrice = createRecipeIngredientOne();
        ingredientWithBigPrice.setPrice(1000);
        return ingredientRepository.save(ingredientWithBigPrice);
    }

}
