package seedu.address.model.version;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.util.Copyable;

/**
 * An implementation of Version with a linear list.
 * @param <T> a class that can produce independent copies
 */
public class LinearHistory<T extends Copyable<T>> implements Version<T> {
    private T currentState;
    private List<T> history = new ArrayList<>();
    private int statePointer;

    /**
     * Creates a {@code LinearHistory} with a starting state.
     * @param object the initial state
     */
    public LinearHistory(T object) {
        requireNonNull(object);

        history.add(object);
        currentState = object.copy();
        statePointer = 0;
    }

    @Override
    public void commit() {
        assert currentState != null;

        history = history.subList(0, statePointer + 1);
        history.add(getCurrentState().copy());
        statePointer++;

        assert history.contains(currentState);
    }

    @Override
    public void undo() throws StateNotFoundException {
        try {
            T previousState = history.get(statePointer - 1);
            statePointer--;

            assert previousState != null;
            currentState = previousState.copy();
        } catch (IndexOutOfBoundsException e) {
            throw new StateNotFoundException();
        }

        assert currentState != null;
    }

    @Override
    public void redo() throws StateNotFoundException {
        try {
            T nextState = history.get(statePointer + 1);
            statePointer++;

            assert nextState != null;
            currentState = nextState.copy();
        } catch (IndexOutOfBoundsException e) {
            throw new StateNotFoundException();
        }

        assert currentState != null;
    }

    @Override
    public T getCurrentState() {
        assert currentState != null;
        return currentState;
    }
}
