package retoatasco.examples;

import retoatasco.*;
import retoatasco.coordinate.Coordinate;

public class BasicTrafficJam extends ExtendableBoard {
	private static final int BOARD_SIZE = 6;
	
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
		super(BOARD_SIZE, BOARD_SIZE, escape, cars, lorries);
	}
}