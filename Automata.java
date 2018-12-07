/**
    Canvas on which to paint the automaton

    Implemented using an AWT canvas

    CS 201 Final Project - Totalistic Cellular Automata
    Danny Grubbs-Donovan and Toby Weed
**/

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

@SuppressWarnings("serial") // to avoid Eclipse warning
public class Automata extends Applet implements ActionListener, ChangeListener, ItemListener {
    // FIELDS ==================================================================
    private AutomatonCanvas ac; // automaton display

    // Variable components
    private Choice kChoice;
    private TextField codeField;
    private JFormattedTextField decCodeField;
    private JSlider decCodeSlider;
    private JSlider rZeroColorField, gZeroColorField, bZeroColorField;
    private JSlider rKColorField, gKColorField, bKColorField;
    private Button runButton, zoomIn, zoomOut, randomInit, singleInit;

    // Automaton state
    // Note on initialization: it's alright to just set the fields here (not in a
    // constructor) because we only want to initialize the applet once (on load),
    // never again
    private int zoom = 5; //How many pixels is one edge of one cell
    private boolean random = false; //Should the first generation be randomized?
    private int k = 3; //the number of possible states for each cell
    private int ruleCode = 777; //the decimal value of the rule code
    private String[] kAryRuleCode; //the k-ary value of the rule code
    private Vector<Integer> randomConfig = new Vector<Integer>(); //A vector of cell with random states
    private Color zeroColor = Color.white; //color bounds
    private Color kColor = Color.black;

    // SETUP ===================================================================
    // Initialize the applet
    public void init() {
        setFont(new Font("TimesRoman", Font.BOLD, 14));

        setLayout(new BorderLayout());
        BorderLayout b = new BorderLayout();
        Panel automata = new Panel(b); //Frame
        kAryRuleCode = Automaton.intToKAry(ruleCode,k);

        automata.add("North",Specs());

        Panel sim = new Panel(new BorderLayout());
        ac = new AutomatonCanvas(this);
        sim.add(ac);
        automata.add("Center",sim);
        automata.add("South",Controls());
        //Set initial state for displays
        rZeroColorField.setValue(254);
        gZeroColorField.setValue(254);
        bZeroColorField.setValue(254);
        decCodeField.setValue(777);
        decCodeSlider.setValue(777);

        add(automata);
    }

    // COMPONENTS ==============================================================
    // UI to change k & decimal rule code. Also displays visualization of kary
    // rule code
    private Panel Specs() {
        Panel specs = new Panel(new FlowLayout());
        specs.setBackground(new Color(145, 153, 186));

        Label kLabel = new Label("Number of Colors:");
        kChoice = new Choice();
        for(int n = 2; n <= 100; n++) {
            kChoice.addItem("" + n);
        }
        kChoice.addItemListener(this);
        kChoice.setForeground(Color.black);
        kChoice.select(1);

        Panel kChoicePanel = new Panel(new BorderLayout());
        kChoicePanel.add("West", kLabel);
        kChoicePanel.add("Center", kChoice);

        Label codeLabel = new Label("Decimal Rule Code:");
        int numPoss = (int)Math.pow(k,(3*k-2));
        JSlider codeSlider = new JSlider(JSlider.HORIZONTAL,0,numPoss,0);

        decCodeSlider = new JSlider(JSlider.HORIZONTAL,0,numPoss,0);
        decCodeSlider.addChangeListener(this);
        NumberFormatter codeLimits = new NumberFormatter();
        codeLimits.setMinimum(0);
        codeLimits.setMaximum(numPoss);
        decCodeField = new JFormattedTextField(codeLimits);
        decCodeField.addActionListener(this);
        decCodeField.setColumns(7);
        decCodeField.setValue(0);

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

        int numCodes = (int)Math.pow(k,(3*k-2));
        // int[] avgs = someMethod() <--get danny's method

        // for( int avg : avgs ) {
        //     Color
        // }
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
        Panel controls = new Panel(new GridLayout(1,3,0,5));
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
        controls.add(ColorPanel());
        return controls;
    }

    // The panel to change color themese
    public Panel ColorPanel() {
        Panel colorControl1 = new Panel(new GridLayout(4,2));
        Panel colorControl2 = new Panel(new GridLayout(4,2));
        Panel sliders1 = new Panel(new GridLayout(3,1));
        Panel labels1 = new Panel(new GridLayout(3,1));

        rZeroColorField = new JSlider(JSlider.HORIZONTAL, 0,255,0);
        rZeroColorField.addChangeListener(this);
        gZeroColorField = new JSlider(JSlider.HORIZONTAL, 0,255,0);
        gZeroColorField.addChangeListener(this);
        bZeroColorField = new JSlider(JSlider.HORIZONTAL, 0,255,0);
        bZeroColorField.addChangeListener(this);
        rKColorField = new JSlider(JSlider.HORIZONTAL, 0,255,0);
        rKColorField.addChangeListener(this);
        gKColorField = new JSlider(JSlider.HORIZONTAL, 0,255,0);
        gKColorField.addChangeListener(this);
        bKColorField = new JSlider(JSlider.HORIZONTAL, 0,255,0);
        bKColorField.addChangeListener(this);

        colorControl1.add(new Label(""));
        colorControl1.add(new Label("Lower Color Bound"));
        colorControl1.add(new Label("R:",Label.RIGHT));
        colorControl1.add(rZeroColorField);
        colorControl1.add(new Label("G:",Label.RIGHT));
        colorControl1.add(gZeroColorField);
        colorControl1.add(new Label("B:",Label.RIGHT));
        colorControl1.add(bZeroColorField);

        colorControl2.add(new Label(""));
        colorControl2.add(new Label("Upper Color Bound"));
        colorControl2.add(new Label("R:", Label.RIGHT));
        colorControl2.add(rKColorField);
        colorControl2.add(new Label("G:",Label.RIGHT));
        colorControl2.add(gKColorField);
        colorControl2.add(new Label("B:",Label.RIGHT));
        colorControl2.add(bKColorField);


        Panel colorPanel = new Panel(new GridLayout(1,2));
        colorPanel.add(colorControl1);
        colorPanel.add(colorControl2);

        return colorPanel;
    }

