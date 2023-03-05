package validation;
public class DataValidation {
    //properties
    private int rows;
    private int columns;
    private int speed;
    private String initialDataGeneration;

    private char[] initialDataGenerationChar;

    private boolean error = false ;


    //constructor

    public DataValidation(int rows, int columns, int speed, String initialDataGeneration) {
        this.rows = rows;
        this.columns = columns;
        this.speed = speed;
        this.initialDataGeneration = initialDataGeneration.toLowerCase();
        this.initialDataGenerationChar = this.initialDataGeneration.toCharArray();


        this.columnsValidation();
        this.rowsValidation();
        this.speedValidation();
        this.dataGenerationValidation();
    }

    //methods
    public void columnsValidation(){
        if ((this.columns != 10) && (this.columns != 20) && (this.columns != 40) && (this.columns != 80) ){
            this.error = true;

        }
    }

    public void rowsValidation(){
        if ((this.rows != 10) && (this.rows != 20) && (this.rows != 40) ){
            this.error = true;
        }
    }

    public void speedValidation(){
        if ((this.speed != 250) && (this.speed != 1000)){
            this.error = true;
        }
    }

    private void dataGenerationValidation(){
        boolean string = this.dataGenerationValidationString();
        boolean cha = this.dataGenerationValidationChar();
        if (!string && !cha){
            this.error = true;
        }
        
    }

    private boolean dataGenerationValidationString(){

        boolean validationSuccess = false;
        if (this.initialDataGeneration.equals("rnd")  || this.initialDataGeneration.equals("lexicon")){
            validationSuccess = true;
            if (this.initialDataGeneration.equals("lexicon")  && (this.rows < 20 || this.columns < 20)){
                validationSuccess = false;
            }
        }
        return validationSuccess;
    }

    private boolean dataGenerationValidationChar(){
        int line = 0;
        int colum = 0;
        boolean validationSuccess = true;

        for (char carac: this.initialDataGenerationChar) {
            switch (carac){
                case '0' ->{
                   colum += 1;
                }
                case '1' ->{
                    colum += 1;
                }
                case '#' ->{
                    line += 1;
                    colum = 0;

                }
                default -> {
                    validationSuccess = false;
                }
            }
            if (line > this.rows || colum > this.columns){
                validationSuccess = false;
            }
        }
        return validationSuccess;
    }
    //get
    public boolean getError() {
        return error;
    }
}