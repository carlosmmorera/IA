package retoatasco.coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class which manage the coordinates on the board
 * @author Carlos Moreno Morera
 * @author Pablo Martín Huertas
 */
public class Coordinate {
	private int row;
	private int column;
	
	/**
	 * Constructor
	 */
	public Coordinate() {
		row = 0;
		column = 0;
	}
	
	/**
	 * Constructor with a given row and a given column
	 * @param r row
	 * @param c column
	 */
	public Coordinate(int r, int c) {
		row = r;
		column = c;
	}
	
	/**
	 * Constructor with a given coordinate
	 * @param copyCoord coordinate to copy
	 */
	public Coordinate(Coordinate copyCoord) {
		this(copyCoord.getRow(), copyCoord.getColumn());
	}
	
	/**
	 * Obtain the row of the coordinate
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Set the given value of the row
	 * @param row value to set
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Obtain the column of the coordinate
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Set the given value of the column
	 * @param column value to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * Determines whether there is a possible coordinate on the given direction
	 * @param dir direction
	 * @param numRows number of rows of the board
	 * @param numCols number of columns of the board
	 * @return whether there is or not
	 */
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
	
	/**
	 * Obtain the possible movements from the coordinate
	 * @param numRows number of rows of the board
	 * @param numCols number of columns of the board
	 * @return array list of possible directions of movement
	 */
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
	
	/**
	 * Apply the movement with the given direction
	 * @param dir direction
	 * @return the coordinate result of the movement
	 */
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
	
	/**
	 * Obtain the direction if you apply a movement from the coordinate to the given one
	 * @param c given coordinate
	 * @return the direction
	 */
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
	
	/**
	 * Given a direction, calculate the opposite direction
	 * @param d direction
	 * @return opposite direction
	 */
	public static Direction oppositeDirection(Direction d) {
		Direction result;
		if (d == Direction.NORTH)
			result = Direction.SOUTH;
		else if (d == Direction.EAST)
			result = Direction.WEST;
		else if (d == Direction.SOUTH)
			result = Direction.NORTH;
		else
			result = Direction.EAST;
		return result;
	}
	
	/**
	 * Given a direction, calculate a perpendicular direction
	 * @param d direction
	 * @return perpendicular direction
	 */
	public static Direction perpendicularDirection(Direction d) {
		Direction result;
		if (d == Direction.NORTH || d == Direction.SOUTH)
			result = Direction.EAST;
		else
			result = Direction.NORTH;
		return result;
	}
	
	/**
	 * Order an array list of coordinate
	 * @param l array list to order
	 */
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
