package cs2103_w14_2.inventory.testutil;

import java.util.Set;

import cs2103_w14_2.inventory.logic.commands.DeleteGoodPricePairFromSupplierCommand;

import cs2103_w14_2.inventory.model.good.GoodName;

/**
 * A utility class to help with building DeleteSupplierGoodName objects.
 */
public class DeleteSupplierGoodNameBuilder {

    private DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName descriptor;

    public DeleteSupplierGoodNameBuilder() {
        descriptor = new DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName();
    }

    public DeleteSupplierGoodNameBuilder(DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName descriptor) {
        this.descriptor = new DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName(descriptor);
    }

    /**
     * Set the {@code goodNames} to the {@code DeleteSupplierGoodName} that we are building.
     */
    public DeleteSupplierGoodNameBuilder withGoodNames(Set<GoodName> goodNames) {
        descriptor.setGoodNames(goodNames);
        return this;
    }

    public DeleteGoodPricePairFromSupplierCommand.DeleteSupplierGoodName build() {
        return descriptor;
    }
}
