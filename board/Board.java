package board;

import board.Space;
import game.Backgammon;

public class Board {

    // game-play-related constants
    private static final int NUM_SPACES = 24;
    public static final int BAR_SPACE = -1;
    public static final int HOME_SPACE = 25;

    // display-related constants
    private static final int BOARD_WIDTH = 16;
    private static final char EMPTY_SPACE = ' ';
    private static final char vertEdge = '|';
    private static final char horizEdge = '=';
    private static final char[] pieces = {'X', '0', ' '};

    private Space[] spaces;
    private int[] bar = {0, 0};
    private int[] home = {0, 0};


    public Board()
    {

        this.spaces = new Space[this.NUM_SPACES];
        for (int i = 0; i < this.NUM_SPACES; i++) { 
            this.spaces[i] = new Space();
        }

        // set up Player 1's spaces
        this.spaces[0].setPlayer(Backgammon.PLAYER1);
        this.spaces[0].setNumPieces(2);

        this.spaces[11].setPlayer(Backgammon.PLAYER1);
        this.spaces[11].setNumPieces(5);

        this.spaces[16].setPlayer(Backgammon.PLAYER1);
        this.spaces[16].setNumPieces(3);

        this.spaces[18].setPlayer(Backgammon.PLAYER1);
        this.spaces[18].setNumPieces(5);

        // set up Player 2's spaces
        this.spaces[23].setPlayer(Backgammon.PLAYER2);
        this.spaces[23].setNumPieces(2);

        this.spaces[12].setPlayer(Backgammon.PLAYER2);
        this.spaces[12].setNumPieces(5);

        this.spaces[7].setPlayer(Backgammon.PLAYER2);
        this.spaces[7].setNumPieces(3);

        this.spaces[5].setPlayer(Backgammon.PLAYER2);
        this.spaces[5].setNumPieces(5);
    }

    public void test()
    {
        Space sp = new Space();
        this.display();
        return;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.NUM_SPACES; i++) {
            sb.append(Integer.toString(i) + ": " + this.spaces[i].toString()); 
            if (i < this.NUM_SPACES - 1) {
                sb.append("\n");
            }
        }

