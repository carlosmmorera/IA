package retoatasco;

import retoatasco.coordinate.*;

import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;


/**
 * @author Carlos Moreno Morera
 * @author Pablo Martín Huertas
 */

public class RetoAtascoBoard {

	private static final int BOARD_SIZE = 6;
	private static final int LORRY_SIZE = 3;
	private static final int CAR_SIZE = 2;
	private static final int NUM_CAR = 6;
	private static final int NUM_LORRY = 2;
	
	private static Square[] board;
	private static Coordinate exit;
	private static Coordinate[] vehicules;
	private static int numRows;
	private static int numColumns;
	
	public static Action FORWARD = new DynamicAction("Forward");

	public static Action BACKWARDS = new DynamicAction("Backwards");
	
	public RetoAtascoBoard() {
		board = new Square[BOARD_SIZE*BOARD_SIZE];
		exit = new Coordinate();
		vehicules = new Coordinate[NUM_CAR + NUM_LORRY];
		numRows = BOARD_SIZE;
		numColumns = BOARD_SIZE;
	}
	
	public RetoAtascoBoard(Square[] b, Coordinate c, int nRows, int nColumns,
			int numVehicules) {
		board = new Square[b.length];
		vehicules = new Coordinate[numVehicules];
		for (int i= 0; i < b.length; ++i) {
			board[i].setId(b[i].getId());
			board[i].setPiece(b[i].getPiece());
			if (b[i].getPiece() != Piece.EMPTY) {
				vehicules[b[i].getId()].setRow(getRowCoord(i));
				vehicules[b[i].getId()].setColumn(getColCoord(i));
			}
		}
		exit = new Coordinate(c);
	}
	
	public RetoAtascoBoard(RetoAtascoBoard copyBoard) {
		this(copyBoard.getBoard(), copyBoard.getExit(), copyBoard.getNumRows(),
				copyBoard.getNumColumns(), copyBoard.getNumVehicules());
	}
	
	public Square[] getBoard() {
		return board;
	}
	
	public Coordinate getExit() {
		return exit;
	}
	
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public int getNumVehicules() {
		return vehicules.length;
	}
	
	public Square getValueAt(Coordinate c) {
		return getValueAt(c.getRow(), c.getColumn());
	}

	public Coordinate getLocationOf(int id) {
		return vehicules[id];
	}
	
	public void moveVehiculeForward(int id) {
		Coordinate c = new Coordinate(getLocationOf(id));
		Square s = new Square(getValueAt(c));
		int vehiculeSize = s.getPiece() == Piece.CAR ? CAR_SIZE : LORRY_SIZE;
		Coordinate [] v = new Coordinate[vehiculeSize];
	}
/*
	public void moveGapRight() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int ypos = getYCoord(gapPos);
		if (!(ypos == 2)) {
			int valueOnRight = getValueAt(x, ypos + 1);
			setValue(x, ypos, valueOnRight);
			setValue(x, ypos + 1, 0);
		}
	}

	public void moveGapLeft() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int ypos = getYCoord(gapPos);
		if (!(ypos == 0)) {
			int valueOnLeft = getValueAt(x, ypos - 1);
			setValue(x, ypos, valueOnLeft);
			setValue(x, ypos - 1, 0);
		}
	}

	public void moveGapDown() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int y = getYCoord(gapPos);
		if (!(x == 2)) {
			int valueOnBottom = getValueAt(x + 1, y);
			setValue(x, y, valueOnBottom);
			setValue(x + 1, y, 0);
		}

	}

	public void moveGapUp() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int y = getYCoord(gapPos);
		if (!(x == 0)) {
			int valueOnTop = getValueAt(x - 1, y);
			setValue(x, y, valueOnTop);
			setValue(x - 1, y, 0);
		}
	}

	public List<XYLocation> getPositions() {
		ArrayList<XYLocation> retVal = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			int absPos = getPositionOf(i);
			XYLocation loc = new XYLocation(getXCoord(absPos),
					getYCoord(absPos));
			retVal.add(loc);

		}
		return retVal;
	}

	public void setBoard(List<XYLocation> locs) {
		int count = 0;
		for (XYLocation loc : locs) {
			this.setValue(loc.getXCoOrdinate(), loc.getYCoOrdinate(), count);
			count = count + 1;
		}
	}

	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		int absPos = getPositionOf(0);
		if (where.equals(LEFT))
			retVal = (getYCoord(absPos) != 0);
		else if (where.equals(RIGHT))
			retVal = (getYCoord(absPos) != 2);
		else if (where.equals(UP))
			retVal = (getXCoord(absPos) != 0);
		else if (where.equals(DOWN))
			retVal = (getXCoord(absPos) != 2);
		return retVal;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o != null && getClass() == o.getClass()) {
			RetoAtascoBoard aBoard = (RetoAtascoBoard) o;
			for (int i = 0; i < 8; i++) {
				if (this.getPositionOf(i) != aBoard.getPositionOf(i))
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < 8; i++) {
			int position = this.getPositionOf(i);
			result = 37 * result + position;
		}
		return result;
	}

	@Override
	public String toString() {
		return state[0] + " " + state[1] + " " + state[2] + "\n"
				+ state[3] + " " + state[4] + " " + state[5] + " " + "\n"
				+ state[6] + " " + state[7] + " " + state[8];
	}
*/

	//
	// PRIVATE METHODS
	//
	
	/**
	 * Note: The graphic representation maps x values on row numbers (x-axis in
	 * vertical direction).
	 */
	private int getRowCoord(int absPos) {
		return absPos / numRows;
	}

	/**
	 * Note: The graphic representation maps y values on column numbers (y-axis
	 * in horizontal direction).
	 */
	private int getColCoord(int absPos) {
		return absPos % numRows;
	}

	private int getAbsPosition(int x, int y) {
		return x * numRows + y;
	}

	private Square getValueAt(int x, int y) {
		return board[getAbsPosition(x, y)];
	}
	
/*
	private int getGapPosition() {
		return getPositionOf(0);
	}

	private int getPositionOf(int val) {
		for (int i = 0; i < 9; i++)
			if (state[i] == val)
				return i;
		return -1;
	}

	private void setValue(int x, int y, int val) {
		int absPos = getAbsPosition(x, y);
		state[absPos] = val;
	}
*/
}