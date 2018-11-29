// Implements a Totalistic Cellular Automaton

import java.util.*;
import java.awt.Color;
import java.lang.Integer;

public class Automaton {
    public static void main(String[] args){
        Automaton a = new Automaton(3,777);
        Cell c = new Cell(2);
        //Test state printing
        System.out.println(c.state());
        //Test averaging
        System.out.println(avgParentStates(c));
        //Test generate
        printGeneration(c);
        Cell c1 = generate(c);
        printGeneration(c1);
        //Test generations
        Cell[] generations = generations(c, 5);
        for(Cell g : generations) {
            printGeneration(g);
            System.out.println("Generation: ");
        }
        //test int to kary
        System.out.println(intToKAry(40,2));
    }
    // FIELDS ==============================================================
    int k;
    int ruleCode;
    String kAryRuleCode; //Unnecessary but avoids having to constantly recalculate
    HashMap<Integer,Color> valToColor;
    HashMap<Double,Integer> avgToVal;

    //CONSTRUCTOR ==========================================================
    public Automaton(int kValue, int code) {
        k = kValue;
        ruleCode = code;
        kAryRuleCode = intToKAry(ruleCode, k);
        //then set other three fields
    };

    // METHODS =============================================================
    public int getK() {
        return k;
    };

    public String getKAryCode() {
        return kAryRuleCode;
    };

    //converts integer rule code to KAry rule code
    public String intToKAry(int code) {
        if(k==2) {
        	return Integer.toBinaryString(code);
        	}
        else {
        	String kCode = "";
        	return intToKAryHelper(code,kCode,k);
        }
//        else if (k==3) {
//        	String kCode = "";
//        	int div = 3;
//        	return inToKAryHelper(code,kCode,div);
//        }
//        else if(k==4) {
//        	String kCode = "";
//        	int div = 4;
//        	return inToKAryHelper(code,kCode,div);
//        }
//        else {
//        	return "";
//        }
     };

     public static String intToKAryHelper(int code, String kCode, int div) {
    	 if(code==0) {
    		 return kCode;
    	 } else {
    		 String add = Integer.toString(code%div);
    		 kCode = add+kCode;
    		 return intToKAryHelper(code/div,kCode,div);
    	 }
     }

    //zeroColor & kColor determine how the rgb values change, will normally
    //just be RGB(255,255,255) & RGB(0,0,0). Also idk how colors work in Java
    //so it prob wonâ€™t actually be of type Color
    protected void mapValToColor(Color zeroColor, Color kColor) {
    };

    protected void mapAvgToVal(){} //this sets the rules

    // UTILS ===================================================================

    //Return an array of n generations, starting from generation g
    public static Cell[] generations(Cell g, int n){ //later add parameter HashMap rule
        Cell[] generations = new Cell[n];
        generations[0] = generate(g);
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

    //utility method to print a generation with g as the genesis cell
    public static void printGeneration(Cell g){
        if(g.next() != null) {
            printLeft(g.next()); //print the cells on the left
            System.out.println(g.state()); //Print genesis cell
            printRight(g.next()); //print the cells on the right
        } else {
            System.out.println(g.state()); //Just print genesis cell
        }
    }

    //helper methods to printGeneration
    public static void printLeft(Cell c){
        if(c.next() != null)
            printLeft(c.next());
        System.out.println(c.state());
    }

    public static void printRight(Cell c){
        System.out.println(c.state());
        if(c.next() != null)
            printRight(c.next());
    }
}
