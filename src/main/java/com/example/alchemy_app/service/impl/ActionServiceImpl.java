package com.example.alchemy_app.service.impl;

import com.example.alchemy_app.dto.ingredient.BuyDto;
import com.example.alchemy_app.dto.ingredient.MixDto;
import com.example.alchemy_app.dto.ingredient.SellDto;
import com.example.alchemy_app.dto.ingredient.MixResponse;
import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import com.example.alchemy_app.enums.OperationType;
import com.example.alchemy_app.mapper.MixMapper;
import com.example.alchemy_app.repository.IngredientRepository;
import com.example.alchemy_app.repository.UserRepository;
import com.example.alchemy_app.security.UserHolder;
import com.example.alchemy_app.service.ActionService;
import com.example.alchemy_app.service.UserIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final UserIngredientService userIngredientService;
    private final MixMapper mixMapper;
    private final UserHolder userHolder;

    @Override
    @Transactional
    public MixResponse mixIngredients(MixDto mixDto) {
        Ingredient recipeIngredient = getIngredientRecipeFromIngredients(mixDto);
        List<Ingredient> ingredientsToMix = ingredientRepository.findAllById(mixDto.getIngredientIds());
        if (recipeIngredient != null) {
            userIngredientService.saveUserIngredient(recipeIngredient,1);
            userIngredientService.changeUserIngredientCountByIngredients(ingredientsToMix,OperationType.DECREASE,1);
        } else {
            List<Long> deletedIngredients = deleteIngredients(ingredientsToMix);
            return mixMapper.buildUnsuccessfulMixResponse(deletedIngredients);
        }
        return mixMapper.buildSuccessfulMixResponse(recipeIngredient);
    }

    @Override
    @Transactional
    public void sellIngredients(SellDto sellDto) {
        Ingredient ingredientToSell = ingredientRepository.findIngredientById(sellDto.getIngredientId());
        UserIngredient userIngredient = userIngredientService.findUserIngredientByIngredient(ingredientToSell);
        if (userIngredient.getCount() < sellDto.getCount()) {
            throw new IllegalArgumentException("You have no enough count to sell this ingredient." +
                    " You pass: " + userIngredient.getCount() + ", you have: " + sellDto.getCount());
        }
        User seller = userHolder.getUser();
        userIngredientService.changeUserIngredientCountByIngredients(List.of(ingredientToSell),OperationType.DECREASE,sellDto.getCount());
        int goldToReceive = sellDto.getCount() * ingredientToSell.getPrice();
        seller.setGold(seller.getGold() + goldToReceive);
        userRepository.save(seller);
    }

    @Override
    @Transactional
    public void buyIngredients(BuyDto buyDto) {
        User buyer = userHolder.getUser();
        Ingredient ingredientToBuy = ingredientRepository.findIngredientById(buyDto.getIngredientId());
        int fullIngredientPrice = ingredientToBuy.getPrice() * buyDto.getCount();
        if (buyer.getGold() < fullIngredientPrice) {
            throw new IllegalArgumentException("You have no enough gold to buy this ingredient. " +
                    "You have: " + buyer.getGold() + ", you need: " + fullIngredientPrice);
        }
        userIngredientService.saveUserIngredient(ingredientToBuy,buyDto.getCount());
        buyer.setGold(buyer.getGold() - fullIngredientPrice);
        userRepository.save(buyer);
    }

    private Ingredient getIngredientRecipeFromIngredients(MixDto mixDto) {
        List<Ingredient> ingredientsMatching = ingredientRepository.findIngredientByIngredients(mixDto.getIngredientIds().size());
        List<Ingredient> mixedIngredientRecipe = ingredientsMatching.stream().filter(recipeIngredient -> {
            List<Ingredient> ingredientsFrom = recipeIngredient.getIngredients();
            return matchIngredients(ingredientsFrom,mixDto.getIngredientIds());
        }).collect(Collectors.toUnmodifiableList());

        if (mixedIngredientRecipe.isEmpty()) {
            return null;
        } else {
            return mixedIngredientRecipe.get(0);
        }
    }

    private boolean matchIngredients(List<Ingredient> ingredientsFrom,List<Long> ingredientIds) {
        return ingredientsFrom.
                stream().
                allMatch(ingredient -> ingredientIds.
                        stream().
                        anyMatch(id -> id.equals(ingredient.getId())));
    }

    private List<Long> deleteIngredients(List<Ingredient> ingredients) {
        List<Ingredient> ingredientsToDelete = ingredients.
                stream().
                filter(this::isNeedToDelete).
                collect(Collectors.toUnmodifiableList());

        userIngredientService.changeUserIngredientCountByIngredients(ingredientsToDelete,OperationType.DECREASE,1);

        return ingredientsToDelete.
                stream().
                map(Ingredient::getId).
                collect(Collectors.toUnmodifiableList());
    }

    private boolean isNeedToDelete(Ingredient ingredient) {
        Short lossProbability = ingredient.getLossProbability();
        if (lossProbability == 100) {
            return true;
        } else if (lossProbability == 0) {
            return false;
        }
        return lossProbability < ThreadLocalRandom.current().nextInt(0, 100);
    }
}
