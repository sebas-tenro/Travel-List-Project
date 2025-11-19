package ui;

import model.CountriesToVisit;
import model.Country;
import model.PlacesToVisit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class OptionPanel extends JPanel {
    private JPanel optionPanel;
    private CountriesToVisit app;
    private static List<JComponent> list = new ArrayList<JComponent>();
    private AroundTheWorldApp functions;
    private JLabel countryName = new JLabel("Country Name : ");
    private JLabel rating = new JLabel("Rating : ");
    private JLabel places = new JLabel("Places to visit :");
    private ImagePanel image;
    private GUI gui;
    private String selected;


    public OptionPanel(CountriesToVisit workingList, GUI gui, ImagePanel image) {
        app = workingList;
        this.image = image;
        this.gui = gui;
        optionPanel = new JPanel();
        BoxLayout layout = new BoxLayout(optionPanel, BoxLayout.Y_AXIS);
        optionPanel.setLayout(layout);
        try {
            functions = new AroundTheWorldApp();
            functions.setMyCountryList(app);
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

    }

    public JPanel runPanelCreation() {
        optionPanel.add(firstPanel());
        return optionPanel;
    }

    public JPanel firstPanel() {
        JPanel firstPanel = new JPanel(new FlowLayout());
        JLabel label1 = new JLabel("<html>List of<br>countries stored : <html>");
        JComboBox<String> options = createOptions();
        firstPanel.add(label1);
        firstPanel.add(options);
        firstPanel.setPreferredSize(new Dimension(40, firstPanel.getHeight() - 60));
        return firstPanel;

    }

    public void updateOptionPanel(String selected) {
        emptyList();
        removeComponents();
        if (functions.retrieveCountry(selected) != null) {
            gui.loadPanels(image, displayCountryPanel(selected));
        } else {
            gui.loadPanels(image, displayCountryPanel(app.getList().get(0).getName()));
        }
    }

    public void emptyList() {
        if (!list.isEmpty()) {
            list.remove(0);
            emptyList();
        }
    }

    public void removeComponents() {
        for (Component component : optionPanel.getComponents()) {
            optionPanel.remove(component);

        }
    }


    public JPanel displayCountryPanel(String selected) {
//        functions.setMyCountryList(app);
        Country workingCountry = functions.retrieveCountry(selected);
        list.add(new JLabel("<html>List of<br>countries stored : <html>"));
        JComboBox<String> comboBox = createOptions();
        list.add(comboBox);
        list.add(countryName);
        list.add(new JLabel(workingCountry.getName()));
        list.add(rating);
        list.add(new JLabel(String.valueOf(workingCountry.getRating())));
        //list.add(createChangeRating(workingCountry));
        list.add(places);
        JComboBox<String> options = new JComboBox<>();
        options.addItem(null);
        loadPlaces(options, workingCountry);
        list.add(options);
        JLabel nullLabel = new JLabel("");
        list.add(nullLabel);
        list.add(createChangeRating(workingCountry));
        list.add(nullLabel);
        list.add(createAddPlaceButton(workingCountry));
        list.add(nullLabel);
        list.add(createRemoveCountry());
        list.add(nullLabel);
        list.add(createQuitButton());


        return createPanels();


    }

    public void loadPlaces(JComboBox loading, Country country) {
        for (PlacesToVisit place : country.getPlacesToVisit()) {
            loading.addItem(place.getName());
        }
    }


    public void loadCountries(JComboBox loading) {
        for (Country country : app.getList()) {
            loading.addItem(country.getName());
        }

    }

    public JButton createChangeRating(Country workingCountry) {
        JButton button = new JButton("Change Rating");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialoguePanel panel = gui.getDialoguePanel();
                int selection = Integer.parseInt(panel.createDialogueBox("Rating"));
                workingCountry.changeCountryRating(selection);
                updateOptionPanel(selected);

            }
        });
        return button;
    }

    public JButton createAddPlaceButton(Country workingCountry) {
        JButton button = new JButton("Add a place");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialoguePanel panel = gui.getDialoguePanel();
                String name = panel.createDialogueBox("Place");
                PlacesToVisit newPlace = new PlacesToVisit(name);
                String address = panel.createDialogueBox("Address");
                newPlace.setAddress(address);
                workingCountry.addPlaceToVisit(newPlace);
                updateOptionPanel(selected);

            }
        });
        return button;
    }

    public JButton createQuitButton() {
        JButton button = new JButton("Quit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialoguePanel panel = gui.getDialoguePanel();
                panel.quitDialogueBox();
            }
        });
        return button;
    }

    public JButton createRemoveCountry() {
        JButton button = new JButton("<html>Remove a country<br>from the list<html>");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialoguePanel panel = gui.getDialoguePanel();
                panel.removeCountryDialogueBox();
                updateOptionPanel(selected);

            }
        });
        return button;
    }


    public JComboBox<String> createOptions() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(null);
        loadCountries(comboBox);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = (String) comboBox.getSelectedItem();
                if (selected != null) {
                    updateOptionPanel(selected);
                }
            }
        });
        return comboBox;
    }


    public JPanel createPanels() {
        for (int i = 0; i < list.size(); i += 2) {
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(list.get(i));
            panel.add(list.get(i + 1));
            panel.setPreferredSize(new Dimension(40, panel.getHeight() - 60));
            optionPanel.add(panel);

        }

        return optionPanel;
    }


}
