package com.example.alchemy_app.api.impl;

import com.example.alchemy_app.api.ActionControllerApiDescription;
import com.example.alchemy_app.dto.ingredient.BuyDto;
import com.example.alchemy_app.dto.ingredient.MixDto;
import com.example.alchemy_app.dto.ingredient.SellDto;
import com.example.alchemy_app.dto.ingredient.MixResponse;
import com.example.alchemy_app.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ActionControllerImpl implements ActionControllerApiDescription {

    private final ActionService actionService;

    @Override
    public ResponseEntity<MixResponse> mixIngredients(MixDto mixDto) {
        MixResponse mixResponse = actionService.mixIngredients(mixDto);
        if (mixResponse.isSuccessful()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(mixResponse);
        }
        return ResponseEntity.ok(mixResponse);
    }

    @Override
    public ResponseEntity<Void> sellIngredients(SellDto sellDto) {
        actionService.sellIngredients(sellDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> buyIngredients(BuyDto buyDto) {
        actionService.buyIngredients(buyDto);
        return ResponseEntity.ok().build();
    }
}
