package testModel;

import model.PlacesToVisit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testPersistence.TestJson;

import static org.junit.jupiter.api.Assertions.*;


public class TestPlacesToVisitJson extends TestJson {
    PlacesToVisit testPlace1;
    PlacesToVisit testPlace2;

    @BeforeEach
    public void setUp(){
        testPlace1 = new PlacesToVisit("Brentwood");
        testPlace2 = new PlacesToVisit("RecRoom");
    }

    @Test
    public void testConstructor(){
        assertEquals("Brentwood", testPlace1.getName());
        assertEquals("RecRoom", testPlace2.getName());
        assertEquals(null, testPlace1.getAddress());
        assertEquals(0,testPlace1.getOpeningTime());
        assertEquals(0, testPlace1.getClosingTime());
    }

    @Test
    public void testSetAddressOnce(){
        testPlace1.setAddress("456 E 40th Ave");
        assertEquals("456 E 40th Ave",testPlace1.getAddress());
    }

    @Test
    public void testSetAddressMultipleTimes(){
        testPlace1.setAddress("456 E 40th Ave");
        testPlace1.setAddress("11 W 10th Ave");
        testPlace1.setAddress("150 N 20th Ave");
        assertEquals("150 N 20th Ave",testPlace1.getAddress());
    }

    @Test
    public void testSetClosingTimeOnce(){
        testPlace1.setClosingTime(17);
        assertEquals(17,testPlace1.getClosingTime());
    }

    @Test
    public void testSetClosingTimeMultipleTimes(){
        testPlace1.setClosingTime(16);
        testPlace1.setClosingTime(5);
        testPlace1.setClosingTime(16);
        assertEquals(16,testPlace1.getClosingTime());
    }

    @Test
    public void testSetOpeningTimeOnce(){
        testPlace1.setOpeningTime(9);
        assertEquals(9,testPlace1.getOpeningTime());
    }

    @Test
    public void testSetOpeningTimeMultipleTimes(){
        testPlace1.setOpeningTime(12);
        testPlace1.setOpeningTime(9);
        testPlace1.setOpeningTime(10);
        assertEquals(10,testPlace1.getOpeningTime());
    }

    @Test
    public void testToJsonPlacesToVisit(){
        testPlace1.setAddress("xxxxx");
        testPlace1.toJsonPlace();
        checkPlacesToVisit("Brentwood","xxxxx",0,0,testPlace1);

    }

}
