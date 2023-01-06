package com.example.alchemy_app.api;

import com.example.alchemy_app.dto.ingredient.BuyDto;
import com.example.alchemy_app.dto.ingredient.MixDto;
import com.example.alchemy_app.dto.ingredient.SellDto;
import com.example.alchemy_app.dto.ingredient.MixResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/v1/action")
public interface ActionControllerApiDescription {

    @Operation(summary = "Mix ingredient's",requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = MixDto.class,requiredMode = Schema.RequiredMode.REQUIRED),mediaType = "application/json")))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = MixResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"successful\":false," +
                                            "\"deleted_ingredient_ids\":[1000,1200]}"))),
            @ApiResponse(responseCode = "201",description = "Created new ingredient while mixing",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = MixResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"successful\":true," +
                                            "\"created_ingredient\":" +
                                            "{\"id\":1400,\"name\":\"lava\",\"price\":10,\"loss_probability\":20," +
                                            "\"ingredients\":[{\"id\":1600,\"name\":\"water\",\"price\":10,\"loss_probability\":10}," +
                                            "{\"id\":1700,\"name\":\"earth\",\"price\":5,\"loss_probability\":20}]}}"))),
            @ApiResponse(responseCode = "400",description = "Client does not have required ingredient(s) or ingredient(s) don't exist"),
            @ApiResponse(responseCode = "401",description = "Client did not provide an authorization token"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @PostMapping("/mix")
    ResponseEntity<MixResponse> mixIngredients(@RequestBody MixDto mixDto);

    @Operation(summary = "Sell ingredient from user bag",requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = SellDto.class,requiredMode = Schema.RequiredMode.REQUIRED),mediaType = "application/json")
    ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "400",description = "Client does not have required ingredient or ingredient don't exist"),
            @ApiResponse(responseCode = "401",description = "Client did not provide an authorization token"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @PostMapping("/sell")
    ResponseEntity<Void> sellIngredients(@RequestBody SellDto sellDto);

    @Operation(summary = "Buy ingredients from shop",requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = BuyDto.class,requiredMode = Schema.RequiredMode.REQUIRED),mediaType = "application/json")
    ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "400",description = "Client does not have enough gold or ingredient don't exist"),
            @ApiResponse(responseCode = "401",description = "Client did not provide an authorization token"),
            @ApiResponse(responseCode = "500",description = "Internal server error")
    })
    @PostMapping("/buy")
    ResponseEntity<Void> buyIngredients(@RequestBody BuyDto buyDto);

}
