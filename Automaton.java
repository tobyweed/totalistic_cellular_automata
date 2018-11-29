// Implements a Totalistic Cellular Automaton

import java.util.*;
import java.awt.Color;

public class Automaton {
    // FIELDS ==============================================================
    int k;
    int ruleCode;
    int[] kAryRuleCode; //Unnecessary but avoids having to constantly recalculate
    HashMap<Integer,Color> valToColor;
    HashMap<Double,Integer> avgToVal;

    //CONSTRUCTOR ==========================================================
    public Automaton(int newK, int code) {
        k = newK;
        ruleCode = code;
        //then set other three fields
    };

    // METHODS =============================================================
    public int getK() {
        return 0;
    };
    public int getCode() {
        return 0;
    };
    public int[] getKAryCode() {
        return new int[0];
    };
    public static int[] intToKAry(int code) {
        return new int[0];
    };
    public static int kAryToInt(int[] code) {
        return 0;
    };

    //zeroColor & kColor determine how the rgb values change, will normally
    //just be RGB(255,255,255) & RGB(0,0,0). Also idk how colors work in Java
    //so it prob won’t actually be of type Color
    protected void mapValToColor(Color zeroColor, Color kColor) {
    };

    protected void mapAvgToVal(){} //this sets the rules

    // UTILS ===================================================================

    // THIS METHOD MAY NOT BE NECESSARY
    // Return an array of n generations, starting from generation g
    public static Cell[] generations(Cell g, int n){ //later add parameter HashMap rule
        Cell[] generations = new Cell[n];
        generations[0] = g;
        for(int x = 1; x < n; x++) {
            generations[x] = generate(generations[x-1]);
        }
        return generations;
    }

    //Create a linked list representing one half of a generation of cells from
    //genesis cell of past generation
    public static Cell generate(Cell g) { //later add parameter HashMap rule
        //determine the value of the c
        double total = g.state();
        total += (g.next() != null) ? (2*g.next().state()) : 0; //multiply by 2 for symmetry
        double avg = total / 3.0;
        //int center = rule.get(avg)
        int state = (int) avg; //just round for now

        //resursively get left & right by generateNext n other one
        Cell nextCell = generateNext(g);

        return new Cell(state,nextCell);
    }

    //Generate the next cell based on its three parents. If we're at the end of
    //the list, then base the next cell's state on that of the parent cell in
    //the direction of the center (lastState)
    public static Cell generateNext(Cell p) { //later add parameter HashMap rule
        Cell ret;
        Cell next;
        double avg = avgParentStates(p);
        //int center = rule.get(avg)
        int center = (int) avg; //just round for now

        if(p.next() != null) {
            next = generateNext(p.next());
        } else {
            next = null;
        }

        return new Cell(center,next);
    }

    //Avg a cell's parent states, given its parent closest to the center.
    public static double avgParentStates(Cell c) {
        double total = 0;
        if(c != null) {
            total += c.state();
            if(c.next() != null) {
                total += c.next().state();
                if(c.next().next() != null)
                total += c.next().next().state();
            }
        }
        return total / 3.0;
    }

    //utility method to convert generation g into a string
    public static String g2S(Cell g){
        String s = "";
        if(g.next() != null) {
            s += g2SLeft(g.next()); //Concatenate the cells on the left
            s += g.state(); //Concatenate genesis cell
            s += g2SRight(g.next()); //Concatenate the cells on the right
        } else {
            s += g.state(); //Just Concatenate genesis cell
        }
        return s;
    }

    //helper methods to g2S
    public static String g2SLeft(Cell c){
        String s = "";
        if(c.next() != null)
            s += g2SLeft(c.next());
        s += c.state();
        return s;
    }

    public static String g2SRight(Cell c){
        String s = "";
        s += c.state();
        if(c.next() != null)
            s += g2SRight(c.next());
        return s;
    }
}
