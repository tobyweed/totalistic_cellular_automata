// Implements a Totalistic Cellular Automaton

import java.util.*;
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
    public Automaton(int kValue, int code) {
        k = kValue;
        ruleCode = code;
        kAryRuleCode = intToKAry(ruleCode);
        //then set other three fields
    };

    // METHODS =============================================================
    public int getK() {
        return k;
    };
    public int getCode() {
        return ruleCode;
    };
    public String[] getKAryCode() {
        return kAryRuleCode;
    };
    //takes in code and k
    public String[] intToKAry(int code) {
    	String[] kCode = new String[(3*k-2)];
        for(int n = 0; n < kCode.length; n++) {
            String digit = Integer.toString((code/((int)Math.pow(k,n)))%k);
            kCode[kCode.length - n - 1] = digit;
        }
    	return kCode;
     };
    //public static int KAryToInt(int[] code) {
      //  return 0;
    //};

    //zeroColor & kColor determine how the rgb values change, will normally
    //just be RGB(255,255,255) & RGB(0,0,0). Also idk how colors work in Java
    //so it prob wonâ€™t actually be of type Color
    //Takes in zeroColor and kColor, which are the bounds of the colors,
    //and returns the color
    protected Color mapValToColor(Color zeroColor, Color kColor, int val) {
    	float valFactor = ((float)val/k);
    	float rangeGreen = Math.abs((float)kColor.getGreen()-(float)zeroColor.getGreen());
    	float rangeBlue = Math.abs((float)kColor.getBlue()-(float)zeroColor.getBlue());
    	float rangeRed = Math.abs((float)kColor.getRed()-(float)zeroColor.getRed());
    	Color tile = new Color((rangeRed*valFactor)/255,(rangeGreen*valFactor)/255,(rangeBlue*valFactor)/255);
    	return tile;
    };
    //this sets the rules
    //sets color value for child based on avg value of parents
    //passes in the avg
    protected String mapAvgToVal(double avg){
    	double dif = this.k-avg;
    	double index = 3*(dif-1);
    	int ind = (int)Math.round(index);
    	return this.kAryRuleCode[ind];
    }

    //just for testing
    //public static void main(String[] args) {
    //	Automaton a = new Automaton(3,30);
    //	System.out.println(a.mapValToColor(Color.green,Color.red,1));
    //	System.out.println(a.intToKAry(32));
    //	System.out.println(a.mapAvgToVal(.67));
    }

}
