package retoatasco;

import java.util.ArrayList;
import retoatasco.coordinate.*;

public class Vehicle {
	private static final int LORRY_SIZE = 3;
	private static final int CAR_SIZE = 2;
	
	private ArrayList<Coordinate> position;
	private Square properties;
	private int size;
	
	//Construct the vehicle which is on that square
	public Vehicle(RetoAtascoBoard board, Coordinate c) {
		properties = new Square(board.getValueAt(c));
		int size = properties.getPiece() == Piece.CAR ? CAR_SIZE : LORRY_SIZE;
		ArrayList<Direction> dir = c.getPossibleMovements(board.getNumRows(), board.getNumColumns());
	}
}
