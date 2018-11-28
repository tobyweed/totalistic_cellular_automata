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
    public Automaton(int k, int code) {
        k = k;
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
    public static int KAryToInt(int[] code) {
        return 0;
    };

    //zeroColor & kColor determine how the rgb values change, will normally
    //just be RGB(255,255,255) & RGB(0,0,0). Also idk how colors work in Java
    //so it prob won’t actually be of type Color
    protected void mapValToColor(Color zeroColor, Color kColor) {
    };

    protected void mapAvgToVal(){} //this sets the rules

}
