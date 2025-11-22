package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads a country list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads country list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CountriesToVisit read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCountryToVisit(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses country list from JSON object and returns it
    private CountriesToVisit parseCountryToVisit(JSONObject jsonObject) {
        String name = jsonObject.getString("Title");
        CountriesToVisit cv = new CountriesToVisit();
        cv.setMyTravels(name);
        addCountries(cv, jsonObject);
        EventLog.getInstance().logEvent(new Event(name + " has been opened from file"));
        return cv;
    }

    // MODIFIES: country list
    // EFFECTS: parses country list from JSON object and adds them to country list
    private void addCountries(CountriesToVisit main, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("My Country List");
        for (Object json : jsonArray) {
            JSONObject nextCountry = (JSONObject) json;
            addCountry(main, nextCountry);
        }
    }

    // MODIFIES: country list
    // EFFECTS: parses country from JSON object and adds it to country list
    private void addCountry(CountriesToVisit main, JSONObject jsonObject) {
        String name = jsonObject.getString("Country name");
        Country country = new Country(name);
        int rating = jsonObject.getInt("Rating");
        country.changeCountryRating(rating);
        int x = jsonObject.getInt("posX");
        int y = jsonObject.getInt("posY");
        country.setCoordinates(x, y);
        addPlaces(country, jsonObject);
        main.addCountry(country);
    }

    // MODIFIES: country list
    // EFFECTS: parses places to visit from JSON object and adds them to country
    private void addPlaces(Country mainCountry, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Places to visit");
        for (Object json : jsonArray) {
            JSONObject nextPlace = (JSONObject) json;
            addPlace(mainCountry, nextPlace);
        }
    }

    // MODIFIES: country list
    // EFFECTS: parses place from JSON object and adds it to places to visit
    private void addPlace(Country main, JSONObject jsonObject) {
        String name = jsonObject.getString("Place name");
        String location = jsonObject.getString("Place location");
        int openingTime = jsonObject.getInt("O Time");
        int closingTime = jsonObject.getInt("C Time");
        PlacesToVisit place = new PlacesToVisit(name);
        place.setAddress(location);
        place.setOpeningTime(openingTime);
        place.setClosingTime(closingTime);
        main.addPlaceToVisit(place);
    }

}
