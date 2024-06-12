package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoordinatesToWeatherService {

    private static final String OPENWEATHER_API_KEY = "0a04cb04506fe62a19e1b76bab62d198"; // Replace with your actual API key

    public WeatherInfo getWeather(double latitude, double longitude) throws Exception {
        String urlString = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric",
                latitude, longitude, OPENWEATHER_API_KEY);
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
        JsonObject main = jsonObject.getAsJsonObject("main");
        double temperature = main.get("temp").getAsDouble();
        String condition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        return new WeatherInfo(temperature, condition);
    }

    public static class WeatherInfo {
        private double temperature;
        private String condition;

        public WeatherInfo(double temperature, String condition) {
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
