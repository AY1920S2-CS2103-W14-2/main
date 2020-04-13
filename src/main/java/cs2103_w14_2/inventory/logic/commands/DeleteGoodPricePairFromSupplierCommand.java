package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.commons.util.CollectionUtil;
import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.supplier.Address;
import cs2103_w14_2.inventory.model.supplier.Email;
import cs2103_w14_2.inventory.model.supplier.Phone;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.offer.Offer;
import cs2103_w14_2.inventory.model.supplier.Name;

/**
 * Delete entries of good price pair from supplier's good list given the good name which is identified by keywords.
 */
public class DeleteGoodPricePairFromSupplierCommand extends Command {

    public static final String COMMAND_WORD = "delete-gp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an entry / entries of good price pair(s) in the supplier's good list given a good's or goods' "
            + "name(s) which is / are identified by keyword(s) (case-sensitive)\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "g/GOOD_NAME [g/MORE_GOOD_NAME]...\n"
            + "Example: " + COMMAND_WORD + " 1" + " g/apple" + " g/orange";

    public static final String MESSAGE_SUCCESS_DELETE_GOOD = "Successfuly deleted goods from supplier's list: ";
    public static final String MESSAGE_COULD_NOT_FIND_GOOD = "Could not find goods: ";
    public static final String MESSAGE_MUST_INCLUDE_GOODNAME = "Must include at least one good name";

    private static String errorMessage = MESSAGE_COULD_NOT_FIND_GOOD;
    private static String successMessage = MESSAGE_SUCCESS_DELETE_GOOD;

    private final Index index;
    private final DeleteSupplierGoodName deleteSupplierGoodName;

    public DeleteGoodPricePairFromSupplierCommand(Index index, DeleteSupplierGoodName deleteSupplierGoodName) {
        requireNonNull(index);

        this.index = index;
        this.deleteSupplierGoodName =
                new DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName(deleteSupplierGoodName);
    }

    public DeleteGoodPricePairFromSupplierCommand(Index indexFirstSupplier) {
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

        Supplier supplierWhoHasGoodToDelete = lastShownList.get(index.getZeroBased());
        Supplier editedSupplier = createEditedSupplier(supplierWhoHasGoodToDelete, deleteSupplierGoodName.goodNames);

        model.setSupplier(supplierWhoHasGoodToDelete, editedSupplier);
        model.updateFilteredSupplierList(Model.PREDICATE_SHOW_ALL_SUPPLIERS);
        model.commit();

        String returnMessage;
        if (errorMessage.equals(MESSAGE_COULD_NOT_FIND_GOOD)) {
            returnMessage = successMessage;
        } else {
            returnMessage = successMessage.equals(MESSAGE_SUCCESS_DELETE_GOOD) ? errorMessage
                    : successMessage.concat("\n").concat(errorMessage);
        }

        //reset message for next round of deletion
        errorMessage = MESSAGE_COULD_NOT_FIND_GOOD;
        successMessage = MESSAGE_SUCCESS_DELETE_GOOD;

        return new CommandResult(String.format(returnMessage, supplierWhoHasGoodToDelete));
    }

    /**
     * Creates and returns a {@code Supplier} with the details of {@code supplierWhoHasGoodToDelete}
     * edited with {@code goodName}.
     */
    private static Supplier createEditedSupplier(Supplier supplierWhoHasGoodToDelete, Set<GoodName> goodNames) {
        assert supplierWhoHasGoodToDelete != null;

        Name updatedName = supplierWhoHasGoodToDelete.getName();
        Phone updatedPhone = supplierWhoHasGoodToDelete.getPhone();
        Email updatedEmail = supplierWhoHasGoodToDelete.getEmail();
        Address updatedAddress = supplierWhoHasGoodToDelete.getAddress();

        Set<Offer> duplicate = new HashSet<>(supplierWhoHasGoodToDelete.getOffers());

        goodNames.stream().forEach(goodName -> {
            if (duplicate.removeIf(tempOffer -> tempOffer.getGoodName().equals(goodName))) {
                successMessage = successMessage.concat(goodName.toString()).concat(", ");
            } else {
                errorMessage = errorMessage.concat(goodName.toString()).concat(", ");
            }
        });

        return new Supplier(updatedName, updatedPhone, updatedEmail, updatedAddress, duplicate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGoodPricePairFromSupplierCommand // instanceof handles nulls
                && index.equals(((DeleteGoodPricePairFromSupplierCommand) other).index)); // state check
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
        public DeleteSupplierGoodName(DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName toCopy) {
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
            if (!(other instanceof DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName)) {
                return false;
            }

            // state check
            DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName e =
                    (DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName) other;

            return getGoodNames().equals(e.getGoodNames());
        }
    }
}
