package testModel;

import model.CountriesToVisit;
import model.Country;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCountriesToVisit {
    CountriesToVisit testList;
    Country testCountry;

    @BeforeEach
    public void setUp() {
        testList = new CountriesToVisit();
        testList.setMyTravels("My new Travel");
        testCountry = new Country("Mexico");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testList.getList().size());
    }

    @Test
    public void testAddCountryOnce() {
        testList.addCountry(testCountry);
        assertEquals(1, testList.getList().size());

    }

    @Test
    public void testAddCountryMultipleTimes() {
        testList.addCountry(testCountry);
        testList.addCountry(testCountry);
        testList.addCountry(testCountry);
        assertEquals(3, testList.getList().size());

    }

    @Test
    public void testRemoveCountryOnce() {
        testList.addCountry(testCountry);
        assertEquals(1, testList.getList().size());
        testList.removeCountry(testCountry);
        assertEquals(0, testList.getList().size());

    }

    @Test
    public void testRemoveCountriesMultipleTimes() {
        testList.addCountry(testCountry);
        testList.addCountry(testCountry);
        testList.addCountry(testCountry);
        assertEquals(3, testList.getList().size());
        testList.removeCountry(testCountry);
        testList.removeCountry(testCountry);
        assertEquals(1, testList.getList().size());

    }

    @Test
    public void testToJsonCountryWithEmptyCountries() {
        JSONObject testJson = testList.toJsonCountries();
        assertEquals("My new Travel", testJson.get("Title"));
        JSONArray countries = (JSONArray) testJson.get("My Country List");
        assertTrue(countries.isEmpty());

    }

    @Test
    public void testToJsonCountryWithPlaces() {
        testList.addCountry(testCountry);
        JSONObject testJson = testList.toJsonCountries();
        assertEquals("My new Travel", testJson.get("Title"));
        JSONArray countries = (JSONArray) testJson.get("My Country List");
        assertEquals(1, countries.length());


    }

    @Test
    public void testPlacesToVisitInJson() {
        testList.addCountry(testCountry);
        testList.addCountry(testCountry);
        JSONObject testJson = testList.toJsonCountries();
        assertEquals("My new Travel", testJson.get("Title"));
        JSONArray countries = (JSONArray) testJson.get("My Country List");
        assertEquals(2, countries.length());

    }
    @Test
    public void testSetMyTravel(){
        testList.setMyTravels("My new Travel");
        assertEquals("My new Travel",testList.getName());
    }

}
