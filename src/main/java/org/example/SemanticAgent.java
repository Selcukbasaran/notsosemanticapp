package org.example;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.File;

public class SemanticAgent {

    public static void main(String[] args) {
        try {
            // Ontolojileri yükleme
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology geoOntology = manager.loadOntologyFromOntologyDocument(new File("src/main/resources/ontology/geolocation.owl"));
            OWLOntology weatherOntology = manager.loadOntologyFromOntologyDocument(new File("src/main/resources/ontology/weather.owl"));

            // SPARQL ile şehir adını ontolojiden sorgulama
            CityToCoordinatesService.Coordinates coords = getCityCoordinatesFromOntology("Çanakkale", "src/main/resources/ontology/geolocation.owl");

            // Enlem ve boylam ile hava durumu alınması
            CoordinatesToWeatherService.Weather weather = CoordinatesToWeatherService.getWeather(coords.getLatitude(), coords.getLongitude());

            // Sonuçları yazdırma
            System.out.println("Temperature: " + weather.getTemperature() + "°C");
            System.out.println("Condition: " + weather.getCondition());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CityToCoordinatesService.Coordinates getCityCoordinatesFromOntology(String cityName, String ontologyPath) throws Exception {
        String queryString = "PREFIX geo: <http://www.example.org/geolocation#> " +
                "SELECT ?lat ?long WHERE { " +
                "?city geo:CityName \"" + cityName + "\" . " +
                "?city geo:latitude ?lat . " +
                "?city geo:longitude ?long . }";

        Model model = ModelFactory.createDefaultModel().read(ontologyPath);

        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            if (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                double lat = soln.getLiteral("lat").getDouble();
                double lon = soln.getLiteral("long").getDouble();
                return new CityToCoordinatesService.Coordinates(lat, lon);
            } else {
                throw new Exception("City not found in ontology.");
            }
        }
    }
}
