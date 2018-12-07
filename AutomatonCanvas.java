/**
    Canvas on which to paint the automaton

    Implemented using an AWT canvas

    CS 201 Final Project - Totalistic Cellular Automata
    Danny Grubbs-Donovan and Toby Weed
**/

import java.awt.*;
import java.lang.Math.*;

@SuppressWarnings("serial") // to avoid Eclipse warning
public class AutomatonCanvas extends Canvas {
    protected Automata parent; // access to main applet class
    protected Automaton a; // quick access to the parent's current automaton

    // constructor
    public AutomatonCanvas(Automata app) {
        parent = app;
        a = parent.automaton();
    }

    // automaton setter for when parent automaton changes
    public void setAutomaton(Automaton atmtn) {
        a = atmtn;
    }

    // paint the canvas
    public void paint ( Graphics g ) {
        // draw as many gens as we can fit on the screen
        int cellSize =  parent.zoom();
        int width = getWidth()/cellSize;
        int numGens = getHeight()/cellSize;

        // store numGens of our automaton in a 2D array
        int[][] gens = new int[numGens][width];
        int[] gen1 = new int[width];

        // if we are in random init then init with one cell of state 1 in the center
        if(!parent.randomInit()){
            gen1[width/2] = 1;
        } // otherwise set the first generation as a random array
        else {
            //the number fewer cells displayed relative to the min zoom (1)
            int lostInFront = (getWidth() - width)/2;
            for( int n = 0; n < gen1.length; n++ ){
                gen1[n] = a.getRndmAtN(n+lostInFront);
            }
        }
        gens[0] = gen1;

        // produce all of our generations
        for( int n = 1; n < gens.length; n++ ){
            gens[n] = a.generate(gens[n-1],a.getK(),a.getKAryCode());
        }
        drawGens(g,gens,cellSize);
    }

    // loop through 2D array gens and print them starting in the top left corner
    // of the canvas. Each generations proceed left to right, top to bottom.
    // Each cell will be printed as a square of dimension sixe x size.
    public void drawGens( Graphics g, int[][] gens, int size) {
        int k = a.getK();
        int x = 0;
        int y = 0;
        for( int[] gen : gens ){
            for( int cell : gen ){
                g.setColor(a.mapValToColor(a.getZeroColor(), a.getKColor(), (double)cell, k));
                g.fillRect(x,y,size,size);
                x += size;
            }
            y += size;
            x = 0;
        }
    }
}
