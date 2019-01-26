package com.igornewgit.TelegramBot.Processors;

import java.io.IOException;
import java.net.URISyntaxException;

public class MessagesHandler {


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

        //process common requests

        if(messageFromUser.toLowerCase().equals("/start")) {
            return "Nice to see you here! " + "\n" + "Enjoy this simple weather bot :)" + "\n" +"/help";
        }

        if(messageFromUser.toLowerCase().equals("/help")) {
            return "I see you have some problem with bot's interface" + "\n" +
                    "You can use commands to manage the bot:" + "\n" +
                    "Use /weather to see the weather in default city" + "\n" +
                    "A default city is your current city and you don't have to waste time to state it in requests each time"
                    + "\n" +
                    "You can change your default city by /setDefaultCity command"
                    + "\n" +
                    "To learn what is default city now use /whatIsDefaultCity command"
                    + "\n" +
                    "To learn the weather in another city just ask the bot \"weather New York\""
                    + "\n" + "\n" +
                    "Enjoy and don't hesitate to ask me help again :)";
        }

        if(messageFromUser.toLowerCase().equals("/whatisdefaultcity")) {
            return "Now default city is " + getDefaultCity();
        }


        //process weather request

        if (messageFromUser.toLowerCase().equals("/weather")) {
            return sendMessageToWeatherApiAndGetResponse(defaultCity) + "\n" + "/help";
        }


        //process complex request

        //split words in message
        String[] complexRequest = messageFromUser.toLowerCase().split(" ");

        //the first word is a request
        //because city could have some words let's sum other words
        String theCity = new String();
        for(int i = 1; i < complexRequest.length; i++) {
            theCity = theCity + complexRequest[i] + " ";
        }
        theCity = theCity.trim();


        //learn User's request from first word and process second word
        // 1) the User wants to change default city by request: "/setDefaultCity San Francisco"
        if(complexRequest[0].equals("/setdefaultcity")) {
            setDefaultCity(theCity);
            return "Now default city is " + defaultCity;
        }

        // 2) the User wants to learn the weather in another city: weather London
        if (complexRequest[0].equals("weather")) {
            return sendMessageToWeatherApiAndGetResponse(theCity) + "\n" + "/help";
        }
        return "Invalid data is entered. Try again";
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

    public void setDefaultCity(String defaultCity) {
        this.defaultCity = defaultCity;
    }

    public String getDefaultCity() {
        return defaultCity;
    }
}
