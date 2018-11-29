//Unit Tests for automata

import java.util.*;

public class Tests {
    public static void main(String[] args){
        Automaton a = new Automaton(3,777);
        Cell c = new Cell(2);
        //Test state assignment
        assert c.state() == 2 : "State assignment malfunction";
        print("State assigned to 2");
        //Test averaging
        assert a.avgParentStates(c) == (2.0/3) : "Parent state averaging malfunction";
        print("avgParentStates returned .666");
        //Test generate
        Cell c1 = a.generate(c);
        assert a.g2S(c1).equals("000") : "Generation malfunction";
        print("2 generated into 000");
        // //Test generations
        // Cell[] generations = a.generations(c, 5);
        // for(int x = 1; x < 5; x++) {
        //     int numZeros = (1+(x*2));
        //     char[] zeros = new char[numZeros];
        //     print(a.g2S(generations[x]));
        //     print(new String(zeros));
        //     assert generations[x].equals(new String(zeros)) : ("Generation " + x + " didn't have " + (1+(x*2)) + " zeros");
        // }

        //Test intToKAry
        String[] ternary = {"1","0","0","1","2","1","0"};
        String s = Arrays.toString(ternary);
        String s1 = Arrays.toString(a.intToKAry(777));
        assert s1.equals(s) : "int to KAry code conversion malfunction";
        print("777 converted to 1001210");
    }

    //printing shorthand
    public static void print(String s) {
        System.out.println(s);
    }

}
