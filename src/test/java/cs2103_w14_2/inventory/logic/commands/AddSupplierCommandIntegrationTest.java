package cs2103_w14_2.inventory.logic.commands;

import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.testutil.SupplierBuilder;

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
