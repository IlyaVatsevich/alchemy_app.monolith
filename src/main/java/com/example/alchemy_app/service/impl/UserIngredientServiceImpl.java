package com.example.alchemy_app.service.impl;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import com.example.alchemy_app.mapper.UserIngredientMapper;
import com.example.alchemy_app.repository.IngredientRepository;
import com.example.alchemy_app.repository.UserIngredientRepository;
import com.example.alchemy_app.repository.UserRepository;
import com.example.alchemy_app.service.UserIngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserIngredientServiceImpl implements UserIngredientService {

    private final UserRepository userRepository;

    private final UserIngredientRepository userIngredientRepository;

    private final IngredientRepository ingredientRepository;

    private final UserIngredientMapper userIngredientMapper;

    @Override
    @Transactional
    public void addNewBasicIngredientToUsers(Ingredient ingredient) {
        Set<UserIngredient> userIngredients = userRepository.findAll().
                stream().
                map(user -> userIngredientMapper.buildUserIngredient(ingredient, user)).
                collect(Collectors.toCollection(HashSet::new));
        userIngredientRepository.saveAll(userIngredients);
        log.info("Basic ingredient with id: " + ingredient.getId() + ", added to all user bags, successfully." );
    }

    @Override
    @Transactional
    public void initializeUserIngredient(User newUser) {
        Set<UserIngredient> userIngredients = ingredientRepository.getAllBasicIngredients().
                stream().
                map(ingredient -> userIngredientMapper.buildUserIngredient(ingredient, newUser)).
                collect(Collectors.toCollection(HashSet::new));
        userIngredientRepository.saveAll(userIngredients);
        log.info("All basic ingredient's added to new user with id: " + newUser.getId() + ", successfully." );
    }

    @Override
    @Transactional
    public void saveRecipeAfterMix(Ingredient createdIngredient) {
        // TODO after security add
    }
}
