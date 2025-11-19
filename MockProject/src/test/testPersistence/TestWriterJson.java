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

import static org.junit.jupiter.api.Assertions.*;

public class TestWriterJson extends TestJson {
    private CountriesToVisit cv;
    private  PlacesToVisit p1;
    private PlacesToVisit p2;
    private Country c1;
    private Country c2;

    @BeforeEach
    public void setUp(){
        cv = new CountriesToVisit();
        cv.setMyTravels("My new travel");

        p1 = new PlacesToVisit("Beijing");
        p1.setAddress("xxxxx");
        p1.setOpeningTime(9);
        p1.setClosingTime(18);
        p2 = new PlacesToVisit("Tokio");
        p2.setAddress("yyyyy");
        p2.setOpeningTime(8);
        p2.setClosingTime(19);

        c1 = new Country("China");
        c1.changeCountryRating(5);
        c1.addPlaceToVisit(p1);
        c2 = new Country("Japan");
        c2.changeCountryRating(10);
        c2.addPlaceToVisit(p2);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            cv.setMyTravels("My new travel");
            JsonWriter writer = new JsonWriter();
            writer.setDestination("./data/my\0illegal.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCountryList() {
        try {
            JsonWriter writer = new JsonWriter();
            writer.setDestination("./data/testWriterEmptyCountryList.json");
            writer.open();
            writer.write(cv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCountryList.json");
            cv = reader.read();
            assertEquals("My new travel", cv.getName());
            assertEquals(0, cv.getList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCountryList() {
        try {
            cv.addCountry(c1);
            cv.addCountry(c2);
            JsonWriter writer = new JsonWriter();
            writer.setDestination("./data/testWriterGeneralCountryList.json");
            writer.open();
            writer.write(cv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCountryList.json");
            cv = reader.read();
            assertEquals("My new travel", cv.getName());
            List<Country> countries = cv.getList();
            assertEquals(2, countries.size());
            checkCountryList("My new travel", cv);
            checkCountry("China",5,countries.get(0));
            checkCountry("Japan",10,countries.get(1));
            checkPlacesToVisit("Beijing","xxxxx",9,18, countries.get(0).getPlacesToVisit().get(0));
            checkPlacesToVisit("Tokio","yyyyy",8,19, countries.get(1).getPlacesToVisit().get(0));


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testOpenThrowException(){
        JsonWriter writer = new JsonWriter();
        writer.setDestination("./data/testMyIllegal.json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    public void testOpenWithoutThrowingException(){
        JsonWriter writer = new JsonWriter();
        writer.setDestination("./data/testWriterGeneralCountryList.json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("File not found exception thrown, not expected");
        }
    }


}