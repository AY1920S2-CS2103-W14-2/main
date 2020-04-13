package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.FindTransactionCommandParser.TransactionType;

/**
 * Tests that a {@code Transaction} matches any of the conditions given.
 */
public class TransactionContainKeywordsPredicate implements Predicate<Transaction> {

    private TransactionType transactionType;
    private List<String> supplierNameKeywords;
    private List<String> goodNameKeywords;

    public TransactionContainKeywordsPredicate(TransactionType transactionType,
                                               List<String> supplierNameKeywords, List<String> goodNameKeywords) {
        requireAllNonNull(transactionType, supplierNameKeywords, goodNameKeywords);
        this.transactionType = transactionType;
        this.supplierNameKeywords = supplierNameKeywords;
        this.goodNameKeywords = goodNameKeywords;
    }

    // fulfill all 3 tests
    @Override
    public boolean test(Transaction transaction) {
        return testTransactionType(transaction)
                && testSupplierName(transaction)
                && testGoodName(transaction);
    }

    /**
     * filters valid transaction by transaction type.
     *
     * @param transaction to be filtered.
     * @return true if the transactionType is unspecified or the type of transaction matches the transactionType.
     */
    private boolean testTransactionType(Transaction transaction) {
        // transactionType unspecified
        if (transactionType.equals(TransactionType.EMPTY)) {
            return true;
        }
        // type of transaction matches the transactionType
        return (transactionType.equals(TransactionType.BUY_TRANSACTION) && transaction instanceof BuyTransaction)
                || (transactionType.equals(TransactionType.SELL_TRANSACTION) && transaction instanceof SellTransaction);
    }

    /**
     * filters valid transaction by supplier name.
     *
     * @param transaction must be buy transaction to have a supplier.
     * @return true if the supplier name is unspecified or the buy transaction contains supplier that have name that
     * partial matches the keyword in supplierName.
     */
    private boolean testSupplierName(Transaction transaction) {
        // supplier name is unspecified
        if (supplierNameKeywords.size() == 0) {
            return true;
        }
        // transaction type is invalid, since sellTransaction does not have a supplier
        if (transaction instanceof SellTransaction) {
            return false;
        }
        // valid transaction type with partial matching supplier name
        BuyTransaction buyTransaction = (BuyTransaction) transaction;
        return supplierNameKeywords.stream()
                        .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                                buyTransaction.getSupplier().getName().fullName, keyword));
    }

    /**
     * filters valid transaction by good name.
     *
     * @param transaction to be filtered.
     * @return true if the good name is unspecified or the transaction contains good that have good name that
     * partial matches the keyword in goodName.
     */
    private boolean testGoodName(Transaction transaction) {
        // goodName is unspecified
        if (goodNameKeywords.size() == 0) {
            return true;
        }
        // goodName partial matches
        return goodNameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        transaction.getGood().getGoodName().fullGoodName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionContainKeywordsPredicate // instanceof handles nulls
                && transactionType.equals(((TransactionContainKeywordsPredicate) other).transactionType))
                && supplierNameKeywords.equals(((TransactionContainKeywordsPredicate) other).supplierNameKeywords)
                && goodNameKeywords.equals(((TransactionContainKeywordsPredicate) other).goodNameKeywords);
    }

}
