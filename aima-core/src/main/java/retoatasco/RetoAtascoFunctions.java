package retoatasco;

import aima.core.search.framework.Node;
import retoatasco.board.RetoAtascoBoard;
import retoatasco.coordinate.Coordinate;
import retoatasco.coordinate.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleFunction;

/**
 * Class where the necessary functions for changing the state are defined
 * @author Carlos Moreno Morera
 */
public class RetoAtascoFunctions {
	/**
	 * Get all the possible actions that we can apply to the given state
	 * @param state
	 * @return list of possible actions
	 */
	public static List<AtascoAction> getActions(RetoAtascoBoard state) {
		int numVehicles = state.getNumVehicles();
		List<AtascoAction> actions = new ArrayList<>(2*numVehicles);
		for (int i = 0; i < numVehicles; i++) {
			boolean[] movements = state.canMoveVehicle(i);
			if (movements[state.FORWARD_INDEX])
				actions.add(new AtascoAction(AtascoAction.FORWARD, i));
			if (movements[state.BACKWARD_INDEX])
				actions.add(new AtascoAction(AtascoAction.BACKWARDS, i));
		}
		return actions;
	}
	
	/**
	 * Obtains the result of applying to the given state the given action
	 * @param state
	 * @param action
	 * @return the board which is result of that
	 */
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
	
	/**
	 * Evaluate if the given state is the final one.
	 * @param state
	 * @return whether or not is the final state.
	 */
	public static boolean testGoal(RetoAtascoBoard state) {
		//Determines whether the red car is at the exit
		return state.getValueAt(state.getExit()).isRedCar();
	}
	
	/**
	 * Create an Absolute Distance Heuristic Function class
	 * @return the created class
	 */
	public static ToDoubleFunction<Node<RetoAtascoBoard, AtascoAction>> 
		createAbsoluteDistanceHeuristicFunction(){
		return new AbsoluteDistanceHeuristicFunction();
	}
	
	/**
	 * Create an Vehicles Per Line Heuristic Function class
	 * @return the created class
	 */
	public static ToDoubleFunction<Node<RetoAtascoBoard, AtascoAction>>
		createVehiclesPerLineHeuristicFunction(){
		return new VehiclesPerLineHeuristicFunction();
	}
	
	/**
	 * Class which implements the Absolute Distance Heuristic Function
	 * @author Carlos Moreno Morera
	 */
	private static class AbsoluteDistanceHeuristicFunction implements
		ToDoubleFunction<Node<RetoAtascoBoard, AtascoAction>> {
		/**
		 * Obtains the result of applying the heuristic function to the node
		 * @param node
		 * @return the result of the heuristic
		 */
		public double applyAsDouble(Node<RetoAtascoBoard, AtascoAction> node) {
			RetoAtascoBoard board = (RetoAtascoBoard) node.getState();
			return getDistanceToTheExit(board);
		}
		
		/**
		 * Obtain the distance between the red car and the exit
		 * @param b board
		 * @return the distance
		 */
		private int getDistanceToTheExit(RetoAtascoBoard b) {
			Coordinate pos = new Coordinate(b.getExit());
			Direction dir = pos.getDirectionOfMovement(b.getRedCarPosition());
			int distance = 0;
			while(!b.getValueAt(pos).isRedCar()) {
				distance++;
				pos = pos.applyMovement(dir);
			}
			return distance;
		}
	}
	
	/**
	 * Class which implements the Vehicles Per Line Heuristic Function
	 * @author Carlos Moreno Morera
	 *
	 */
	private static class VehiclesPerLineHeuristicFunction implements
		ToDoubleFunction<Node<RetoAtascoBoard, AtascoAction>>{
		/**
		 * Obtains the result of applying the heuristic function to the node
		 * @param node
		 * @return the value of the heuristic function on the node
		 */
		public double applyAsDouble(Node<RetoAtascoBoard, AtascoAction> node) {
			RetoAtascoBoard b = (RetoAtascoBoard) node.getState();
			Coordinate pos = new Coordinate(b.getExit());
			Direction dir = pos.getDirectionOfMovement(b.getRedCarPosition());
			Direction perpendicular = Coordinate.perpendicularDirection(dir);
			int hValue = 0;
			while(!b.getValueAt(pos).isRedCar()) {
				hValue += numOfVehiclesInLine(b, pos, perpendicular);
				pos = pos.applyMovement(dir);
			}
			return hValue;
		}
		
		/**
		 * Calculate the number of minimum vehicles that we have to move to put the red
		 * car on that line
		 * @param b board
		 * @param c coordinate where we want to move the red car
		 * @param d direction of the line (perpendicular to the red car orientation)
		 * @return the number
		 */
		private int numOfVehiclesInLine(RetoAtascoBoard b, Coordinate c, Direction d) {
			//Calculate the number of different vehicles in direction d
			int numVehicles = 1;
			Coordinate aux = new Coordinate(c);
			int lastId = b.getValueAt(aux).getId();
			if (!b.isEmpty(aux))
				numVehicles++;
			while (!b.isEmpty(aux) && aux.canMove(d, b.getNumRows(), b.getNumColumns())) {
				aux = aux.applyMovement(d);
				if (!b.isEmpty(aux) && b.getValueAt(aux).getId() != lastId) {
					numVehicles++;
					lastId = b.getValueAt(aux).getId();
				}
			}
			
			//Calculate the number of different vehicles in the opposite direction
			int numVehiclesOp = 1;
			d = Coordinate.oppositeDirection(d);
			lastId = b.getValueAt(c).getId();
			if (!b.isEmpty(aux))
				numVehiclesOp++;
			while (!b.isEmpty(c) && c.canMove(d, b.getNumRows(), b.getNumColumns())) {
				c = c.applyMovement(d);
				if (!b.isEmpty(c) && b.getValueAt(c).getId() != lastId) {
					numVehiclesOp++;
					lastId = b.getValueAt(c).getId();
				}
			}
			//At least we have to move the minimun number of vehicle in one direction
			return Math.min(numVehicles, numVehiclesOp);
		}
	}
}