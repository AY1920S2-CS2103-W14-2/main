package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.FindTransactionCommand;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.TransactionContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTransactionCommand object
 */
public class FindTransactionCommandParser implements Parser<FindTransactionCommand> {

    /**
     * defines the type of transaction
     */
    public enum TransactionType {
        BUY_TRANSACTION, SELL_TRANSACTION, EMPTY
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindTransactionCommand
     * and returns a FindTransactionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTransactionCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // find transaction by type of transaction, name of supplier, name of good.
        // or any combination of the above
        // more factor -> more constrain
        // if there are multiple input for name, good name, all inputs will be taken.
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_GOOD_NAME);

        // preamble stores the type of transaction
        TransactionType typeOfTransaction = parseTypeOfTransaction(argMultimap);

        // name stores the name of supplier
        String[] supplierNameKeywords = setKeywords(argMultimap, PREFIX_NAME);

        // good name stores the name of the good
        String[] goodNameKeywords = setKeywords(argMultimap, PREFIX_GOOD_NAME);

        // at least one field must be provided
        if (typeOfTransaction.equals(TransactionType.EMPTY)
                && supplierNameKeywords.length == 0 && goodNameKeywords.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindTransactionCommand.MESSAGE_NO_FIELD_PROVIDED));
        }

        return new FindTransactionCommand(
                new TransactionContainKeywordsPredicate(typeOfTransaction,
                        Arrays.asList(supplierNameKeywords), Arrays.asList(goodNameKeywords)));
    }

    /**
     * parses values in the prefix
     * @param argMultimap stores the input
     * @param prefix can be supplier name or good name
     * @return arrays of the individual words in the input for particular prefix, if the input is empty (which means
     * this prefix is unspecified, return an empty array
     * @throws ParseException if the prefix is given, but the value is empty
     */
    private String[] setKeywords(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        Optional<String> words = argMultimap.getValue(prefix);
        // prefix is absent, return empty array
        if (!words.isPresent()) {
            return new String[0];
        }
        // prefix is present, but the value is empty
        if (words.get().equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTransactionCommand.EMPTY_VALUE_WITH_PREFIX));
        }
        // prefix is present and the value is valid
        return words.get().split("\\s+");
    }

    /**
     * parses type of transaction.
     * @param argMultimap stores the input
     * @return type of transaction: buy, sell or empty(which means the type of transaction is unspecified)
     * @throws ParseException if the type of transaction is invalid
     */
    private TransactionType parseTypeOfTransaction(ArgumentMultimap argMultimap) throws ParseException {
        switch (argMultimap.getPreamble().trim()) {
        case "":
            return TransactionType.EMPTY;
        case BuyCommand.COMMAND_WORD:
            return TransactionType.BUY_TRANSACTION;
        case SellCommand.COMMAND_WORD:
            return TransactionType.SELL_TRANSACTION;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindTransactionCommand.INVALID_TRANSACTION_TYPE));
        }
    }

}
