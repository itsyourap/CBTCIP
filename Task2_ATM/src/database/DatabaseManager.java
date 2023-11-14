package database;

import model.AccountModel;
import model.TransactionModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

/**
 * DatabaseManager is a singleton class that manages the database connection and provides methods to interact with the
 * database.
 * <p>
 * The database is initialized using the initializeDatabase.sql file in the same package.
 * The database is created if it doesn't exist.
 * The database is created with 3 tables:
 *  <ul>
 *      <li>accounts</li>
 *      <li>transaction_type</li>
 *      <li>transactions</li>
 * </ul>
 *
 * @author <a href="https://github.com/itsyourap">Ankan Pal</a>
 */
public class DatabaseManager {
    // Singleton instance
    private static DatabaseManager instance;

    // Database URL
    private static final String DB_URL = "jdbc:sqlite:Task2_ATM/atm_database.db";

    // Database initialization file
    private static final String DATABASE_INITIALIZATION_FILE = "initializeDatabase.sql";

    // Database connection
    private Connection conn;

    /**
     * Returns the singleton instance of DatabaseManager
     * @return DatabaseManager instance
     */
    public static DatabaseManager getInstance() {
        synchronized (DatabaseManager.class) {
            if (instance == null)
                instance = new DatabaseManager();
            return instance;
        }
    }

    /**
     * Private constructor to prevent instantiation
     * Initializes the database connection and creates the database if it doesn't exist
     */
    private DatabaseManager() {
        initializeDatabaseConnection();
        createDatabaseIfNotExists();
    }

