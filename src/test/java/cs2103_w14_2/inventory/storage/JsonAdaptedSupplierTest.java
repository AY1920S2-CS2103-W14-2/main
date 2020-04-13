package cs2103_w14_2.inventory.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import cs2103_w14_2.inventory.commons.exceptions.IllegalValueException;
import cs2103_w14_2.inventory.model.supplier.Address;
import cs2103_w14_2.inventory.model.supplier.Email;
import cs2103_w14_2.inventory.model.supplier.Name;
import cs2103_w14_2.inventory.model.supplier.Phone;
import cs2103_w14_2.inventory.testutil.Assert;
import cs2103_w14_2.inventory.testutil.TypicalSuppliers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonAdaptedSupplierTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PRICE = "-1.0";
    private static final String VALID_GOOD = "anything";

    private static final String VALID_NAME = TypicalSuppliers.BENSON.getName().toString();
    private static final String VALID_PHONE = TypicalSuppliers.BENSON.getPhone().toString();
    private static final String VALID_EMAIL = TypicalSuppliers.BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = TypicalSuppliers.BENSON.getAddress().toString();
    private static final Set<JsonAdaptedOffer> VALID_OFFERS = TypicalSuppliers.BENSON.getOffers().stream()
            .map(JsonAdaptedOffer::new)
            .collect(Collectors.toSet());

    @Test
    public void toModelType_validSupplierDetails_returnsSupplier() throws Exception {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(TypicalSuppliers.BENSON);
        Assertions.assertEquals(TypicalSuppliers.BENSON, supplier.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_OFFERS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_OFFERS);
        String expectedMessage = String.format(JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidOffers_throwsIllegalValueException() {
        Set<JsonAdaptedOffer> invalidOffers = new HashSet<>(VALID_OFFERS);
        invalidOffers.add(new JsonAdaptedOffer(VALID_GOOD, INVALID_PRICE));
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidOffers);
        Assert.assertThrows(IllegalValueException.class, supplier::toModelType);
    }

}
