package controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import data.Database;
import model.BankAccount;
import view.ATM;
import view.DepositView;
import view.HomeView;
import view.InfoView;
import view.LoginView;
import view.TransferView;
import view.WithdrawView;

public class ViewManager {
	
	private Container views;				// the collection of all views in the application
	private Database db;					// a reference to the database
	private BankAccount account;			// the user's bank account
	private BankAccount destination;		// an account to which the user can transfer funds
	
	/**
	 * Constructs an instance (or object) of the ViewManager class.
	 * 
	 * @param layout
	 * @param container
	 */
	
	public ViewManager(Container views) {
		this.views = views;
		this.db = new Database();
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	
	/**
	 * Routes a login request from the LoginView to the Database.
	 * 
	 * @param accountNumber
	 * @param pin
	 */
	
	public void login(String accountNumber, char[] pin) {
		try {
			account = db.getAccount(Long.valueOf(accountNumber), Integer.valueOf(new String(pin)));
		} catch (NumberFormatException e) {
			account = null;
		}
			
		if (account == null) {
			LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
			lv.updateErrorMessage("Invalid account number and/or PIN.");
		} else {
			if (account.getStatus() == 'N') {
				LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
				lv.updateErrorMessage("This account has been deactivated.");
				return;
			}
			
			HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
			hv.setLabels(account);
			hv.initLabels();
			
			switchTo(ATM.HOME_VIEW);
			
			LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
			lv.updateErrorMessage("");
		}
	}
	
	public BankAccount getAccount() {
		return account;
	}
	
	public void setComponents(String view) {
		if (view.equals("Deposit")) {
			DepositView dv = ((DepositView) views.getComponents()[ATM.DEPOSIT_VIEW_INDEX]);
			dv.setAccount(account);
			switchTo(ATM.DEPOSIT_VIEW);
		} else if (view.equals("Home")) {
			HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
			hv.setLabels(account);
			switchTo(ATM.HOME_VIEW);
		} else if (view.equals("Withdraw")) {
			WithdrawView wv = ((WithdrawView) views.getComponents()[ATM.WITHDRAW_VIEW_INDEX]);
			wv.setAccount(account);
			switchTo(ATM.WITHDRAW_VIEW);
		} else if (view.equals("Transfer")) {
			TransferView tv = ((TransferView) views.getComponents()[ATM.TRANSFER_VIEW_INDEX]);
			tv.setAccount(account);
			switchTo(ATM.TRANSFER_VIEW);
		} else if (view.equals("Info")) {
			InfoView iv = ((InfoView) views.getComponents()[ATM.INFO_VIEW_INDEX]);
			iv.setAccount(account);
			switchTo(ATM.INFO_VIEW);
		}
	}
	
	public void updateAccount(BankAccount account) {
		db.updateAccount(account);
	}
	
	public BankAccount findAccount(Long accountNum) {
		return db.getAccount(accountNum);
	}
	
	public void logout() {
		account = null;
		switchTo(ATM.LOGIN_VIEW);
	}
	
	/**
	 * Switches the active (or visible) view upon request.
	 * 
	 * @param view
	 */
	
	public void switchTo(String view) {
		((CardLayout) views.getLayout()).show(views, view);
	}
	
	public void close(BankAccount account) {
		db.closeAccount(account);
	}
	
	/**
	 * Routes a shutdown request to the database before exiting the application. This
	 * allows the database to clean up any open resources it used.
	 */
	
	public void shutdown() {
		try {			
			int choice = JOptionPane.showConfirmDialog(
				views,
				"Are you sure?",
				"Shutdown ATM",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			
			if (choice == 0) {
				db.shutdown();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
