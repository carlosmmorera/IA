package retoatasco.board;

import retoatasco.Vehicle;
import retoatasco.coordinate.*;

/**
 * Class which implements the board of the traffic jam problem
 * @author Carlos Moreno Morera
 * @author Pablo Martín Huertas
 */
public class RetoAtascoBoard {
	public final int FORWARD_INDEX = 0;
	public final int BACKWARD_INDEX = 1;
	
	private Square[] board;
	private Coordinate exit;
	protected Coordinate[] vehicles;
	private int numRows;
	private int numColumns;
	
	/**
	 * Construct the board
	 * @param nRows number of rows of the board
	 * @param nColumns number of columns of the board
	 * @param numVehicles number of vehicles on the board
	 * @param c coordinate of the exit
	 */
	public RetoAtascoBoard(int nRows, int nColumns, int numVehicles, Coordinate c) {
		 board = new Square[nRows * nColumns];
		 exit = new Coordinate(c);
		 vehicles = new Coordinate[numVehicles];
		 numRows = nRows;
		 numColumns = nColumns;
		//Create an empty board
		for (int i = 0; i < numRows*numColumns; i++)
			board[i] = new Square();
		//Initialize the vehicles array
		for (int i = 0; i < numVehicles; i++)
			vehicles[i] = new Coordinate();
	}
	
	/**
	 * Construct the board
	 * @param b board
	 * @param c coordinate of the exit
	 * @param nRows number of rows of the board
	 * @param nColumns number of the board
	 * @param numVehicles number of vehicles on the board
	 */
	public RetoAtascoBoard(Square[] b, Coordinate c, int nRows, int nColumns,
			int numVehicles) {
		numRows = nRows;
		numColumns = nColumns;
		board = new Square[b.length];
		vehicles = new Coordinate[numVehicles];
		for (int i= 0; i < b.length; ++i) {
			board[i] = new Square();
			board[i].setId(b[i].getId());
			board[i].setPiece(b[i].getPiece());
			if (!b[i].isEmpty()) {
				vehicles[b[i].getId()] = new Coordinate();
				vehicles[b[i].getId()].setRow(getRowCoord(i));
				vehicles[b[i].getId()].setColumn(getColCoord(i));
			}
		}
		exit = new Coordinate(c);
	}
	
	/**
	 * Construct a board
	 * @param copyBoard board to copy
	 */
	public RetoAtascoBoard(RetoAtascoBoard copyBoard) {
		this(copyBoard.getBoard(), copyBoard.getExit(), copyBoard.getNumRows(),
				copyBoard.getNumColumns(), copyBoard.getNumVehicles());
	}
	
	/**
	 * @return the board
	 */
	public Square[] getBoard() {
		return board;
	}
	
	/**
	 * @return the coordinate of the exit
	 */
	public Coordinate getExit() {
		return exit;
	}
	
	/**
	 * @return the number of rows of the board
	 */
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * @return the number of columns of the board
	 */
	public int getNumColumns() {
		return numColumns;
	}
	
	/**
	 * @return the number of vehicles on the board
	 */
	public int getNumVehicles() {
		return vehicles.length;
	}
	
	/**
	 * Obtain the value at the square
	 * @param c coordinate of the square
	 * @return the value
	 */
	public Square getValueAt(Coordinate c) {
		return getValueAt(c.getRow(), c.getColumn());
	}
	
	/**
	 * Obtain a coordinate of the vehicle which identifier is id
	 * @param id identifier
	 * @return location of the vehicle
	 */
	public Coordinate getLocationOf(int id) {
		return vehicles[id];
	}
	
	/**
	 * Determines whether the square is empty
	 * @param c coordinate of the square
	 * @return whether it is or not
	 */
	public boolean isEmpty(Coordinate c) {
		return getValueAt(c).isEmpty();
	}
	
	/**
	 * Obtain a red car coordinate
	 * @return the coordinate
	 */
	public Coordinate getRedCarPosition() {
		return vehicles[0];
	}
	
	/**
	 * Move the vehicle with the given identifier forward
	 * @param id identifier
	 */
	public void moveVehicleForward(int id) {
		Coordinate c = getLocationOf(id);
		Vehicle v = new Vehicle(this, c);
		if (v.canMoveForward(this)) {
			setEmpty(v.getLastCoordinate());
			setValue(v.getFirstCoordinate().applyMovement(v.getDirectionForward()), 
					v.getProperties());
			vehicles[id] = v.getFirstCoordinate();
		}
	}
	
