package retoatasco;

import aima.core.agent.impl.DynamicAction;

/**
 * @author Carlos Moreno Morera
 * @author Pablo Martín Huertas
 */

public class AtascoAction extends DynamicAction {
	public static final String FORWARD = "Forward";
	public static final String BACKWARDS = "Backwards";
	public static final String VEHICLE_ID = "identification";

	public AtascoAction(String name, int id) {
		super(name);
		setAttribute(VEHICLE_ID, id);
	}
	
	public int getId() {
		return (int) getAttribute(VEHICLE_ID);
	}
}
