package retoatasco.board;

/**
 * Class which describes each square of the board
 * @author Carlos Moreno Morera
 * @author Pablo Martín Huertas
 */
public class Square {
	
	private static final int ID_RED_CAR = 0;
	private static final int ID_EMPTY = -1;
	
	private Piece piece;
	private int id;
	
	/**
	 * Constructor
	 */
	public Square() {
		piece = Piece.EMPTY;
		id = ID_EMPTY;
	}
	
	/**
	 * Constructor given the piece and the identifier
	 * @param p piece
	 * @param i identifier
	 */
	public Square(Piece p, int i) {
		piece = p;
		id = i;
	}
	
	/**
	 * Constructor
	 * @param copySquare square to copy
	 */
	public Square(Square copySquare) {
		this(copySquare.getPiece(), copySquare.getId());
	}
	
	/**
	 * Determines whether the red car is on the square
	 * @return wheter it is or not
	 */
	public boolean isRedCar() {
		return id == ID_RED_CAR;
	}
	
	/**
	 * Obtain the piece which is on the square
	 * @return the piece
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * Set the given value of the piece on the square
	 * @param piece
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * Obtain the identifier of the vehicle on the square
	 * @return identifier
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set the given identifier of the vehicle on the square
	 * @param id identifier
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Determines if the square is empty
	 * @return whether the square is empty or not
	 */
	public boolean isEmpty() {
		return piece == Piece.EMPTY;
	}
	
	/**
	 * Set the square with the value empty
	 */
	public void setEmpty() {
		piece = Piece.EMPTY;
		id = ID_EMPTY;
	}
}
