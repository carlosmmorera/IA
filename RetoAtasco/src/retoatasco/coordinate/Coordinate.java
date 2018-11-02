package retoatasco.coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	
	public boolean canMove(Direction dir, int numRows, int numCols) {
		if (dir == Direction.NORTH)
			return row > 0;
		else if (dir == Direction.EAST)
			return column < numCols - 1;
		else if (dir == Direction.SOUTH)
			return row < numRows - 1;
		else
			return column > 0;
	}
	
	public ArrayList<Direction> getPossibleMovements(int numRows, int numCols){
		ArrayList<Direction> dir = new ArrayList<Direction>();
		if (canMove(Direction.NORTH, numRows, numCols))
			dir.add(Direction.NORTH);
		if (canMove(Direction.EAST, numRows, numCols))
			dir.add(Direction.EAST);
		if (canMove(Direction.SOUTH, numRows, numCols))
			dir.add(Direction.SOUTH);
		if (canMove(Direction.WEST, numRows, numCols))
			dir.add(Direction.WEST);
		return dir;
	}
	
	public Coordinate applyMovement(Direction dir) {
		Coordinate c = new Coordinate(this);
		if (dir == Direction.NORTH)
			c.setRow(row - 1);
		else if (dir == Direction.EAST)
			c.setColumn(column + 1);
		else if (dir == Direction.SOUTH)
			c.setRow(row + 1);
		else
			c.setColumn(column - 1);
		return c;
	}
	
	public Direction getDirectionOfMovement(Coordinate c) {
		if (row - c.getRow() > 0)
			return Direction.NORTH;
		else if (c.getColumn() - column > 0)
			return Direction.EAST;
		else if (c.getRow() - row > 0)
			return Direction.SOUTH;
		else
			return Direction.WEST;
	}
	
	public static void orderCoordinates(ArrayList<Coordinate> l){
		Collections.sort(l, new Comparator<Coordinate>() {
			@Override
			public int compare(Coordinate c1, Coordinate c2) {
				return c1.getRow() != c2.getRow() ? 
						new Integer(c1.getRow()).compareTo(new Integer(c2.getRow()))
						: new Integer(c2.getColumn()).compareTo(new Integer(c1.getColumn()));
			}
		});
	}
}
