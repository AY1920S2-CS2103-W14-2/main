package cs2103_w14_2.inventory.storage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.model.offer.Offer;
import cs2103_w14_2.inventory.model.supplier.Address;
import cs2103_w14_2.inventory.model.supplier.Email;
import cs2103_w14_2.inventory.model.supplier.Name;
import cs2103_w14_2.inventory.model.supplier.Phone;
import cs2103_w14_2.inventory.model.supplier.Supplier;

/**
 * Jackson-friendly version of {@link Supplier}.
 */
class JsonAdaptedSupplier {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Supplier's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final Set<JsonAdaptedOffer> offers = new HashSet<>();

    /**
     * Constructs a {@code JsonAdaptedSupplier} with the given supplier details.
     */
    @JsonCreator
    public JsonAdaptedSupplier(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email, @JsonProperty("address") String address,
                               @JsonProperty("offers") Set<JsonAdaptedOffer> offers) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (offers != null) {
            this.offers.addAll(offers);
        }
    }

    /**
     * Converts a given {@code Supplier} into this class for Jackson use.
     */
    public JsonAdaptedSupplier(Supplier source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        offers.addAll(source.getOffers().stream()
                .map(JsonAdaptedOffer::new)
                .collect(Collectors.toSet()));
    }

    /**
     * Converts this Jackson-friendly adapted supplier object into the model's {@code Supplier} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted supplier.
     */
    public Supplier toModelType() throws IllegalValueException {
        final Set<Offer> supplierOffers = new HashSet<>();
        for (JsonAdaptedOffer offer : offers) {
            supplierOffers.add(offer.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Offer> modelOffers = new HashSet<>(supplierOffers);
        return new Supplier(modelName, modelPhone, modelEmail, modelAddress, modelOffers);
    }

}
