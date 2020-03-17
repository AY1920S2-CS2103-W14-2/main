package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_DATE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;

/**
 * Sells the stated quantity of the good from the given supplier on the provided transaction date.
 * User will not be allowed to sell more than their current stock in inventory.
 */
public class SellCommand extends Command {
    public static final String COMMAND_WORD = "sell";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sells the stated "
            + "quantity of the good on the provided transaction date. "
            + "Users are not allowed to sell more than they have in stock. "
            + "Parameters: "
            + PREFIX_GOOD_NAME + "GOOD_NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_TRANSACTION_DATE + "TRANSACTION_DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GOOD_NAME + "Apples "
            + PREFIX_QUANTITY + "50 "
            + PREFIX_TRANSACTION_DATE + "2020-01-01";

    public static final String MESSAGE_SUCCESS = "Sold %1$d %2$ss";

    private Good soldGood;

    public SellCommand(Good soldGood) {
        this.soldGood = soldGood;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        /*
        if model doesn't have good, you cannot sell it

        if model has good but not enough quantity, you cannot sell it

        only if have good and sufficient qty then can sell
         */
        if (!model.hasGood(soldGood)) {
            throw new CommandException("Trying to sell non-existent good");
        }

        if (!haveSufficientQuantity(model, soldGood)) {
            throw new CommandException("Unable to sell a higher quantity"
                    + "than amount in inventory");
        }

        decreaseQuantity(model, soldGood);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                soldGood.getGoodQuantity().goodQuantity, soldGood.getGoodName().fullGoodName));
    }

    private void decreaseQuantity(Model model, Good soldGood) {
        int oldGoodIndex = model.indexOfGood(soldGood);
        Good oldGood = model.getFilteredGoodList().get(oldGoodIndex);

        int updatedQuantity = oldGood.getGoodQuantity().goodQuantity -
                soldGood.getGoodQuantity().goodQuantity;

        Good updatedGood = new Good(new GoodName(soldGood.getGoodName().toString()),
                new GoodQuantity(updatedQuantity));

        model.setGood(oldGood, updatedGood);
    }

    private boolean haveSufficientQuantity(Model model, Good soldGood) {
        int oldGoodIndex = model.indexOfGood(soldGood);
        Good oldGood = model.getFilteredGoodList().get(oldGoodIndex);

        return oldGood.getGoodQuantity().goodQuantity >=
                soldGood.getGoodQuantity().goodQuantity;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SellCommand)) {
            return false;
        }

        // state check
        SellCommand e = (SellCommand) other;
        return soldGood.equals(e.getGood());
    }

    private Good getGood() {
        return soldGood;
    }
}
