package retoatasco;

import java.util.ArrayList;

import retoatasco.board.Piece;
import retoatasco.board.RetoAtascoBoard;
import retoatasco.board.Square;
import retoatasco.coordinate.*;

/**
 * Class which implements the necessary function for each vehicle
 * @author Carlos Moreno Morera
 *
 */
public class Vehicle {
	private static final int LORRY_SIZE = 3;
	private static final int CAR_SIZE = 2;

	private ArrayList<Coordinate> position;
	private Square properties;
	private int size;
	private Orientation orient;
	
	/**
	 * Construct the vehicle which is on that square
	 * @param b board
	 * @param c coordinate of the square
	 */
	public Vehicle(RetoAtascoBoard b, Coordinate c) {
		properties = new Square(b.getValueAt(c));
		size = properties.getPiece() == Piece.CAR ? CAR_SIZE : LORRY_SIZE;
		position = new ArrayList<Coordinate>();
		ArrayList<Direction> dir = c.getPossibleMovements(b.getNumRows(), b.getNumColumns());
		
		for (Direction d: dir) {
			Coordinate cor = c.applyMovement(d);
			//If in that square is the vehicle we add it
			if (b.getValueAt(cor).getId() == properties.getId())
				position.add(cor);
		}
		position.add(c);
		Direction dirMov = c.getDirectionOfMovement(position.get(0));
		orient = dirMov == Direction.NORTH || dirMov == Direction.SOUTH ? 
				Orientation.VERTICAL: Orientation.HORIZONTAL;
		if (position.size() < size)
			position.add(position.get(0).applyMovement(dirMov));
		Coordinate.orderCoordinates(position);
	}
	
	/**
	 * Determines if the vehicle can move forward
	 * @param b board
	 * @return whether it can or not
	 */
	public boolean canMoveForward(RetoAtascoBoard b) {
		Direction d;
		if (orient == Orientation.VERTICAL)
			d = Direction.NORTH;
		else
			d = Direction.EAST;
		return position.get(0).canMove(d, b.getNumRows(), b.getNumColumns())
				&& b.isEmpty(position.get(0).applyMovement(d));
	}
	
	/**
	 * Determines if the vehicle can move backwards
	 * @param b board
	 * @return whether it can or not
	 */
	public boolean canMoveBackwards(RetoAtascoBoard b) {
		Direction d;
		if (orient == Orientation.VERTICAL)
			d = Direction.SOUTH;
		else
			d = Direction.WEST;
		return position.get(size - 1).canMove(d, b.getNumRows(), b.getNumColumns())
				&& b.isEmpty(position.get(size - 1).applyMovement(d));
	}
	
	/**
	 * Obtain the first coordinate (the top one or the coordinate with the second 
	 * coordinate bigger then the others) of the vehicle
	 * @return the first coordinate
	 */
	public Coordinate getFirstCoordinate() {
		return position.get(0);
	}
	
	/**
	 * Obtain the last coordinate (the bottom one or the coordinate with the second 
	 * coordinate smaller then the others) of the vehicle
	 * @return the last coordinate
	 */
	public Coordinate getLastCoordinate() {
		return position.get(size - 1);
	}
	
	/**
	 * Obtain the direction to move the vehicle forward (it depends on its orientation)
	 * @return the direction
	 */
	public Direction getDirectionForward() {
		return orient == Orientation.VERTICAL ? Direction.NORTH : Direction.EAST;
	}
	
	/**
	 * Obtain the direction to move the vehicle backwards (it depends on its orientation)
	 * @return the direction
	 */
	public Direction getDirectionBackwards() {
		return orient == Orientation.VERTICAL ? Direction.SOUTH : Direction.WEST;
	}
	
	/**
	 * Get the properties of the vehicle (the identifier and the type of piece)
	 * @return the properties
	 */
	public Square getProperties() {
		return properties;
	}
}
