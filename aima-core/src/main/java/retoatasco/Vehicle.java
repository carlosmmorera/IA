package retoatasco;

import java.util.ArrayList;

import retoatasco.board.Piece;
import retoatasco.board.RetoAtascoBoard;
import retoatasco.board.Square;
import retoatasco.coordinate.*;

public class Vehicle {
	private static final int LORRY_SIZE = 3;
	private static final int CAR_SIZE = 2;
	
	private ArrayList<Coordinate> position;
	private Square properties;
	private int size;
	private Orientation orient;
	
	//Construct the vehicle which is on that square
	public Vehicle(RetoAtascoBoard b, Coordinate c) {
		properties = new Square(b.getValueAt(c));
		size = properties.getPiece() == Piece.CAR ? CAR_SIZE : LORRY_SIZE;
		position = new ArrayList<Coordinate>();
		ArrayList<Direction> dir = c.getPossibleMovements(b.getNumRows(), b.getNumColumns());
		
		for (Direction d: dir) {
			Coordinate cor = c.applyMovement(d);
			if (b.getValueAt(cor).getId() == properties.getId())
				position.add(cor);
		}
		position.add(c);
		Direction dirMov = c.getDirectionOfMovement(position.get(0));
		orient = dirMov == Direction.NORTH || dirMov == Direction.SOUTH ? Orientation.VERTICAL:
				Orientation.HORIZONTAL;
		if (position.size() < size)
			position.add(position.get(0).applyMovement(dirMov));
		Coordinate.orderCoordinates(position);
	}
	
	public boolean canMoveForward(RetoAtascoBoard b) {
		Direction d;
		if (orient == Orientation.VERTICAL)
			d = Direction.NORTH;
		else
			d = Direction.EAST;
		return position.get(0).canMove(d, b.getNumRows(), b.getNumColumns())
				&& b.isEmpty(position.get(0).applyMovement(d));
	}
	
	public boolean canMoveBackwards(RetoAtascoBoard b) {
		Direction d;
		if (orient == Orientation.VERTICAL)
			d = Direction.SOUTH;
		else
			d = Direction.WEST;
		return position.get(size - 1).canMove(d, b.getNumRows(), b.getNumColumns())
				&& b.isEmpty(position.get(size - 1).applyMovement(d));
	}
	
	public Coordinate getFirstCoordinate() {
		return position.get(0);
	}
	
	public Coordinate getLastCoordinate() {
		return position.get(size - 1);
	}
	
	public Direction getDirectionForward() {
		return orient == Orientation.VERTICAL ? Direction.NORTH : Direction.EAST;
	}
	
	public Direction getDirectionBackwards() {
		return orient == Orientation.VERTICAL ? Direction.SOUTH : Direction.WEST;
	}
	
	public Square getProperties() {
		return properties;
	}
}
