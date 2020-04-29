package gameinterface;

import java.util.Scanner;

public class Main {

    // This boolean keeps the main game loop running until game is lost, won or quit
    private static MyMinesweeper game;

    /**
     * Main game loop
     * @param args parameters when started from CMD - unused
     */
    public static void main(String[] args) throws InvalidRangeException {
        Scanner input = new Scanner(System.in);
        int rows = 0;
        int columns = 0;
        int bombs = 0;

        System.out.println("Please enter [ROWS] [COLUMNS] [BOMBS]:");
        System.out.println("If [BOMBS] 0 you get a random amount!");
        String[] settings =  input.nextLine().split(" ");
        try{
            rows = Integer.parseInt(settings[0]);
            columns = Integer.parseInt(settings[1]);
            bombs = Integer.parseInt(settings[2]);
        }catch (NumberFormatException e){
            System.out.println(e);
            System.err.println("Your input was not well formatted please try again!");
            System.exit(0);
        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("You did not enter 3 parameters!");
            System.exit(0);
        }finally {
            if(bombs == 0){
                game = new MyMinesweeper(rows, columns);
            }else{
                game = new MyMinesweeper(rows, columns, bombs);
            }
        }

        // Now we show the help screen once to deliver all possible commands for the user
        displayHelp();

        game.printBoard();
        input();
        game.printBoard();

        while(!game.getLost()){
            input();
            game.printBoard();
            if (game.remainingBombs() == 0) System.out.println("You win");
        }
        lost();
    }

    private static void displayHelp() {
        System.out.println("****************** Minesweeper - By Jerne ******************");
        System.out.println("Possible commands are:");
        System.out.println("CLICK X Y - Where X is the COLUMN and Y is the ROW.");
        System.out.println("FLAG X Y - Where X is the COLUMN and Y is the ROW.");
        System.out.println("QUIT - Stops the program.");
        System.out.println("HELP - Displays this menu again.");
        System.out.println("************************************************************");
    }

    private static void input() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter next command. Enter HELP to get command list.");
        String input = (scanner.nextLine()).toUpperCase();
        String[] inputArray = input.split(" ");

        switch(inputArray[0]){
            case "HELP": displayHelp(); break;
            case "FLAG": flag(inputArray); break;
            case "CLICK": click(inputArray); break;
            case "QUIT": System.exit(0); break;

            default:
                System.err.println("Didn't recognise that command!");
                input();
                break;
        }
    }

    private static void click(String[] inputArray) {
        try{
            int xValue = Integer.parseInt(inputArray[1])-1;
            int yValue = Integer.parseInt(inputArray[2])-1;
            //System.out.println(xValue + " ---- " + yValue);
            MyLocation loc = new MyLocation(xValue, yValue);
            game.checkLocation(loc);

        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Please make sure to enter 2 values!");
            input();
        }catch (NumberFormatException e){
            System.err.println("Please make sure to enter integer values! Or not a valid range.");
            input();
        }
    }

    private static void flag(String[] inputArray) {
        try{
            int xValue = Integer.parseInt(inputArray[1])-1;
            int yValue = Integer.parseInt(inputArray[2])-1;
            MyLocation loc = new MyLocation(xValue, yValue);
            game.flagLocation(loc);

        }catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Please make sure to enter 2 values!");
            input();
        }catch (NumberFormatException e){
            System.err.println("Please make sure to enter integer values!");
            input();
        } catch (InvalidRangeException e) {
            e.printStackTrace();
        }
    }

    private static void lost(){
        System.err.println("****************** YOU LOST ******************");
        System.exit(0);
    }



}
