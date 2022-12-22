package com.example.alchemy_app.dto.ingredient;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Jacksonized
@Getter
@Schema(title = "IngredientResponse",description = "Already created ingredient")
public class IngredientResponseDto {

    @Schema(description = "name of ingredient",example = "Iron")
    private String name;

    @Schema(description = "price of ingredient",example = "120")
    private int price;

    @Schema(description = "loss probability of ingredient",example = "50")
    private int lossProbability;

    @ArraySchema(arraySchema = @Schema(implementation = IngredientResponseDto.class,description = "ingredient's from ingredient created",
            example ="[{\"name\":\"lava\",\"price\":10,\"loss_probability\":10}," +
                    "{\"name\":\"iron ore\",\"price\":5,\"loss_probability\":20}]"))
    private List<IngredientResponseDto> ingredients;

}
