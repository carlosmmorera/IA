package retoatasco;

import aima.core.agent.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Pablo Martín Huertas
 * @author Carlos Moreno Morera
 */
public class RetoAtascoFunctions {
	public static List<Action> getActions(RetoAtascoBoard state) {
		int numVehicles = state.getNumVehicles();
		List<Action> actions = new ArrayList<>(2*numVehicles);
		for (int i = 0; i < numVehicles; i++) {
			boolean[] movements = state.canMoveVehicle(i);
			if (movements[state.FORWARD_INDEX])
				actions.add(new AtascoAction(AtascoAction.FORWARD, i));
			if (movements[state.BACKWARD_INDEX])
				actions.add(new AtascoAction(AtascoAction.BACKWARDS, i));
		}
		return actions;
	}
	
	public static RetoAtascoBoard getResult(RetoAtascoBoard state, AtascoAction action) {
		RetoAtascoBoard result = new RetoAtascoBoard(state);
		
		if (Objects.equals(action.getName(), AtascoAction.FORWARD))
			result.moveVehicleForward(action.getId());
		else if (Objects.equals(action.getName(), AtascoAction.BACKWARDS))
			result.moveVehicleBackwards(action.getId());
		// if action is not understood or is a NoOp
        // the result will be the current state.
        return result;
	}
	
	 public static boolean testGoal(RetoAtascoBoard state) {
		 return state.getValueAt(state.getExit()).isRedCar();
	 }
/*
	public static ToDoubleFunction<Node<RetoAtascoBoard, Action>> createManhattanHeuristicFunction() {
		return new ManhattanHeuristicFunction();
	}

	public static ToDoubleFunction<Node<RetoAtascoBoard, Action>> createMisplacedTileHeuristicFunction() {
		return new MisplacedTileHeuristicFunction();
	}
	*/

	/**
	 * @author Carlos Moreno Morera
	 * @author Pablo Martín Huertas
	 */
	
	/*
    private static class ManhattanHeuristicFunction implements ToDoubleFunction<Node<RetoAtascoBoard, Action>> {

        @Override
        public double applyAsDouble(Node<RetoAtascoBoard, Action> node) {
            RetoAtascoBoard board = node.getState();
            int retVal = 0;
            for (int i = 1; i < 9; i++) {
                XYLocation loc = board.getLocationOf(i);
                retVal += evaluateManhattanDistanceOf(i, loc);
            }
            return retVal;
        }

        private int evaluateManhattanDistanceOf(int i, XYLocation loc) {
            int retVal = -1;
            int xpos = loc.getXCoOrdinate();
            int ypos = loc.getYCoOrdinate();
            switch (i) {

            case 1:
                retVal = Math.abs(xpos - 0) + Math.abs(ypos - 1);
                break;
            case 2:
                retVal = Math.abs(xpos - 0) + Math.abs(ypos - 2);
                break;
            case 3:
                retVal = Math.abs(xpos - 1) + Math.abs(ypos - 0);
                break;
            case 4:
                retVal = Math.abs(xpos - 1) + Math.abs(ypos - 1);
                break;
            case 5:
                retVal = Math.abs(xpos - 1) + Math.abs(ypos - 2);
                break;
            case 6:
                retVal = Math.abs(xpos - 2) + Math.abs(ypos - 0);
                break;
            case 7:
                retVal = Math.abs(xpos - 2) + Math.abs(ypos - 1);
                break;
            case 8:
                retVal = Math.abs(xpos - 2) + Math.abs(ypos - 2);
                break;

            }
            return retVal;
        }
    }
*/
	/**
	 * @author Carlos Moreno Morera
	 * @author Pablo Martín Huertas
	 */
	/*
    private  static class MisplacedTileHeuristicFunction implements ToDoubleFunction<Node<RetoAtascoBoard, Action>> {

        public double applyAsDouble(Node<RetoAtascoBoard, Action> node) {
            RetoAtascoBoard board = (RetoAtascoBoard) node.getState();
            return getNumberOfMisplacedTiles(board);
        }

        private int getNumberOfMisplacedTiles(RetoAtascoBoard board) {
            int numberOfMisplacedTiles = 0;
            if (!(board.getLocationOf(0).equals(new XYLocation(0, 0)))) {
                numberOfMisplacedTiles++;
            }
            if (!(board.getLocationOf(1).equals(new XYLocation(0, 1)))) {
                numberOfMisplacedTiles++;
            }
            if (!(board.getLocationOf(2).equals(new XYLocation(0, 2)))) {
                numberOfMisplacedTiles++;
            }
            if (!(board.getLocationOf(3).equals(new XYLocation(1, 0)))) {
                numberOfMisplacedTiles++;
            }
            if (!(board.getLocationOf(4).equals(new XYLocation(1, 1)))) {
                numberOfMisplacedTiles++;
            }
            if (!(board.getLocationOf(5).equals(new XYLocation(1, 2)))) {
                numberOfMisplacedTiles++;
            }
            if (!(board.getLocationOf(6).equals(new XYLocation(2, 0)))) {
                numberOfMisplacedTiles++;
            }
            if (!(board.getLocationOf(7).equals(new XYLocation(2, 1)))) {
                numberOfMisplacedTiles++;
            }
            if (!(board.getLocationOf(8).equals(new XYLocation(2, 2)))) {
                numberOfMisplacedTiles++;
            }
            // Subtract the gap position from the # of misplaced tiles
            // as its not actually a tile (see issue 73).
            if (numberOfMisplacedTiles > 0)
                numberOfMisplacedTiles--;
            return numberOfMisplacedTiles;
        }
    }
    */
}