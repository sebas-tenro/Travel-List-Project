package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a country that has a name, a list of places to visit and a rating
public class Country {
    private final String name;
    private final List<PlacesToVisit> placesToVisit;
    private int rating;
    private int posX;
    private int posY;

    // EFFECTS: constructs a new country with given name
    //          a list of places and a rating
    public Country(String name) {
        this.name = name;
        this.placesToVisit = new ArrayList<PlacesToVisit>();
        this.rating = 0;
    }

    // MODIFIES: this
    // EFFECTS: add a new place to visit to the list
    public void addPlaceToVisit(PlacesToVisit place) {
        placesToVisit.add(place);
        EventLog.getInstance().logEvent(new Event(place.getName() + " has been added to : " + this.getName()));
    }

    // REQUIRES: list of places is a non-empty list
    // MODIFIES: this
    // EFFECTS: if placeName is on the list of places
    //              -removes place from the list
    //              -returns true
    //          otherwise false
    public boolean removePlaceVisit(String placeName) {
        for (PlacesToVisit p : placesToVisit) {
            if (p.getName() == placeName) {
                EventLog.getInstance().logEvent(new Event(p.getName() + " has been removed from : " + this.getName()));
                placesToVisit.remove(p);
                return true;

            }

        }
        return false;
    }

    // REQUIRES: new rating > 0
    // MODIFIES: this
    // EFFECTS: changes the rating of the country
    public void changeCountryRating(int newRating) {
        this.rating = newRating;
        EventLog.getInstance().logEvent(new Event(this.getName() + " changed rating to : " + Integer.toString(newRating)));
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJsonCountry() {
        JSONObject json = new JSONObject();
        json.put("Country name", name);
        json.put("Places to visit", placesToJson());
        json.put("Rating", rating);
        json.put("posX", posX);
        json.put("posY", posY);
        return json;
    }

    // EFFECTS: returns places to visit in the country as a JSON array
    public JSONArray placesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (PlacesToVisit p : placesToVisit) {
            jsonArray.put(p.toJsonPlace());
        }

        return jsonArray;
    }

    //
    public void setCoordinates(int x, int y) {
        posX = x;
        posY = y;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public List<PlacesToVisit> getPlacesToVisit() {
        return placesToVisit;
    }

}
