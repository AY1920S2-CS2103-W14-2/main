package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OFFER_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditSupplierCommand.EditSupplierDescriptor;
import seedu.address.testutil.EditSupplierDescriptorBuilder;

public class EditSupplierDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditSupplierDescriptor descriptorWithSameValues = new EditSupplierDescriptor(DESC_AMY);
        assertEquals(DESC_AMY, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_AMY, DESC_AMY);

        // null -> returns false
        assertNotEquals(null, DESC_AMY);

        // different types -> returns false
        assertNotEquals(5, DESC_AMY);

        // different values -> returns false
        assertNotEquals(DESC_AMY, DESC_BOB);

        // different name -> returns false
        EditSupplierDescriptor editedAmy = new EditSupplierDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);

        // different phone -> returns false
        editedAmy = new EditSupplierDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);

        // different email -> returns false
        editedAmy = new EditSupplierDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);

        // different address -> returns false
        editedAmy = new EditSupplierDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertNotEquals(DESC_AMY, editedAmy);

        // different offers -> returns false
        editedAmy = new EditSupplierDescriptorBuilder(DESC_AMY).withOffers(VALID_OFFER_BANANA).build();
        assertNotEquals(DESC_AMY, editedAmy);
    }
}
