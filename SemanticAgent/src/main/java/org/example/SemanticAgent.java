package org.example;

import org.example.cityclient.CityToCoordinatesService;
import org.example.cityclient.CityToCoordinatesPortType;
import org.example.weatherclient.CoordinatesToWeatherService;
import org.example.weatherclient.CoordinatesToWeatherPortType;

import java.net.URL;
import java.util.Scanner;

public class SemanticAgent {

    public static void main(String[] args) {
        try {
            // Ensure correct WSDL URLs
            URL cityServiceUrl = new URL("http://localhost:8080/wsdl/CityToCoordinatesService.wsdl");
            CityToCoordinatesService cityService = new CityToCoordinatesService(cityServiceUrl);
            CityToCoordinatesPortType cityPort = cityService.getCityToCoordinatesPort();
            System.out.println("CityToCoordinatesService connected" + cityPort.toString());

            URL weatherServiceUrl = new URL("http://localhost:8081/wsdl/CoordinatesToWeatherService.wsdl");
            CoordinatesToWeatherService weatherService = new CoordinatesToWeatherService(weatherServiceUrl);
            CoordinatesToWeatherPortType weatherPort = weatherService.getCoordinatesToWeatherPort();

            // Get user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the city name: ");
            String cityName = scanner.nextLine();

            // Call CityToCoordinatesService
            org.example.cityclient.GetCoordinatesRequest cityRequest = new org.example.cityclient.GetCoordinatesRequest();
            cityRequest.setCityName(cityName);
            org.example.cityclient.GetCoordinatesResponse cityResponse = cityPort.getCoordinates(cityRequest);

            double latitude = cityResponse.getLatitude();
            double longitude = cityResponse.getLongitude();
            System.out.println("Coordinates for " + cityName + ": " + latitude + ", " + longitude);

            // Call CoordinatesToWeatherService
            org.example.weatherclient.GetWeatherRequest weatherRequest = new org.example.weatherclient.GetWeatherRequest();
            weatherRequest.setLatitude(latitude);
            weatherRequest.setLongitude(longitude);
            org.example.weatherclient.GetWeatherResponse weatherResponse = weatherPort.getWeather(weatherRequest);

            System.out.println("Weather for " + cityName + ": " + weatherResponse.getTemperature() + "°C, " + weatherResponse.getCondition());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
