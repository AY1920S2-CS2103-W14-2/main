package cs2103_w14_2.inventory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import cs2103_w14_2.inventory.commons.core.Config;
import cs2103_w14_2.inventory.commons.core.LogsCenter;
import cs2103_w14_2.inventory.commons.core.Version;
import cs2103_w14_2.inventory.commons.exceptions.DataConversionException;
import cs2103_w14_2.inventory.commons.util.ConfigUtil;
import cs2103_w14_2.inventory.commons.util.StringUtil;
import cs2103_w14_2.inventory.logic.Logic;
import cs2103_w14_2.inventory.logic.LogicManager;
import cs2103_w14_2.inventory.model.AddressBook;
import cs2103_w14_2.inventory.model.Inventory;
import cs2103_w14_2.inventory.model.Model;
import cs2103_w14_2.inventory.model.ModelManager;
import cs2103_w14_2.inventory.model.ReadOnlyList;
import cs2103_w14_2.inventory.model.ReadOnlyUserPrefs;
import cs2103_w14_2.inventory.model.TransactionHistory;
import cs2103_w14_2.inventory.model.UserPrefs;
import cs2103_w14_2.inventory.model.good.Good;
import cs2103_w14_2.inventory.model.supplier.Supplier;
import cs2103_w14_2.inventory.model.transaction.Transaction;
import cs2103_w14_2.inventory.model.util.SampleDataUtil;
import cs2103_w14_2.inventory.storage.AddressBookStorage;
import cs2103_w14_2.inventory.storage.InventoryStorage;
import cs2103_w14_2.inventory.storage.JsonAddressBookStorage;
import cs2103_w14_2.inventory.storage.JsonInventoryStorage;
import cs2103_w14_2.inventory.storage.JsonTransactionHistoryStorage;
import cs2103_w14_2.inventory.storage.JsonUserPrefsStorage;
import cs2103_w14_2.inventory.storage.Storage;
import cs2103_w14_2.inventory.storage.StorageManager;
import cs2103_w14_2.inventory.storage.TransactionHistoryStorage;
import cs2103_w14_2.inventory.storage.UserPrefsStorage;
import javafx.application.Application;
import javafx.stage.Stage;
import cs2103_w14_2.inventory.ui.Ui;
import cs2103_w14_2.inventory.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Inventory Manager ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        InventoryStorage inventoryStorage = new JsonInventoryStorage(userPrefs.getInventoryFilePath());
        TransactionHistoryStorage transactionHistoryStorage =
                new JsonTransactionHistoryStorage(userPrefs.getTransactionHistoryFilePath());
        storage = new StorageManager(addressBookStorage, inventoryStorage, transactionHistoryStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book,
     * {@code storage}'s inventory and {@code storage}'s transaction history and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     * The data from the sample inventory will be used instead if {@code storage}'s inventory is not found,
     * or an empty inventory will be used instead if errors occur when reading {@code storage}'s inventory.
     * The data from the sample transaction history will be used instead if {@code storage}'s  transaction history
     * is not found, or an empty  transaction history will be used instead if errors occur when reading
     * {@code storage}'s transaction history.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyList<Supplier>> addressBookOptional;
        Optional<ReadOnlyList<Good>> inventoryOptional;
        Optional<ReadOnlyList<Transaction>> transactionHistoryOptional;
        ReadOnlyList<Supplier> initialAddressBookData;
        ReadOnlyList<Good> initialInventoryData;
        ReadOnlyList<Transaction> initialTransactionHistoryData;

        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Address book data file not found. Will be starting with a sample AddressBook");
            }
            initialAddressBookData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Address book data file not in the correct format. "
                    + "Will be starting with an empty AddressBook");
            initialAddressBookData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the address book file."
                    + " Will be starting with an empty AddressBook");
            initialAddressBookData = new AddressBook();
        }

        try {
            inventoryOptional = storage.readInventory();
            if (!inventoryOptional.isPresent()) {
                logger.info("Inventory data file not found. Will be starting with a sample Inventory");
            }
            initialInventoryData = inventoryOptional.orElseGet(SampleDataUtil::getSampleInventory);
        } catch (DataConversionException e) {
            logger.warning("Inventory data file not in the correct format."
                    + " Will be starting with an empty Inventory");
            initialInventoryData = new Inventory();
        } catch (IOException e) {
            logger.warning("Problem while reading from the inventory file."
                    + " Will be starting with an empty Inventory");
            initialInventoryData = new Inventory();
        }

        try {
            transactionHistoryOptional = storage.readTransactionHistory();
            if (!transactionHistoryOptional.isPresent()) {
                logger.info("TransactionHistory data file not found."
                        + "Will be starting with a sample TransactionHistory");
            }
            initialTransactionHistoryData = transactionHistoryOptional
                    .orElseGet(SampleDataUtil::getSampleTransactionHistory);
        } catch (DataConversionException e) {
            logger.warning("TransactionHistory data file not in the correct format."
                    + " Will be starting with an empty TransactionHistory");
            initialTransactionHistoryData = new TransactionHistory();
        } catch (IOException e) {
            logger.warning("Problem while reading from the inventory file."
                    + " Will be starting with an empty TransactionHistory");
            initialTransactionHistoryData = new TransactionHistory();
        }

        return new ModelManager(initialAddressBookData, initialInventoryData, initialTransactionHistoryData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Inventory Manager " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Inventory Manager ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
