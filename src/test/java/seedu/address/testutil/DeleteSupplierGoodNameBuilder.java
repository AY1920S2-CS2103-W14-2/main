package seedu.address.testutil;

import java.util.Set;

import seedu.address.logic.commands.DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName;

import seedu.address.model.good.GoodName;

/**
 * A utility class to help with building DeleteSupplierGoodName objects.
 */
public class DeleteSupplierGoodNameBuilder {

    private DeleteSupplierGoodName descriptor;

    public DeleteSupplierGoodNameBuilder() {
        descriptor = new DeleteSupplierGoodName();
    }

    public DeleteSupplierGoodNameBuilder(DeleteSupplierGoodName descriptor) {
        this.descriptor = new DeleteSupplierGoodName(descriptor);
    }

    /**
     * Set the {@code goodNames} to the {@code DeleteSupplierGoodName} that we are building.
     */
    public DeleteSupplierGoodNameBuilder withGoodNames(Set<GoodName> goodNames) {
        descriptor.setGoodNames(goodNames);
        return this;
    }

    public DeleteSupplierGoodName build() {
        return descriptor;
    }
}
