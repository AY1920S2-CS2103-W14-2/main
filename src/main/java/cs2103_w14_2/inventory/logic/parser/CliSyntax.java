package cs2103_w14_2.inventory.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_CONTACT = new Prefix("c/");
    public static final Prefix PREFIX_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_GOOD_NAME = new Prefix("g/");
    public static final Prefix PREFIX_EXPIRY_DATE = new Prefix("x/");
    public static final Prefix PREFIX_TRANSACTION_DATE = new Prefix("d/");
    public static final Prefix PREFIX_OFFER = new Prefix("gp/");

}
