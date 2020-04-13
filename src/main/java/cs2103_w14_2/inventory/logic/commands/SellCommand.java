package cs2103_w14_2.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static cs2103_w14_2.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.UUID;

import cs2103_w14_2.inventory.logic.commands.exceptions.CommandException;
import cs2103_w14_2.inventory.logic.parser.CliSyntax;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.good.GoodName;
import cs2103_w14_2.inventory.model.good.GoodQuantity;
import cs2103_w14_2.inventory.model.offer.Price;
import cs2103_w14_2.inventory.model.transaction.SellTransaction;
import cs2103_w14_2.inventory.model.transaction.TransactionId;

/**
 * Sells the stated quantity of the good from the given supplier on the provided transaction date.
 * User will not be allowed to sell more than their current stock in inventory.
 */
public class SellCommand extends Command {
    public static final String COMMAND_WORD = "sell";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sells goods from inventory at the displayed index.\n"
            + "The sell command keyword is followed by the displayed index of the good to sell.\n"
            + "The quantity to sell will be given as a parameter.\n"
            + "The price to sell each unit will be given as a parameter.\n"
            + "Users are unable to sell a larger quantity than is present in the inventory for "
            + "the good at the displayed index.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_QUANTITY + "QUANTITY "
            + CliSyntax.PREFIX_PRICE + "PRICE"
            + "\nExample: " + COMMAND_WORD + " 2 "
            + CliSyntax.PREFIX_QUANTITY + "50 "
            + CliSyntax.PREFIX_PRICE + "6.90";

    public static final String MESSAGE_SUCCESS = "Sold %1$d units of %2$s at %3$s each";
    public static final String MESSAGE_INSUFFICIENT_QUANTITY =
            "Unable to sell a higher quantity than amount in inventory";

    private GoodQuantity sellingQuantity;
    private Price sellingPrice;
    private Index inventoryIndex;

    public SellCommand(GoodQuantity sellingQuantity, Price sellingPrice, Index inventoryIndex) {
        requireAllNonNull(sellingQuantity, sellingPrice, inventoryIndex);

        this.sellingQuantity = sellingQuantity;
        this.sellingPrice = sellingPrice;
        this.inventoryIndex = inventoryIndex;
    }

    // TODO: follow buy command comment
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Good> lastShownList = model.getFilteredGoodList();

        if (inventoryIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GOOD_DISPLAYED_INDEX);
        }

        //user cannot sell more of a good than is present in inventory
        Good soldGoodEntry = lastShownList.get(inventoryIndex.getZeroBased());

        if (!hasSufficientQuantity(soldGoodEntry, sellingQuantity)) {
            throw new CommandException(MESSAGE_INSUFFICIENT_QUANTITY);
        }

        // since there is sufficient quantity, selling is valid
        decreaseQuantity(model, soldGoodEntry, sellingQuantity);

        // create sell transaction command in transaction history
        GoodName soldGoodName = soldGoodEntry.getGoodName();
        Good soldGood = Good.newGoodEntry(soldGoodName, sellingQuantity);
        TransactionId transactionId = new TransactionId(UUID.randomUUID().toString());

        SellTransaction sellTransaction = new SellTransaction(transactionId, soldGood, sellingPrice);

        model.addTransaction(sellTransaction);

        model.commit();

        return new CommandResult(String.format(MESSAGE_SUCCESS, sellingQuantity.goodQuantity,
                soldGoodEntry.getGoodName().fullGoodName, sellingPrice));
    }

    /**
     * Checks whether the inventory has at least the quantity of good trying to be sold.
     *
     * @param soldGoodEntry The good in the inventory to be sold
     * @param sellingQuantity The quantity of the good in inventory to sell
     * @return true if there is at least the quantity of good to be sold in the inventory.
     */
    private boolean hasSufficientQuantity(Good soldGoodEntry, GoodQuantity sellingQuantity) {
        GoodQuantity inventoryQuantity = soldGoodEntry.getGoodQuantity();

        return inventoryQuantity.goodQuantity >= sellingQuantity.goodQuantity;
    }

    /**
     * Decreases the quantity of an existing good in inventory with the same name as {@code newGood}
     * by the quantity in {@code newGood}
     *  @param model underlying model to make modifications in
     * @param soldGoodEntry contains the good name and quantity to increase by
     * @param sellingQuantity quantity to decrease the existing inventory amount by
     */
    private void decreaseQuantity(Model model, Good soldGoodEntry, GoodQuantity sellingQuantity) {

        int updatedQuantity = soldGoodEntry.getGoodQuantity().goodQuantity
                - sellingQuantity.goodQuantity;

        Good updatedGood = new Good(soldGoodEntry.getGoodName(), new GoodQuantity(updatedQuantity),
                soldGoodEntry.getThreshold());

        model.setGood(soldGoodEntry, updatedGood);
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
        return sellingQuantity.equals(e.sellingQuantity)
                && sellingPrice.equals(e.sellingPrice)
                && inventoryIndex.equals(e.inventoryIndex);
    }
}
