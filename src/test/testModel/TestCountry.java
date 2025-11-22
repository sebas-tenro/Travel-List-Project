package testModel;

import model.Country;
import model.PlacesToVisit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class TestCountry  {
    Country testCountry;
    PlacesToVisit testPlace1;
    PlacesToVisit testPlace2;

    @BeforeEach
    public void setTestCountry() {
        testCountry = new Country("Canada");
        testPlace1 = new PlacesToVisit("UBC");
        testPlace2 = new PlacesToVisit("Downtown");
    }

    @Test
    public void testConstructor() {
        assertEquals("Canada", testCountry.getName());
        assertTrue(testCountry.getPlacesToVisit().isEmpty());
        assertEquals(0, testCountry.getRating());
    }

    @Test
    public void testAddPlaceToVisit(){
        testCountry.addPlaceToVisit(testPlace1);
        assertEquals(1,testCountry.getPlacesToVisit().size());
    }
    @Test
    public void testAddPlaceToVisitMultipleTimes(){
        testCountry.addPlaceToVisit(testPlace1);
        testCountry.addPlaceToVisit(testPlace1);
        testCountry.addPlaceToVisit(testPlace1);
        testCountry.addPlaceToVisit(testPlace1);
        testCountry.addPlaceToVisit(testPlace1);
        assertEquals(5,testCountry.getPlacesToVisit().size());
    }

    @Test
    public void testRemoveOnePlaceWithCorrectName(){
        testCountry.addPlaceToVisit(testPlace1);
        assertEquals(1,testCountry.getPlacesToVisit().size());
        assertTrue(testCountry.removePlaceVisit("UBC"));
        assertEquals(0, testCountry.getPlacesToVisit().size());

    }
    @Test
    public void testRemoveOnePlaceWithWrongName(){
        testCountry.addPlaceToVisit(testPlace1);
        assertFalse(testCountry.removePlaceVisit("Granville"));

    }
    @Test
    public void testRemoveMultiplePlaces(){
        testCountry.addPlaceToVisit(testPlace1);
        testCountry.addPlaceToVisit(testPlace2);
        testCountry.addPlaceToVisit(testPlace1);
        testCountry.addPlaceToVisit(testPlace2);
        testCountry.addPlaceToVisit(testPlace2);
        assertTrue(testCountry.removePlaceVisit("UBC"));
        assertTrue(testCountry.removePlaceVisit("UBC"));
        assertTrue(testCountry.removePlaceVisit("Downtown"));
        assertEquals(2, testCountry.getPlacesToVisit().size());

    }
    @Test
    public void testChangeRatingOnce(){
        testCountry.changeCountryRating(1);
        assertEquals(1,testCountry.getRating());
    }
    @Test
    public void testChangeRatingMultipleTimes(){
        testCountry.changeCountryRating(3);
        testCountry.changeCountryRating(4);
        testCountry.changeCountryRating(5);
        assertEquals(5, testCountry.getRating());
    }

    @Test
    public void testToJsonCountryWithEmptyPlaces(){
        JSONObject countryJ = testCountry.toJsonCountry();
        assertEquals(0,countryJ.getInt("Rating"));
        assertEquals("Canada",countryJ.get("Country name"));
        JSONArray places = (JSONArray) countryJ.get("Places to visit");
        assertTrue(places.isEmpty());

    }

    @Test
    public void testToJsonCountryWithPlaces(){
        testCountry.addPlaceToVisit(testPlace1);
        testCountry.addPlaceToVisit(testPlace2);
        JSONObject testJson = testCountry.toJsonCountry();
        assertEquals(0,testJson.getInt("Rating"));
        assertEquals("Canada",testJson.get("Country name"));
        JSONArray places = (JSONArray) testJson.get("Places to visit");
        assertEquals(2,places.length());

    }

    @Test
    public void testPlacesToVisitInJson(){
        testCountry.addPlaceToVisit(testPlace1);
        testCountry.addPlaceToVisit(testPlace2);
        JSONObject testJson = testCountry.toJsonCountry();
       assertEquals(2,testJson.getJSONArray("Places to visit").length());


    }

}
