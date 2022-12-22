package com.example.alchemy_app.repository;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import com.example.alchemy_app.generator.IngredientGeneratorUtil;
import com.example.alchemy_app.generator.SecondaryGeneratorUtil;
import com.example.alchemy_app.generator.UserIngredientGeneratorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class UserIngredientRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserIngredientRepository userIngredientRepository;

    @Test
    void testSaveUserIngredientShouldSaveUserIngredient() {
        UserIngredient userIngredient = userIngredientRepository.save(getUserIngredient(1));
        Assertions.assertDoesNotThrow(()->userIngredientRepository.saveAndFlush(userIngredient));
    }

    @Test
    void testSaveUserIngredientShouldSaveUserIngredientWithIncreasedCount() {
        UserIngredient userIngredient = userIngredientRepository.save(getUserIngredient(1));
        Integer count = SecondaryGeneratorUtil.generatePositiveInteger();
        userIngredient.setCount(userIngredient.getCount() + count);
        Assertions.assertEquals(count+1,userIngredientRepository.saveAndFlush(userIngredient).getCount());
    }

    @Test
    void testSaveUserIngredientShouldThrowUserIngredientWithCountLessThanZero() {
        Integer countLessThanZero = SecondaryGeneratorUtil.generateNegativeInteger();
        UserIngredient userIngredient = getUserIngredient(countLessThanZero);
        Assertions.assertThrows(DataIntegrityViolationException.class,
                ()->userIngredientRepository.saveAndFlush(userIngredient));
    }

    @Test
    void testSaveUserIngredientShouldThrowUserIngredientWithDecreasedCountLessThanZero() {
        UserIngredient userIngredient = userIngredientRepository.save(getUserIngredient(1));
        Integer countLessThanZero = SecondaryGeneratorUtil.generateNegativeInteger();
        userIngredient.setCount(userIngredient.getCount()+countLessThanZero);

        Assertions.assertThrows(DataIntegrityViolationException.class,
                ()->userIngredientRepository.saveAndFlush(userIngredient));
    }

    @Test
    void testSaveUserIngredientShouldSaveUserIngredientWithDecreasedCount() {
        UserIngredient userIngredient = userIngredientRepository.save(getUserIngredient(1));
        userIngredient.setCount(userIngredient.getCount()-1);
        userIngredient = userIngredientRepository.save(userIngredient);
        Assertions.assertEquals(0,userIngredient.getCount());
    }

    @Test
    void testDeleteUserIngredientShouldDeleteUserIngredient() {
        UserIngredient userIngredient = getUserIngredient(1);
        userIngredientRepository.delete(userIngredient);
        Assertions.assertFalse(userIngredientRepository.existsById(userIngredient.getId()));
    }

    private UserIngredient getUserIngredient(int count) {
        return UserIngredientGeneratorUtil.createUserIngredient(getExistingUser(),getValidIngredient(),count);
    }

    private User getExistingUser() {
        return userRepository.findById(500L).get();
    }

    private Ingredient getValidIngredient() {
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        validIngredient.setIngredients(getFireAndWindIngredient());
        return ingredientRepository.save(validIngredient);
    }

    private List<Ingredient> getFireAndWindIngredient() {
        return ingredientRepository.findAllById(List.of(1000L, 1100L));
    }
}
