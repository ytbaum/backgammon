package board;
import game.Backgammon;

public class Space {

    private int player = Backgammon.NO_PLAYER; 
    private int numPieces = 0;

    public int getPlayer()
    {
        return this.player;
    }

    public void setPlayer(int newPlayer)
    {
        this.player = newPlayer;
        return;
    }

    public int getNumPieces()
    {
        return this.numPieces;
    }

    public void setNumPieces(int newNumPieces)
    {
        this.numPieces = newNumPieces;
        return;
    }

    @Override
    public String toString()
    {
        return "Player: " + Integer.toString(this.player) + "   NumPieces: " + Integer.toString(this.numPieces);
    }

    public void addPiece(int curPlayer)
    {

        if (this.player == Backgammon.NO_PLAYER) {
            this.player = curPlayer;
            this.numPieces += 1;
            return;
        } else if (this.player == curPlayer) {
            this.numPieces += 1;
            return;
        } else {
            if (this.numPieces == 0) {
                System.out.println("ERROR: inconsistent state- space is occupied by player " + Integer.toString(this.player) + " but this.numPieces equals 0");
                System.exit(1);
            } else if (this.numPieces == 1) {
                System.out.println("ERROR: space has not been cleared");
                System.exit(1);
            } else {
                System.out.println("ERROR: space is occupied by the other player with more than one piece.");
                System.exit(1);
            }
        }
            
        /*
        if this.player == 0, no problem just add the piece and set this.player = 1

        if this.player == curPlayer, no problem, just add one to the piece count

        if this.player == other player:
            if pieceCount == 1 return false or exit, saying that the piece should have been cleared by now

        if this.player == other player and this.numPieces > 1
            return false, saying that this space is occupied and the player can't move there.


        */

        return;

    }

    public void removePiece()
    { 
        if (this.numPieces >= 1) {    
            this.numPieces--;
        } else {
            System.out.println("ERROR (removePiece): current piece count is " + Integer.toString(this.numPieces));
            System.exit(1);
        } 

        if (this.numPieces == 0) {
            this.player = Backgammon.NO_PLAYER;
        }
        
        return;
    }    

    public boolean isEmpty()
    {
        return this.player == Backgammon.NO_PLAYER;
    }

    // does this space have a lone piece and is it being occupied by the player opposite the player whose turn it is?
    public boolean hasExposedPiece(int curPlayer)
    {
        return (this.player != curPlayer && this.numPieces == 1);
    }

}
