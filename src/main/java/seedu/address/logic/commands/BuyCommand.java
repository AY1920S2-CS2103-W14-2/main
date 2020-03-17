package seedu.address.logic.commands;

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
            + PREFIX_TRANSACTION_DATE + "TRANSACTION_DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Leong Fai Produce Co. "
            + PREFIX_GOOD_NAME + "Apples "
            + PREFIX_QUANTITY + "50 "
            + PREFIX_EXPIRY_DATE + "2020-02-02 "
            + PREFIX_TRANSACTION_DATE + "2020-01-01";

    public static final String MESSAGE_SUCCESS = "Bought %1$d %2$ss";

    private Good boughtGood;

    public BuyCommand(Good boughtGood) {
        this.boughtGood = boughtGood;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.hasGood(boughtGood)) {
            increaseQuantity(model, boughtGood);
        } else {
            model.addGood(boughtGood);
        }

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
    private void increaseQuantity(Model model, Good newGood) {
        int oldGoodIndex = model.indexOfGood(newGood);
        Good oldGood = model.getFilteredGoodList().get(oldGoodIndex);

        int updatedQuantity = oldGood.getGoodQuantity().goodQuantity + newGood.getGoodQuantity().goodQuantity;

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
