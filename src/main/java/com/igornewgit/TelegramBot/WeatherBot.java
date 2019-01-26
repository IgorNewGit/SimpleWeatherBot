package com.igornewgit.TelegramBot;

import com.igornewgit.TelegramBot.Processors.MessagesHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class WeatherBot extends TelegramLongPollingBot {


    //@Override
    public void onUpdateReceived(Update update) {
        // 1. receive a message from user
        String messageFromUser = update.getMessage().getText();
        // 2. than give the message to a handler
        //    and receive a message that we will return to the user
        String messageToUser = new MessagesHandler(messageFromUser).getMessageToUser();


        // 3. Finally send last message to user
        SendMessage message = new SendMessage();
        message.setText(messageToUser);
        message.setChatId(update.getMessage().getChatId());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //@Override
    public String getBotUsername() {
        return BotConstatnts.USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstatnts.TOKEN;
    }
}
