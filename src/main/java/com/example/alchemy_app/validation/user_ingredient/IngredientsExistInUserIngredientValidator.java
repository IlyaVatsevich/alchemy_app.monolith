package com.example.alchemy_app.validation.user_ingredient;

import com.example.alchemy_app.annotation.user_ingredient.ValidIfIngredientsExistInUserIngredient;
import com.example.alchemy_app.entity.UserIngredient;
import com.example.alchemy_app.repository.UserIngredientRepository;
import com.example.alchemy_app.security.UserHolder;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class IngredientsExistInUserIngredientValidator implements
        ConstraintValidator<ValidIfIngredientsExistInUserIngredient, List<Long>> {

    private final UserIngredientRepository userIngredientRepository;
    private final UserHolder userHolder;

    @Override
    public boolean isValid(List<Long> value, ConstraintValidatorContext context) {
        Set<UserIngredient> userIngredientsByUser = userIngredientRepository.findUserIngredientsByUser(userHolder.getUser());
        List<UserIngredient> matchingUserIngredients = userIngredientsByUser.
                stream().
                filter(userIngredient -> value.
                        stream().
                        anyMatch(ingredientId -> userIngredient.getIngredient().getId().equals(ingredientId) && userIngredient.getCount() > 0)).
                collect(Collectors.toUnmodifiableList());
        return matchingUserIngredients.size() == value.size();
    }
}
