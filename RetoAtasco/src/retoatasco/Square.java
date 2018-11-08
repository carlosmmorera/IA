package retoatasco;

public class Square {
	
	private static final int ID_RED_CAR = 0;
	private static final int ID_EMPTY = -1;
	
	private Piece piece;
	private int id;
	
	public Square() {
		piece = Piece.EMPTY;
		id = ID_EMPTY;
	}
	
	public Square(Piece p, int i) {
		piece = p;
		id = i;
	}
	
	public Square(Square copySquare) {
		this(copySquare.getPiece(), copySquare.getId());
	}
	
	public boolean isRedCar() {
		return id == ID_RED_CAR;
	}
	
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isEmpty() {
		return piece == Piece.EMPTY;
	}
	public void setEmpty() {
		piece = Piece.EMPTY;
		id = ID_EMPTY;
	}
}
