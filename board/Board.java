package board;

import board.Space;

import java.util.ArrayList;

public class Board {

    private static final int NUM_SPACES = 24;
    private static final int BOARD_WIDTH = 16;
    private static final char vertEdge = '|';
    private static final char horizEdge = '=';
    private static final char[] pieces = {' ', 'X', 'O'};

    private Space[] spaces;

    public static void main(String[] args)
    {
        Board b = new Board();
        b.test();
    }

    public Board()
    {

        this.spaces = new Space[this.NUM_SPACES];
        for (int i = 0; i < this.NUM_SPACES; i++) { 
            this.spaces[i] = new Space();
        }

        // set up Player 1's spaces
        this.spaces[0].setPlayer(1);
        this.spaces[0].setNumPieces(2);

        this.spaces[11].setPlayer(1);
        this.spaces[11].setNumPieces(5);

        this.spaces[16].setPlayer(1);
        this.spaces[16].setNumPieces(3);

        this.spaces[18].setPlayer(1);
        this.spaces[18].setNumPieces(5);

        // set up Player 2's spaces
        this.spaces[23].setPlayer(2);
        this.spaces[23].setNumPieces(2);

        this.spaces[12].setPlayer(2);
        this.spaces[12].setNumPieces(5);

        this.spaces[7].setPlayer(2);
        this.spaces[7].setNumPieces(3);

        this.spaces[5].setPlayer(2);
        this.spaces[5].setNumPieces(5);
    }

    public void test()
    {
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
            
            sb.append(' ');           
        }
        sb.append(this.vertEdge);

        String output = sb.toString();
        return output;
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

        int lowerBoardDepth = this.getLowerBoardDepth();
        for (int i = 0; i < lowerBoardDepth; i++) {
            sb.append(this.getLowerBoardRow(i) + delim);
        }

        sb.append(this.getHorizBorder() + delim);

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

            int numPieces = this.spaces[i].getNumPieces();
            int player = this.spaces[i].getPlayer();
            char piece = this.pieces[player];
            if (piece == ' ') {
                sb.append(' ');
            } else if (numPieces - 1 >= row) {
                sb.append(piece);

            // we're on a deeper depth than the number of pieces in that row
            } else {
                sb.append(' ');
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

            int numPieces = this.spaces[i].getNumPieces();
            int player = this.spaces[i].getPlayer();
            char piece = this.pieces[player];
            int lowerBoardDepth = this.getLowerBoardDepth();
            if (piece == ' ') {
                sb.append(' ');
            // since we're appending rows downward, but numPieces on a space is counted up from the beginning of the space, need to compare against lowerBoardDepth - row instead of just against row, i.e. need to "flip" the lower board so that pieces are being counted in the same direction that we're printing rows
            } else if (numPieces >= lowerBoardDepth - row) {
                sb.append(piece);

            // we're on a deeper depth than the number of pieces in that row
            } else {
                sb.append(' ');
            }
        }            
        sb.append(this.vertEdge);

        String output = sb.toString();
        return output;
    }
}
