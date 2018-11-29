// Implements a Totalistic Cellular Automaton

import java.util.*;
import java.awt.Color;
import java.lang.Integer;

public class Automaton {
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
    //public int getCode() {
      //  return 0;
    //};
    public String getKAryCode() {
        return kAryRuleCode;
    };
    //takes in code and k
    public static String intToKAry(int code,int k) {
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
    	 }
    	 else {
    		 String add = Integer.toString(code%div);
    		 kCode = add+kCode;
    		 return intToKAryHelper(code/div,kCode,div);
    	 }
     }
    //public static int KAryToInt(int[] code) {
      //  return 0;
    //};

    //zeroColor & kColor determine how the rgb values change, will normally
    //just be RGB(255,255,255) & RGB(0,0,0). Also idk how colors work in Java
    //so it prob wonâ€™t actually be of type Color
    protected void mapValToColor(Color zeroColor, Color kColor) {
    };

    protected void mapAvgToVal(){} //this sets the rules

    //just for testing
    public static void main(String[] args) {
    	System.out.println(intToKAry(40,2));
    }

}
