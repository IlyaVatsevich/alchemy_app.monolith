package com.example.alchemy_app.service;

import com.example.alchemy_app.dto.ingredient.IngredientCreateDto;
import com.example.alchemy_app.exception.EntityNotExistException;
import com.example.alchemy_app.extension.GMailCustomExtension;
import com.example.alchemy_app.generator.IngredientDtoGeneratorUtil;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;


@SpringBootTest
@ActiveProfiles("postgres")
@Transactional
class IngredientServiceTest {

    @Autowired
    private IngredientService ingredientService;

    @RegisterExtension
    static GreenMailExtension greenMailExtension = new GMailCustomExtension().withConfiguration();

    @Test
    void testCreateIngredientShouldSaveNewBasicIngredient() {
        IngredientCreateDto basicIngredientDto = IngredientDtoGeneratorUtil.createBasicIngredientDto();
        Assertions.assertDoesNotThrow(()->ingredientService.createIngredient(basicIngredientDto));
    }

    @Test
    void testCreateIngredientShouldSaveNewRecipeIngredient() {
        IngredientCreateDto recipeIngredientDto = IngredientDtoGeneratorUtil.createRecipeIngredientDto();
        Assertions.assertDoesNotThrow(()->ingredientService.createIngredient(recipeIngredientDto));
    }

    @Test
    void testCreateIngredientShouldThrowIngredientWithInvalidLossProbability() {
        IngredientCreateDto basicIngredientWithInvalidLossProbability =
                IngredientDtoGeneratorUtil.createIngredientWithInvalidLossProbability();
        Assertions.assertThrows(ValidationException.class,
                ()->ingredientService.createIngredient(basicIngredientWithInvalidLossProbability));
    }

    @Test
    void testCreateIngredientShouldThrowIngredientWithInvalidPrice() {
        IngredientCreateDto basicIngredientWithInvalidPrice = IngredientDtoGeneratorUtil.createIngredientWithInvalidPrice();
        Assertions.assertThrows(ValidationException.class,
                ()->ingredientService.createIngredient(basicIngredientWithInvalidPrice));
    }

    @Test
    void testCreateIngredientShouldThrowIngredientWithIngredientsSizeEqualOne() {
        IngredientCreateDto ingredientWithIngredientsSizeEqualOne = IngredientDtoGeneratorUtil.createIngredientWithIngredientsSizeEqualOne();
        Assertions.assertThrows(ValidationException.class,
                ()->ingredientService.createIngredient(ingredientWithIngredientsSizeEqualOne));
    }

    @Test
    void testCreateIngredientShouldThrowRecipeIngredientWithNotExistingIngredients() {
        IngredientCreateDto recipeIngredientDtoWithNotExistingIngredients =
                IngredientDtoGeneratorUtil.createRecipeIngredientDtoWithNotExistingIngredients();
        Assertions.assertThrows(ValidationException.class,
                ()->ingredientService.createIngredient(recipeIngredientDtoWithNotExistingIngredients));
    }

    @Test
    void testCreateIngredientShouldThrowRecipeIngredientWithAlreadyExistingFromIngredients() {
        IngredientCreateDto recipeIngredientDto = IngredientDtoGeneratorUtil.createRecipeIngredientDto();
        ingredientService.createIngredient(recipeIngredientDto);
        IngredientCreateDto recipeIngredientDtoWithAlreadyExistingFromIngredients = IngredientDtoGeneratorUtil.createRecipeIngredientDto();
        Assertions.assertThrows(ValidationException.class,
                ()->ingredientService.createIngredient(recipeIngredientDtoWithAlreadyExistingFromIngredients));
    }

    @Test
    void testCreateIngredientShouldThrowIngredientWithAlreadyExistingName() {
        IngredientCreateDto basicIngredientDto = IngredientDtoGeneratorUtil.createBasicIngredientDto();
        String name = basicIngredientDto.getName();
        ingredientService.createIngredient(basicIngredientDto);
        IngredientCreateDto ingredientWithAlreadyExistingName = IngredientDtoGeneratorUtil.createIngredientWithAlreadyExistingName(name);
        Assertions.assertThrows(ValidationException.class,
                ()->ingredientService.createIngredient(ingredientWithAlreadyExistingName));
    }

    @Test
    void testCreateIngredientShouldThrowBasicIngredientWithInvalidPriceAndLossProbability() {
        IngredientCreateDto basicIngredientWithInvalidPriceAndLossProbability = IngredientDtoGeneratorUtil.createBasicIngredientWithInvalidPriceAndLossProbability();
        Assertions.assertThrows(ValidationException.class,
                ()->ingredientService.createIngredient(basicIngredientWithInvalidPriceAndLossProbability));
    }

    @Test
    void testGetIngredientShouldThrowIngredientWithIdNotExist() {
        Assertions.assertThrows(EntityNotExistException.class,()->ingredientService.getIngredientById(1L));
    }

    @Test
    void testGetIngredientShouldReturnIngredient() {
        Assertions.assertDoesNotThrow(()->ingredientService.getIngredientById(1000L));
    }

}
