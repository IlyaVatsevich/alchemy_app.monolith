package com.example.alchemy_app.service;


import com.example.alchemy_app.annotation.pageable.ValidSortProperty;
import com.example.alchemy_app.dto.UserIngredientDto;
import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import com.example.alchemy_app.enums.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface UserIngredientService {

    void addNewBasicIngredientToUsers(Ingredient ingredient);

    void initializeUserIngredient(User newUser);

    void saveUserIngredient(Ingredient createdIngredient,int count);

    Page<UserIngredientDto> getUserIngredient(@Valid @ValidSortProperty(
            allowedProperties = {"count,ingredient.name,ingredient.id,ingredient.price,ingredient.lossProbability"}) Pageable pageable);

    void changeUserIngredientCountByIngredients(List<Ingredient> ingredients,OperationType operationType,int count);

    UserIngredient findUserIngredientByIngredient(Ingredient ingredient);

}
