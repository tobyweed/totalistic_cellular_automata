// Implements a Totalistic Cellular Automaton

import java.util.*;
import java.lang.Math.*;
import java.awt.Color;
import java.lang.Integer;
import java.awt.*;


public class Automaton {
    // FIELDS ==============================================================
    private int k;
    private int ruleCode;
    private String[] kAryRuleCode; //Unnecessary but avoids having to constantly recalculate
    private Vector<Integer> randomConfig;
    Color zeroColor, kColor;

    //CONSTRUCTOR ==========================================================
    public Automaton(int kVal, int code, Color zColor, Color k_Color) {
        k = kVal;
        ruleCode = code;
        kAryRuleCode = intToKAry(ruleCode,this.k);
        zeroColor = zColor;
        kColor = k_Color;
        randomConfig = new Vector<Integer>();
    };

    // METHODS =============================================================
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

    public int getK() {
        return k;
    };

    public String[] getKAryCode() {
        return kAryRuleCode;
    };

    public Color getZeroColor(){
    	return zeroColor;
    }

    // return the value of randomConfig[n]
    public int getRndmAtN(int n) {
        if(n < randomConfig.size())
            return randomConfig.elementAt(n);
        else
            return setRndmAtN(n);
    }

    private int setRndmAtN(int n) {
        int rand = (int)(k * Math.random());
        if(n < randomConfig.size())
            randomConfig.add( n, rand );
        else
            randomConfig.add( rand );
        return rand;
    }

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
    	float valFactor = ((float)val/(float)(kInt));
    	Color tile;
    	if(val == 0){
    		tile = zeroColor;
    		}
    	else if(val == kInt-1){
    		tile = kColor;
    		}
    	else{
    		float rangeGreen = Math.abs((float)kColor.getGreen()-(float)zeroColor.getGreen());
    		float rangeBlue = Math.abs((float)kColor.getBlue()-(float)zeroColor.getBlue());
    		float rangeRed = Math.abs((float)kColor.getRed()-(float)zeroColor.getRed());
    		tile = new Color((rangeRed*valFactor)/255,(rangeGreen*valFactor)/255,(rangeBlue*valFactor)/255);
    		}
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
}
