package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.good.GoodName;
import seedu.address.model.offer.Offer;
import seedu.address.model.supplier.Address;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;

/**
 * Deletes a supplier identified using its displayed index from the address book or delete a specific good in the
 * supplier's list
 */
public class DeleteSupplierCommand extends Command {

    public static final String COMMAND_WORD = "delete-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the supplier identified by the index number used in the displayed supplier list.\n"
            + "Parameters: INDEX (must be a positive integer),"
            + " goods (g/good name to be included to only delete the listed good)\n"
            + "Example: " + COMMAND_WORD + " 1" + " g/apple";

    public static final String MESSAGE_DELETE_SUPPLIER_SUCCESS = "Deleted Supplier: %1$s";
    public static final String MESSAGE_DELETE_GOOD = "Deleted good in the supplier's list";
    public static final String MESSAGE_COULD_NOT_FIND_GOOD = "GOOD COULD NOT BE FOUND IN THE SUPPLIER'S LIST!";

    private final Index index;
    private final DeleteSupplierGoodName deleteSupplierGoodName;

    public DeleteSupplierCommand(Index index, DeleteSupplierGoodName deleteSupplierGoodName) {
        requireNonNull(index);

        this.index = index;
        this.deleteSupplierGoodName = new DeleteSupplierCommand.DeleteSupplierGoodName(deleteSupplierGoodName);
    }

    public DeleteSupplierCommand(Index indexFirstSupplier) {
        this.index = indexFirstSupplier;
        deleteSupplierGoodName = new DeleteSupplierGoodName();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Supplier> lastShownList = model.getFilteredSupplierList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        if (deleteSupplierGoodName.getGoodNames().equals(Optional.empty())) {
            Supplier supplierWhoHasGoodToDelete = lastShownList.get(index.getZeroBased());
            model.deleteSupplier(supplierWhoHasGoodToDelete);
            model.commit();
            return new CommandResult(String.format(MESSAGE_DELETE_SUPPLIER_SUCCESS, supplierWhoHasGoodToDelete));

        } else {
            Supplier supplierWhoHasGoodToDelete = lastShownList.get(index.getZeroBased());
            Supplier editedSupplier = createEditedSupplier(supplierWhoHasGoodToDelete,
                    deleteSupplierGoodName.goodNames.iterator().next());

            if (supplierWhoHasGoodToDelete.getOffers().equals(editedSupplier.getOffers())) {
                return new CommandResult(String.format(MESSAGE_COULD_NOT_FIND_GOOD, supplierWhoHasGoodToDelete));
            }

            model.setSupplier(supplierWhoHasGoodToDelete, editedSupplier);
            model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
            model.commit();
            return new CommandResult(String.format(MESSAGE_DELETE_GOOD, supplierWhoHasGoodToDelete));
        }
    }

    /**
     * Creates and returns a {@code Supplier} with the details of {@code supplierWhoHasGoodToDelete}
     * edited with {@code goodName}.
     */
    private static Supplier createEditedSupplier(Supplier supplierWhoHasGoodToDelete, GoodName goodName) {
        assert supplierWhoHasGoodToDelete != null;

        Name updatedName = supplierWhoHasGoodToDelete.getName();
        Phone updatedPhone = supplierWhoHasGoodToDelete.getPhone();
        Email updatedEmail = supplierWhoHasGoodToDelete.getEmail();
        Address updatedAddress = supplierWhoHasGoodToDelete.getAddress();

        Set<Offer> supplierToEditOffer = new HashSet<>(supplierWhoHasGoodToDelete.getOffers());
        supplierToEditOffer.removeIf(tempOffer -> tempOffer.getGoodName().equals(goodName));

        return new Supplier(updatedName, updatedPhone, updatedEmail, updatedAddress, supplierToEditOffer);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSupplierCommand // instanceof handles nulls
                && index.equals(((DeleteSupplierCommand) other).index)); // state check
    }

    /**
     * Stores the details to edit the supplier with. Each non-empty field value will replace the
     * corresponding field value of the supplier.
     */
    public static class DeleteSupplierGoodName {
        private Set<GoodName> goodNames;

        public DeleteSupplierGoodName() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code goods} is used internally.
         */
        public DeleteSupplierGoodName(DeleteSupplierCommand.DeleteSupplierGoodName toCopy) {
            setGoodNames(toCopy.goodNames);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(goodNames);
        }

        /**
         * Sets {@code goods} to this object's {@code goods}.
         * A defensive copy of {@code goods} is used internally.
         */
        public void setGoodNames(Set<GoodName> goodNames) {
            this.goodNames = (goodNames != null) ? new HashSet<>(goodNames) : null;
        }

        /**
         * Returns an unmodifiable good set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code goods} is null.
         */
        public Optional<Set<GoodName>> getGoodNames() {
            return (goodNames != null) ? Optional.of(Collections.unmodifiableSet(goodNames)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeleteSupplierCommand.DeleteSupplierGoodName)) {
                return false;
            }

            // state check
            DeleteSupplierCommand.DeleteSupplierGoodName e = (DeleteSupplierCommand.DeleteSupplierGoodName) other;

            return getGoodNames().equals(e.getGoodNames());
        }
    }
}
