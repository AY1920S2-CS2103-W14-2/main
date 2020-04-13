package cs2103_w14_2.inventory.model.version;

/**
 * Signals that the operation is trying to go to a state that does not exist.
 */
public class StateNotFoundException extends RuntimeException {
    public StateNotFoundException() {
        super("Attempting to go to a state that does not exist");
    }
}
