package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Testing {
    private static List<JComponent> list = new ArrayList<JComponent>();

    public static void main(String[] args) {
        // Show a simple message dialog

        // Show a dialog with a dropdown list
        JFrame frame = new JFrame("My Frame");
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        //String[] options = {"Option 1", "Option 2", "Option 3"};

        String[] options = {"Option 1", "Option 2", "Option 3"};

        JComboBox<String> comboBox = new JComboBox<>(options);

        String chosenOption = (String) JOptionPane.showInputDialog(
                null, // parent component, null for a standalone dialog
                "Choose an option", // message
                "Input", // title
                JOptionPane.PLAIN_MESSAGE, // message type
                null, // icon
                options, // options array
                options[0]); // initial value

        if (chosenOption != null) {
            System.out.println("You chose: " + chosenOption);
        }





        //frame.add(comboBox);
        frame.setPreferredSize(new Dimension(400,400));
        frame.setVisible(true);

    }


}








