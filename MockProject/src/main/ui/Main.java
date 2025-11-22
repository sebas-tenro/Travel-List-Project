package ui;

import java.io.FileNotFoundException;

//Starts the around the world application
public class Main {
    public static void main(String[] args) {
        try {
            AroundTheWorldApp app = new AroundTheWorldApp();
            app.run();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }

}

