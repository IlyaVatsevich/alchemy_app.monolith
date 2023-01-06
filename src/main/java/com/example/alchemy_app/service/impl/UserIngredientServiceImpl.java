package com.example.alchemy_app.service.impl;

import com.example.alchemy_app.dto.UserIngredientDto;
import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import com.example.alchemy_app.enums.OperationType;
import com.example.alchemy_app.mapper.UserIngredientMapper;
import com.example.alchemy_app.repository.IngredientRepository;
import com.example.alchemy_app.repository.UserIngredientRepository;
import com.example.alchemy_app.repository.UserRepository;
import com.example.alchemy_app.security.UserHolder;
import com.example.alchemy_app.service.UserIngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private final UserHolder userHolder;
    private Set<Ingredient> basicIngredients;

    @PostConstruct
    private void initializeBasicIngredients() {
        basicIngredients = ingredientRepository.getAllBasicIngredients();
    }

    @Override
    @Transactional
    public void addNewBasicIngredientToUsers(Ingredient ingredient) {
        Set<UserIngredient> userIngredients = userRepository.findAll().
                stream().
                map(user -> userIngredientMapper.buildUserIngredient(ingredient, user,1)).
                collect(Collectors.toCollection(HashSet::new));
        userIngredientRepository.saveAll(userIngredients);
        basicIngredients.add(ingredient);
        log.info("Basic ingredient with id: " + ingredient.getId() + ", added to all user bags, successfully." );
    }

    @Override
    @Transactional
    public void initializeUserIngredient(User newUser) {
        Set<UserIngredient> userIngredients = basicIngredients.
                stream().
                map(ingredient -> userIngredientMapper.buildUserIngredient(ingredient, newUser,1)).
                collect(Collectors.toCollection(HashSet::new));
        userIngredientRepository.saveAll(userIngredients);
        log.info("All basic ingredient's added to new user with id: " + newUser.getId() + ", successfully." );
    }

    @Override
    @Transactional
    public void saveUserIngredient(Ingredient createdIngredient,int count) {
        boolean isIngredientExistInUserIngredient =
                userIngredientRepository.existsUserIngredientByIngredientAndUser(createdIngredient,userHolder.getUser());
        if (isIngredientExistInUserIngredient) {
            changeUserIngredientCountByIngredients(List.of(createdIngredient),OperationType.INCREASE,count);
        } else {
            UserIngredient newUserIngredient = userIngredientMapper.buildUserIngredient(createdIngredient, userHolder.getUser(), count);
            userIngredientRepository.save(newUserIngredient);
        }
    }

    @Override
    public Page<UserIngredientDto> getUserIngredient(Pageable pageable) {
        return userIngredientRepository.findUserIngredientByUser(userHolder.getUser(),pageable).
                map(userIngredientMapper::mapUserIngredientDtoFromUserIngredient);
    }

    @Override
    @Transactional
    public void changeUserIngredientCountByIngredients(List<Ingredient> ingredients,OperationType operationType,int count) {
        List<UserIngredient> userIngredientsWhoseCountIsChanged = changeCountOfExistingUserIngredient(ingredients, operationType, count);
        userIngredientRepository.saveAll(userIngredientsWhoseCountIsChanged);
    }

    @Override
    public UserIngredient findUserIngredientByIngredient(Ingredient ingredient) {
        return userIngredientRepository.findUserIngredientByIngredientAndUser(ingredient,userHolder.getUser());
    }

    private List<UserIngredient> changeCountOfExistingUserIngredient(List<Ingredient> ingredients, OperationType operation, int count) {

        List<UserIngredient> userIngredients = new ArrayList<>();

        ingredients.forEach(ingredient -> {
            UserIngredient existingUserIngredient = userIngredientRepository.
                    findUserIngredientByIngredientAndUser(ingredient, userHolder.getUser());

            if (operation.equals(OperationType.INCREASE)) {
                existingUserIngredient.setCount(existingUserIngredient.getCount() + count);
            } else {
                existingUserIngredient.setCount(existingUserIngredient.getCount() - count);
            }
            userIngredients.add(existingUserIngredient);
        });

        return userIngredients;
    }
}
