package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoods.getTypicalInventory;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTransactions.getTypicalTransactionHistory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.SupplierBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddSupplierCommand}.
 */
public class AddSupplierCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());
    }

    @Test
    public void execute_newSupplier_success() {
        Supplier validSupplier = new SupplierBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getInventory(),
                model.getTransactionHistory(), new UserPrefs());
        expectedModel.addSupplier(validSupplier);

        assertCommandSuccess(new AddSupplierCommand(validSupplier), model,
                String.format(AddSupplierCommand.MESSAGE_SUCCESS, validSupplier), expectedModel);
    }

    @Test
    public void execute_duplicateSupplier_throwsCommandException() {
        Supplier supplierInList = model.getAddressBook().getReadOnlyList().get(0);
        assertCommandFailure(new AddSupplierCommand(supplierInList), model,
                AddSupplierCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

}