    /**
     * Initializes the database connection
     * Prints an error message if the connection fails
     * Called by the constructor
     * @see DatabaseManager#DatabaseManager()
     */
    private void initializeDatabaseConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Couldn't Connect to Database...");
        }
    }

    /**
     * Creates the database if it does not already exist
     * Checks if tables 'accounts', 'transaction_type', and 'transactions' exist
     * If any of the tables do not exist, calls the initializeDatabase method
     * @see DatabaseManager#initializeDatabase()
     */
    private void createDatabaseIfNotExists() {
        if (!doesTableExist("accounts") || !doesTableExist("transaction_type") || !doesTableExist("transactions")) {
            initializeDatabase();
        }
    }

    /**
     * Checks if a table exists in the database
     *
     * @param tableName the name of the table to check
     * @return true if the table exists, false otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean doesTableExist(String tableName) {
        boolean tableExists = false;
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, tableName, new String[]{"TABLE"});

            if (tables.next()) {
                tableExists = true;
            }
        } catch (SQLException e) {
            System.out.println("Error checking table existence: " + e.getMessage());
        }
        return tableExists;
    }

    /**
     * Initializes the database by executing SQL queries from the {@link #DATABASE_INITIALIZATION_FILE}  file
     * Prints an error message if the execution fails
     * @see DatabaseManager#DATABASE_INITIALIZATION_FILE
     */
    private void initializeDatabase() {
        try (Statement statement = conn.createStatement()) {
            InputStream inputStream = getClass().getResourceAsStream(DATABASE_INITIALIZATION_FILE);
            assert inputStream != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder query = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                query.append(line);
            }
            reader.close();

            String[] queries = query.toString().split(";");

            for (String sqlQuery : queries) {
                if (!sqlQuery.trim().isEmpty()) {
                    statement.addBatch(sqlQuery);
                }
            }

            statement.executeBatch();
            System.out.println("Database Created Successfully");
        } catch (SQLException | IOException e) {
            System.out.println("Error executing SQL from file: " + e.getMessage());
        }
    }

    /**
     * Retrieves an account based on the provided user ID and user PIN.
     *
     * @param userId  the user ID of the account
     * @param userPin the user PIN of the account
     * @return the {@link AccountModel} object representing the account, or null if the account is not found or an error occurs
     * @see AccountModel
     */
    public AccountModel getAccount(String userId, int userPin) {
        String query = "SELECT id, user_id, user_name, balance FROM accounts WHERE user_id = ? AND user_pin = ?";
        AccountModel account = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setInt(2, userPin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                account = new AccountModel();
                account.setId(rs.getInt("id"));
                account.setUserId(rs.getString("user_id"));
                account.setUserName(rs.getString("user_name"));
                account.setBalance(rs.getDouble("balance"));
                account.setTransactions(getTransactionHistory(account));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving account: " + e.getMessage());
        }
        return account;
    }

    /**
     * Retrieves an account based on the provided user ID.
     *
     * @param userId  the user ID of the account
     * @return the {@link AccountModel} object representing the account, or null if the account is not found or an error occurs
     * @see AccountModel
     */
    public AccountModel getAccount(String userId) {
        String query = "SELECT id, user_id, user_name, balance FROM accounts WHERE user_id = ?";
        AccountModel account = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                account = new AccountModel();
                account.setId(rs.getInt("id"));
                account.setUserId(rs.getString("user_id"));
                account.setUserName(rs.getString("user_name"));
                account.setBalance(rs.getDouble("balance"));
                account.setTransactions(getTransactionHistory(account));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving account: " + e.getMessage());
        }
        return account;
    }

    /**
     * Retrieves an account based on the provided account ID.
     *
     * @param accountId  the account ID of the account
     * @return the {@link AccountModel} object representing the account,
     * or null if the account is not found or an error occurs
     * @see AccountModel
     */
    public AccountModel getAccount(long accountId) {
        String query = "SELECT id, user_id, user_name, balance FROM accounts WHERE id = ?";
        AccountModel account = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                account = new AccountModel();
                account.setId(rs.getInt("id"));
                account.setUserId(rs.getString("user_id"));
                account.setUserName(rs.getString("user_name"));
                account.setBalance(rs.getDouble("balance"));
                account.setTransactions(getTransactionHistory(account));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving account: " + e.getMessage());
        }
        return account;
    }

    /**
     * Adds a transaction to the database and updates the account balance.
     *
     * @param transaction  the transaction to be added
     * @return true if the transaction is successfully added, false otherwise
     */
    public boolean addTransaction(TransactionModel transaction) {
        String operation;
        if (
                transaction.getTransactionType() == TransactionModel.TransactionType.DEPOSIT ||
                        transaction.getTransactionType() == TransactionModel.TransactionType.TRANSFER_DEPOSIT
        )
            operation = "+";
        else
            operation = "-";

        try {
            conn.setAutoCommit(false);

            PreparedStatement addTransactionStatement =
                    conn.prepareStatement(
                            "INSERT INTO " +
                                    "transactions (account_id, transaction_type, amount, other_party_account_id) " +
                                    "VALUES (?, ?, ?, ?)"
                    );

            addTransactionStatement.setLong(1, transaction.getAccountId());
            addTransactionStatement.setInt(2, transaction.getTransactionType().getValue());
            addTransactionStatement.setDouble(3, transaction.getAmount());
            if (transaction.getOtherPartyAccountId() != 0)
                addTransactionStatement.setLong(4, transaction.getOtherPartyAccountId());
            else
                addTransactionStatement.setNull(4, Types.INTEGER);

            addTransactionStatement.executeUpdate();
            ResultSet rs = conn.prepareStatement("SELECT last_insert_rowid() AS id;").executeQuery();
            if (rs.next())
                transaction.setId(rs.getLong("id"));


            PreparedStatement balanceUpdateStatement = conn.prepareStatement(
                    "UPDATE accounts SET balance = balance " + operation + " ? WHERE id = ?"
            );
            balanceUpdateStatement.setDouble(1, transaction.getAmount());
            balanceUpdateStatement.setLong(2, transaction.getAccountId());
            balanceUpdateStatement.executeUpdate();
            conn.commit();

            if (transaction.getTransactionType() == TransactionModel.TransactionType.TRANSFER_WITHDRAW) {
                TransactionModel counterTransaction = new TransactionModel();
                counterTransaction.setAccountId(transaction.getOtherPartyAccountId());
                counterTransaction.setAmount(transaction.getAmount());
                counterTransaction.setTransactionType(TransactionModel.TransactionType.TRANSFER_DEPOSIT);
                counterTransaction.setOtherPartyAccountId(transaction.getAccountId());

                return addTransaction(counterTransaction);
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error in attempting transaction: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the transaction history for a given account.
     *
     * @param account the account for which to retrieve the transaction history
     * @return an ArrayList of TransactionModel objects representing the transaction history of the account
     */
    public ArrayList<TransactionModel> getTransactionHistory(AccountModel account) {
        ArrayList<TransactionModel> transactionList = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT t.id, t.account_id, tt.transaction_type, t.amount, t.other_party_account_id " +
                        "FROM transactions t " +
                        "LEFT JOIN transaction_type tt ON t.transaction_type = tt.id " +
                        "WHERE t.account_id = ?"
        )
        ) {

            stmt.setLong(1, account.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TransactionModel transaction = new TransactionModel();
                transaction.setId(rs.getLong("id"));
                transaction.setAccountId(rs.getLong("account_id"));
                transaction.setTransactionType(TransactionModel.TransactionType.valueOf(
                        rs.getString("transaction_type")
                ));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setOtherPartyAccountId(rs.getInt("other_party_account_id"));
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving transaction history: " + e.getMessage());
        }

        return transactionList;
    }
}
