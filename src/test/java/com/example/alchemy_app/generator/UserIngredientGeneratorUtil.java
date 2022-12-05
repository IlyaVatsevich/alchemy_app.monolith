package com.example.alchemy_app.generator;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;

public class UserIngredientGeneratorUtil {

    private UserIngredientGeneratorUtil(){}

    public static UserIngredient createUserIngredient(User user, Ingredient ingredient,int count) {
        return UserIngredient.builder().
                withUser(user).
                withIngredient(ingredient).
                withCount(count).
                withId(createUserIngredientId(ingredient.getId(), user.getId())).
                build();
    }

    private static UserIngredient.UserIngredientId createUserIngredientId(Long ingredientId,Long userId) {
        return UserIngredient.UserIngredientId.builder().
                withIngredientId(ingredientId).
                withUserId(userId).
                build();
    }
}
