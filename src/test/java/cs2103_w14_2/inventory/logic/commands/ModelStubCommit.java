package cs2103_w14_2.inventory.logic.commands;

import cs2103_w14_2.inventory.model.ModelManager;

/**
 * Class to stub the commit method of Model, and adds a certain person to the model when commit is called.
 */
public class ModelStubCommit extends ModelManager {
    private boolean isCommitted = false;

    public boolean isCommitted() {
        return isCommitted;
    }

    @Override
    public void commit() {
        isCommitted = true;
    }
}

