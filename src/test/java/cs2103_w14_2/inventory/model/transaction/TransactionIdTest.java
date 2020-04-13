package cs2103_w14_2.inventory.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static cs2103_w14_2.inventory.testutil.Assert.assertThrows;

import java.util.UUID;

import cs2103_w14_2.inventory.testutil.Assert;
import org.junit.jupiter.api.Test;

public class TransactionIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TransactionId(null));
    }

    @Test
    public void constructor_invalidTransactionId_throwsIllegalArgumentException() {
        String invalidTransactionId = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TransactionId(invalidTransactionId));
    }

    @Test
    public void getTransactionIdTest() {
        UUID uuid = UUID.randomUUID();
        TransactionId transactionId = new TransactionId(uuid.toString());
        assertEquals(transactionId.getTransactionId(), uuid.toString());

        UUID otherId = UUID.randomUUID();
        assertNotEquals(transactionId.getTransactionId(), otherId.toString());
    }

    @Test
    public void isValidTransactionId() {
        // null transaction id
        Assert.assertThrows(NullPointerException.class, () -> TransactionId.isValidTransactionId(null));

        // invalid transaction id
        assertFalse(TransactionId.isValidTransactionId("")); // empty string
        assertFalse(TransactionId.isValidTransactionId(" ")); // spaces only
        assertFalse(TransactionId.isValidTransactionId("3")); // only 3 numbers
        assertFalse(TransactionId.isValidTransactionId("transaction")); // alphabets that greater than f
        assertFalse(TransactionId.isValidTransactionId("9312 1534")); // spaces within digits

        // valid transaction id
        assertTrue(TransactionId.isValidTransactionId("cbd83eae-1e34-4c6e-90c1-e9fbc3d0e3ef"));
        assertTrue(TransactionId.isValidTransactionId("af43a9b7-f896-44ad-be92-39ae801462d8"));
        assertTrue(TransactionId.isValidTransactionId("1b505667-4970-47c2-8484-34a9b43ce260"));
    }

    @Test
    public void equalsTest() {
        UUID uuid = UUID.randomUUID();
        TransactionId transactionId = new TransactionId(uuid.toString());
        assertEquals(transactionId, transactionId); //same object

        TransactionId otherTransactionId = new TransactionId(uuid.toString());
        assertEquals(transactionId, otherTransactionId); //same id

        UUID otherId = UUID.randomUUID();
        otherTransactionId = new TransactionId(otherId.toString());
        assertNotEquals(transactionId, otherTransactionId);
    }

    @Test
    public void toStringTest() {
        UUID uuid = UUID.randomUUID();
        TransactionId transactionId = new TransactionId(uuid.toString());
        assertEquals(transactionId.toString(), uuid.toString());

        UUID otherId = UUID.randomUUID();
        assertNotEquals(transactionId.toString(), otherId.toString());
    }
}
