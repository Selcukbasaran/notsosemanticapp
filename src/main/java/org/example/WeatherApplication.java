package org.example;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.File;

public class WeatherApplication {

    public static void main(String[] args) {
        try {
            // Ontolojileri yükleme
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology geoOntology = manager.loadOntologyFromOntologyDocument(new File("src/main/resources/ontology/geolocation.owl"));
            OWLOntology weatherOntology = manager.loadOntologyFromOntologyDocument(new File("src/main/resources/ontology/weather.owl"));

            // Ontolojileri kullanarak işlemler yapma (örneğin, sınıfları ve özellikleri kullanma)
            IRI cityNameIRI = IRI.create("http://www.example.org/geo#CityName");

            // İlk servis: Şehir ismi ile enlem ve boylam alınması
            CityToCoordinatesService.Coordinates coords = CityToCoordinatesService.getCoordinates("Ankara");

            // İkinci servis: Enlem ve boylam ile hava durumu alınması
            CoordinatesToWeatherService.Weather weather = CoordinatesToWeatherService.getWeather(coords.getLatitude(), coords.getLongitude());

            // Sonuçları yazdırma
            System.out.println("Temperature: " + weather.getTemperature() + "°C");
            System.out.println("Condition: " + weather.getCondition());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
