package com.example.alchemy_app.service_unit;

import com.example.alchemy_app.dto.ingredient.BuyDto;
import com.example.alchemy_app.dto.ingredient.MixDto;
import com.example.alchemy_app.dto.ingredient.MixResponse;
import com.example.alchemy_app.dto.ingredient.SellDto;
import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import com.example.alchemy_app.generator.IngredientGeneratorUtil;
import com.example.alchemy_app.generator.MixSellBuyGeneratorUtil;
import com.example.alchemy_app.generator.UserGeneratorUtil;
import com.example.alchemy_app.generator.UserIngredientGeneratorUtil;
import com.example.alchemy_app.mapper.MixMapper;
import com.example.alchemy_app.repository.IngredientRepository;
import com.example.alchemy_app.repository.UserRepository;
import com.example.alchemy_app.security.UserHolder;
import com.example.alchemy_app.service.UserIngredientService;
import com.example.alchemy_app.service.impl.ActionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ActionServiceTest {

    @InjectMocks
    private ActionServiceImpl actionService;
    private final MockClass mockClass = MockClass.builder().
            userRepository(mock(UserRepository.class)).
            ingredientRepository(mock(IngredientRepository.class)).
            build();
    @Mock
    private UserIngredientService userIngredientService;
    @Mock
    private MixMapper mixMapper;
    @Mock
    private UserHolder userHolder;

    @BeforeEach
    void setupMocks() {
        List<ActionServiceImpl> services = List.of(actionService);
        mockClass.setupIngredientRepository(services);
        mockClass.setupUserRepository(services);
    }

    @Test
    void testMixIngredientsShouldMixIngredients() {
        Ingredient validIngredientOne = IngredientGeneratorUtil.createValidIngredient();
        Ingredient validIngredientTwo = IngredientGeneratorUtil.createValidIngredient();
        validIngredientOne.setId(1000L);
        validIngredientTwo.setId(1200L);
        Ingredient recipeIngredient = IngredientGeneratorUtil.createValidIngredient();
        recipeIngredient.setId(1400L);
        recipeIngredient.setIngredients(List.of(validIngredientOne,validIngredientTwo));
        MixDto mixDto = MixSellBuyGeneratorUtil.generateMixDto();
        given(mockClass.getIngredientRepository().findIngredientByIngredients(mixDto.getIngredientsIds().size())).willReturn(List.of(recipeIngredient));
        given(mixMapper.buildSuccessfulMixResponse(recipeIngredient)).willReturn(getMixResponse());
        Assertions.assertTrue(actionService.mixIngredients(mixDto).isSuccessful());
    }

    @Test
    void testSellIngredientShouldSell() {
        SellDto sellDto = MixSellBuyGeneratorUtil.generateSellDto();
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        User user = UserGeneratorUtil.createValidUser();
        user.setGold(100);
        validIngredient.setId(sellDto.getIngredientId());
        validIngredient.setPrice(100);
        given(userHolder.getUser()).willReturn(user);
        given(mockClass.getIngredientRepository().findIngredientById(sellDto.getIngredientId())).willReturn(validIngredient);
        UserIngredient userIngredient = UserIngredientGeneratorUtil.createUserIngredient(user, validIngredient, 1);
        given(userIngredientService.findUserIngredientByIngredient(validIngredient)).willReturn(userIngredient);
        Assertions.assertDoesNotThrow(()->actionService.sellIngredients(sellDto));
        Assertions.assertEquals(200,user.getGold());
    }

    @Test
    void testSellIngredientShouldThrowCauseNoEnoughCountInUserIngredient() {
        SellDto sellDto = MixSellBuyGeneratorUtil.generateSellDto();
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        User user = UserGeneratorUtil.createValidUser();
        UserIngredient userIngredient = UserIngredientGeneratorUtil.createUserIngredient(user, validIngredient, 0);
        given(mockClass.getIngredientRepository().findIngredientById(sellDto.getIngredientId())).willReturn(validIngredient);
        given(userIngredientService.findUserIngredientByIngredient(validIngredient)).willReturn(userIngredient);
        Assertions.assertThrows(IllegalArgumentException.class,()->actionService.sellIngredients(sellDto));
    }

    @Test
    void testBuyIngredientShouldBuyIngredient() {
        BuyDto buyDto = MixSellBuyGeneratorUtil.generateBuyDto();
        User validUser = UserGeneratorUtil.createValidUser();
        validUser.setGold(20);
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        validIngredient.setPrice(10);
        given(userHolder.getUser()).willReturn(validUser);
        given(mockClass.getIngredientRepository().findIngredientById(buyDto.getIngredientId())).willReturn(validIngredient);
        Assertions.assertDoesNotThrow(()->actionService.buyIngredients(buyDto));
        Assertions.assertEquals(10,validUser.getGold());
    }

    @Test
    void testBuyIngredientShouldThrowCauseNoEnoughGold() {
        BuyDto buyDto = MixSellBuyGeneratorUtil.generateBuyDto();
        User validUser = UserGeneratorUtil.createValidUser();
        validUser.setGold(20);
        Ingredient validIngredient = IngredientGeneratorUtil.createValidIngredient();
        validIngredient.setPrice(30);
        given(userHolder.getUser()).willReturn(validUser);
        given(mockClass.getIngredientRepository().findIngredientById(buyDto.getIngredientId())).willReturn(validIngredient);
        Assertions.assertThrows(IllegalArgumentException.class,()->actionService.buyIngredients(buyDto));
    }

    private MixResponse getMixResponse() {
        return MixResponse.builder().withIsSuccessful(true).build();
    }
}
