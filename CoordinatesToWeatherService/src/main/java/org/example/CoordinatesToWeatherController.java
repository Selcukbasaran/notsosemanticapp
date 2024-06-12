package org.example;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Endpoint
public class CoordinatesToWeatherController {

    private static final String NAMESPACE_URI = "http://www.example.org/CoordinatesToWeatherService";

    private final CoordinatesToWeatherService weatherService = new CoordinatesToWeatherService();

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetWeatherRequest")
    @ResponsePayload
    public GetWeatherResponse getWeather(@RequestPayload GetWeatherRequest request) throws Exception {
        CoordinatesToWeatherService.WeatherInfo weatherInfo = weatherService.getWeather(request.getLatitude(), request.getLongitude());
        GetWeatherResponse response = new GetWeatherResponse();
        response.setTemperature(weatherInfo.getTemperature());
        response.setCondition(weatherInfo.getCondition());
        return response;
    }

    @XmlRootElement(namespace = NAMESPACE_URI)
    public static class GetWeatherRequest {
        private double latitude;
        private double longitude;

        @XmlElement
        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        @XmlElement
        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    @XmlRootElement(namespace = NAMESPACE_URI)
    public static class GetWeatherResponse {
        private double temperature;
        private String condition;

        @XmlElement
        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        @XmlElement
        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }
    }
}
