package testPersistence;

import model.CountriesToVisit;
import model.Country;
import model.PlacesToVisit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJson {
    protected void checkCountryList(String title, CountriesToVisit countryList) {
        assertEquals(title, countryList.getName());
    }
    protected void checkCountry(String name, int rating, Country country){
        assertEquals(name, country.getName());
        assertEquals(rating, country.getRating());

    }

    protected void checkPlacesToVisit (String name, String address, int opening, int closing, PlacesToVisit place){
        assertEquals(name, place.getName());
        assertEquals(address, place.getAddress());
        assertEquals(opening, place.getOpeningTime());
        assertEquals(closing, place.getClosingTime());
    }


}
