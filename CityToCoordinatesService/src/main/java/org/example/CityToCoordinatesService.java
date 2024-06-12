package org.example;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CityToCoordinatesService {

    private static final String GOOGLE_API_KEY = "AIzaSyBiPHcMRjFdpzmuoRcXLuMbjsyABGSuQng";

    public static Coordinates getCoordinates(String cityName) throws Exception {
        // Attempt to get coordinates from local ontology
        Coordinates coordinates = getCoordinatesFromOntology(cityName);

        // If not found, fallback to Google Maps API
        if (coordinates == null) {
            coordinates = getCoordinatesFromGoogleMaps(cityName);
        }

        return coordinates;
    }

    private static Coordinates getCoordinatesFromOntology(String cityName) throws Exception {
        String geoOntologyPath = "src/main/resources/ontology/geolocation.owl";
        Model model = ModelFactory.createDefaultModel();
        FileManager.get().readModel(model, geoOntologyPath);

        String queryString = "PREFIX geo: <http://www.example.org/geolocation#> " +
                "SELECT ?lat ?long WHERE { " +
                "?city geo:CityName \"" + cityName + "\" . " +
                "?city geo:latitude ?lat . " +
                "?city geo:longitude ?long . }";

        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            if (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                double lat = soln.getLiteral("lat").getDouble();
                double lon = soln.getLiteral("long").getDouble();
                return new Coordinates(lat, lon);
            }
        }
        return null; // Return null if city is not found in ontology
    }

    private static Coordinates getCoordinatesFromGoogleMaps(String cityName) throws Exception {
        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" + cityName + "&key=" + GOOGLE_API_KEY;
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
        if ("OK".equals(jsonObject.get("status").getAsString())) {
            JsonObject location = jsonObject.getAsJsonArray("results").get(0)
                    .getAsJsonObject().get("geometry").getAsJsonObject()
                    .get("location").getAsJsonObject();
            double lat = location.get("lat").getAsDouble();
            double lon = location.get("lng").getAsDouble();
            return new Coordinates(lat, lon);
        } else {
            throw new Exception("Google Maps API error: " + jsonObject.get("status").getAsString());
        }
    }

    public static class Coordinates {
        private double latitude;
        private double longitude;

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
