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

    // paint the Canvas
    public void paint ( Graphics g ) {
        drawGeneration(g,new Cell(2),0,0,getWidth()/2);
    }

    // Draw a generation gen w/ top at height y, centered at x. n represents the
    // generation number, starting at 0
    public void drawGeneration( Graphics g, Cell gen, int n, int y, int x ) {
        g.setColor(Color.black);
        g.fillRect(x-3,y,6,6);
        if(gen.next() != null) {
            drawLeft(g,gen.next(),n,y,x-6);
            drawRight(g,gen.next(),n,y,x+6);
        }
        drawGeneration( g, a.generate(gen), n+1, y+6, x );
    }

    public void drawLeft( Graphics g, Cell gen, int n, int y, int x ) {
        g.fillRect(x-3,y,6,6);
        if(gen.next() != null)
            drawLeft(g,gen.next(),n,y,x-6);
    }

    public void drawRight( Graphics g, Cell gen, int n, int y, int x ) {
        g.fillRect(x-3,y,6,6);
        if(gen.next() != null)
            drawRight(g,gen.next(),n,y,x+6);
    }

}
