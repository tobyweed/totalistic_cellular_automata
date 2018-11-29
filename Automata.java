// Totalistic Cellular Automata Applet
//
// Toby Weed & Danny Grubbs-Donovan

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Automata extends Applet implements ActionListener {
    protected Automaton automaton; //our automaton
    protected AutomatonCanvas ac; //its display

    public void init() {
        setFont(new Font("TimesRoman", Font.BOLD, 28));

        automaton = new Automaton(3,777);
        setLayout(new BorderLayout());
        add(Automata());
    }

    //Set up the automata
    protected Panel Automata() {
        BorderLayout b = new BorderLayout();
        Panel automata = new Panel(b); //Frame
        automata.add("North",Specs());
        automata.add("Center",Simulation());
        automata.add("South",Config());
        return automata;
    }

    //Where num of colors, decimal rule code, & k-ary rule code live
    protected Panel Specs() {
        Panel specs = new Panel(new BorderLayout());
        specs.setBackground(Color.black);
        specs.setPreferredSize(new Dimension(0,40));
        return specs;
    }

    //Where the sim gets drawn. Maybe run and clear buttons too
    protected Panel Simulation() {
        Panel sim = new Panel(new BorderLayout());
        ac = new AutomatonCanvas(this);
        sim.add(ac);
        return sim;
    }

    //Where we set initial config
    protected Panel Config() {
        Panel config = new Panel(new BorderLayout());
        config.setBackground(Color.black);
        config.setPreferredSize(new Dimension(0,40));
        return config;
    }

    // placeholder to get it to compile
    public void actionPerformed(ActionEvent e) {
    }

    // accessor for automaton variable
    public Automaton automaton() {
        return this.automaton;
    }
}
