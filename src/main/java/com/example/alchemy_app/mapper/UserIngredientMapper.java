package com.example.alchemy_app.mapper;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import org.springframework.stereotype.Component;

@Component
public class UserIngredientMapper {

    public UserIngredient buildUserIngredient(Ingredient ingredient, User user) {
        return UserIngredient.builder().
                withId(buildUserIngredientId(ingredient.getId(),user.getId())).
                withIngredient(ingredient).
                withUser(user).
                withCount(1).
                build();
    }

    private UserIngredient.UserIngredientId buildUserIngredientId(Long ingredientId,Long userId) {
        return UserIngredient.UserIngredientId.builder().
                withIngredientId(ingredientId).
                withUserId(userId).build();
    }
}
