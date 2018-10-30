package retoatasco.coordinate;

import java.util.ArrayList;

public class Coordinate {
	private int row;
	private int column;
	
	public Coordinate() {
		row = 0;
		column = 0;
	}
	
	public Coordinate(int r, int c) {
		row = r;
		column = c;
	}
	
	public Coordinate(Coordinate copyCoord) {
		this(copyCoord.getRow(), copyCoord.getColumn());
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public ArrayList<Direction> getPossibleMovements(int numRows, int numCols){
		ArrayList<Direction> dir = new ArrayList<Direction>();
		if (row > 0)
			dir.add(Direction.NORTH);
		if (column < numCols - 1)
			dir.add(Direction.EAST);
		if (row < numRows - 1)
			dir.add(Direction.SOUTH);
		if (column > 0)
			dir.add(Direction.WEST);
		return dir;
	}
}
