package org.example;

import org.example.cityclient.CityToCoordinatesPortType;
import org.example.cityclient.CityToCoordinatesService;
import org.example.weatherclient.CoordinatesToWeatherPortType;
import org.example.weatherclient.CoordinatesToWeatherService;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.Scanner;

public class SemanticAgent {

    public static void main(String[] args) {
        try {
            // WSDL URL and QName for CityToCoordinatesService
            URL cityWsdlURL = new URL("http://localhost:8080/ws/cityToCoordinates.wsdl");
            QName cityQName = new QName("http://localhost/CityToCoordinatesService", "CityToCoordinatesService");
            CityToCoordinatesService cityService = new CityToCoordinatesService(cityWsdlURL, cityQName);
            CityToCoordinatesPortType cityPort = cityService.getCityToCoordinatesPort();

            // WSDL URL and QName for CoordinatesToWeatherService
            URL weatherWsdlURL = new URL("http://localhost:8081/ws/coordinatesToWeather.wsdl");
            QName weatherQName = new QName("http://localhost/CoordinatesToWeatherService", "CoordinatesToWeatherService");
            CoordinatesToWeatherService weatherService = new CoordinatesToWeatherService(weatherWsdlURL, weatherQName);
            CoordinatesToWeatherPortType weatherPort = weatherService.getCoordinatesToWeatherPort();

            // Get city name from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the city name: ");
            String cityName = scanner.nextLine();

            // Create request for city coordinates
            org.example.cityclient.GetCoordinatesRequest cityRequest = new org.example.cityclient.GetCoordinatesRequest();
            cityRequest.setCityName(cityName);

            // Making API call to CityToCoordinatesService
            org.example.cityclient.GetCoordinatesResponse cityResponse = cityPort.getCoordinates(cityRequest);
            System.out.println("Coordinates for " + cityName + ": " + cityResponse.getLatitude() + ", " + cityResponse.getLongitude());

            // Create request for weather information
            org.example.weatherclient.GetWeatherRequest weatherRequest = new org.example.weatherclient.GetWeatherRequest();
            weatherRequest.setLatitude(cityResponse.getLatitude());
            weatherRequest.setLongitude(cityResponse.getLongitude());
            org.example.weatherclient.GetWeatherResponse weatherResponse = weatherPort.getWeather(weatherRequest);
            System.out.println("Weather at " + cityName + ": " + weatherResponse.getTemperature() + "°C, " + weatherResponse.getCondition());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
