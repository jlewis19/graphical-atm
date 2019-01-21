package data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import model.BankAccount;

public class Database {
	
	/*
	 * Field names for database table: accounts.
	 */
	
	public static final String ACCOUNT_NUMBER = "account_number";
	public static final String PIN = "pin";
	public static final String BALANCE = "balance";
	public static final String LAST_NAME = "last_name";
	public static final String FIRST_NAME = "first_name";
	public static final String DOB = "dob";
	public static final String PHONE = "phone";
	public static final String STREET_ADDRESS = "street_address";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String ZIP = "zip";
	public static final String STATUS = "status";
	
	private Connection conn;			// a connection to the database
	private Statement stmt;				// the statement used to build inserts, updates and selects
	private ResultSet rs;				// result set used for selects
	private DatabaseMetaData meta;		// metadata about the database
	
	/**
	 * Constructs an instance (or object) of the Database class.
	 */
	
	public Database() {
		try {
			this.connect();
			this.setup();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	
	/**
	 * Retrieves an existing account by account number and PIN.
	 * 
	 * @param accountNumber
	 * @param pin
	 * @return
	 */
	
	public BankAccount getAccount(long accountNumber, int pin) {
		try {
			stmt = conn.createStatement();
			
			PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND pin = ?");
			selectStmt.setLong(1, accountNumber);
			selectStmt.setInt(2, pin);
			
			rs = selectStmt.executeQuery();
			if (rs.next()) {
				return new BankAccount(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Retrieves an existing account by account number.
	 * 
	 * @param accountNumber
	 * @return
	 */
	
	public BankAccount getAccount(long accountNumber) {
		try {
			stmt = conn.createStatement();
			
			PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM accounts WHERE account_number = ?");
			selectStmt.setLong(1, accountNumber);
			
			rs = selectStmt.executeQuery();
			if (rs.next()) {
				return new BankAccount(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Inserts an account into the database.
	 * 
	 * @param account
	 * @return true if the insert is successful; false otherwise.
	 */
	
	public boolean insertAccount(BankAccount account) {
		try {
			stmt = conn.createStatement();
			
			PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");		
			insertStmt.setLong(1, account.getAccountNumber());
			insertStmt.setInt(2, account.getUser().getPin());
			insertStmt.setDouble(3, account.getBalance());
			insertStmt.setString(4, account.getUser().getLastName());
			insertStmt.setString(5, account.getUser().getFirstName());
			insertStmt.setInt(6, account.getUser().getDob());
			insertStmt.setLong(7, account.getUser().getPhone());
			insertStmt.setString(8, account.getUser().getStreetAddress());
			insertStmt.setString(9, account.getUser().getCity());
			insertStmt.setString(10, account.getUser().getState());
			insertStmt.setString(11, account.getUser().getZip());
			insertStmt.setString(12, String.valueOf(account.getStatus()));
			
			insertStmt.executeUpdate();
			insertStmt.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Performs a soft delete of an account by setting the status to closed.
	 * 
	 * @param account
	 * @return true if the transaction is successful; false otherwise.
	 */
	
	public boolean closeAccount(BankAccount account) {
		try {
			stmt = conn.createStatement();
			
			PreparedStatement insertStmt = conn.prepareStatement("UPDATE accounts SET status = ? WHERE account_number = ?");		
			insertStmt.setString(1, "N");
			insertStmt.setLong(2, account.getAccountNumber());
			
			insertStmt.executeUpdate();
			insertStmt.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Updates all potentially edited fields (i.e., PIN, account balance, phone number,
	 * street address, city, state, and zip code).
	 * 
	 * @param account
	 * @return true if the transaction is successful; false otherwise.
	 */
	
	public boolean updateAccount(BankAccount account) {
		try {
			stmt = conn.createStatement();
			
			// all editable fields are included in this update statement
			PreparedStatement insertStmt = conn.prepareStatement(
				"UPDATE accounts SET " +
					"pin = ?, " +
					"balance = ?, " +
					"phone = ?, " +
					"street_address = ?, " +
					"city = ?, " +
					"state = ?, " +
					"zip = ? " +
				"WHERE account_number = ?"
			);		
			insertStmt.setInt(1, account.getUser().getPin());
			insertStmt.setDouble(2, account.getBalance());
			insertStmt.setLong(3, account.getUser().getPhone());
			insertStmt.setString(4, account.getUser().getStreetAddress());
			insertStmt.setString(5, account.getUser().getCity());
			insertStmt.setString(6, account.getUser().getState());
			insertStmt.setString(7, account.getUser().getZip());
			insertStmt.setLong(8, account.getAccountNumber());
			
			insertStmt.executeUpdate();
			insertStmt.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Shuts down the database, releasing all allocated resources.
	 * 
	 * @throws SQLException
	 */
	
	public void shutdown() throws SQLException {
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (conn != null) conn.close();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Establishes a connection to the database.
	 * 
	 * @throws SQLException
	 */
	
	private void connect() throws SQLException {
		Properties props = new Properties();
        props.put("user", "user1");
        props.put("password", "user1");

        try {
        	conn = DriverManager.getConnection("jdbc:derby:atm;create=true", props);
        } catch (SQLException e) {
        	
        }
	}
	
	/*
	 * Performs initial database setup.
	 * 
	 * @throws SQLException
	 */
	
	private void setup() throws SQLException {
		createAccountsTable();
		insertDefaultAccount();
	}
	
	/*
	 * Creates the initial accounts table. This will only be done once during initial setup.
	 * 
	 * @throws SQLException
	 */
	
	private void createAccountsTable() throws SQLException {
		meta = conn.getMetaData();
		rs = meta.getTables(null, "USER1", "ACCOUNTS", null);
		
		if (!rs.next()) {
			stmt = conn.createStatement();
			
			stmt.execute(
				"CREATE TABLE accounts (" +
					"account_number BIGINT PRIMARY KEY, " +
					"pin INT, " +
					"balance FLOAT, " +
					"last_name VARCHAR(20), " +
					"first_name VARCHAR(15), " +
					"dob INT, " +
					"phone BIGINT, " +
					"street_address VARCHAR(30), " +
					"city VARCHAR(30), " +
					"state VARCHAR(2), " +
					"zip VARCHAR(5), " +
					"status CHAR(1)" +
				")"
			);
		}
	}
	
	/*
	 * Inserts a default account into the database. This will only be done once during initial setup.
	 * 
	 * @throws SQLException
	 */
	
	private void insertDefaultAccount() throws SQLException {
		stmt = conn.createStatement();
		rs = stmt.executeQuery("SELECT COUNT(*) FROM accounts");
		if (rs.next() && rs.getInt(1) == 0) {
			PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			insertStmt.setLong(1, 100000001L);
			insertStmt.setInt(2, 1234);
			insertStmt.setDouble(3, 0.00);
			insertStmt.setString(4, "Wilson");
			insertStmt.setString(5, "Ryan");
			insertStmt.setInt(6, 19700707);
			insertStmt.setLong(7, 55555555555L);
			insertStmt.setString(8, "1776 Raritan Road");
			insertStmt.setString(9, "Scotch Plains");
			insertStmt.setString(10, "NJ");
			insertStmt.setString(11, "07065");
			insertStmt.setString(12, "Y");
			
			insertStmt.executeUpdate();
			insertStmt.close();
		}
	}
}
