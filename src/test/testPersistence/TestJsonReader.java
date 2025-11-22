package testPersistence;

import model.CountriesToVisit;
import model.Country;
import model.PlacesToVisit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestJsonReader extends TestJson {
    private CountriesToVisit cv;
    private  PlacesToVisit p1;
    private PlacesToVisit p2;
    private Country c1;
    private Country c2;

    @BeforeEach
    public void setUp(){
        cv = new CountriesToVisit();
        cv.setMyTravels("My europe trip");

        p1 = new PlacesToVisit("Barcelona");
        p1.setAddress("xxxxx");
        p1.setOpeningTime(7);
        p1.setClosingTime(16);
        p2 = new PlacesToVisit("Berlin");
        p2.setAddress("yyyyy");
        p2.setOpeningTime(9);
        p2.setClosingTime(22);

        c1 = new Country("Spain");
        c1.changeCountryRating(9);
        c1.addPlaceToVisit(p1);
        c2 = new Country("Germany");
        c2.changeCountryRating(7);
        c2.addPlaceToVisit(p2);
        cv.addCountry(c1);
        cv.addCountry(c2);
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCountryList() {
        cv.removeCountry(c1);
        cv.removeCountry(c2);
        JsonWriter writer = new JsonWriter();
        writer.setDestination("./data/testReaderEmptyCountryList.json");
        try {
            writer.open();
            writer.write(cv);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown");
        }

        JsonReader reader = new JsonReader("./data/testReaderEmptyCountryList.json");
        try {
            CountriesToVisit testCV = reader.read();
            assertEquals("My europe trip", testCV.getName());
            assertEquals(0, testCV.getList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCountryList() {

        JsonWriter writer = new JsonWriter();
        writer.setDestination("./data/testReaderGeneralCountryList.json");
        try {
            writer.open();
            writer.write(cv);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown");
        }

        JsonReader reader = new JsonReader("./data/testReaderGeneralCountryList.json");
        try {
            CountriesToVisit testCV = reader.read();
            List<Country> countries = testCV.getList();
            assertEquals(2, countries.size());
            checkCountryList("My europe trip",  testCV);
            checkCountry("Spain",9,testCV.getList().get(0));
            checkCountry("Germany",7, testCV.getList().get(1));
            checkPlacesToVisit("Barcelona","xxxxx",7,16, testCV.getList().get(0).getPlacesToVisit().get(0));
            checkPlacesToVisit("Berlin","yyyyy",9,22, testCV.getList().get(1).getPlacesToVisit().get(0));


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }



}