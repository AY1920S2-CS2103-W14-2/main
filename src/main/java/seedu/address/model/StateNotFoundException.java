package seedu.address.model;

/**
 * Signals that the operation is trying to go to a state that does not exist.
 */
public class StateNotFoundException extends RuntimeException {
    public StateNotFoundException() {
        super("Attempting to go to a state that does not exist");
    }
}
