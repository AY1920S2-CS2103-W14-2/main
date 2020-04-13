package cs2103_w14_2.inventory.logic.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import cs2103_w14_2.inventory.commons.core.Messages;
import cs2103_w14_2.inventory.commons.core.index.Index;
import cs2103_w14_2.inventory.logic.parser.exceptions.ParseException;
import cs2103_w14_2.inventory.logic.commands.DeleteGoodPricePairFromSupplierCommand;
import cs2103_w14_2.inventory.model.good.GoodName;

/**
 * Parses input arguments and creates a new DeleteGoodPricePairFromSupplierCommand object
 */
public class DeleteGoodPricePairFromSupplierCommandParser implements Parser<DeleteGoodPricePairFromSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGoodPricePairFromSupplierCommand
     * and returns a DeleteGoodPricePairFromSupplierCommand object for execution.
     * @throws cs2103_w14_2.inventory.logic.parser.exceptions.ParseException if the user input does not conform the expected format
     */
    public DeleteGoodPricePairFromSupplierCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_GOOD_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteGoodPricePairFromSupplierCommand.MESSAGE_USAGE), pe);
        }

        //Throw exception if the user did not include any goodname.
        if (argMultimap.getAllValues(CliSyntax.PREFIX_GOOD_NAME).isEmpty()) {
            throw new ParseException(DeleteGoodPricePairFromSupplierCommand.MESSAGE_MUST_INCLUDE_GOODNAME);
        }

        DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName deleteSupplierGoodName =
                new DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName();

        parseGoodNamesForDelete(argMultimap.getAllValues(CliSyntax.PREFIX_GOOD_NAME))
                .ifPresent(deleteSupplierGoodName::setGoodNames);

        return new DeleteGoodPricePairFromSupplierCommand(index, deleteSupplierGoodName);
    }

    /**
     * Parses {@code Collection<String> goodNames} into a {@code Set<GoodName>} if {@code goodNames} is non-empty.
     */
    private Optional <Set<GoodName>> parseGoodNamesForDelete(Collection<String> goodNames) throws ParseException {
        assert goodNames != null;

        //This is to initialize a list of good name
        //if there is no good names, it will initialise as empty collection
        Collection<String> goodNameList = goodNames.size() == 1 && goodNames.contains("") ? Collections.emptyList()
                : goodNames;

        return Optional.of(ParserUtil.parseGoodNames(goodNameList));
    }
}
