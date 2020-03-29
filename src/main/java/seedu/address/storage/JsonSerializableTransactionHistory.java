package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.TransactionHistory;
import seedu.address.model.transaction.Transaction;

/**
 * An Immutable TransactionHistory that is serializable to JSON format.
 */
@JsonRootName(value = "transactionHistory")
class JsonSerializableTransactionHistory {

    public static final String MESSAGE_DUPLICATE_TRANSACTION = "Transactions list contains duplicate transaction(s).";

    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTransactionHistory} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableTransactionHistory(@JsonProperty("transactions") List<JsonAdaptedTransaction> transactions) {
        this.transactions.addAll(transactions);
    }

    /**
     * Converts a given {@code ReadOnlyTransactionHistory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTransactionHistory}.
     */
    public JsonSerializableTransactionHistory(ReadOnlyList<Transaction> source) {
        transactions.addAll(source.getReadOnlyList()
                .stream().map(JsonAdaptedTransaction::new).collect(Collectors.toList()));
    }

    /**
     * Converts this transaction history into the model's {@code TransactionHistory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TransactionHistory toModelType() throws IllegalValueException {
        TransactionHistory transactionHistory = new TransactionHistory();
        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            if (transactionHistory.hasTransaction(transaction)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TRANSACTION);
            }
            transactionHistory.addTransaction(transaction);
        }
        return transactionHistory;
    }

}
