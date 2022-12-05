package com.example.alchemy_app.dto.ingredient;

import com.example.alchemy_app.annotation.ingredient.ValidIfIngredientsExist;
import com.example.alchemy_app.annotation.user_ingredient.ValidIfIngredientExistInUserIngredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class MixDto {

    @NotEmpty(message = "Ingredients to mix must be filled")
    @Size(min = 2,message = "You can't mix less than {min} ingredients")
    @ValidIfIngredientsExist
    @ValidIfIngredientExistInUserIngredient
    private List<Long> ingredientsIds;

}
