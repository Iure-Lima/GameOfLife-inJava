package gol;

import gridOfCells.GridOfCells;
import validation.DataValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Gol{
    //properties
    private GridOfCells grid;
    private int maxGeneration;
    private int generationNumber = 1;

    private int speed;
    private int rows;

    private int columns;

    private String initialDataGeneration;

    private char[] initialDataGenerationChar;

    private DataValidation validation;

    private boolean gridEmpty;

    //constructor
    public Gol(int maxGeneration, int rows, int columns, int speed, String initialDataGeneration) {
        System.out.println("Welcome to the game of life");

        this.validation = new DataValidation(rows,columns,speed,initialDataGeneration);

        if(this.validation.getError() == false){

            this.rows = rows + 2;
            this.columns = columns + 2;
            this.initialDataGeneration = initialDataGeneration.toLowerCase();
            this.maxGeneration = maxGeneration;
            this.grid = new GridOfCells(this.rows,this.columns);
            this.speed = speed;

            if(this.initialDataGeneration.equals("rnd")){
                this.initialGenerationRnd();
            }else if(this.initialDataGeneration.equals("lexicon")){
                this.initialGenerationLexicon();
            } else{
                this.initialDataGenerationChar = this.initialDataGeneration.toCharArray();
                this.initialGenerationChar();
            }
            this.printGeneration();


        }else{
            System.out.println("Unfortunately the data entered is wrong.");
            System.out.println("Accepted values are:");
            System.out.println("w = [10, 20, 40, 80]");
            System.out.println("h = [10, 20, 40]");
            System.out.println("s = [250 , 1000]");
            System.out.println("p = 010101#10101 or rnd or lexicon(w => 20 and h => 20)");
        }

    }

    //methods

    private void printGeneration(){


        while ((this.generationNumber <= this.maxGeneration) || (this.maxGeneration == 0)){
            try {
                System.out.println("Generation ["+this.generationNumber+"]");
                this.grid.printGrid();
                this.newGeneration();
                if (this.gridEmpty){
                    System.out.println("As we have no more living ceells, we will be stopping the game");
                    System.out.println("Generation ["+(this.generationNumber+1)+"]");
                    this.grid.printGrid();
                    break;
                }
                System.out.println("\n" + "\n");
                TimeUnit.MILLISECONDS.sleep(this.speed);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.generationNumber += 1;
        }

    }

    private void newGeneration(){

        List<List<Integer>> cellsToDelete = new ArrayList<>();
        List<List<Integer>> cellsToRevive = new ArrayList<>();

        for (int i = 1; i <(this.rows - 1) ; i++) {
            for (int j = 1; j < (this.columns - 1); j++) {

                boolean newState = this.rules(i, j, this.grid.getCellGrind(i, j));


                List<Integer> lineAndColumn = new ArrayList<>();

                lineAndColumn.add(i);
                lineAndColumn.add(j);

                if (newState){
                    cellsToRevive.add(lineAndColumn);
                }else{
                    cellsToDelete.add(lineAndColumn);
                }

            }
        }
        for (List<Integer> cellLocation : cellsToRevive) {
            this.grid.modifyCell(cellLocation.get(0), cellLocation.get(1) , true);
        }

        for (List<Integer> cellLocation : cellsToDelete) {
            this.grid.modifyCell(cellLocation.get(0), cellLocation.get(1) , false);

        }
        this.emptyArrayCheck(cellsToRevive);
    }

    private void emptyArrayCheck(List<List<Integer>> cellsToRevive){
        if (cellsToRevive.isEmpty()){
            this.gridEmpty = true;
        }
    }

    private  boolean rules(int x, int y,boolean cellLife){
        int neighbors = 0;
        boolean life = cellLife;

        for (int i = 0; i < 8; i++) {
            if (this.grid.getCellGrind(x ,y - 1) && i == 0){
                neighbors += 1;
            }else if (this.grid.getCellGrind(x -1,y - 1) && i == 1){
                neighbors += 1;

            }else if (this.grid.getCellGrind(x -1,y ) && i == 2){
                neighbors += 1;

            }else if (this.grid.getCellGrind(x - 1,y +1) && i == 3){
                neighbors += 1;

            }else if (this.grid.getCellGrind(x ,y +1) && i == 4){
                neighbors += 1;

            }else if (this.grid.getCellGrind(x +1,y +1) && i == 5){
                neighbors += 1;

            }else if (this.grid.getCellGrind(x +1,y ) && i == 6){
                neighbors += 1;
            }else if (this.grid.getCellGrind(x + 1,y - 1) && i == 7){
                neighbors += 1;

            }
        }


        if (cellLife){
            if (neighbors < 2 || neighbors > 3){
                life = false;
            }

            if (neighbors > 1 && neighbors < 4){
                life = true;
            }
        }

        if (!cellLife && neighbors == 3){
            life = true;
        }
        return life;
    }

    private void initialGenerationRnd(){
        Random random = new Random();
        int randomLine;
        int randomColumns;
        int repeatTotal = 0;

        if (this.columns == (10 +2)){
            repeatTotal = 30;
        }else if (this.columns == (20 +2)){
            repeatTotal = 50;
        }else if (this.columns == (40 +2)){
            repeatTotal = 80;
        }else if (this.columns == (80 +2)){
            repeatTotal = 140;
        }

        for (int i = 0; i < repeatTotal; i++) {
            randomLine = random.nextInt(this.rows - 2);
            randomColumns = random.nextInt(this.columns - 2);
            this.grid.modifyCell(randomLine + 1,randomColumns + 1,true);


        }

    }
    private void initialGenerationLexicon(){
        String lexicon = "####000000011110000##000001111111100##000111111111111##000001111111100##000000011110000";
        char[] initialDataGenerationCharLexicon = lexicon.toCharArray();
        int colunm = 1;
        int line = 1;

        for (char cha : initialDataGenerationCharLexicon) {
            switch (cha){
                case '0' ->{
                    colunm += 1;
                }
                case '1' ->{
                    this.grid.modifyCell(line,colunm,true);
                    colunm +=1 ;
                }
                case '#' ->{
                    colunm = 1;
                    line +=1;
                }
            }
        }

    }
    private void initialGenerationChar(){
        int colunm = 1;
        int line = 1;

        for (char cha : initialDataGenerationChar) {
            switch (cha){
                case '0' ->{
                    colunm += 1;
                }
                case '1' ->{
                    this.grid.modifyCell(line,colunm,true);
                    colunm +=1 ;
                }
                case '#' ->{
                    colunm = 1;
                    line +=1;
                }
            }
        }
    }
}