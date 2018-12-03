// Totalistic Cellular Automata Applet
//
// Toby Weed & Danny Grubbs-Donovan

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JSlider;
import java.util.*;

public class Automata extends Applet implements ActionListener {
    private Automaton automaton = new Automaton(3,777); //our automaton
    private AutomatonCanvas ac; //its display

    private Choice kChoice;
    private TextField codeField;
    private Button runButton, zoomIn, zoomOut;

    private int zoom = 5; //How many pixels is one edge of one cell

    public void init() {
        setFont(new Font("TimesRoman", Font.BOLD, 14));

        //automaton = new Automaton(3,777);
        setLayout(new BorderLayout());
        add(Automata());
    }

    //Set up the automata
    protected Panel Automata() {
        BorderLayout b = new BorderLayout();
        Panel automata = new Panel(b); //Frame
        automata.add("North",Specs());
        automata.add("Center",Simulation());
        automata.add("South",Controls());
        return automata;
    }

    //Where num of colors, decimal rule code, & k-ary rule code live
    private Panel Specs() {
        Label kLabel = new Label("Number of Colors:");
        kChoice = new Choice();
        kChoice.addItem("2");
        kChoice.addItem("3");
        kChoice.addItem("4");
        kChoice.addItem("5");
        kChoice.addItem("6");
        kChoice.addItem("7");
        kChoice.addItem("8");
        kChoice.addItem("9");
        kChoice.addItem("10");
        kChoice.setForeground(Color.black);

        Panel kChoicePanel = new Panel();
        kChoicePanel.setLayout(new BorderLayout());
        kChoicePanel.add("West", kLabel);
        kChoicePanel.add("Center", kChoice);

        Label codeLabel = new Label("Decimal Rule Code:");
        int kVal = Integer.parseInt(kChoice.getSelectedItem());
        int numPoss = (int)Math.pow(kVal,(3*kVal-2));
        JSlider codeSlider = new JSlider(JSlider.HORIZONTAL,0,numPoss,0);
        Label decCodeDisplay = new Label("");

        Panel codePanel = new Panel();
        codePanel.setLayout(new BorderLayout());
        codePanel.add("West", codeLabel);
        codePanel.add("Center", codeSlider);

        Panel specs = new Panel(new GridLayout(1,3,0,5));
        specs.setBackground(new Color(244, 67, 65));
        specs.add(kChoicePanel);
        specs.add(codePanel);
        specs.add(new Panel());
        return specs;
    }

    //Where the sim gets drawn. Maybe run and clear buttons too
    protected Panel Simulation() {
        Panel sim = new Panel(new BorderLayout());
        ac = new AutomatonCanvas(this);
        sim.add(ac);
        return sim;
    }

    //Where we run the applet
    protected Panel Controls() {
        Panel controls = new Panel(new GridLayout(1,2,0,5));
        controls.setBackground(new Color(244, 67, 65));
        // controls.setPreferredSize(new Dimension(0,40));

        runButton = new Button("Run");
        runButton.addActionListener(this);

        Label zoomLabel = new Label("Zoom:");
        zoomIn = new Button("+");
        zoomIn.addActionListener(this);
        Label zoomSlash = new Label("/");
        zoomOut = new Button("-");
        zoomOut.addActionListener(this);

        Panel zoomPanel = new Panel(new FlowLayout());
        zoomPanel.add(zoomLabel);
        zoomPanel.add(zoomIn);
        zoomPanel.add(zoomSlash);
        zoomPanel.add(zoomOut);

        controls.add(runButton);
        controls.add(zoomPanel);
        return controls;
    }

    // action handler for buttons & TextField
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == runButton) {
            int kVal = Integer.parseInt(kChoice.getSelectedItem());
            // int decCode = Integer.parseInt(codeField.getText());
            automaton = new Automaton(kVal,777);
            ac.repaint();
        } else if (evt.getSource() == zoomIn && zoom <= 50) {
            zoom++;
            ac.repaint();
        } else if (evt.getSource() == zoomOut && zoom >= 2) {
            zoom--;
            ac.repaint();
        }
    }

    // ACCESSORS================================================================
    // accessor for automaton variable
    public Automaton automaton() {
        return this.automaton;
    }

    // accessor for zoom variable
    public int zoom() {
        return this.zoom;
    }
}
