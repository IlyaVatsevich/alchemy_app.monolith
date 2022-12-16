package com.example.alchemy_app.dto.ingredient;

import com.example.alchemy_app.annotation.ingredient.ValidIfIngredientExist;
import com.example.alchemy_app.annotation.user_ingredient.ValidIfIngredientExistInUserIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class SellDto {

    @NotNull(message = "Ingredients to sell must be filled.")
    @ValidIfIngredientExist
    @ValidIfIngredientExistInUserIngredient
    private Long ingredientId;

    @NotNull(message = "Count of ingredient's to sell must be filled.")
    @Min(value = 1,message = "For selling you can't select less than {value} ingredient(s).")
    private int count;

}
