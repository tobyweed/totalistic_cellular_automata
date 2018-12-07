/**
    Class with methods to generate 1D, totalistic, nearest-neighbor cellular automata

    Generates generations of automata as simple arrays of integers

    A note on implementation: this class has been set up as a functional one,
    keeping all the methods to generate automata static, and storing the
    necessary fields (k, ruleCode, etc) in Automata. This allows flexibility,
    prevents having to constantly switch automatons in/change automaton state from
    Automata, and (mostly) prevents dual sources of truth on automaton state.
    It also has downsides, like only being able to have one automaton at a time
    & not really having a clearly defined "automaton" in general. It seemed like
    making Automaton functional was the best route for now, but it could be easily
    changed. Anyways, just thought that deserved explanation.

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
    // METHODS =================================================================

    //Create a linked list representing a generation of cells from
    //genesis cell of past generation
    //Note: this method treats the parent array as circular, so the edge cells
    //include the cell on the parent's other edge when updating their state.
    //The alternatives to this are to have the arrays be infinite or treat the edges
    //as some value (ex.: "0"). Circularity seemed cleanest, but can create
    //unexpected behavior on the edges when resizing.
    public static int[] generate(int[] parent, int kInt, String[] kCode) {
        int[] nextGen = new int[parent.length];

        //average parent state
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

            //assign correct state
            int state = mapAvgToVal(avg,kInt,kCode);
            nextGen[n] = state;
        }

        return nextGen;
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
	public static double[] avgFromK(int k) {
    	DecimalFormat kFormat = new DecimalFormat("#.00");
    	double[] avgs = new double[((3*k)-2)];
    	for(int x=0;x < ((3*(double)k)-2);x++) {
    		avgs[x] = Double.parseDouble(kFormat.format(((double)x/3)));
    		System.out.println((double)x/3);
    		//avgs = avgs + kFormat.format(((double)x/3));
    	}
    	return avgs;
    }
	
	
	
}
