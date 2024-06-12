package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CoordinatesToWeatherService {

    private static final String API_KEY = "0a04cb04506fe62a19e1b76bab62d198";

    public static Weather getWeather(double latitude, double longitude) throws Exception {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        double temperature = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
        String condition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

        return new Weather(temperature, condition);
    }

    public static class Weather {
        private double temperature;
        private String condition;

        public Weather(double temperature, String condition) {
            this.temperature = temperature;
            this.condition = condition;
        }

        public double getTemperature() {
            return temperature;
        }

        public String getCondition() {
            return condition;
        }
    }
}
