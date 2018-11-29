// A linked list to store each Cell of our automaton
// "Generations" refer to the cell and all connected cells
// We will only store one half of the pyramid, as all totalistic 1D automata which
// start with one value are symmetrical

public class Cell {
    public static void main(String[] args){
        // Cell c = new Cell(1);
    }

    // FIELDS ==============================================================
    protected Integer state; //represents the color of the cell
    protected Cell next;

    //CONSTRUCTORS ==========================================================
    public Cell(Integer s) {
        state = s;
        next = null;
    };

    public Cell(Integer s, Cell n) {
        state = s;
        next = n;
    };

    // INSTANCE METHODS ========================================================
    public Integer state() {
        return state;
    }

    public Cell next() {
        return next;
    }

    public void changeState(Integer newState) {
        state = newState;
    }

    public void addNext(Cell c) {
        next = c;
    }
}
