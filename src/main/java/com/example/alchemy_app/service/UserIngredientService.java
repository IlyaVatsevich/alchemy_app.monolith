package com.example.alchemy_app.service;


import com.example.alchemy_app.dto.UserIngredientDto;
import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserIngredientService {

    void addNewBasicIngredientToUsers(Ingredient ingredient);

    void initializeUserIngredient(User newUser);

    void saveRecipeAfterMix(Ingredient createdIngredient);

    Page<UserIngredientDto> findUserIngredientByUser(User user, Pageable pageable);

}
