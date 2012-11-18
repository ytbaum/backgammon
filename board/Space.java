package board;

public class Space {

    private char pieceType = ' '; 
    private int numPieces = 0;

    public char getPieceType()
    {
        return this.pieceType;
    }

    public void setPieceType(char newPieceType) {
        this.pieceType = newPieceType;
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
        return this.pieceType + " " + Integer.toString(this.numPieces);
    }
}
