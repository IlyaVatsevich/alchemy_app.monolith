package com.example.alchemy_app.service;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.extension.GMailCustomExtension;
import com.example.alchemy_app.generator.IngredientGeneratorUtil;
import com.example.alchemy_app.repository.IngredientRepository;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@ActiveProfiles("test")
class EmailSenderServiceTest {

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private IngredientRepository ingredientRepository;
    @RegisterExtension
    static GreenMailExtension greenMail = new GMailCustomExtension().withConfiguration();

    @Test
    void testSendNotificationAboutNewIngredientShouldSend()  {
        Ingredient ingredient = ingredientRepository.save(IngredientGeneratorUtil.createValidIngredient());
        emailSenderService.sendNotificationAboutNewIngredient(ingredient);

        Awaitility.await().
                atMost(2, TimeUnit.SECONDS).
                untilAsserted(() -> {
                    MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
                    Assertions.assertEquals(2,receivedMessages.length);
                });
    }
}
