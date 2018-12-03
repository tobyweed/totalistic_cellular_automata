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
        //draw as many gens as we can fit on the screen
        int cellSize =  parent.zoom();
        int numOfGens = getWidth()/cellSize/2;
        drawGeneration(g,new Cell(2),0,0,getWidth()/2,cellSize,0,numOfGens);
    }

    // Draw a generation gen w/ top at height y, centered at x. n represents the
    // generation number, starting at 0. Stop drawing gens when genNum is greater
    // than or equal to numOfGens. Draw each cell w/ edge of size pixels
    public void drawGeneration( Graphics g, Cell gen, int n, int y, int x, int size, int genNum, int numOfGens) {
    	//int kInt = a.k;
    	//String[] kCode = a.kAryRuleCode;
        g.setColor(a.mapValToColor(Color.black, Color.white, gen.state(), a.k));
        g.fillRect(x-(size/2),y,size,size);
        if(gen.next() != null) {
            drawLeft(g,gen.next(),n,y,x-size,size);
            drawRight(g,gen.next(),n,y,x+size,size);
        }
        if( genNum < numOfGens - 1 )
            drawGeneration( g, a.generate(gen, a.k,a.kAryRuleCode), n+1, y+size, x, size, genNum+1, numOfGens);
    }

    public void drawLeft( Graphics g, Cell gen, int n, int y, int x, int size ) {
    	g.setColor(a.mapValToColor(Color.white, Color.black, gen.state(), a.k));
    	g.fillRect(x-(size/2),y,size,size);
        if(gen.next() != null)
            drawLeft(g,gen.next(),n,y,x-size,size);
    }

    public void drawRight( Graphics g, Cell gen, int n, int y, int x, int size ) {
    	g.setColor(a.mapValToColor(Color.white, Color.black, gen.state(), a.k));
      g.fillRect(x-(size/2),y,size,size);
        if(gen.next() != null)
            drawRight(g,gen.next(),n,y,x+size,size);
    }

}