	/**
	 * Move the vehicle with the given identifier backwards
	 * @param id identifier
	 */
	public void moveVehicleBackwards(int id) {
		Coordinate c = getLocationOf(id);
		Vehicle v = new Vehicle(this, c);
		if (v.canMoveBackwards(this)) {
			setEmpty(v.getFirstCoordinate());
			setValue(v.getLastCoordinate().applyMovement(v.getDirectionBackwards()),
					v.getProperties());
			vehicles[id] = v.getLastCoordinate();
		}
	}
	
	/**
	 * Determines if the vehicle with the given identifier can move forward and backwards
	 * @param id identifier
	 * @return a boolean array which first element determines whether the vehicle can move
	 * forward or not and the second one determines if the vehicle can move backwards
	 * or not
	 */
	public boolean[] canMoveVehicle(int id){
		boolean[] movement = new boolean[2];
		Coordinate c = getLocationOf(id);
		Vehicle v = new Vehicle(this, c);
		movement[FORWARD_INDEX] = v.canMoveForward(this);
		movement[BACKWARD_INDEX] = v.canMoveBackwards(this);
		return movement;
	}
	
	/**
	 * Determines whether the given object is equal than the board
	 * @param o given object
	 * @return whether it is or not
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o != null && getClass() == o.getClass()) {
			RetoAtascoBoard aBoard = (RetoAtascoBoard) o;
			if (aBoard.getNumRows() != numRows || aBoard.getNumColumns() != numColumns
					|| aBoard.getExit().getRow() != exit.getRow() 
					|| aBoard.getExit().getColumn() != exit.getColumn())
				return false;
			Square[] b = aBoard.getBoard();
			for (int i = 0; i < numRows*numColumns; i++) {
				if (board[i].getId() != b[i].getId() || board[i].getPiece() != b[i].getPiece())
					return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Obtain a unique number from the board
	 * @return the hash code
	 */
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < vehicles.length; i++) {
			Vehicle v = new Vehicle(this, vehicles[i]);
			result = 37 * result + v.getFirstCoordinate().getRow()*3 + v.getFirstCoordinate().getColumn()*5
					  + v.getLastCoordinate().getRow()*7 + v.getLastCoordinate().getColumn()*11;
		}
		return result;
	}
	
	/**
	 * Obtain a string which describes the board
	 * @return the string
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numRows; i++) {
			sb.append("|");
			for (int j = 0; j < numColumns; j++) {
				if (this.getValueAt(i,j).isEmpty()) sb.append("   |");
				else if (this.getValueAt(i,j).isRedCar()) sb.append(" X |");
				else sb.append(" " + this.getValueAt(i,j).getId() + " |");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Set the given value on the board
	 * @param c coordinate where is going to be set the value
	 * @param s value to set
	 */
	protected void setValue(Coordinate c, Square s) {
		int absPos = getAbsPosition(c.getRow(), c.getColumn());
		board[absPos].setPiece(s.getPiece());
		board[absPos].setId(s.getId());
	}
	
	//
	// PRIVATE METHODS
	//
	/**
	 * Obtain the row on the board given the absolute position
	 * @param absPos given absolute position on the board array
	 * @return the row
	 */
	private int getRowCoord(int absPos) {
		return absPos / numColumns;
	}
	
	/**
	 * Obtain the column on the board given the absolute position
	 * @param absPos given absolute position on the board array
	 * @return the column
	 */
	private int getColCoord(int absPos) {
		return absPos % numColumns;
	}
	
	/**
	 * Obtain the position on the board array given the row and the column
	 * @param x row
	 * @param y column
	 * @return position on the array
	 */
	private int getAbsPosition(int x, int y) {
		return x * numColumns + y;
	}
	
	/**
	 * Obtain the value on the given square
	 * @param x row
	 * @param y column
	 * @return the value
	 */
	private Square getValueAt(int x, int y) {
		return board[getAbsPosition(x, y)];
	}
	
	/**
	 * Set empty the given position
	 * @param c given coordinate
	 */
	private void setEmpty(Coordinate c) {
		int absPos = getAbsPosition(c.getRow(), c.getColumn());
		board[absPos].setEmpty();
	}
}
