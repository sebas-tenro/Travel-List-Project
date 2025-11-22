package ui;

import model.CountriesToVisit;
import model.Country;
import model.EventLog;
import model.PlacesToVisit;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Travelling around the world application
public class AroundTheWorldApp {
    private static String JSON_STORE;
    private CountriesToVisit myCountryList;
    private Scanner userInput;
    private String currentCountrySelected;
    private Boolean proceed;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the around the world application
    public AroundTheWorldApp() throws FileNotFoundException {
    }

    public void run() {
        runAroundTheWorldApp();
    }

    // MODIFIES: this
    // EFFECTS: process user input
    public void runAroundTheWorldApp() {
        System.out.println("Welcome to Around the World app");
        initializeProgram();
        boolean keepGoing = true;
        String nextAction;

        while (keepGoing) {
            displayOptions();
            nextAction = userInput.next();
            nextAction = nextAction.toLowerCase();

            if (nextAction.equals("q")) {
                keepGoing = false;
            } else {
                processAction(nextAction);
            }
        }
        Printer printerLog = new Printer();
        printerLog.printLog(EventLog.getInstance());
        System.out.println("\nGoodbye!");
    }


    // MODIFIES: this
    // EFFECTS: initializes the app by loading previous list or starting a new one
    public void initializeProgram() {
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
        System.out.println("Do you have a previous list you would like to keep working on ? Yes or No");
        String firstSelection = userInput.next();
        if (firstSelection.equals("Yes")) {
            loadWorldApp();
        } else if (firstSelection.equals("No")) {
            myCountryList = new CountriesToVisit();
            System.out.println("Whats the name for this travel list ? ");
            String myList = userInput.next();
            myCountryList.setMyTravels(myList);
            setUpWriter(myList);
            System.out.println("\nLet's add your first country");
            String firstCountry = userInput.next();
            Country country = new Country(firstCountry);
            myCountryList.addCountry(country);
            System.out.println("Congrats " + firstCountry + " has been added to the list");
        } else {
            System.out.println("Not a valid option, lets start again");
            initializeProgram();
        }

    }

    // MODIFIES: this
    // EFFECTS: sets up a new jason writer with new path with given saving location
    public void setUpWriter(String savingLocation) {
        jsonWriter = new JsonWriter();
        String mySave = savingLocation.toLowerCase();
        String noSpace = mySave.replace(" ", "");
        JSON_STORE = "/" + noSpace + ".json";
        jsonWriter.setDestination("./data/" + noSpace + ".json");

    }


    // EFFECTS: displays menu of options to user
    public void displayOptions() {
        System.out.println("\n Select one of the following:");
        System.out.println("\t c --> Add a new country to the list");
        System.out.println("\t p --> Add a place to a country");
        System.out.println("\t a --> Rate a country");
        System.out.println("\t r --> Remove a country from the list");
        System.out.println("\t s --> Save my countries to file");
        System.out.println("\t x --> Print current list");
        System.out.println("\t q --> Quit");

    }

    // MODIFIES: this
    // EFFECTS: processes user action
    public void processAction(String action) {
        if (action.equals("c")) {
            addNewCountry();
        } else if (action.equals("p")) {
            primaryOperation();
            addNewPlaceToCountry();
        } else if (action.equals("a")) {
            primaryOperation();
            rateCountry();
        } else if (action.equals("r")) {
            primaryOperation();
            removeCountry();
        } else if (action.equals("s")) {
            saveWorldApp();
        } else if (action.equals("x")) {
            printCountryList();
        } else {
            System.out.println("\nOption not valid");
        }

    }

    // MODIFIES: this
    // EFFECTS: adds a new country to the list according to user input
    public void addNewCountry() {
        String newCountryName;
        System.out.println("\n Please enter the name of the country you want to add");
        newCountryName = userInput.next();
        Country newCountry = new Country(newCountryName);
        this.myCountryList.addCountry(newCountry);
        System.out.println(newCountryName + " has been added to the list");
    }

    // MODIFIES: this
    // EFFECTS: checks if country selected by user is actually on the list of added countries
    //          before proceeding to other operations
    public void primaryOperation() {
        String validCountry = countrySetUp();
        if (validCountry == null) {
            System.out.println("Not a valid selection ...");
            this.proceed = false;
        } else {
            this.currentCountrySelected = validCountry;
            this.proceed = true;

        }
    }

    // MODIFIES: this
    // EFFECTS: adds a newly created place to selected country
    public void addNewPlaceToCountry() {
        if (proceed) {
            PlacesToVisit newPlace = createNewPlace();
            Country country = retrieveCountry(this.currentCountrySelected);
            country.addPlaceToVisit(newPlace);
            System.out.println(newPlace.getName() + " has been added to " + currentCountrySelected);
        }

    }


