package com.example.alchemy_app.service.impl;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.repository.UserRepository;
import com.example.alchemy_app.service.EmailSenderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final TaskExecutor taskExecutorCustom;

    @Override
    public void sendNotificationAboutNewIngredient(Ingredient ingredient) {
        Queue<SimpleMailMessage> messages = userRepository.findAll().
                stream().
                map(user -> createNotificationAboutNewIngredient(user.getMail(), ingredient)).
                collect(Collectors.toCollection(LinkedBlockingQueue::new));
        taskExecutorCustom.execute(new MessagesSendRunnable(messages));
    }

    private SimpleMailMessage createNotificationAboutNewIngredient(String address, Ingredient ingredient) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(address);
        simpleMailMessage.setSubject("Alchemy, new ingredient");
        simpleMailMessage.setText("In Alchemy appeared a new ingredient: " + mapIngredient(ingredient) );
        return simpleMailMessage;
    }

    private String mapIngredient(Ingredient ingredient) {
        return "name : " + ingredient.getName() +
               ", price " + ingredient.getPrice() +
               ", loss probability " + ingredient.getLossProbability();
    }

    @AllArgsConstructor
    private class MessagesSendRunnable implements Runnable {

        private final Queue<SimpleMailMessage> messages;

        @Override
        public void run() {
            while (!messages.isEmpty()) {
                SimpleMailMessage[] messagesToSend = getArrayOfSimpleMessages();
                mailSender.send(messagesToSend);
                log.info("Messages to send left: " + messages.size());
                sleepForOneMinute();
            }
        }

        private SimpleMailMessage[] getArrayOfSimpleMessages() {
            int size = Math.min(messages.size(), 50);
            return Stream.
                    generate(messages::poll).
                    limit(size).
                    toArray(SimpleMailMessage[]::new);
        }

        private void sleepForOneMinute() {
            try {
                Thread.sleep(70000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
