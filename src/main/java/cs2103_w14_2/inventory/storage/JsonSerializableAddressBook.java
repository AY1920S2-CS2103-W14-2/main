package cs2103_w14_2.inventory.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.model.AddressBook;
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.supplier.Supplier;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_SUPPLIER = "Suppliers list contains duplicate supplier(s).";

    private final List<JsonAdaptedSupplier> suppliers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given suppliers.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("suppliers") List<JsonAdaptedSupplier> suppliers) {
        this.suppliers.addAll(suppliers);
    }

    /**
     * Converts a given {@code ReadOnlyList<Person>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyList<Supplier> source) {
        suppliers.addAll(source.getReadOnlyList().stream().map(JsonAdaptedSupplier::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws cs2103_w14_2.inventory.commons.exceptions.IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedSupplier jsonAdaptedSupplier : suppliers) {
            Supplier supplier = jsonAdaptedSupplier.toModelType();
            if (addressBook.hasSupplier(supplier)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SUPPLIER);
            }
            addressBook.addSupplier(supplier);
        }
        return addressBook;
    }

}
