package ui;

import model.CountriesToVisit;
import model.Country;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialoguePanel {
    private final AroundTheWorldApp workingApp;
    private CountriesToVisit workingList;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;


    public DialoguePanel() {
        try {
            workingApp = new AroundTheWorldApp();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public CountriesToVisit run() {
        // Show a simple message dialog
        JOptionPane.showMessageDialog(null, "Welcome to Around the World app");
        ArrayList<String> listOne = firstOption();

        int firstChoice = JOptionPane.showOptionDialog(null, "Choose an option", "Option Dialog",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, listOne.toArray(new String[0]), null);

        if (firstChoice == 0) {
            createNewCountyDialogue();
        } else {
            openExistingDialogue();
        }

        return workingList;
    }


    public ArrayList firstOption() {
        ArrayList<String> booleanString = new ArrayList<String>();
        booleanString.add("No");
        booleanString.add("Yes");
        return booleanString;
    }

    public void createNewCountyDialogue() {
        String input = JOptionPane.showInputDialog(
                null, "What is name for your new travel list?", "Input Dialog",
                JOptionPane.QUESTION_MESSAGE);

        workingList = new CountriesToVisit();
        workingList.setMyTravels(input);
        workingApp.setUpWriter(input);
        workingApp.setMyCountryList(workingList);


        System.out.println("Your name is " + input);
    }

    public void openExistingDialogue() {
        // Show a dialog with a dropdown list

        int choice = JOptionPane.showOptionDialog(
                null, "Choose an option", "Option Dialog",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, workingApp.optionsToLoad().toArray(new String[0]), null);

        String destination = workingApp.retrieveLoading(String.valueOf(choice + 1));
        jsonReader = new JsonReader(destination);
        jsonWriter = new JsonWriter();
        jsonWriter.setDestination(destination);
        workingApp.setJsonReader(jsonReader);
        workingApp.setJsonWriter(jsonWriter);


        try {
            workingList = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        System.out.println("You chose option " + (choice + 1));
        System.out.println(workingApp.retrieveLoading(String.valueOf(choice + 1)));
        workingApp.setMyCountryList(workingList);


    }

    public String createDialogueBox(String command) {
        String input;
        if (command.equals("Rating")) {
            input = JOptionPane.showInputDialog(
                    null, "What is the new rating for your country?", "Input Dialog",
                    JOptionPane.QUESTION_MESSAGE);

        } else if (command.equals("Place")) {
            input = JOptionPane.showInputDialog(
                    null, "What is the name of the place you wish to add?", "Input Dialog",
                    JOptionPane.QUESTION_MESSAGE);
        } else if (command.equals("Address")) {
            input = JOptionPane.showInputDialog(
                    null, "What is the address of that place?", "Input Dialog",
                    JOptionPane.QUESTION_MESSAGE);
        } else {
            input = JOptionPane.showInputDialog(
                    null, "What is the name of the place you wish to add?", "Input Dialog",
                    JOptionPane.QUESTION_MESSAGE);
        }


        return input;
    }

    public void quitDialogueBox() {
        int confirm = JOptionPane.showConfirmDialog(
                null, "Are you sure you want to quit?", "Confirmation Dialog",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int save = JOptionPane.showConfirmDialog(
                    null, "Would you like to save your list before quitting ?", "Confirmation Dialog",
                    JOptionPane.YES_NO_OPTION);
            if (save == JOptionPane.YES_OPTION) {
                workingApp.saveWorldApp();
                System.exit(0);
            } else {
                System.exit(0);
            }
        }
    }

    public void removeCountryDialogueBox() {

        ArrayList<String> listOne = new ArrayList<>();
        List<Country> countries = workingList.getList();
        for (Country country : countries) {
            listOne.add(country.getName());
        }
        String[] options = listOne.toArray(new String[0]);
        JComboBox<String> comboBox = new JComboBox<>(options);
        String choice = (String) JOptionPane.showInputDialog(
                null, // parent component, null for a standalone dialog
                "Choose an option", // title
                "Input",
                JOptionPane.PLAIN_MESSAGE, // message type
                null, // icon
                options, // options array
                options[0]); // initial value

        workingList.removeCountry(workingApp.retrieveCountry(choice));

    }

    public String saveCountryDialogue(int posX, int posY) {
        String name = createDialogueBox("Save");
        Country country = new Country(name);
        country.setCoordinates(posX, posY);
        workingList.addCountry(country);
        return name;

    }


}
