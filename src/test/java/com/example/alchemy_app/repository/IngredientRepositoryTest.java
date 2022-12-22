package com.example.alchemy_app.repository;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.generator.IngredientGeneratorUtil;
import com.example.alchemy_app.generator.SecondaryGeneratorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository repository;

    @Test
    void testSaveIngredientShouldThrowIngredientWithAlreadyExistingName() {
        Ingredient fireIngredient = IngredientGeneratorUtil.getFireIngredient();
        Assertions.assertThrows(DataIntegrityViolationException.class,
                ()->repository.saveAndFlush(fireIngredient));
    }

    @Test
    void testSaveIngredientShouldSaveValidIngredient() {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        validIngredient.setIngredients(getFireAndWindIngredient());
        Assertions.assertDoesNotThrow(()-> repository.saveAndFlush(validIngredient));
    }

    @Test
    void testSaveIngredientShouldSaveIngredientWithValidPrice() {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        Integer price = SecondaryGeneratorUtil.generatePositiveInteger();
        validIngredient.setPrice(price);
        Assertions.assertDoesNotThrow(()->repository.saveAndFlush(validIngredient));
    }

    @Test
    void testSaveIngredientShouldThrowIngredientWithInvalidPrice() {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        Integer invalidPrice = SecondaryGeneratorUtil.generateNegativeInteger();
        validIngredient.setPrice(invalidPrice);
        Assertions.assertThrows(DataIntegrityViolationException.class,
                ()->repository.saveAndFlush(validIngredient));
    }

    @Test
    void testSaveIngredientShouldSaveIngredientWithValidLossProbability() {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        Short validLossProbability = SecondaryGeneratorUtil.generateShortForValidLossProbability();
        validIngredient.setLossProbability(validLossProbability);
        Assertions.assertDoesNotThrow(()->repository.saveAndFlush(validIngredient));
    }

    @Test
    void testSaveIngredientShouldThrowIngredientWithInvalidLossProbability() {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        Short invalidLossProbability = SecondaryGeneratorUtil.generateShortForInvalidLossProbability();
        validIngredient.setLossProbability(invalidLossProbability);
        Assertions.assertThrows(DataIntegrityViolationException.class,
                ()->repository.saveAndFlush(validIngredient));
    }

    private List<Ingredient> getFireAndWindIngredient() {
        return repository.findAllById(List.of(1000L, 1100L));
    }

}
