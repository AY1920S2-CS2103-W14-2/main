package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSuppliers.BENSON;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.supplier.Address;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;

public class JsonAdaptedSupplierTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PRICE = "-1.0";
    private static final String VALID_GOOD = "anything";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final Set<JsonAdaptedOffer> VALID_OFFERS = BENSON.getOffers().stream()
            .map(JsonAdaptedOffer::new)
            .collect(Collectors.toSet());

    @Test
    public void toModelType_validSupplierDetails_returnsSupplier() throws Exception {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(BENSON);
        assertEquals(BENSON, supplier.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_OFFERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidOffers_throwsIllegalValueException() {
        Set<JsonAdaptedOffer> invalidOffers = new HashSet<>(VALID_OFFERS);
        invalidOffers.add(new JsonAdaptedOffer(VALID_GOOD, INVALID_PRICE));
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidOffers);
        assertThrows(IllegalValueException.class, supplier::toModelType);
    }

}
