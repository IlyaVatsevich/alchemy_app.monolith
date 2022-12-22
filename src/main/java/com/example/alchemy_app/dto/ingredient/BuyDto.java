package com.example.alchemy_app.dto.ingredient;

import com.example.alchemy_app.annotation.ingredient.ValidIfIngredientExist;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "Buy",description = "Use this to buy ingredient")
public class BuyDto {

    @Schema(example = "1000")
    @NotNull(message = "Ingredient which you want to buy must be filled.")
    @ValidIfIngredientExist
    private Long ingredientId;

    @Min(value = 1,message = "For buying you can't select less than {value} ingredient(s).")
    @NotNull(message = "Count of ingredient's to buy must be filled")
    private int count;

}
