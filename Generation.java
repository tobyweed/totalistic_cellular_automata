// A structure which represents the center of the generation. It stores the
// value of the center cell and two linked lists of cells extending to the left
// & right

//THIS CLASS IS NOT CURRENTLY BEING USED


public class Generation {
    public static void main(String[] args){
        // Cell c = new Cell(1);
    }

    // FIELDS ==============================================================
    protected Cell center; //the cell at the center of the generation. Never
                           //has a "next"
    protected Cell left, right;

    //CONSTRUCTORS ==========================================================
    public Generation(Cell c) {
        center = c;
        left = null;
        right = null;
    };

    public Generation(Cell c, Cell l, Cell r) {
        center = c;
        left = l;
        right = r;
    };
}
