package retoatasco;

public class Square {
	
	private static final int ID_RED_CAR = 0;
	
	private Piece piece;
	private int id;
	
	public Square() {
		piece = Piece.EMPTY;
		id = 0;
	}
	
	public Square(Piece p, int i) {
		piece = p;
		id = i;
	}
	
	public Square(Square copySquare) {
		this(copySquare.getPiece(), copySquare.getId());
	}
	
	public boolean isRedCar(Square s) {
		return s.getId() == ID_RED_CAR;
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
}
