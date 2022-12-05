package com.example.alchemy_app.service;

import com.example.alchemy_app.entity.Ingredient;

public interface EmailSenderService {

    void sendNotificationAboutNewIngredient(Ingredient ingredient);

}
