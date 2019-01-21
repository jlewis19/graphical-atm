package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import controller.ViewManager;
import data.Database;
import view.ATM;

public class BankAccount {
	
	private char status;			// account open/closed status (Y for open, N for closed)
	private long accountNumber;		// account number (a 9-digit number)
	private double balance;			// account balance (restricted to two places after the decimal)
	private User user;				// account holder (see User class)
	
	/**
	 * Constructs an instance (or object) of the BankAccount class.
	 * 
	 * @param status
	 * @param accountNumber
	 * @param balance
	 * @param user
	 */
	
	public BankAccount(char status, long accountNumber, double balance, User user) {
		this.status = status;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.user = user;
	}
	
	/**
	 * Constructs an instance (or object) of the BankAccount class from a ResultSet.
	 * 
	 * @param rs
	 * @throws SQLException 
	 */
	
	public BankAccount(ResultSet rs) throws SQLException {	
		this(rs.getString(Database.STATUS).charAt(0), rs.getLong(Database.ACCOUNT_NUMBER), rs.getDouble(Database.BALANCE), new User(rs));
	}
	
	///////////////////// GETTERS & SETTERS ///////////////////////////////////////////
	
	/**
	 * Retrieves the account status.
	 * 
	 * @return status
	 */
	
	public char getStatus() {
		return status;
	}
	
	/**
	 * Retrieves the account number.
	 * 
	 * @return accountNumber
	 */
	
	public long getAccountNumber() {
		return accountNumber;
	}
	
	/**
	 * Retrieves the account balance.
	 * 
	 * @return balance
	 */
	
	public double getBalance() {
		return balance;
	}
	
	/**
	 * Retrieves the user associated with this account.
	 * 
	 * @return user
	 */
	
	public User getUser() {
		return user;
	}
	
	/**
	 * Updates the account status.
	 * 
	 * @param status
	 */
	
	public void setStatus(char status) {
		this.status = status;
	}
	
	/**
	 * Updates the user associated with this account.
	 * 
	 * @param user
	 */
	
	public void setUser(User user) {
		this.user = user;
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	
	/**
	 * Deposits funds into this account.
	 * 
	 * @param amount the money to deposit
	 * @return a status code (0: invalid amount, 3: success)
	 */
	
	public int deposit(double amount) {
		if (amount <= 0) {
			return ATM.INVALID_AMOUNT;
		} else {
			balance = balance + amount;

			return ATM.SUCCESS;
		}
	}
	
	/**
	 * Withdraws funds from this account.
	 * 
	 * @param amount the money to withdraw
	 * @return a status code (0: invalid amount, 2: insufficient funds, 3: success)
	 */
	
	public int withdraw(double amount) {
		if (amount <= 0) {
			return ATM.INVALID_AMOUNT;
		} else if (amount > balance) {
			return ATM.INSUFFICIENT_FUNDS;
		} else {
			balance = balance - amount;

			return ATM.SUCCESS;
		}
	}
	
	/**
	 * Transfers funds from this account to another account.
	 * 
	 * @param destination
	 * @param amount
	 * @return a status code (0: invalid amount, 1: insufficient funds, 2: account not found, 3: success)
	 */
	
	public int transfer(BankAccount destination, double amount) {
		if (destination == null) {
			return ATM.ACCOUNT_NOT_FOUND;
		} else {
			int status = this.withdraw(amount);
			
			if (status == ATM.SUCCESS) {
				status = destination.deposit(amount);
			}
			
			return status;
		}
	}
	
	public String getFormattedBalance() {
		return NumberFormat.getCurrencyInstance(Locale.US).format(balance);
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////

	/*
	 * Generates a String representation of the BankAccount
	 * 
	 * @return
	 */
	
	@Override
	public String toString() {
		return "{ Account No.: " + accountNumber + ", Balance: " + getFormattedBalance() + " }";	// modify as needed
	}
}
