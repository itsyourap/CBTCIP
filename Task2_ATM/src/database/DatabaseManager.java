package database;

import model.AccountModel;
import model.TransactionModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static DatabaseManager instance;

    private static final String DB_URL = "jdbc:sqlite:Task2_ATM/atm_database.db";
    private Connection conn;

    public static DatabaseManager getInstance() {
        synchronized (DatabaseManager.class) {
            if (instance == null)
                instance = new DatabaseManager();
            return instance;
        }
    }

    private DatabaseManager() {
        initializeDatabaseConnection();
        createDatabaseIfNotExists();
    }

    private void initializeDatabaseConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Couldn't Connect to Database...");
        }
    }

    private void createDatabaseIfNotExists() {
        if (!doesTableExist("accounts") || !doesTableExist("transaction_type") || !doesTableExist("transactions")) {
            initializeDatabase();
        }
    }

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

    private void initializeDatabase() {
        try (Statement statement = conn.createStatement()) {
            InputStream inputStream = getClass().getResourceAsStream("initializeDatabase.sql");
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

    public AccountModel getAccount(String userId, int userPin) {
        // TODO: Get Account Details from userId and PIN
        return null;
    }

    public AccountModel getAccount(String userId) {
        // TODO: Get Account Details from userId
        return null;
    }

    public boolean addTransaction(TransactionModel transaction) {
        // TODO: Implement Add Transaction Logic
        return false;
    }

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
