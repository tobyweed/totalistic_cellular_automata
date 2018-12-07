/**
    Class to generate 1D, totalistic, nearest-neighbor cellular automata

    Stores necessary values & methods to generate automata but not automata themselves

    Generates generations of automata as simple arrays of integers

    CS 201 Final Project - Totalistic Cellular Automata
    Danny Grubbs-Donovan and Toby Weed
**/

import java.util.*;
import java.lang.Math.*;
import java.awt.Color;
import java.lang.Integer;
import java.awt.*;

@SuppressWarnings("serial") // to avoid Eclipse warning
public class Automaton {
    // FIELDS ==============================================================
    private int k; //the number of possible states for each cell
    private int ruleCode; //the decimal value of the rule code
    private String[] kAryRuleCode; //the k-ary value of the rule code
    private Vector<Integer> randomConfig; //A vector of cell with random states
    private Color zeroColor, kColor; //the color bounds

    //CONSTRUCTOR ==========================================================
    public Automaton(int kVal, int code, Color zColor, Color k_Color) {
        k = kVal;
        ruleCode = code;
        kAryRuleCode = intToKAry(ruleCode,this.k);
        zeroColor = zColor;
        kColor = k_Color;
        randomConfig = new Vector<Integer>();
    };

    // METHODS =================================================================
    // Generation & Initialization =============================================

    //Create a linked list representing a generation of cells from
    //genesis cell of past generation
    public static int[] generate(int[] parent, int kInt, String[] kCode) {
        int[] nextGen = new int[parent.length];
        for(int n = 0; n < parent.length; n++) {
            double total = parent[n];
            if(n > 0)
                total += parent[n-1];
            else
                total += parent[parent.length-1]; //make circular
            if(n < parent.length - 1)
                total += parent[n+1];
            else
                total += parent[0]; //make circular
            double avg = total / 3.0;
            int state = mapAvgToVal(avg,kInt,kCode);
            nextGen[n] = state;
        }
        return nextGen;
    }

    // Return the value of randomConfig[n]. Vector which will expand as necessary
    // but remain constant if we zoom in
    public int getRndmAtN(int n) {
        if(n < randomConfig.size())
            return randomConfig.elementAt(n);
        else // if there's no value at n then make one
            return setRndmAtN(n);
    }

    // Put a random value from 0 to k at index n
    private int setRndmAtN(int n) {
        int rand = (int)(k * Math.random());
        if(n < randomConfig.size())
            randomConfig.add( n, rand );
        else
            randomConfig.add( rand );
        return rand;
    }

    // Getters -----------------------------------------------------------------
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

    public Color getZeroColor(){
        return zeroColor;
    }

    // Setters -----------------------------------------------------------------
    public void setK( int kVal ) {
        k = kVal;
    }

    public void setCode( int code ) {
        ruleCode = code;
        kAryRuleCode = intToKAry(ruleCode,this.k);
    }

    public void setZeroColor(Color zColor){
    	zeroColor = zColor;
    }

    // Utils -------------------------------------------------------------------
    //converts integer rule code to KAry rule code
    public static String[] intToKAry(int code, int kVal) {
        String[] kCode = new String[(3*kVal-2)];
        for(int n = 0; n < kCode.length; n++) {
            String digit = Integer.toString((code/((int)Math.pow(kVal,n)))%kVal);
            kCode[kCode.length - n - 1] = digit;
        }
        return kCode;
    };

    //Takes zeroColor and kColor, which are the bounds of the colors,
    //and returns the correct color for state val in a kInt-ary automaton
    public static Color mapValToColor(Color zeroColor, Color kColor, double val, int kInt) {
        double vf = val/((double)kInt-1); //vf for "value factor"
        //Color ranges
        double rr = (double)kColor.getRed()-(double)zeroColor.getRed();
        double rg = (double)kColor.getGreen()-(double)zeroColor.getGreen();
        double rb = (double)kColor.getBlue()-(double)zeroColor.getBlue();
        Color cellColor;
        int rv = (int)(zeroColor.getRed()+(int)(rr*vf));
        int gv = (int)(zeroColor.getGreen()+(int)(rg*vf));
        int bv = (int)(zeroColor.getBlue()+(int)(rb*vf));
        cellColor = new Color(rv,gv,bv);
        return cellColor;
	}

    // Given an average of parent value, a k value, and a kary rule code, return
    // the correct state for the child
    public static int mapAvgToVal(double avg, int kInt, String[] kCode){
        double dif = kInt-avg;
        double index = 3*(dif-1);
        int ind = (int)Math.round(index);
        return Integer.parseInt(kCode[ind]);
    }
}
