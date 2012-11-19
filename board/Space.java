package board;

public class Space {

    private int player = 0; 
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
}
