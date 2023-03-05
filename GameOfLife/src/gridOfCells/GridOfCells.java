package gridOfCells;

import cell.Cell;

public class GridOfCells{
    //properties
    private int rows;
    private int columns;

    private Cell cell;
    private Cell[][] cellGrind;


    //construtor
    public GridOfCells(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.cellGrind = new Cell[this.rows][this.columns];
        this.fillGrid();

    }

    //method
    public void fillGrid(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cellGrind[i][j] = new Cell(false);

            }
        }
    }
    public void printGrid(){

        for (int i = 1; i < (rows - 1); i++) {
           for (int j = 1; j < (columns - 1); j++) {
                if (this.cellGrind[i][j].getState() == false){
                    System.out.print(" .");
                }else{
                    System.out.print(" @");
                }

            }
            System.out.println();
        }

    }


    public void modifyCell(int x, int z, boolean life){
        if (life == true){
            this.cellGrind[x][z].setState(true);
        }else{
            this.cellGrind[x][z].setState(false);
        }
    }

    //gets
    public boolean getCellGrind(int x, int y) {
        return this.cellGrind[x][y].getState();
    }

}
