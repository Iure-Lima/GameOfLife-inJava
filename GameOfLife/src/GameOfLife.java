import gol.Gol;

public class GameOfLife {
    public static void main(String[] args) {


        int rows = Integer.parseInt(getParam(args, "h"));
        int columns = Integer.parseInt(getParam(args, "w"));
        int maxGeneration = Integer.parseInt(getParam(args, "g"));
        int speed = Integer.parseInt(getParam(args, "s"));
        String initialDataGerenation = getParam(args,"p") ;



        Gol game = new Gol(maxGeneration,rows,columns, speed, initialDataGerenation);


    }
    public static String getParam(String[] args, String field) {
        for (String param : args) {
            String[] keyAndValue = param.split("=");
            if (keyAndValue[0].equals(field)) {
                return keyAndValue[1];
            }
        }

        throw new RuntimeException("Invalid param provided");
    }
}