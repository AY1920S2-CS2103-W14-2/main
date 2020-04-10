package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.offer.Offer;
import seedu.address.model.supplier.Address;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;


/**
 * Edits the details of an existing supplier in the address book.
 */
public class EditSupplierCommand extends Command {

    public static final String COMMAND_WORD = "edit-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the supplier identified "
            + "by the index number used in the displayed supplier list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_CONTACT + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_OFFER + "GOOD_PRICE_PAIR]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CONTACT + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_SUPPLIER_SUCCESS = "Edited Supplier: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SUPPLIER = "This supplier already exists in the address book.";

    private static Set<Offer> supplierToEditOffer = null;
    private final Index index;
    private final EditSupplierDescriptor editSupplierDescriptor;

    //public static Set<Offer> supplierToEditOffer;

    /**
     * @param index of the supplier in the filtered supplier list to edit
     * @param editSupplierDescriptor details to edit the supplier with
     */
    public EditSupplierCommand(Index index, EditSupplierDescriptor editSupplierDescriptor) {
        requireNonNull(index);
        requireNonNull(editSupplierDescriptor);

        this.index = index;
        this.editSupplierDescriptor = new EditSupplierDescriptor(editSupplierDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Supplier> lastShownList = model.getFilteredSupplierList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        Supplier supplierToEdit = lastShownList.get(index.getZeroBased());
        Supplier editedSupplier = createEditedSupplier(supplierToEdit, editSupplierDescriptor);

        if (!supplierToEdit.isSameSupplier(editedSupplier) && model.hasSupplier(editedSupplier)) {
            throw new CommandException(MESSAGE_DUPLICATE_SUPPLIER);
        }

        model.setSupplier(supplierToEdit, editedSupplier);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        model.commit();
        return new CommandResult(String.format(MESSAGE_EDIT_SUPPLIER_SUCCESS, editedSupplier));
    }

    /**
     * combine the offer set of supplierToEdit with EditSupplierDescriptor
     * return the combined set
     * @return
     */
    public static Set<Offer> mergeOfferSets(Set<Offer> supplierToEditOffer,
                                                     Set<Offer> editSupplierDescriptorOffer) {
        return Stream.concat(editSupplierDescriptorOffer.stream(),
                supplierToEditOffer.stream()).collect(Collectors.toSet());
    }

    /**
     * Creates and returns a {@code Supplier} with the details of {@code supplierToEdit}
     * edited with {@code editSupplierDescriptor}.
     */
    private static Supplier createEditedSupplier(Supplier supplierToEdit,
                                                 EditSupplierDescriptor editSupplierDescriptor) {
        assert supplierToEdit != null;

        Name updatedName = editSupplierDescriptor.getName().orElse(supplierToEdit.getName());
        Phone updatedPhone = editSupplierDescriptor.getPhone().orElse(supplierToEdit.getPhone());
        Email updatedEmail = editSupplierDescriptor.getEmail().orElse(supplierToEdit.getEmail());
        Address updatedAddress = editSupplierDescriptor.getAddress().orElse(supplierToEdit.getAddress());

        supplierToEditOffer = supplierToEdit.getOffers();

        if (editSupplierDescriptor.offers != null) {
            Set<Offer> updatedOffers = mergeOfferSets(supplierToEditOffer, editSupplierDescriptor.getOffers().get());
            return new Supplier(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedOffers);

        } else {
            return new Supplier(updatedName, updatedPhone, updatedEmail, updatedAddress, supplierToEditOffer);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditSupplierCommand)) {
            return false;
        }

        // state check
        EditSupplierCommand e = (EditSupplierCommand) other;
        return index.equals(e.index)
                && editSupplierDescriptor.equals(e.editSupplierDescriptor);
    }

    /**
     * Stores the details to edit the supplier with. Each non-empty field value will replace the
     * corresponding field value of the supplier.
     */
    public static class EditSupplierDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Offer> offers;

        public EditSupplierDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code offers} is used internally.
         */
        public EditSupplierDescriptor(EditSupplierDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setOffers(toCopy.offers);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, offers);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code offers} to this object's {@code offers}.
         * A defensive copy of {@code offers} is used internally.
         */
        public void setOffers(Set<Offer> offers) {
            this.offers = (offers != null) ? new HashSet<>(offers) : null;
        }

        /**
         * Returns an unmodifiable offer set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code offers} is null.
         */
        public Optional<Set<Offer>> getOffers() {
            return (offers != null) ? Optional.of(Collections.unmodifiableSet(offers)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSupplierDescriptor)) {
                return false;
            }

            // state check
            EditSupplierDescriptor e = (EditSupplierDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getOffers().equals(e.getOffers());
        }
    }
}
