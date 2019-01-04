package com.igornewgit.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class WeatherBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return BotConstatnts.USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstatnts.TOKEN;
    }
}
