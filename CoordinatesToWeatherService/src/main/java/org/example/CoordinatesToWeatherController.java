package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoordinatesToWeatherController {

    @GetMapping("/getWeather")
    public CoordinatesToWeatherService.Weather getWeather(@RequestParam double latitude, @RequestParam double longitude) {
        try {
            return CoordinatesToWeatherService.getWeather(latitude, longitude);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