    // EFFECTS: creates a new place with input provided by user and returns created place
    public PlacesToVisit createNewPlace() {
        String newPlaceName;
        String newAddress;
        int newOpeningTime;
        int newClosingTime;

        System.out.println("\n Please enter the name of the place to add");
        newPlaceName = userInput.next();
        PlacesToVisit newPlace = new PlacesToVisit(newPlaceName);
        System.out.println("\n Please enter an address");
        newAddress = userInput.next();
        newPlace.setAddress(newAddress);
        System.out.println("\n Please enter an opening time");
        newOpeningTime = Integer.parseInt(userInput.next());
        newPlace.setOpeningTime(newOpeningTime);
        System.out.println("\n Please enter a closing time");
        newClosingTime = Integer.parseInt(userInput.next());
        newPlace.setClosingTime(newClosingTime);

        return newPlace;
    }


    // MODIFIES: this
    // EFFECTS:  changes the rating of the country selected to given rating
    public void rateCountry() {
        if (proceed) {
            System.out.println("\n Please enter new rating");
            int rating = Integer.parseInt(userInput.next());
            Country count = retrieveCountry(currentCountrySelected);
            count.changeCountryRating(rating);
            System.out.println(currentCountrySelected + " has been rated to be " + rating);
        }

    }

    // MODIFIES: this
    // EFFECTS: removes a country from the list
    public void removeCountry() {
        if (proceed) {
            myCountryList.removeCountry(retrieveCountry(currentCountrySelected));
            System.out.println(currentCountrySelected + " has been removed from the list");
        }
    }

    // EFFECTS: displays the countries currently on the list to user and returns the name
    //          of the selected country
    private String countrySetUp() {
        String selectedCountry;
        String validSelection = null;
        System.out.println("\n Select one country from the list");
        printCountryList();
        selectedCountry = userInput.next();
        for (int i = 0; i < myCountryList.getList().size(); i++) {
            if (myCountryList.getList().get(i).getName().equals(selectedCountry)) {
                validSelection = selectedCountry;
                break;
            }
        }
        return validSelection;
    }


    // EFFECTS: looks for a country with countryName in the list of countries
    //          and returns it
    public Country retrieveCountry(String countryName) {
        Country currentCountry = null;
        for (int i = 0; i < myCountryList.getList().size(); i++) {
            if (myCountryList.getList().get(i).getName().equals(countryName)) {
                currentCountry = myCountryList.getList().get(i);
            }
        }
        return currentCountry;
    }

    // EFFECTS: prints list of countries with current rating to the screen
    private void printCountryList() {
        for (int i = 0; i < myCountryList.getList().size(); i++) {
            System.out.println("\t" + myCountryList.getList().get(i).getName()
                    + " " + myCountryList.getList().get(i).getRating());
        }

    }

    // EFFECTS: saves the current country to file
    public void saveWorldApp() {
        try {
            jsonWriter.open();
            jsonWriter.write(myCountryList);
            jsonWriter.close();
            System.out.println("Saved " + myCountryList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads country list from file
    private void loadWorldApp() {
        System.out.println("Select a number from the following list");
        ArrayList<String> options = optionsToLoad();
        String selection = userInput.next();
        JSON_STORE = retrieveLoading(selection);
        if (!(JSON_STORE == "Out of bound")) {
            try {
                this.jsonReader = new JsonReader(JSON_STORE);
                jsonWriter = new JsonWriter();
                jsonWriter.setDestination(JSON_STORE);
                myCountryList = jsonReader.read();
                System.out.println("Loaded " + myCountryList.getName() + " from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            } catch (JSONException e) {
                System.out.println("Something went wrong, lets start again");
                initializeProgram();
            }
        } else {
            System.out.println("Something went wrong, lets start again");
            loadWorldApp();
        }
    }


    // EFFECTS: displays loading option on screen
    public ArrayList<String> optionsToLoad() {
        File folder = new File("data/");
        File[] files = folder.listFiles();
        ArrayList<String> list = new ArrayList<>();
        int i = 0;
        for (File file : files) {
            if (!file.getPath().contains("test")) {
                try {
                    i++;
                    Scanner scanner = new Scanner(file);

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains("Title")) {
                            String myString = String.valueOf(i);
                            String option = line.replace("Title", myString);
                            list.add(option);
                            System.out.println(option);
                        }
                    }

                    scanner.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + e.getMessage());
                }
            }
        }
        return list;
    }

    // EFFECTS: returns the path of the given selection
    public String retrieveLoading(String selection) {
        File folder = new File("data/");
        File[] files = folder.listFiles();
        ArrayList<File> workingFile = new ArrayList<File>();
        for (File file : files) {
            if (!file.getPath().contains("test")) {
                workingFile.add(file);
            }
        }
        int ns = Integer.parseInt(selection);
        if (ns <= workingFile.size()) {
            ns -= 1;
            File currentFile = workingFile.get(ns);
            return currentFile.getPath();

        } else {
            return "Out of bound";
        }
    }

    public void setMyCountryList(CountriesToVisit countries) {
        myCountryList = countries;
    }

    public void setJsonWriter(JsonWriter jsonWriter) {
        this.jsonWriter = jsonWriter;
    }

    public void setJsonReader(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }
}


