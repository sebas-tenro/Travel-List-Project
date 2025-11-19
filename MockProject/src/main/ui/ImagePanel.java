package ui;

import model.CountriesToVisit;
import model.Country;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ImagePanel extends JPanel implements MouseListener {
    private ArrayList<Point> points = new ArrayList<Point>();
    private CountriesToVisit countries;
    private DialoguePanel dialoguePanel;
    private GUI frame;


    public ImagePanel(CountriesToVisit countries, DialoguePanel dialoguePanel, GUI frame) {
        addMouseListener(this);
        this.frame = frame;
        this.countries = countries;
        this.dialoguePanel = dialoguePanel;
        if (!countries.getList().isEmpty()) {
            ArrayList<Country> list = (ArrayList<Country>) countries.getList();
            for (Country country : list) {
                Point point = new Point(country.getPosX(), country.getPosY());
                points.add(point);
            }
        }
        repaint();


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        points.add(point);
        String name = dialoguePanel.saveCountryDialogue(point.x, point.y);
        OptionPanel optionPanel = frame.getOptionPanel();
        optionPanel.updateOptionPanel(name);
        repaint();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon imageIcon = new ImageIcon("./images/MapaMundi.jpeg");
        Image image = imageIcon.getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);


        for (Point point : points) {
            g.setColor(Color.RED);
            g.fillOval(point.x - 6, point.y - 6, 10, 10);
        }

    }

}
