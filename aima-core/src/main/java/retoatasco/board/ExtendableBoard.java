package retoatasco.board;

import retoatasco.coordinate.Coordinate;

/**
 * Class which allows the user to create their own board
 * @author Carlos Moreno Morera
 *
 */
public class ExtendableBoard extends RetoAtascoBoard {
	public final static int CAR_SIZE = 2;
	public final static int LORRY_SIZE = 3;
	
	/**
	 * Construct the board (the initial state of the problem)
	 * @param numRows number of rows of the board
	 * @param numCols number of columns of the board
	 * @param escape coordinate of the exit
	 * @param cars coordinates of the cars on the board
	 * @param lorries coordinates of the lorries on the board
	 */
	public ExtendableBoard(int numRows, int numCols, Coordinate escape, Coordinate[][] 
			cars, Coordinate[][] lorries){
		super(numRows, numCols, cars.length + lorries.length, escape);
		int index = 0;
		//Put the cars on board
		for (int i = 0; i < cars.length; i++) {
			for (int j = 0; j < CAR_SIZE; j++) 
				setValue(cars[i][j], new Square(Piece.CAR, index));
			//Include the car in vehicles array
			vehicles[index].setRow(cars[i][0].getRow());
			vehicles[index].setColumn(cars[i][0].getColumn());
			index++;
		}
		//Put the lorries on board
		for (int i = 0; i < lorries.length; i++) {
			for (int j = 0; j < LORRY_SIZE; j++) 
				setValue(lorries[i][j], new Square(Piece.LORRY, index));
			//Include the lorry in vehicles array
			vehicles[index].setRow(lorries[i][0].getRow());
			vehicles[index].setColumn(lorries[i][0].getColumn());
			index++;
		}
	}
}
