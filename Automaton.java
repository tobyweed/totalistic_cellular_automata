// Implements a Totalistic Cellular Automaton

import java.util.*;
import java.lang.Math.*;
import java.awt.Color;
import java.lang.Integer;
import java.awt.*;


public class Automaton {
    // FIELDS ==============================================================
    int k;
    int ruleCode;
    String[] kAryRuleCode; //Unnecessary but avoids having to constantly recalculate
    HashMap<Integer,Color> valToColor;
    HashMap<Double,Integer> avgToVal;

    //CONSTRUCTOR ==========================================================
    public Automaton(int kVal, int code) {
        k = kVal;
        ruleCode = code;
        kAryRuleCode = intToKAry(ruleCode,this.k);
        //then set other three fields
    };

    // METHODS =============================================================
    public void setK( int kVal ) {
        k = kVal;
    }

    public void setCode( int code ) {
        ruleCode = code;
        kAryRuleCode = intToKAry(ruleCode,this.k);
    }

    public int getK() {
        return k;
    };

    public String[] getKAryCode() {
        return kAryRuleCode;
    };

    //converts integer rule code to KAry rule code
    public static String[] intToKAry(int code, int kVal) {
        String[] kCode = new String[(3*kVal-2)];
        for(int n = 0; n < kCode.length; n++) {
            String digit = Integer.toString((code/((int)Math.pow(kVal,n)))%kVal);
            kCode[kCode.length - n - 1] = digit;
        }
        return kCode;
    };

    //Takes in zeroColor and kColor, which are the bounds of the colors,
    //and returns the color
    public static Color mapValToColor(Color zeroColor, Color kColor, int val, int kInt) {
        float valFactor = ((float)(val)/(float)(kInt));
        float rangeGreen = Math.abs((float)kColor.getGreen()-(float)zeroColor.getGreen());
        float rangeBlue = Math.abs((float)kColor.getBlue()-(float)zeroColor.getBlue());
        float rangeRed = Math.abs((float)kColor.getRed()-(float)zeroColor.getRed());
        Color tile = new Color((rangeRed*valFactor)/255,(rangeGreen*valFactor)/255,(rangeBlue*valFactor)/255);
        return tile;
    }

    //this sets the rules
    //sets color value for child based on avg value of parents
    //passes in the avg
    public static int mapAvgToVal(double avg,int kInt, String[] kCode){
        double dif = kInt-avg;
        double index = 3*(dif-1);
        int ind = (int)Math.round(index);
        return Integer.parseInt(kCode[ind]);
    }

    // UTILS ===================================================================

    //Create a linked list representing one half of a generation of cells from
    //genesis cell of past generation
    public static Cell generate(Cell g, int kInt, String[] kCode) {
        //determine the value of the c
        double total = g.state();
        total += (g.next() != null) ? (2*g.next().state()) : 0; //multiply by 2 for symmetry
        double avg = total / 3.0;
        int state = mapAvgToVal(avg,kInt,kCode);

        //resursively get left & right by generateNext n other one
        Cell nextCell = generateNext(g,kInt,kCode);

        return new Cell(state,nextCell);
    }

    //Generate the next cell based on its three parents. If we're at the end of
    //the list, then base the next cell's state on that of the parent cell in
    //the direction of the center (lastState)
    public static Cell generateNext(Cell p, int kInt, String[] kCode) {
        Cell ret;
        Cell next;
        double avg = avgParentStates(p);
        int center = mapAvgToVal(avg,kInt,kCode);

        if(p.next() != null) {
            next = generateNext(p.next(), kInt, kCode);
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

    //public static void main(String[]args) {
    //System.out.println(mapValToColor(Color.black,Color.white,0,3));
    //}
}
