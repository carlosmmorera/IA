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
 * @author Pablo Martín Huertas
 * @author Carlos Moreno Morera
 */
public class RetoAtascoFunctions {
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
	 
	public static ToDoubleFunction<Node<RetoAtascoBoard, AtascoAction>> 
		createAbsoluteDistanceHeuristicFunction(){
		return new AbsoluteDistanceHeuristicFunction();
	}
	
	public static ToDoubleFunction<Node<RetoAtascoBoard, AtascoAction>>
		createVehiclesPerLineHeuristicFunction(){
		return new VehiclesPerLineHeuristicFunction();
	}
	 
	private static class AbsoluteDistanceHeuristicFunction implements
		ToDoubleFunction<Node<RetoAtascoBoard, AtascoAction>> {
		public double applyAsDouble(Node<RetoAtascoBoard, AtascoAction> node) {
			RetoAtascoBoard board = (RetoAtascoBoard) node.getState();
			return getDistanceToTheExit(board);
		}
		 
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
	
	private static class VehiclesPerLineHeuristicFunction implements
		ToDoubleFunction<Node<RetoAtascoBoard, AtascoAction>>{
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