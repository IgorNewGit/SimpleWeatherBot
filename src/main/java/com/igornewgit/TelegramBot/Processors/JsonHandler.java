package com.igornewgit.TelegramBot.Processors;

import com.igornewgit.TelegramBot.BotConstatnts;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JsonHandler {


    public JsonHandler() {}

    public static String getResponse(String city) throws IOException, URISyntaxException {
        //Send a request to Weather Server
        JSONObject jsonMessage = sendRequestToServer(city);
        //retrieve result message for user
        return BuildMessageToUser(jsonMessage);
    }

    private static JSONObject sendRequestToServer(String city) throws IOException, URISyntaxException {
        CloseableHttpClient client = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("api.openweathermap.org")
                .setPath("/data/2.5/weather")
                .setParameter("q",city)
                .setParameter("APPID", BotConstatnts.WEATHER_TOKEN)
                .build();
        HttpGet request = new HttpGet(uri);

        //retrieve the response and parse it in JSON
        HttpResponse response = client.execute(request);
        return new JSONObject(IOUtils.toString(response.getEntity().getContent()));
    }

    private static String BuildMessageToUser(JSONObject jsonMessage) {
        //Now start to construct the message to the user
        //retrieve a temperature from JSON and transfer it in celsius
        Double temperuture = Double.parseDouble(jsonMessage.getJSONObject("main").get("temp").toString())-273.15;

        //start to build the result message to the user
        StringBuilder resultMessage = new StringBuilder("The weather in ")
                .append("Moscow:")
                .append("\n")
                .append(String.format("%.1f",temperuture) + " C, ");

        //retrieve weather conditions and add add it to result message
        JSONArray array = jsonMessage.getJSONArray("weather");
        for(int i = 0; i < array.length(); i++) {
            resultMessage.append(array.getJSONObject(i).get("description") + ", ");
        }

        resultMessage.append("\n");

        //add wind speed and humidity
        resultMessage.append("Wind: " + jsonMessage.getJSONObject("wind").get("speed") + " m/sec, " + "\n")
                .append("Humidity: " + jsonMessage.getJSONObject("main").get("humidity") + "%, " + "\n");

        //finally add pressure in mmHg
        Double pressure = 0.75 * Double.parseDouble(jsonMessage.getJSONObject("main").get("pressure").toString());
        resultMessage.append("Air pressure: " + pressure + " mmHg");
        return resultMessage.toString();
    }


}
