package com.example.alchemy_app.dto.ingredient;

import com.example.alchemy_app.annotation.ingredient.ValidIfIngredientsExist;
import com.example.alchemy_app.annotation.ingredient.ValidIngredientFromIngredientsExist;
import com.example.alchemy_app.annotation.ingredient.ValidIngredientName;
import com.example.alchemy_app.annotation.ingredient.ValidIngredientsBasicOrRecipeSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
public class IngredientCreateDto {

    @NotBlank(message = "Name should be filled.")
    @Length(min = 2,max = 50,message = "Name length should be between {min} and {max} characters.")
    @ValidIngredientName
    private String name;

    @NotNull(message = "Price should be filled.")
    @Min(value = 0,message = "Price shouldn't be less than {value} .")
    private Integer price;

    @NotNull(message = "Loss probability should be filled.")
    @Range(min = 0,max = 100,message = "Loss probability should be between {min} and {max} value")
    private Short lossProbability;

    @ValidIngredientsBasicOrRecipeSize
    @ValidIfIngredientsExist
    @ValidIngredientFromIngredientsExist
    private List<Long> ingredientIds;

}
