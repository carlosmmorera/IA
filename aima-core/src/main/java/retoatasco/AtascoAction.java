package retoatasco;

import aima.core.agent.impl.DynamicAction;

/**
 * Class which describes the actions that we can apply to the state.
 * @author Carlos Moreno Morera
 */
public class AtascoAction extends DynamicAction {
	public static final String FORWARD = "Forward";
	public static final String BACKWARDS = "Backwards";
	public static final String VEHICLE_ID = "identification";
	
	/**
	 * Constructor
	 * @param name direction of the movement of the vehicle
	 * @param id identifier of the vehicle which is going to be moved
	 */
	public AtascoAction(String name, int id) {
		super(name);
		setAttribute(VEHICLE_ID, id);
	}
	
	/**
	 * Obtain the identifier of the vehicle
	 * @return id
	 */
	public int getId() {
		return (int) getAttribute(VEHICLE_ID);
	}
}
