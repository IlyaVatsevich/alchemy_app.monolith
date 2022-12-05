package com.example.alchemy_app.dto.ingredient;

import com.example.alchemy_app.annotation.ingredient.ValidIfIngredientExist;
import com.example.alchemy_app.annotation.user.ValidEnoughGoldToBuy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class BuyDto {

    @NotEmpty(message = "Ingredients which you want to buy must be filled.")
    @ValidIfIngredientExist
    @ValidEnoughGoldToBuy
    private Long ingredientId;

    @Min(value = 1,message = "For buying you can't select less than {value} ingredient(s).")
    private int count;

}
