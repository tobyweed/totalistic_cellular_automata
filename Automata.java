// Totalistic Cellular Automata Applet
//
// Toby Weed & Danny Grubbs-Donovan

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JSlider;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;
import java.text.*;

public class Automata extends Applet implements ActionListener, ChangeListener, ItemListener {
    private Automaton automaton; //our automaton
    private AutomatonCanvas ac; //its display

    private Choice kChoice;
    private TextField codeField;
    private JFormattedTextField decCodeField;
    private JSlider decCodeSlider;
    private Button runButton, zoomIn, zoomOut, randomInit, singleInit;

    private int zoom = 5; //How many pixels is one edge of one cell
    private boolean random = false; //Should the first generation be randomized?

    public void init() {
        setFont(new Font("TimesRoman", Font.BOLD, 14));

        automaton = new Automaton(3,777);
        setLayout(new BorderLayout());
        BorderLayout b = new BorderLayout();
        Panel automata = new Panel(b); //Frame

        // Panel topDisplay = new Panel();
        // topDisplay.setLayout(new GridLayout(2,1));
        // topDisplay.add(Specs());
        // topDisplay.add(codeVis());
        automata.add("North",Specs());

        Panel sim = new Panel(new BorderLayout());
        ac = new AutomatonCanvas(this);
        sim.add(ac);
        automata.add("Center",sim);

        automata.add("South",Controls());

        add(automata);
    }

    //Where num of colors & decimal rule code live
    private Panel Specs() {
        Panel specs = new Panel(new FlowLayout());
        specs.setBackground(new Color(145, 153, 186));

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
        kChoice.addItem("11");
        kChoice.addItem("12");
        kChoice.addItem("13");
        kChoice.addItem("100");
        kChoice.addItem("1000");
        kChoice.addItemListener(this);
        kChoice.setForeground(Color.black);

        Panel kChoicePanel = new Panel(new BorderLayout());
        kChoicePanel.add("West", kLabel);
        kChoicePanel.add("Center", kChoice);

        Label codeLabel = new Label("Decimal Rule Code:");
        int kVal = Integer.parseInt(kChoice.getSelectedItem());
        int numPoss = (int)Math.pow(kVal,(3*kVal-2));
        JSlider codeSlider = new JSlider(JSlider.HORIZONTAL,0,numPoss,0);
        Label decCodeDisplay = new Label("");

        decCodeSlider = new JSlider(JSlider.HORIZONTAL,0,numPoss,0);
        decCodeSlider.addChangeListener(this);
        NumberFormatter codeLimits = new NumberFormatter();
        codeLimits.setMinimum(0);
        codeLimits.setMaximum(numPoss);
        decCodeField = new JFormattedTextField(codeLimits);
        decCodeField.addActionListener(this);
        decCodeField.setColumns(7);

        Panel codePanel = new Panel();
        codePanel.setLayout(new FlowLayout());
        codePanel.add(codeLabel);
        codePanel.add(decCodeSlider);
        codePanel.add(decCodeField);

        specs.add(kChoicePanel);
        specs.add(codePanel);
        specs.add(codeVis());

        return specs;
    }

    //A visualization of the rule code
    protected Panel codeVis() {
        Panel codeVis = new Panel();
        codeVis.setBackground(new Color(145, 153, 186));
        codeVis.add(new Label("K-ary Rule Code: "));

        int kVal = automaton.getK();
        int numCodes = (int)Math.pow(kVal,(3*kVal-2));
        String[] ruleCode = automaton.getKAryCode();

        Panel code = new Panel(new GridLayout(2,1,0,2));
        Label average = new Label("" + 0);
        average.setBackground(Color.white);
        code.add(average);
        Label value = new Label("" + 0);
        value.setBackground(Color.white);
        code.add(value);

        codeVis.add(code);
        return codeVis;
    }

    //Draw a knary code visualization
    // protected void drawCode( Panel parent, int code, int x, int y ) {
    //     if(code >= 0) {
    //         Panel code = new Panel();
    //         code.setLayout(new GridLayout)
    //     }
    // }

    //Where we run the applet
    protected Panel Controls() {
        Panel controls = new Panel(new GridLayout(1,2,0,5));
        controls.setBackground(new Color(145, 153, 186));

        randomInit = new Button("Toggle Random Initialization");
        randomInit.addActionListener(this);

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

        controls.add(randomInit);
        controls.add(runButton);
        controls.add(zoomPanel);
        return controls;
    }

    // action handler for buttons & TextField
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == runButton) {
            int kVal = Integer.parseInt(kChoice.getSelectedItem());
            int decCode = Integer.parseInt(decCodeField.getText());
            automaton = new Automaton(kVal,decCode);
            ac.setAutomaton(automaton);
            ac.repaint();
        } else if (evt.getSource() == zoomIn && zoom <= 50) {
            zoom++;
            ac.repaint();
        } else if (evt.getSource() == zoomOut && zoom >= 2) {
            zoom--;
            ac.repaint();
        } else if (evt.getSource() == decCodeField) {
            int code = Integer.parseInt(decCodeField.getText());
            decCodeSlider.setValue(code);
            ac.repaint();
        } else if (evt.getSource() == randomInit ) {
            random = !random;
            ac.repaint();
        }
    }

    // Action handler for slider
    public void stateChanged(ChangeEvent evt) {
        JSlider src = (JSlider)evt.getSource();
        decCodeField.setText(Integer.toString(src.getValue()));
        int kVal = Integer.parseInt(kChoice.getSelectedItem());
        int decCode = Integer.parseInt(decCodeField.getText());
        automaton = new Automaton(kVal,decCode);
        ac.setAutomaton(automaton);
        ac.repaint();
    }

    // action handler for choice menu
    public void itemStateChanged(ItemEvent evt)  {
        int kVal = Integer.parseInt(kChoice.getSelectedItem());
        int numPoss = (int)Math.pow(kVal,(3*kVal-2));
        decCodeSlider.setMaximum(numPoss);
        decCodeSlider.setValue(0);

        NumberFormatter codeLimits = new NumberFormatter();
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setGroupingUsed(false); //avoid commas in formatting
        codeLimits.setFormat(nf);
        codeLimits.setMinimum(0);
        codeLimits.setMaximum(numPoss);
        DefaultFormatterFactory limitsFactory = new DefaultFormatterFactory(codeLimits);
        decCodeField.setFormatterFactory(limitsFactory);
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

    // accessor for init variable
    public boolean randomInit() {
        return this.random;
    }
}
