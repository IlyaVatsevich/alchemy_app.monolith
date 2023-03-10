package com.example.alchemy_app.service;

import com.example.alchemy_app.dto.ingredient.BuyDto;
import com.example.alchemy_app.dto.ingredient.MixDto;
import com.example.alchemy_app.dto.ingredient.SellDto;
import com.example.alchemy_app.dto.ingredient.MixResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface ActionService {

    MixResponse mixIngredients(@Valid MixDto mixDto);

    void sellIngredients(@Valid SellDto sellDto);

    void buyIngredients(@Valid BuyDto buyDto);

}
