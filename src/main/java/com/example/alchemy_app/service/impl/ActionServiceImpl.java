package com.example.alchemy_app.service.impl;

import com.example.alchemy_app.dto.ingredient.BuyDto;
import com.example.alchemy_app.dto.ingredient.MixDto;
import com.example.alchemy_app.dto.ingredient.SellDto;
import com.example.alchemy_app.dto.ingredient.MixResponse;
import com.example.alchemy_app.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {
    @Override
    @Transactional
    public MixResponse mixIngredients(MixDto mixDto) {
        // TODO after add security
        return null;
    }

    @Override
    @Transactional
    public String sellIngredients(@Valid SellDto sellDto) {
        // TODO after add security
        return null;
    }

    @Override
    @Transactional
    public String buyIngredients(BuyDto buyDto) {
        // TODO after add security
        return null;
    }
}
