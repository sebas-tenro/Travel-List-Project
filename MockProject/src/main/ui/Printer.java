package ui;

import model.Event;
import model.EventLog;


/**
 * Represents a screen printer for printing event log to screen.
 */
public class Printer {
    /**
     * Constructor sets up window in which log will be printed on screen
     */
    public Printer() {
    }


    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }

    }


}
