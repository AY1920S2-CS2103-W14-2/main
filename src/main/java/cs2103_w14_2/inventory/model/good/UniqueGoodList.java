package cs2103_w14_2.inventory.model.good;

import static java.util.Objects.requireNonNull;
import static cs2103_w14_2.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import cs2103_w14_2.inventory.commons.util.CollectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import cs2103_w14_2.inventory.model.good.exceptions.DuplicateGoodException;
import cs2103_w14_2.inventory.model.good.exceptions.GoodNotFoundException;

/**
 * A list of goods that enforces uniqueness between its elements and does not allow nulls.
 * A good is considered unique by comparing using {@code Good#isSameGood(Good)}. As such, adding and updating of
 * suppliers uses Good#isSameGood(Good) for equality so as to ensure that the good being added or updated is
 * unique in terms of identity in the UniqueGoodList. However, the removal of a good uses Good#equals(Object) so
 * as to ensure that the good with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Good#isSameGood(Good)
 */
public class UniqueGoodList implements Iterable<Good> {

    private final ObservableList<Good> internalList = FXCollections.observableArrayList();
    private final ObservableList<Good> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent good as the given argument.
     */
    public boolean contains(Good toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(good -> good.isSameGood(toCheck));
    }

    /**
     * Adds a good to the list.
     * The good must not already exist in the list.
     */
    public void add(Good toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGoodException();
        }
        internalList.add(toAdd);
        sort();
    }

    /**
     * Replaces the good {@code target} in the list with {@code editedGood}.
     * {@code target} must exist in the list.
     * The good identity of {@code editedGood} must not be the same as another existing good in the list.
     */
    public void setGood(Good target, Good editedGood) {
        CollectionUtil.requireAllNonNull(target, editedGood);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GoodNotFoundException();
        }

        if (!target.isSameGood(editedGood) && contains(editedGood)) {
            throw new DuplicateGoodException();
        }

        internalList.set(index, editedGood);
        sort();
    }

    /**
     * Removes the equivalent good from the list.
     * The good must exist in the list.
     */
    public void remove(Good toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GoodNotFoundException();
        }
    }

    public void setGoods(UniqueGoodList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        sort();
    }

    /**
     * Replaces the contents of this list with {@code goods}.
     * {@code goods} must not contain duplicate goods.
     */
    public void setGoods(List<Good> goods) {
        CollectionUtil.requireAllNonNull(goods);
        if (!goodsAreUnique(goods)) {
            throw new DuplicateGoodException();
        }

        internalList.setAll(goods);
        sort();
    }

    /**
     * Sorts the good based on whether the good has quantity lower than its threshold. If the good has a quantity that
     * is lower than threshold, then it will be at a lower index. So there will be two sets of goods: "lower than
     * or equals to threshold" or "more than threshold". Within "lower or equals to threshold" set, the order is based
     * on when the threshold was set. Within "more than threshold" set, the order is based on when is the good added
     * into the inventory.
     */
    private void sort() {
        internalList.sort((a, b) ->
                a.isNoMoreThanThresholdQuantity() ? -1 : b.isNoMoreThanThresholdQuantity() ? 1 : -1);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Good> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Good> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGoodList // instanceof handles nulls
                && internalList.equals(((UniqueGoodList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code suppliers} contains only unique suppliers.
     */
    private boolean goodsAreUnique(List<Good> goods) {
        for (int i = 0; i < goods.size() - 1; i++) {
            for (int j = i + 1; j < goods.size(); j++) {
                if (goods.get(i).isSameGood(goods.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list,
     * or -1 if this list does not contain the element, Used to find the index of an existing good
     * so it's quantity can be retreived.
     *
     * @param toFind good to be found
     * @return index of good to be found in the internal list
     */
    public int indexOf(Good toFind) {
        for (int i = 0; i < internalList.size(); ++i) {
            if (internalList.get(i).isSameGood(toFind)) {
                return i;
            }
        }
        return -1;
    }
}
