package com.example.alchemy_app.validation.ingredient;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IngredientValidatorHelper {
    private final IngredientRepository ingredientRepository;

    public boolean ifIngredientExist(Long ingredientId) {
        return ingredientRepository.existsById(ingredientId);
    }

    public boolean ifIngredientsExist(List<Long> ingredientIds) {
        return ingredientIds.size() == getIngredients(ingredientIds).size();
    }

    public boolean checkIngredientsSize(List<Long> ingredientIds) {
        return ingredientIds.size()!=1;
    }

    public boolean ifIngredientWithSuchIngredientsAlreadyExist(List<Long> ingredientIds) {
        List<Long> matchingIngredientIds = getMatchingIngredientIds(ingredientIds);
        if (!matchingIngredientIds.isEmpty()) {
            return matchingIngredientIds.stream().anyMatch(aLong -> aLong == ingredientIds.size());
        }
        return false;
    }

    private List<Long> getMatchingIngredientIds(List<Long> ingredientIds) {
        return ingredientRepository.findByIngredients(getIngredientIdsWithEqualIngredientsSize(ingredientIds),ingredientIds);
    }

    private List<Long> getIngredientIdsWithEqualIngredientsSize(List<Long> ingredientIds) {
        return ingredientRepository.findIngredientByIngredients(ingredientIds.size());
    }

    public boolean ifIngredientWithSuchNameAlreadyExist(String name) {
        return ingredientRepository.existsIngredientByName(name);
    }

    private List<Ingredient> getIngredients(List<Long> ingredientIds) {
        return ingredientRepository.findAllById(ingredientIds);
    }



}
