//Canvas where we will paint our automaton

import java.awt.*;

@SuppressWarnings("serial") // to avoid Eclipse warning
public class AutomatonCanvas extends Canvas {
    protected Automata parent;  // access to main applet class
    protected Automaton a;

    // constructor
    public AutomatonCanvas(Automata app) {
        parent = app;
        a = parent.automaton();
    }

    public void setAutomaton(Automaton atmtn) {
        a = atmtn;
    }

    // paint the Canvas
    public void paint ( Graphics g ) {
        //draw as many gens as we can fit on the screen
        int cellSize =  parent.zoom();
        int width = getWidth()/cellSize;
        int numGens = getHeight()/cellSize;
        int[] gen1 = new int[width];
        gen1[width/2] = 1;
        int[][] gens = new int[numGens][width];
        gens[0] = gen1;
        for( int n = 1; n < gens.length; n++ ){
            gens[n] = a.generate(gens[n-1],a.getK(),a.getKAryCode());
        }
        drawGens(g,gens,cellSize);
    }

    // Draw a generation gen w/ top at height y, centered at x.Stop drawing gens when genNum is greater
    // than or equal to numOfGens. Draw each cell w/ edge of size pixels
    public void drawGens( Graphics g, int[][] gens, int size) {
        int k = a.k;
        int x = 0;
        int y = 0;
        for( int[] gen : gens ){
            for( int cell : gen ){
                g.setColor(a.mapValToColor(Color.black, Color.white, cell, k));
                g.fillRect(x,y,size,size);
                x += size;
            }
            y += size;
            x = 0;
        }
    }
}
