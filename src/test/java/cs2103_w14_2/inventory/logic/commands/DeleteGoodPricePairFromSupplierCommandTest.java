package cs2103_w14_2.inventory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandFailure;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs2103_w14_2.inventory.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static cs2103_w14_2.inventory.testutil.TypicalGoods.getTypicalInventory;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static cs2103_w14_2.inventory.testutil.TypicalIndexes.INDEX_SECOND_SUPPLIER;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.ALICE;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.AMY;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.CARL;
import static cs2103_w14_2.inventory.testutil.TypicalSuppliers.getTypicalAddressBook;
import static cs2103_w14_2.inventory.testutil.TypicalTransactions.getTypicalTransactionHistory;

import java.util.HashSet;
import java.util.Set;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.AddressBook;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import org.junit.jupiter.api.Test;

import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.testutil.DeleteSupplierGoodNameBuilder;
import cs2103_w14_2.inventory.testutil.GoodBuilder;
import cs2103_w14_2.inventory.testutil.SupplierBuilder;

/**
 * Contains integration tests (interaction with the Model,
 * UndoCommand and RedoCommand) and unit tests for DeleteGoodPricePairFromSupplierCommand.
 */
public class DeleteGoodPricePairFromSupplierCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInventory(),
            getTypicalTransactionHistory(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Set<GoodName> goodNames = new HashSet<>();
        goodNames.add(ALICE.getOffers().iterator().next().getGoodName());

        DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName descriptor =
                new DeleteSupplierGoodNameBuilder().withGoodNames(goodNames).build();

        DeleteGoodPricePairFromSupplierCommand deleteGoodPricePairFromSupplierCommand =
                new DeleteGoodPricePairFromSupplierCommand(INDEX_FIRST_SUPPLIER, descriptor);

        String expectedMessage = DeleteGoodPricePairFromSupplierCommand.MESSAGE_SUCCESS_DELETE_GOOD
                .concat(ALICE.getOffers().iterator().next().getGoodName().toString())
                .concat(", ");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());

        Supplier editedSupplier = new SupplierBuilder().withName(ALICE.getName().toString())
                .withAddress(ALICE.getAddress().toString())
                .withEmail(ALICE.getEmail().toString())
                .withPhone(ALICE.getPhone().toString()).build();

        expectedModel.setSupplier(model.getFilteredSupplierList().get(0), editedSupplier);

        assertCommandSuccess(deleteGoodPricePairFromSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);

        Set<GoodName> goodNames = new HashSet<>();
        goodNames.add(ALICE.getOffers().iterator().next().getGoodName());

        Supplier supplierInFilteredList = model.getFilteredSupplierList().get(INDEX_FIRST_SUPPLIER.getZeroBased());

        DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName descriptor =
                new DeleteSupplierGoodNameBuilder().withGoodNames(goodNames).build();

        DeleteGoodPricePairFromSupplierCommand deleteGoodPricePairFromSupplierCommand =
                new DeleteGoodPricePairFromSupplierCommand(INDEX_FIRST_SUPPLIER, descriptor);

        String expectedMessage = DeleteGoodPricePairFromSupplierCommand.MESSAGE_SUCCESS_DELETE_GOOD
                .concat(ALICE.getOffers().iterator().next().getGoodName().toString())
                .concat(", ");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());

        Supplier editedSupplier = new SupplierBuilder(supplierInFilteredList).withName(ALICE.getName().toString())
                .withAddress(ALICE.getAddress().toString())
                .withEmail(ALICE.getEmail().toString())
                .withPhone(ALICE.getPhone().toString()).withOffers().build();

        expectedModel.setSupplier(model.getFilteredSupplierList().get(0), editedSupplier);

        assertCommandSuccess(deleteGoodPricePairFromSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_couldNotFindGood_success() {
        Set<GoodName> goodNames = new HashSet<>();
        goodNames.add(new GoodBuilder().withGoodName("There is no such good").build().getGoodName());

        DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName descriptor =
                new DeleteSupplierGoodNameBuilder().withGoodNames(goodNames).build();

        DeleteGoodPricePairFromSupplierCommand deleteGoodPricePairFromSupplierCommand =
                new DeleteGoodPricePairFromSupplierCommand(INDEX_FIRST_SUPPLIER, descriptor);

        String expectedMessage = DeleteGoodPricePairFromSupplierCommand.MESSAGE_COULD_NOT_FIND_GOOD
                .concat("There is no such good").concat(", ");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());

        assertCommandSuccess(deleteGoodPricePairFromSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mixSuccessfulDeleteGoodAndCouldNotFindGood_success() {
        Set<GoodName> goodNames = new HashSet<>();
        ALICE.getOffers().forEach(offer -> goodNames.add(offer.getGoodName()));
        goodNames.add(new GoodBuilder().withGoodName("There is no such good").build().getGoodName());

        DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName descriptor =
                new DeleteSupplierGoodNameBuilder().withGoodNames(goodNames).build();

        DeleteGoodPricePairFromSupplierCommand deleteGoodPricePairFromSupplierCommand =
                new DeleteGoodPricePairFromSupplierCommand(INDEX_FIRST_SUPPLIER, descriptor);

        String expectedMessage = DeleteGoodPricePairFromSupplierCommand.MESSAGE_SUCCESS_DELETE_GOOD
                .concat(ALICE.getOffers().iterator().next().getGoodName().toString())
                .concat(", \n").concat(DeleteGoodPricePairFromSupplierCommand.MESSAGE_COULD_NOT_FIND_GOOD)
                .concat("There is no such good, ");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), getTypicalInventory(),
                getTypicalTransactionHistory(), new UserPrefs());

        Supplier editedSupplier = new SupplierBuilder(ALICE).withOffers().build();

        expectedModel.setSupplier(model.getFilteredSupplierList().get(0), editedSupplier);

        assertCommandSuccess(deleteGoodPricePairFromSupplierCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSupplierIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSupplierList().size() + 1);

        DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName descriptor =
                new DeleteSupplierGoodNameBuilder().build();

        DeleteGoodPricePairFromSupplierCommand deleteGoodPricePairFromSupplierCommand =
                new DeleteGoodPricePairFromSupplierCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(deleteGoodPricePairFromSupplierCommand, model,
                Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidSupplierIndexFilteredList_failure() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);
        Index outOfBoundIndex = INDEX_SECOND_SUPPLIER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReadOnlyList().size());

        DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName descriptor =
                new DeleteSupplierGoodNameBuilder().build();

        DeleteGoodPricePairFromSupplierCommand deleteGoodPricePairFromSupplierCommand =
                new DeleteGoodPricePairFromSupplierCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(deleteGoodPricePairFromSupplierCommand, model,
                Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_valid_callsModelCommit() throws CommandException {
        ModelStubCommit modelStub = new ModelStubCommit();
        modelStub.addSupplier(CARL);

        Set<GoodName> goodNames = new HashSet<>();
        CARL.getOffers().forEach(offer -> goodNames.add(offer.getGoodName()));

        new DeleteGoodPricePairFromSupplierCommand(INDEX_FIRST_SUPPLIER,
                new DeleteSupplierGoodNameBuilder().withGoodNames(goodNames)
                        .build()).execute(modelStub);

        assertTrue(modelStub.isCommitted());
    }

    @Test
    public void equals() {
        Set<GoodName> goodNames = new HashSet<>();
        AMY.getOffers().forEach(offer -> goodNames.add(offer.getGoodName()));
        final DeleteGoodPricePairFromSupplierCommand standardCommand =
                new DeleteGoodPricePairFromSupplierCommand(INDEX_FIRST_SUPPLIER,
                        new DeleteSupplierGoodNameBuilder().withGoodNames(goodNames).build());

        //same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        //null -> returns false
        assertFalse(standardCommand.equals(null));

        //different types -> returns false
        assertFalse(standardCommand.equals(new ClearSupplierCommand()));

        //different index -> returns false
        assertFalse(standardCommand.equals(new DeleteGoodPricePairFromSupplierCommand(INDEX_SECOND_SUPPLIER,
                new DeleteSupplierGoodNameBuilder().withGoodNames(goodNames).build())));
    }
}
