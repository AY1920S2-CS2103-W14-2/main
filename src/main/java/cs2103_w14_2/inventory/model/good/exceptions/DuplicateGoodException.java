package cs2103_w14_2.inventory.model.good.exceptions;

/**
 * Signals that the operation will result in duplicate Goods (Goods are considered duplicates if they have the same
 * identity).
 */
public class DuplicateGoodException extends RuntimeException {
    public DuplicateGoodException() {
        super("Operation would result in duplicate goods");
    }
}