    // EVENT HANDLERS ==========================================================
    // action handler for buttons & TextField
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == runButton) { // set automaton state and repaint
            //sync up state
            k = Integer.parseInt(kChoice.getSelectedItem());
            ruleCode = Integer.parseInt(decCodeField.getText());
            kAryRuleCode = Automaton.intToKAry(ruleCode,k);
            int rZeroValue = (rZeroColorField.getValue());
            int gZeroValue = (gZeroColorField.getValue());
            int bZeroValue = (bZeroColorField.getValue());
            int rKValue = (rKColorField.getValue());
            int gKValue = (gKColorField.getValue());
            int bKValue = (bKColorField.getValue());
            zeroColor = new Color(((float)rZeroValue/255),((float)gZeroValue/255),((float)bZeroValue/255));
            kColor = new Color(((float)rKValue/255),((float)gKValue/255),((float)bKValue/255));
            decCodeField.setText(Integer.toString(ruleCode));

            //re-randomize randomConfig
            randomConfig = new Vector<Integer>();

            //repaint the automaton
            ac.repaint();
        } else if (evt.getSource() == zoomIn && zoom <= 50) { //zoom in. Cap at 51 pixels
            zoom++;
            ac.repaint();
        } else if (evt.getSource() == zoomOut && zoom >= 2) { //zoom out. Cap at 1 pixel
            zoom--;
            ac.repaint();
        } else if (evt.getSource() == decCodeField) { //Change the rulecodes & update displays
            ruleCode = Integer.parseInt(decCodeField.getText());
            kAryRuleCode = Automaton.intToKAry(ruleCode,k);
            decCodeSlider.setValue(ruleCode);
            ac.repaint();
        } else if (evt.getSource() == randomInit ) { //Toggle random initialization
            random = !random;
            ac.repaint();
        }
    }

    // Action handler for sliders
    public void stateChanged(ChangeEvent evt) {
    	if(evt.getSource()==decCodeSlider) { //Change the rulecodes & update displays
        	JSlider src = (JSlider)evt.getSource();
        	decCodeField.setText(Integer.toString(src.getValue()));
        	ruleCode = src.getValue();
            kAryRuleCode = Automaton.intToKAry(ruleCode,k);
        	ac.repaint();
    	} else if(evt.getSource()==rZeroColorField ||
    			evt.getSource()==gZeroColorField ||
    			evt.getSource()==bZeroColorField ||
    			evt.getSource()==rKColorField ||
    			evt.getSource()==gKColorField ||
    			evt.getSource()==bKColorField) { //Change the colors
    		int rZeroVal = rZeroColorField.getValue();
    		int gZeroVal = gZeroColorField.getValue();
    		int bZeroVal = bZeroColorField.getValue();
    		int rKVal = rKColorField.getValue();
    		int gKVal = gKColorField.getValue();
    		int bKVal = bKColorField.getValue();
    		zeroColor = new Color(((float)rZeroVal/255),((float)gZeroVal/255),((float)bZeroVal/255));
    		kColor = new Color(((float)rKVal/255),((float)gKVal/255),((float)bKVal/255));
        	ac.repaint();
    	}
    }

    // action handler for choice menu
    public void itemStateChanged(ItemEvent evt)  {
        k = Integer.parseInt(kChoice.getSelectedItem());

        // update decCodeSlider maximum value and set current value to zero
        int numPoss = (int)Math.pow(k,(3*k-2));
        decCodeSlider.setMaximum(numPoss);
        decCodeSlider.setValue(0);

        //update decCodeField limits & format
        NumberFormatter codeLimits = new NumberFormatter();
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setGroupingUsed(false); //avoid commas in formatting
        codeLimits.setFormat(nf);
        codeLimits.setMinimum(0);
        codeLimits.setMaximum(numPoss);
        DefaultFormatterFactory limitsFactory = new DefaultFormatterFactory(codeLimits);
        decCodeField.setFormatterFactory(limitsFactory);

        //update state & run
        ruleCode = 0;
        kAryRuleCode = Automaton.intToKAry(ruleCode,k);
        ac.repaint();
    }

    // UTILS====================================================================
    // Getters -----------------------------------------------------------------
    public int zoom() {
        return zoom;
    }

    public boolean randomInit() {
        return random;
    }

    public int getK() {
        return k;
    };

    public Color getKColor() {
        return kColor;
    };

    public int getCode() {
        return ruleCode;
    }

    public String[] getKAryCode() {
        return kAryRuleCode;
    };

    public Color getZeroColor() {
        return zeroColor;
    }

    // Return the value of randomConfig[n]. Vector which will expand as necessary
    // but remain constant if we zoom in
    public int getRndmAtN(int n) {
        if(n < randomConfig.size())
            return randomConfig.elementAt(n);
        else // if there's no value at n then make one
            return setRndmAtN(n);
    }

    // Setters -----------------------------------------------------------------
    // Put a random value from 0 to k at index n
    private int setRndmAtN(int n) {
        int rand = (int)(k * Math.random());
        if(n < randomConfig.size())
            randomConfig.add( n, rand );
        else
            randomConfig.add( rand );
        return rand;
    }
}
