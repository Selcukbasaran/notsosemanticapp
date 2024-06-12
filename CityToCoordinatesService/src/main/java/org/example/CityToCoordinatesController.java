package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityToCoordinatesController {

    @GetMapping("/getCoordinates")
    public CityToCoordinatesService.Coordinates getCoordinates(@RequestParam String cityName) {
        try {
            return CityToCoordinatesService.getCoordinates(cityName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
