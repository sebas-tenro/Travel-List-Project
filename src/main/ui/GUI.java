package ui;

import model.CountriesToVisit;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private CountriesToVisit app;
    private JFrame frame = new JFrame("Around the World");
    private DialoguePanel dialoguePanel;
    private OptionPanel optionPanel;
    //private ImagePanel image;
    //private JPanel options;


    // Constructs main window
    // effects: sets up window in which Space Invaders game will be played
    public GUI() {
        dialoguePanel = new DialoguePanel();
        app = dialoguePanel.run();
        frame.setTitle(app.getName());
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImagePanel image = new ImagePanel(app,dialoguePanel,this);
        optionPanel = new OptionPanel(app,this, image);
        JPanel options = optionPanel.runPanelCreation();
        loadPanels(image, options);


    }

    public void loadPanels(ImagePanel image,JPanel options) {
        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridheight = 2;
        gbc.weightx = 2.0;
        gbc.weighty = 2.0;
        gbc.fill = GridBagConstraints.BOTH;
        containerPanel.add(image, gbc);
        gbc.gridx = 3;
        gbc.gridheight = 1;
        gbc.weightx = 0.3;
        containerPanel.add(options, gbc);
        frame.setContentPane(containerPanel);
        frame.setVisible(true);
    }

    public DialoguePanel getDialoguePanel() {
        return dialoguePanel;
    }

    public OptionPanel getOptionPanel() {
        return optionPanel;
    }

    public static void main(String[] args) {
        new GUI();
    }

}
