package com.example.alchemy_app.service;


import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;

public interface UserIngredientService {

    void addNewBasicIngredientToUsers(Ingredient ingredient);

    void initializeUserIngredient(User newUser);

    void saveRecipeAfterMix(Ingredient createdIngredient);

}
