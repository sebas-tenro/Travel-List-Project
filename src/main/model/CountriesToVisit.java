package model;

// Represent the list of countries a user will/wants to visit
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountriesToVisit {
    private String myTravels;
    private List<Country> listOfCountries;

    // EFFECTS: constructs a list of countries for the user
    public CountriesToVisit() {
        this.listOfCountries = new ArrayList<Country>();
        EventLog.getInstance().logEvent(new Event("A new country list has been created"));
    }

    // EFFECTS: returns list of countries
    public List<Country> getList() {
        return listOfCountries;
    }

    //MODIFIES: this
    //EFFECTS: adds a new country to the list
    public void addCountry(Country country) {
        listOfCountries.add(country);
        EventLog.getInstance().logEvent(new Event(country.getName() + " has been added to : " + myTravels));
    }

    //MODIFIES: this
    //EFFECTS: removes a country from the list
    public void removeCountry(Country country) {
        listOfCountries.remove(country);
        EventLog.getInstance().logEvent(new Event(country.getName() + " has been removed from : " + myTravels));
    }

    //MODIFIES: this
    //EFFECTS: set my travels to given myTravels
    public void setMyTravels(String myTravels) {
        this.myTravels = myTravels;
        EventLog.getInstance().logEvent(new Event("Country list name has been change to : " + myTravels));
    }

    public String getName() {
        return myTravels;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJsonCountries() {
        JSONObject json = new JSONObject();
        json.put("Title",myTravels);
        json.put("My Country List", countriesToJson());
        return  json;
    }

    // EFFECTS: returns countries in the list as a JSON array
    private JSONArray countriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Country c : listOfCountries) {
            jsonArray.put(c.toJsonCountry());
        }
        return jsonArray;
    }
}
