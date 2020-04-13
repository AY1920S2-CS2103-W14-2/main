package cs2103_w14_2.inventory.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs2103_w14_2.inventory.logic.commands.CommandTestUtil;
import cs2103_w14_2.inventory.model.AddressBook;
import cs2103_w14_2.inventory.model.supplier.Supplier;

/**
 * A utility class containing a list of {@code Supplier} objects to be used in tests.
 */
public class TypicalSuppliers {

    public static final Supplier ALICE = new SupplierBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withOffers("hand sanitizer 500.00").build();
    public static final Supplier BENSON = new SupplierBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withOffers("pear 6", "orange 2").build();
    public static final Supplier CARL = new SupplierBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Supplier DANIEL = new SupplierBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withOffers("paper 3.55").build();
    public static final Supplier ELLE = new SupplierBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Supplier FIONA = new SupplierBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Supplier GEORGE = new SupplierBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Supplier HOON = new SupplierBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Supplier IDA = new SupplierBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Supplier's details found in {@code CommandTestUtil}
    public static final Supplier AMY = new SupplierBuilder().withName(CommandTestUtil.VALID_NAME_AMY).withPhone(CommandTestUtil.VALID_PHONE_AMY)
            .withEmail(CommandTestUtil.VALID_EMAIL_AMY).withAddress(CommandTestUtil.VALID_ADDRESS_AMY).withOffers(CommandTestUtil.VALID_OFFER_APPLE).build();
    public static final Supplier BOB = new SupplierBuilder().withName(CommandTestUtil.VALID_NAME_BOB).withPhone(CommandTestUtil.VALID_PHONE_BOB)
            .withEmail(CommandTestUtil.VALID_EMAIL_BOB).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withOffers(CommandTestUtil.VALID_OFFER_APPLE, CommandTestUtil.VALID_OFFER_BANANA)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalSuppliers() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical suppliers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Supplier supplier : getTypicalSuppliers()) {
            ab.addSupplier(supplier);
        }
        return ab;
    }

    public static List<Supplier> getTypicalSuppliers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