        String output = sb.toString();
        return output;
    }

    // count the maximum number of pieces on a space in the upper board
    // helper method for drawing board in console
    private int getUpperBoardDepth()
    {
        int maxPieces = 0;
        for (int i = 0; i < this.NUM_SPACES / 2; i++) {
            int numPieces = this.spaces[i].getNumPieces();
            if (numPieces > maxPieces) {
                maxPieces = numPieces;
            }
        } 

        return maxPieces;
    }

    // count the maximum number of pieces on a space in the upper board
    // helper method for drawing board in console
    private int getLowerBoardDepth()
    {
        int maxPieces = 0;
        for (int i = this.NUM_SPACES / 2; i < this.NUM_SPACES ; i++) {
            int numPieces = this.spaces[i].getNumPieces();
            if (numPieces > maxPieces) {
                maxPieces = numPieces;
            }
        } 

        return maxPieces;
    }

    private String getHorizBorder()
    {
        String output = "";
        for (int i = 0; i < this.BOARD_WIDTH; i++) {
            output += this.horizEdge;           
        }

        return output;
    }

    private String getMiddleBoardRow()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.vertEdge);
        for (int i = 0; i < this.NUM_SPACES / 2; i++) {
            if (i == (this.NUM_SPACES / 4)) {
                sb.append(this.vertEdge).append(this.vertEdge);
            }
            
            sb.append(EMPTY_SPACE);           
        }
        sb.append(this.vertEdge);

        String output = sb.toString();
        return output;
    }

    private String getBarRow(int row)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.vertEdge);
        for (int i = 0; i < (this.NUM_SPACES / 2) + 2; i++) {
            if (i == (this.NUM_SPACES / 4)) {
                if (this.bar[0] > row) {
                    sb.append(this.pieces[Backgammon.PLAYER1]);
                } else {
                    sb.append(this.vertEdge);
                }
            } else if (i == (this.NUM_SPACES / 4) + 1) {
                if (this.bar[1] > row) {
                    sb.append(this.pieces[Backgammon.PLAYER2]);
                } else {
                    sb.append(this.vertEdge);
                }
            } else {
                sb.append(EMPTY_SPACE);
            }
        }

        sb.append(this.vertEdge);

        String output = sb.toString();
        return output;

    }

    private int getBarMaxPieces()
    {
        return Math.max(this.bar[0], this.bar[1]);
    }

    private void addPieceToBar(int player)
    {
        this.bar[player] += 1;
        return;
    }

    public void display()
    {
        String delim = "\n";

        StringBuilder sb = new StringBuilder();
        sb.append(this.getHorizBorder() + delim);

        int upperBoardDepth = this.getUpperBoardDepth(); 
        for (int i = 0; i < upperBoardDepth; i++) {
            sb.append(this.getUpperBoardRow(i) + delim);
        }

        sb.append(this.getMiddleBoardRow() + delim);

        int barMaxPieces = this.getBarMaxPieces();
        for (int i = 0; i < barMaxPieces; i++) {
            sb.append(this.getBarRow(i) + delim);
        }

        sb.append(this.getMiddleBoardRow() + delim);

        int lowerBoardDepth = this.getLowerBoardDepth();
        for (int i = 0; i < lowerBoardDepth; i++) {
            sb.append(this.getLowerBoardRow(i) + delim);
        }

        sb.append(this.getHorizBorder() + delim);

        sb.append(delim);

        sb.append("Home for Player 1: " + Integer.toString(this.home[Backgammon.PLAYER1]) + delim);
        sb.append("Home for Player 2: " + Integer.toString(this.home[Backgammon.PLAYER2]) + delim);

        String output = sb.toString();
        System.out.println(output);
        return; 
    }

    // row: zero-indexed starting from upper-bounding horizontal edge
    private String getUpperBoardRow(int row)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.vertEdge);
        for (int i = (this.NUM_SPACES / 2) - 1; i >= 0; i--) {
            if (i == (this.NUM_SPACES / 4) - 1) {
                sb.append(this.vertEdge).append(this.vertEdge);
            }

            if (this.spaces[i].isEmpty()) {
                sb.append(EMPTY_SPACE);
            } else {
                int numPieces = this.spaces[i].getNumPieces();
                if (numPieces - 1 >= row) {
                    int player = this.spaces[i].getPlayer();
                    char piece = this.pieces[player];
                    sb.append(piece);

            // we're on a deeper depth than the number of pieces in that row
                } else {
                    sb.append(EMPTY_SPACE);
                }
            } 
        }            
        sb.append(this.vertEdge);

        String output = sb.toString();
        return output;
    }

    private String getLowerBoardRow(int row)
    {

        StringBuilder sb = new StringBuilder();

        sb.append(this.vertEdge);
        for (int i = (this.NUM_SPACES / 2); i < this.NUM_SPACES; i++) {
            if (i == (3 * this.NUM_SPACES / 4)) {
                sb.append(this.vertEdge).append(this.vertEdge);
            }

            if (this.spaces[i].isEmpty()) {
                sb.append(EMPTY_SPACE);
            // since we're appending rows downward, but numPieces on a space is counted up from the beginning of the space, need to compare against lowerBoardDepth - row instead of just against row, i.e. need to "flip" the lower board so that pieces are being counted in the same direction that we're printing rows
            } else {
                int numPieces = this.spaces[i].getNumPieces();
                int lowerBoardDepth = this.getLowerBoardDepth();
                if (numPieces >= lowerBoardDepth - row) {
                    int player = this.spaces[i].getPlayer();
                    char piece = this.pieces[player];
                    sb.append(piece);

            // we're on a deeper depth than the number of pieces in that row
                } else {
                    sb.append(EMPTY_SPACE);
                }

            } 
        }            
        sb.append(this.vertEdge);

        String output = sb.toString();
        return output;
    }

    public boolean movePiece(int origin, int dest, int player)
    {
        if (origin == BAR_SPACE) {
            this.bar[player]--;
        } else {
            this.spaces[origin].removePiece();
        }

        if (dest == HOME_SPACE) {
            this.home[player] += 1;
        } else {
            // if there's a lone opposing piece, send it to the bar
            if (this.spaces[dest].hasExposedPiece(player)) {
                int occupyingPlayer = this.spaces[dest].getPlayer();
                this.spaces[dest].removePiece();     
                this.addPieceToBar(occupyingPlayer);
            }
    
            this.spaces[dest].addPiece(player);
        }
        return true;
    }

    public int getBarCount(int player)
    {
        return this.bar[player];
    }

    public int getPlayer(int space)
    {
        return this.spaces[space].getPlayer();
    }


        // To-Dos in an executeTurn method:
        // check if the player can legally move to the space
        // (is it occupied?)
        // does the move match what the die says the player can do?
        // does the player have a guy he needs to get off the bar?
        // is the player's only move of that space blocked?
        // (i.e., know the player has no legal moves remaining)
        // does the player have a piece to move from the space?

}
