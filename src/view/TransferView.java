package view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import controller.ViewManager;
import data.Database;
import model.BankAccount;
import model.User;

public class TransferView extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ViewManager manager;
	
	private JButton backButton;
	private JButton confirmButton;
	private JTextField transferralAmount;
	private JTextField decimalAmount;
	private BankAccount account;
	private JFormattedTextField accountNumField;
	private JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
	private BankAccount receiver;

	public TransferView(ViewManager manager) throws ParseException {
		super();
		
		this.manager = manager;
		initialize();
	}

	private void initialize() throws ParseException {
		initBackButton();
		initConfirmButton();
		initWithdrawField();
	}
	
	private void initWithdrawField() throws ParseException {
		JLabel transferral = new JLabel("Enter the amount to be transferred: $", SwingConstants.LEFT);
		transferral.setBounds(45, 100, 200, 35);
		transferral.setLabelFor(transferralAmount);
		
		transferralAmount = new JTextField();
		transferralAmount.setBounds(250, 100, 80, 35);
		transferralAmount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (transferralAmount.getText().length() >= 9)
					e.consume();
			}
		});
		
		JLabel decimal = new JLabel(".", SwingConstants.LEFT);
		decimal.setBounds(333, 100, 5, 35);
		decimal.setLabelFor(decimalAmount);
		
		decimalAmount = new JTextField();
		decimalAmount.setBounds(340, 100, 40, 35);
		decimalAmount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (decimalAmount.getText().length() >= 2)
					e.consume();
			}
		});
		
		JLabel accountNum = new JLabel("Enter the receiving account number: ", SwingConstants.LEFT);
		accountNum.setBounds(45, 150, 250, 35);
		accountNum.setLabelFor(accountNumField);
		
		MaskFormatter formatter = new MaskFormatter("#########");
		formatter.setValidCharacters("1234567890");
		accountNumField = new JFormattedTextField(formatter);
		accountNumField.setBounds(250, 150, 100, 35);
		
		this.add(transferral);
		this.add(decimal);
		this.add(accountNum);
		this.add(transferralAmount);
		this.add(decimalAmount);
		this.add(accountNumField);
	}
	
	private void initConfirmButton() {
		JLabel confirmLabel = new JLabel("Confirm");
		confirmLabel.setBounds(227, 200, 100, 35);
		confirmLabel.setLabelFor(confirmButton);
		this.add(confirmLabel);
		
		confirmButton = new JButton();
		confirmButton.setBounds(200, 200, 100, 35);
		confirmButton.addActionListener(this);
		this.add(confirmButton);
	}
	
	private void initBackButton() {
		backButton = new JButton();
		backButton.setBounds(5, 5, 50, 50);
		backButton.addActionListener(this);
		this.setLayout(null);
		
		JLabel label = new JLabel("Back", SwingConstants.CENTER);
		label.setBounds(5, 5, 50, 50);
		label.setLabelFor(backButton);
		
		this.add(label);
		this.add(backButton);
	}
	
	public void setAccount(BankAccount account) {
		this.account = account;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		errorLabel.setText("");
		errorLabel.setBounds(150, 250, 200, 35);
		this.add(errorLabel);
		
		if (object.equals(backButton)) {
			manager.switchTo(ATM.HOME_VIEW);
			transferralAmount.setText("");
			decimalAmount.setText("");
			accountNumField.setValue("");
		} else if (object.equals(confirmButton)) {
			String transferral1 = "0";
			String transferral2 = "00";
			
			if (!transferralAmount.getText().equals("")) {
				transferral1 = transferralAmount.getText();
			}
			if (!decimalAmount.getText().equals("")) {
				transferral2 = decimalAmount.getText();
			}
			
			String transferral = transferral1 + "." + transferral2;
			
			if (transferral.equals("0.00")) {
				errorLabel.setText("No transferral has been entered.");
				return;
			}
			
			receiver = manager.findAccount((Long.valueOf(accountNumField.getText())));
			if (receiver.getStatus() == 'N') {
				errorLabel.setText("Account has been deactivated");
				return;
			}
			
			if (receiver == null) {
				errorLabel.setText("Account does not exist.");
				return;
			} else if (accountNumField.getText().equals(String.valueOf(this.account.getAccountNumber()))) {
				errorLabel.setText("Cannot transfer within an account.");
				return;
			}
			
			double amount = 0;
			try {
				amount = Double.valueOf(transferral);
			} catch (NumberFormatException e1) {
				errorLabel.setText("Not a valid number.");
				return;
			}
			if (this.account == null) {
				errorLabel.setText("Account does not exist.");
				return;
			} else {
				int result = account.transfer(receiver, amount);
				if (result != ATM.ACCOUNT_NOT_FOUND && result == ATM.SUCCESS) {
					manager.updateAccount(account);
					manager.updateAccount(receiver);
					manager.setComponents("Home");
					transferralAmount.setText("");
					decimalAmount.setText("");
					accountNumField.setText("");
				} else if (result == ATM.INSUFFICIENT_FUNDS) {
					errorLabel.setText("Insufficient funds.");
					return;
				} else {
					errorLabel.setText("Invalid amount");
					return;
				}
			}
		}
	}
}