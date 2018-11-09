package retoatasco.examples;

import retoatasco.*;
import retoatasco.coordinate.Coordinate;

public class BasicTrafficJam extends RetoAtascoBoard {
	private static final int BOARD_SIZE = 6;
	private static final int NUM_CAR = 6;
	private static final int NUM_LORRY = 2;
	
	private static final Coordinate escape = new Coordinate(2, 5);
	
	//The red car must be the first one
	private static final Coordinate[][] cars = {{new Coordinate(2, 2), new
		Coordinate(2, 3)}, {new Coordinate(0, 4), new Coordinate(1, 4)}, {new
		Coordinate(1, 2), new Coordinate(1, 3)}, {new Coordinate(2, 4), new
		Coordinate(3, 4)}, {new Coordinate(4, 3), new Coordinate(5, 3)}, {new 
		Coordinate(4,4), new Coordinate(4, 5)}};
	
	private static final Coordinate[][] lorries = {{new Coordinate (0, 5), new 
		Coordinate(1, 5), new Coordinate(2,5)}, {new Coordinate(3, 2), new 
		Coordinate(4,2), new Coordinate(5, 2)}};
	
	public BasicTrafficJam() {
		board = new Square[BOARD_SIZE*BOARD_SIZE];
		exit = new Coordinate(escape);
		numRows = BOARD_SIZE;
		numColumns = BOARD_SIZE;
		
		//Create an empty board
		for (int i = 0; i < BOARD_SIZE*BOARD_SIZE; i++)
			board[i].setEmpty();
		
		vehicles = new Coordinate[NUM_CAR + NUM_LORRY];
		int index = 0;
		
		//Put the cars on board
		for (int i = 0; i < NUM_CAR; i++) {
			for (int j = 0; j < CAR_SIZE; j++) 
				setValue(cars[i][j], new Square(Piece.CAR, index));
			//Include the car in vehicles array
			vehicles[index].setRow(cars[i][0].getRow());
			vehicles[index].setColumn(cars[i][0].getColumn());
			index++;
		}
		
		//Put the lorries on board
		for (int i = 0; i < NUM_LORRY; i++) {
			for (int j = 0; j < LORRY_SIZE; j++) 
				setValue(lorries[i][j], new Square(Piece.LORRY, index));
			//Include the lorry in vehicles array
			vehicles[index].setRow(lorries[i][0].getRow());
			vehicles[index].setColumn(lorries[i][0].getColumn());
			index++;
		}
	}
}