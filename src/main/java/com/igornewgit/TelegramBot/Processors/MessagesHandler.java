package com.igornewgit.TelegramBot.Processors;

import java.io.IOException;
import java.net.URISyntaxException;

public class MessagesHandler {

    private String messageFromUser;
    private String messageToUser;
    private static String defaultCity = "Moscow";

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
                    "You can change your default city by request \"Dc Chicago\""
                    + "\n" +
                    "To learn what is default city now use /whatIsDefaultCity command"
                    + "\n" +
                    "To learn the weather in another city just ask the bot \"Weather New York\""
                    + "\n" + "\n" +
                    "Enjoy and don't hesitate to ask me help again :)";
        }

        if(messageFromUser.toLowerCase().equals("/whatisdefaultcity")) {
            return "Now default city is " + makeFirstLetterUpperCase(getDefaultCity());
        }


        //process weather request

        if (messageFromUser.toLowerCase().equals("/weather")) {
            return "The weather in " + makeFirstLetterUpperCase(getDefaultCity()) + ":" + "\n" +
                    sendMessageToWeatherApiAndGetResponse(defaultCity) + "\n" + "/help";
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
        if(complexRequest[0].equals("dc")) {
            setDefaultCity(theCity);
            return "Now default city is " + makeFirstLetterUpperCase(getDefaultCity());
        }

        // 2) the User wants to learn the weather in another city: weather London
        if (complexRequest[0].equals("weather")) {
            return "The weather in " + makeFirstLetterUpperCase(theCity) + ":" + "\n"
                    + sendMessageToWeatherApiAndGetResponse(theCity) + "\n" + "/help";
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

    public String makeFirstLetterUpperCase(String oldString) {
        StringBuilder builder = new StringBuilder(oldString);
        //make first symbol to Upper Case if it is a letter
        if (Character.isAlphabetic(oldString.codePointAt(0)))
            builder.setCharAt(0, Character.toUpperCase(oldString.charAt(0)));

        //change letters after space
        for (int i = 1; i < oldString.length(); i++)
            if (Character.isAlphabetic(oldString.charAt(i)) && Character.isSpaceChar(oldString.charAt(i - 1)))
                builder.setCharAt(i, Character.toUpperCase(oldString.charAt(i)));

        return builder.toString();
    }

    public void setDefaultCity(String defaultCity) {
        this.defaultCity = defaultCity;
    }

    public String getDefaultCity() {
        return defaultCity;
    }
}
