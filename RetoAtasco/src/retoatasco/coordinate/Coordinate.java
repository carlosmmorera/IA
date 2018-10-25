package retoatasco.coordinate;

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
}
