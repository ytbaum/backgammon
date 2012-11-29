package game;

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import board.*;
import java.lang.Thread;

public class Backgammon
{

    private Board board;
    private int[] dice = {0, 0};
    private int currentPlayer = 0;
    private boolean shouldCheckValidity = true;

    public static final int DIE_NUM_SIDES = 6;
    public static final int PLAYER1 = 0;
    public static final int PLAYER2 = 1;
    public static final int NO_PLAYER = 2;

    private int winner = NO_PLAYER;

    public Backgammon()
    {
        this.board = new Board();
    }

    public static void main(String[] args)
    {
        Backgammon bg = new Backgammon();
        bg.test();
    }

    public void test()
    {
        this.board.display();
        this.moveLoop();

        //this.commandLoop();
        return;
    }

    public void moveLoop()
    {

        Scanner input = new Scanner(System.in);
        String moveString = "", yesOrNo; 
        String[] moveAsStringArray;

        int origin = -1;
        int dest = -1;
        int player = -1;

        while (this.winner == NO_PLAYER) {
            System.out.println("Rolling the dice...");
            this.rollDice();

            int[] availableMoves = this.getAvailableMoves();
            
            while (!moveString.equals("pass")) {
                boolean moveIsValid = false;
                while (!moveIsValid) {
                    // parse the move from the command-line input
                    System.out.println("enter an origin, a destination, and a player, separated by whitespace:");
                    moveString = input.nextLine(); 
                    if (moveString.equals("pass")) {
                        break;
                    }
                    moveAsStringArray = moveString.split("\\s");
                    origin = Integer.parseInt(moveAsStringArray[0]);
                    dest = Integer.parseInt(moveAsStringArray[1]);
                    player = this.currentPlayer;

                    moveIsValid = this.checkMoveValidity(origin, dest, player, availableMoves);
                }

                if (!moveString.equals("pass")) {
                    this.board.movePiece(origin, dest, player);
                    int moveDistance = Math.abs(dest - origin);
                    availableMoves[moveDistance - 1]--;
                    this.board.display();
                    this.winner = this.checkForWinner();
                }
            } 

            this.passTheDice();
            moveString = "";
        }    

        return;
    }

    private void commandLoop()
    {
        boolean quit = false;
        String cmd = "";
        String[] cmdArray;

        while (!quit) {
            Scanner s = new Scanner(System.in);
            cmd = s.nextLine();
            cmdArray = cmd.split(" ");

            switch(cmdArray[0]) {
                case "quit":
                    quit = true;
                    break;

                case "printFunnyStuff":
                    System.out.println("ha ha!");
                    break;

                case "rollDice":
                    this.rollDice();
                    break;

                case "printCurPlayer":
                    System.out.println(Integer.toString(this.currentPlayer));
                    break;

                case "noValidity":
                    this.shouldCheckValidity = false;
                    break;

                case "validity":
                    this.shouldCheckValidity = true;
                    break;

                case "add":
                    int space = Integer.parseInt(cmdArray[1]);
                    int player = Integer.parseInt(cmdArray[2]);
                    this.board.addPieceToSpace(space, player);
                    this.board.display();
                    break;

                case "remove":
                    int spaceToRemoveFrom = Integer.parseInt(cmdArray[1]);
                    this.board.removePiece(spaceToRemoveFrom);
                    this.board.display();
                    break;

                case "clearBoard":
                    this.board.clear();
                    this.board.display();
                    break;


                case "move":
                    int origin = Integer.parseInt(cmdArray[1]);
                    int dest = Integer.parseInt(cmdArray[2]);
                    int[] availableMoves = this.getAvailableMoves();
                    if (!this.shouldCheckValidity || this.checkMoveValidity(origin, dest, this.currentPlayer, availableMoves)) {
                        this.board.movePiece(origin, dest, this.currentPlayer);
                        int moveDistance = Math.abs(dest - origin);
                        availableMoves[moveDistance - 1]--;
                    }
                    this.board.display();
                    break;

                default:
                    System.out.println("don't know that command, sorry");
                    break;
            }

        }

        return;

    }

    private void rollDice()
    {
        Random rand = new Random();
        for (int i = 0; i < this.dice.length; i++) {
            this.dice[i] = rand.nextInt(DIE_NUM_SIDES) + 1;
        }

        System.out.println(this.getDiceString());

        return;
    }

    private String getDiceString()
    {
        String output = Integer.toString(this.dice[0]) + " " + Integer.toString(this.dice[1]);

        return output;
    }

    private int[] getAvailableMoves()
    {
        int[] availableMoves = new int[DIE_NUM_SIDES];

        // dice are 1-indexed; convert them to 0-indexed
        int[] zDice = new int[2];
        zDice[0] = this.dice[0] - 1;
        zDice[1] = this.dice[1] - 1;

        for (int i = 0; i < DIE_NUM_SIDES; i++) {
            if (i == zDice[0] && i == zDice[1]) {
                availableMoves[i] = 4;
            } else if (i == zDice[0] || i == zDice[1]) {
                availableMoves[i] = 1;
            } else {
                availableMoves[i] = 0;
            }
        }

        return availableMoves;
    }

    // check for various ways the move might be invalid
    private boolean checkMoveValidity(int origin, int dest, int player, int[] availableMoves)
    {
        // is the player moving in the right direction?
        if (!this.directionIsCorrect(origin, dest, player)) {
            System.out.println("ERROR: you are moving in the wrong direction!");
            return false;
        }

        // is the player trying to move a non-bar piece when he/she has pieces on the bar?
        if (this.board.getBarCount(player) > 0 && origin != this.board.barSpace[player]) {
            System.out.println("ERROR: you must first move your piece off the bar"); 
            return false;
        }

        // is the player trying to move a piece from the bar when he/she doesn't have any there (will only be a problem when typing in moves on the command line)
        if (origin == this.board.barSpace[player] && this.board.getBarCount(player) < 1) {
            System.out.println("ERROR: player is trying to move a piece from the bar, but has no pieces on the bar");
            return false;
        }

        // does the player have pieces on the origin space?
        // (this problem is command-line only)
        if (origin >= 0 && origin < this.board.NUM_SPACES) {
            int originPlayer = this.board.getPlayer(origin);
            if (player != originPlayer) {
                System.out.println("ERROR: player is trying to move a piece from a space s/he doesn't have pieces on");
                return false;
            }
        }

        // is the move allowed to the player by the dice roll?
        int moveDistance = Math.abs(dest - origin);
        if (availableMoves[moveDistance - 1] == 0) {
            System.out.println("ERROR: you have no moves of distance " + Integer.toString(moveDistance) + " remaining");
            return false;
        }

        // if none of the above problems occurred, the move is valid
        return true;
    }

    private boolean directionIsCorrect(int origin, int dest, int player)
    {
        switch(player) {
            case PLAYER1:
                return (dest > origin);
            case PLAYER2:
                return (origin > dest);
            default:
                System.out.println("Error: a player was passed in as an argument and it wasn't PLAYER1 or PLAYER2");
                System.exit(1);
        }
        
        return false;
    }

    private int checkForWinner()
    {
        return NO_PLAYER;
    }

    private void passTheDice()
    {
        this.currentPlayer = (this.currentPlayer + 1) % 2;
        return;
    }
}
