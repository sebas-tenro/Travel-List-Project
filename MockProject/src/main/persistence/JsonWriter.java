package persistence;

import model.CountriesToVisit;
import model.Event;
import model.EventLog;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Represents a writer that writes representations of Around the World App
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter() {

    }

    //MODIFIES: this
    //EFFECTS: sets destination for writer to given destination
    public void setDestination(String destination) {
        this.destination = destination;

    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    //          be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of countries to visit to file
    public void write(CountriesToVisit cv) {
        JSONObject json = cv.toJsonCountries();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event(cv.getName() + " has been saved to file"));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
