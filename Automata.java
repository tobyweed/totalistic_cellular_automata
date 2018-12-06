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
import java.awt.Color;

public class Automata extends Applet implements ActionListener, ChangeListener, ItemListener {
    private Automaton automaton; //our automaton
    private AutomatonCanvas ac; //its display

    private Choice kChoice;
    private TextField codeField;
    private JFormattedTextField decCodeField;
    private JSlider decCodeSlider;
    private Button runButton, zoomIn, zoomOut;
    private JFormattedTextField rZeroColorField, gZeroColorField, bZeroColorField;
    private JFormattedTextField rKColorField, gKColorField, bKColorField;

    private int zoom = 5; //How many pixels is one edge of one cell

    public void init() {
        setFont(new Font("TimesRoman", Font.BOLD, 14));

        automaton = new Automaton(3,777, Color.white, Color.black);
        setLayout(new BorderLayout());
        BorderLayout b = new BorderLayout();
        Panel automata = new Panel(b); //Frame
        automata.add("North",Specs());
        Panel sim = new Panel(new BorderLayout());
        ac = new AutomatonCanvas(this);
        sim.add(ac);
        automata.add("Center",sim);
        automata.add("South",Controls());
        add(automata);
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
        kChoice.addItemListener(this);
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

        decCodeSlider = new JSlider(JSlider.HORIZONTAL,0,numPoss,0);
        decCodeSlider.addChangeListener(this);
        NumberFormatter codeLimits = new NumberFormatter();
        codeLimits.setMinimum(0);
        codeLimits.setMaximum(numPoss);
        decCodeField = new JFormattedTextField(codeLimits);
        decCodeField.addActionListener(this);

        Panel codePanel = new Panel();
        codePanel.setLayout(new GridLayout(1,3));
        codePanel.add(codeLabel);
        codePanel.add(decCodeSlider);
        codePanel.add(decCodeField);

        Panel specs = new Panel(new FlowLayout());
        specs.setBackground(new Color(244, 67, 65));
        specs.add(kChoicePanel);
        specs.add(codePanel);

        return specs;
    }

    //Where we run the applet
    protected Panel Controls() {
        Panel controls = new Panel(new GridLayout(1,3,0,5));
        controls.setBackground(new Color(244, 67, 65));

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

        Panel colorControl = new Panel();
        rZeroColorField = new JFormattedTextField("000");
        rZeroColorField.addActionListener(this);
        gZeroColorField = new JFormattedTextField("000");
        gZeroColorField.addActionListener(this);
        bZeroColorField = new JFormattedTextField("000");
        bZeroColorField.addActionListener(this);
        rKColorField = new JFormattedTextField("000");
        rKColorField.addActionListener(this);
        gKColorField = new JFormattedTextField("000");
        gKColorField.addActionListener(this);
        bKColorField = new JFormattedTextField("000");
        bKColorField.addActionListener(this);

        Label zeroColorLabel = new Label("Lower Bound Color Code (RGB):");
        Label kColorLabel = new Label("Upper Bound Color Code (RGB):");

        Panel colorPanel = new Panel();
        colorPanel.add(zeroColorLabel);
        colorPanel.add(rZeroColorField);
        colorPanel.add(gZeroColorField);
        colorPanel.add(bZeroColorField);
        colorPanel.add(kColorLabel);
        colorPanel.add(rKColorField);
        colorPanel.add(gKColorField);
        colorPanel.add(bKColorField);

        colorControl.add(colorPanel);
        controls.add(runButton);
        controls.add(zoomPanel);
        controls.add(colorControl);
        return controls;
    }

    // action handler for buttons & TextField
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == runButton) {
            int kVal = Integer.parseInt(kChoice.getSelectedItem());
            int decCode = Integer.parseInt(decCodeField.getText());
            int rZeroValue = Integer.parseInt(rZeroColorField.getText());
            int gZeroValue = Integer.parseInt(gZeroColorField.getText());
            int bZeroValue = Integer.parseInt(bZeroColorField.getText());
            int rKValue = Integer.parseInt(rKColorField.getText());
            int gKValue = Integer.parseInt(gKColorField.getText());
            int bKValue = Integer.parseInt(bKColorField.getText());
            Color zeroColor = new Color(((float)rZeroValue/255),((float)gZeroValue/255),((float)bZeroValue/255));
            Color kColor = new Color(((float)rKValue/255),((float)gKValue/255),((float)bKValue/255));
            automaton = new Automaton(kVal,decCode,zeroColor,kColor);
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
        }

    }

    // Action handler for slider
    public void stateChanged(ChangeEvent evt) {
        JSlider src = (JSlider)evt.getSource();
        decCodeField.setText(Integer.toString(src.getValue()));
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
}
