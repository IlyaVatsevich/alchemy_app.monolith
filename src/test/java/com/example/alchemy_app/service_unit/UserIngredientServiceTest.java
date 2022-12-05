package com.example.alchemy_app.service_unit;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import com.example.alchemy_app.generator.IngredientGeneratorUtil;
import com.example.alchemy_app.generator.UserGeneratorUtil;
import com.example.alchemy_app.generator.UserIngredientGeneratorUtil;
import com.example.alchemy_app.mapper.UserIngredientMapper;
import com.example.alchemy_app.repository.IngredientRepository;
import com.example.alchemy_app.repository.UserIngredientRepository;
import com.example.alchemy_app.repository.UserRepository;
import com.example.alchemy_app.service.impl.UserIngredientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserIngredientServiceTest {

    @InjectMocks
    private UserIngredientServiceImpl userIngredientService;

    @Mock
    private UserIngredientMapper userIngredientMapper;

    private final MockClass mockClass = MockClass.builder().
            userRepository(mock(UserRepository.class)).
            ingredientRepository(mock(IngredientRepository.class)).
            build();

    @Mock
    private UserIngredientRepository userIngredientRepository ;

    private Ingredient ingredient;

    private User user;

    private UserIngredient userIngredient;


    @BeforeEach
    void setMocks() throws Exception {
        List<UserIngredientServiceImpl> services = List.of(userIngredientService);
        mockClass.setupIngredientRepository(services);
        mockClass.setupUserRepository(services);
    }


    @BeforeEach
    void setup()  {
        ingredient = IngredientGeneratorUtil.createValidIngredient();
        ingredient.setId(1L);
        user = UserGeneratorUtil.createValidUser();
        user.setId(1L);
        userIngredient = UserIngredientGeneratorUtil.createUserIngredient(user, ingredient,1);
    }

    @Test
    void testAddNewBasicIngredientToUsersShouldSaveAll() {
        List<User> users = List.of(user);
        when(mockClass.getUserRepository().findAll()).thenReturn(users);
        Set<UserIngredient> userIngredients = Set.of(userIngredient);
        when(userIngredientMapper.buildUserIngredient(ingredient,user)).thenReturn(userIngredient);
        when(userIngredientRepository.saveAll(userIngredients)).thenReturn(new ArrayList<>(userIngredients));
        Assertions.assertDoesNotThrow(() -> userIngredientService.addNewBasicIngredientToUsers(ingredient));
    }

    @Test
    void testInitializeUserIngredientShouldSaveAll(){
        Set<Ingredient> ingredients = Set.of(ingredient);
        when(mockClass.getIngredientRepository().getAllBasicIngredients()).thenReturn(ingredients);
        Set<UserIngredient> userIngredients = Set.of(userIngredient);
        when(userIngredientMapper.buildUserIngredient(ingredient,user)).thenReturn(userIngredient);
        when(userIngredientRepository.saveAll(userIngredients)).thenReturn(new ArrayList<>(userIngredients));
        Assertions.assertDoesNotThrow(()->userIngredientService.initializeUserIngredient(user));
    }

}
