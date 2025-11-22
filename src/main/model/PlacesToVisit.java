package model;

import org.json.JSONObject;

// Represents a place the user wants to visit in X country with given specifications
// such as name, address,and opening and closing times
public class PlacesToVisit {
    private String placeName;
    private String address;
    private int openingTime;
    private int closingTime;

    // EFFECTS: constructs a new place with given name
    //          and opening/closing time
    public PlacesToVisit(String name) {
        this.placeName = name;
        openingTime = 0;
        closingTime = 0;

    }

    // MODIFIES: this
    // EFFECTS: sets the location of the place user wants to go
    public void setAddress(String address) {
        this.address = address;
    }

    // REQUIRES: opening time > 0
    // MODIFIES: this
    // EFFECTS: sets the time at which place opens
    public void setOpeningTime(int openingTime) {
        this.openingTime = openingTime;
    }

    // REQUIRES: closing time > 0
    // MODIFIES: this
    // EFFECTS: sets the time at which place closes
    public void setClosingTime(int closingTime) {
        this.closingTime = closingTime;
    }

    public String getName() {
        return placeName;
    }

    public String getAddress() {
        return address;
    }

    public int getOpeningTime() {
        return openingTime;
    }

    public int getClosingTime() {
        return closingTime;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJsonPlace() {
        JSONObject json = new JSONObject();
        json.put("Place name", placeName);
        json.put("Place location", address);
        json.put("O Time", openingTime);
        json.put("C Time", closingTime);
        return json;
    }


}
