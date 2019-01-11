package com.igornewgit.TelegramBot.Processors;

import java.io.IOException;
import java.net.URISyntaxException;

public class MessagesHandler {
    // Обработать сообщение от пользователя
    // Сформировать запрос погодному приложению
    // Обработать ответ погодного приложения
    // Сформировать ответ пользователю

    private String messageFromUser;
    private String messageToUser;
    private String defaultCity = "Moscow";

    public MessagesHandler() {
    }

    public MessagesHandler(String messageFromUser) {
        this.messageFromUser = messageFromUser;
    }

    public String getMessageToUser() {
        messageToUser = processMessageFromUser(messageFromUser);
        return messageToUser;
    }

    private String processMessageFromUser(String messageFromUser) {
        if (messageFromUser.toLowerCase().equals("weather")
                    || messageFromUser.toLowerCase().equals("/weather")) {
            return sendMessageToWeatherApiAndGetResponse(defaultCity);
        }
        else {
            return "Invalid data is entered";
        }
    }

    private String sendMessageToWeatherApiAndGetResponse(String city) {
        try {
            return JsonHandler.getResponse(city);
        } catch (IOException e) {
            return "Problem with connection. Try again later";
        } catch (URISyntaxException e) {
            return "Problem with connection. Try again later";
        }
    }
}
