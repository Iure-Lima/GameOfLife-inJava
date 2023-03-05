package cell;
public class Cell {
    //properties
    private boolean state;

    //constructor

    public Cell(boolean state){
        this.state = state;
    }

    //gets

    public boolean getState() {
        return state;
    }
    //settes

    public void setState(boolean state) {
        this.state = state;
    }
}