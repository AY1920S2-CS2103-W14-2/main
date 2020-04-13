package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.good.Good;
import seedu.address.model.good.GoodName;
import seedu.address.model.good.GoodQuantity;
import seedu.address.model.offer.Price;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.transaction.BuyTransaction;
import seedu.address.model.transaction.TransactionId;

/**
 * Buys the stated quantity of the good stated from the given supplier on the provided transaction date.
 * This batch of goods will be set to expire on the given expiry date, after which they will expire and
 * be removed from inventory.
 */
public class BuyCommand extends Command {
    public static final String COMMAND_WORD = "buy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Buys goods from the supplier at the displayed index.\n"
            + "The buy command keyword is followed by the displayed index of the supplier to buy from.\n"
            + "The name of good to buy will be given as a parameter.\n"
            + "The supplier must have the good on offer, else purchase will be rejected.\n"
            + "The quantity to buy will be given as a parameter.\n"
            + "Parameters: "
            + PREFIX_GOOD_NAME + "GOOD_NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + "\nExample: " + COMMAND_WORD + " 2 "
            + PREFIX_GOOD_NAME + "Apples "
            + PREFIX_QUANTITY + "50 ";

    public static final String MESSAGE_SUCCESS = "Bought %1$d units of %2$s at $%3$s each.";

    public static final String MESSAGE_SUPPLIER_DOES_NOT_SELL_GOOD = "Supplier does not sell that good.";

    public static final String MESSAGE_GOODQUANTITY_OVERFLOW = "Buying this batch of goods exceeds maximum allowed "
            + "inventory quantity.\n"
            + GoodQuantity.MESSAGE_CONSTRAINTS;

    private final GoodName boughtGoodName;
    private final GoodQuantity boughGoodQuantity;
    private final Index supplierIndex;

    public BuyCommand(GoodName boughtGoodName, GoodQuantity boughGoodQuantity, Index supplierIndex) {
        requireAllNonNull(boughtGoodName, boughGoodQuantity, supplierIndex);
        this.boughtGoodName = boughtGoodName;
        this.boughGoodQuantity = boughGoodQuantity;
        this.supplierIndex = supplierIndex;
    }


    /**
     * BuyCommand's execute will do the following things:
     * <ul>
     *     <li>
     *         Update inventory with the new bought good. This creates a new inventory entry if the good name did
     *         not previously exist, or updates the good quantity if it does.
     *     </li>
     *     <li>
     *         Create a new buy transaction in transaction history
     *     </li>
     *     <li>
     *         Commits the model to allow for undo and redo
     *     </li>
     * </ul>
     *
     * @param model {@code Model} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Supplier> lastShownList = model.getFilteredSupplierList();

        if (supplierIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        // verify that seller has good on offer
        Supplier seller = lastShownList.get(supplierIndex.getZeroBased());

        int numMatchingGoods = (int) seller.getOffers().stream()
                .filter(offer -> offer.getGoodName().equals(boughtGoodName))
                .count();

        if (numMatchingGoods <= 0) {
            throw new CommandException(MESSAGE_SUPPLIER_DOES_NOT_SELL_GOOD);
        }

        // updated inventory to reflect purchase
        Good queryGood = Good.goodWithName(boughtGoodName);

        if (model.hasGood(queryGood)) {
            increaseQuantity(model, boughtGoodName, boughGoodQuantity);
        } else {
            Good boughtGood = Good.newGoodEntry(boughtGoodName, boughGoodQuantity);
            model.addGood(boughtGood);
        }

        // get fields to create new transaction history record
        TransactionId transactionId = new TransactionId(UUID.randomUUID().toString());
        Good boughtGood = Good.newGoodEntry(boughtGoodName, boughGoodQuantity);

        // we have checked that seller has good on offer
        // offer is unique is the GoodName because a Set is used, so we take the first and only item
        Price price = seller.getOffers().stream()
                .filter(offer -> offer.getGoodName().equals(boughtGoodName))
                .collect(Collectors.toList())
                .get(0)
                .getPrice();

        BuyTransaction buyTransaction = new BuyTransaction(transactionId, boughtGood, seller, price);

        model.addTransaction(buyTransaction);

        model.commit();

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                boughGoodQuantity.goodQuantity, boughtGoodName.fullGoodName, price.getValue()));
    }

    /**
     * Increases the quantity of an existing good in inventory with the same name as {@code boughtGoodName}
     * by the quantity in {@code boughGoodQuantity}
     *
     * @param model underlying model to make modifications in
     * @param boughtGoodName name of newly bought good
     * @param boughGoodQuantity quantity of good to buy
     * @throws CommandException if the amount of goods being bought overflows the inventory limit as
     * stated in {@link GoodQuantity}
     */
    private void increaseQuantity(Model model,
            GoodName boughtGoodName, GoodQuantity boughGoodQuantity) throws CommandException {

        Good queryGood = Good.goodWithName(boughtGoodName);
        int oldGoodIndex = model.indexOfGood(queryGood);

        Good oldGoodEntry = model.getFilteredGoodList().get(oldGoodIndex);

        int updatedQuantity = oldGoodEntry.getGoodQuantity().goodQuantity + boughGoodQuantity.goodQuantity;

        // this try catch prevents inventory quantity overflows
        GoodQuantity updatedGoodQuantity;
        try {
            updatedGoodQuantity = new GoodQuantity(updatedQuantity);
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_GOODQUANTITY_OVERFLOW);
        }

        Good updatedGood = new Good(boughtGoodName, updatedGoodQuantity, oldGoodEntry.getThreshold());

        model.setGood(oldGoodEntry, updatedGood);
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
        return boughtGoodName.equals(e.boughtGoodName)
                && boughGoodQuantity.equals(e.boughGoodQuantity)
                && supplierIndex.equals(e.supplierIndex);
    }
}

