package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_DATE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Supplier;

/**
 * Buys the stated quantity of the good stated from the given supplier on the provided transaction date.
 * This batch of goods will be set to expire on the given expiry date, after which they will expire and
 * be removed from inventory.
 */
public class BuyCommand extends Command {
    public static final String COMMAND_WORD = "buy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Buys the stated "
            + "quantity of the good stated from the given supplier on the provided transaction date. "
            + "This batch of goods will be set to expire on the given expiry date, after which "
            + "they will expire and be removed from inventory. "
            + "Parameters: "
            + PREFIX_NAME + "SUPPLIER_NAME "
            + PREFIX_GOOD_NAME + "GOOD_NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE "
            + PREFIX_TRANSACTION_DATE + "TRANSACTION_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Leong Fai Produce Co. "
            + PREFIX_GOOD_NAME + "Apples "
            + PREFIX_QUANTITY + "50 "
            + PREFIX_EXPIRY_DATE + "2020-02-02 "
            + PREFIX_TRANSACTION_DATE + "2020-01-01";

    public static final String MESSAGE_SUCCESS = "Bought %1$d units of %2$s.";

    public static final String MESSAGE_BUYING_FROM_NONEXISTENT_SUPPLIER = "No supplier exists with that name.";

    public static final String MESSAGE_SUPPLIER_DOES_NOT_SELL_GOOD = "Supplier does not sell that good.";

    public static final String MESSAGE_GOODQUANTITY_OVERFLOW = "Buying this batch of goods exceeds maximum allowed "
            + "inventory quantity.\n"
            + GoodQuantity.MESSAGE_CONSTRAINTS;


    private Good boughtGood;

    public BuyCommand(Good boughtGood) {
        requireNonNull(boughtGood);
        this.boughtGood = boughtGood;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        // supplier could be absent in the model
        Name supplierNameToFind = boughtGood.getSupplierName();
        int numMatchingSuppliers = (int) model.getFilteredSupplierList().stream()
                .filter(supplier -> supplier.getName().equals(supplierNameToFind)).count();
        if (numMatchingSuppliers <= 0) {
            throw new CommandException(MESSAGE_BUYING_FROM_NONEXISTENT_SUPPLIER);
        }

        // good could be absent in the model
        // currently takes the first supplier with matching Name if there are >1 such suppliers
        Supplier foundSupplier = model.getFilteredSupplierList().stream()
                .filter(supplier -> supplier.getName().equals(supplierNameToFind))
                .findFirst()
                .get();

        int numMatchingGoods = (int) foundSupplier.getOffers().stream()
                .filter(offer -> offer.getGoodName()
                .equals(boughtGood.getGoodName()))
                .count();

        if (numMatchingGoods <= 0) {
            throw new CommandException(MESSAGE_SUPPLIER_DOES_NOT_SELL_GOOD);
        }

        if (model.hasGood(boughtGood)) {
            increaseQuantity(model, boughtGood);
        } else {
            model.addGood(boughtGood);
        }
        model.commit();

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                boughtGood.getGoodQuantity().goodQuantity, boughtGood.getGoodName().fullGoodName));
    }

    /**
     * Increases the quantity of an existing good in inventory with the same name as {@code newGood}
     * by the quantity in {@code newGood}
     *
     * @param model   underlying model to make modifications in
     * @param newGood contains the good name and quantity to increase by
     */
    private void increaseQuantity(Model model, Good newGood) throws CommandException {
        int oldGoodIndex = model.indexOfGood(newGood);
        Good oldGood = model.getFilteredGoodList().get(oldGoodIndex);

        int updatedQuantity = oldGood.getGoodQuantity().goodQuantity + newGood.getGoodQuantity().goodQuantity;
        try {
            GoodQuantity newGoodQuantity = new GoodQuantity(updatedQuantity);
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_GOODQUANTITY_OVERFLOW);
        }

        Good updatedGood = new Good(new GoodName(newGood.getGoodName().toString()),
                new GoodQuantity(updatedQuantity));

        model.setGood(oldGood, updatedGood);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BuyCommand)) {
            return false;
        }

        // state check
        BuyCommand e = (BuyCommand) other;
        return boughtGood.equals(e.getGood());
    }

    private Good getGood() {
        return boughtGood;
    }
}

