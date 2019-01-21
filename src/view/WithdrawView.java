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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.BankAccount;
import model.User;

public class WithdrawView extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ViewManager manager;
	
	private JButton backButton;
	private JButton confirmButton;
	private JTextField withdrawalAmount;
	private JTextField decimalAmount;
	private BankAccount account;
	private JLabel errorLabel = new JLabel("", SwingConstants.CENTER);

	public WithdrawView(ViewManager manager) throws ParseException {
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
		JLabel withdrawal = new JLabel("Enter the withdrawal amount: $", SwingConstants.LEFT);
		withdrawal.setBounds(55, 100, 180, 35);
		withdrawal.setLabelFor(withdrawalAmount);
		
		withdrawalAmount = new JTextField();
		withdrawalAmount.setBounds(230, 100, 80, 35);
		withdrawalAmount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (withdrawalAmount.getText().length() >= 9)
					e.consume();
			}
		});
		
		JLabel decimal = new JLabel(".", SwingConstants.LEFT);
		decimal.setBounds(313, 100, 5, 35);
		decimal.setLabelFor(decimalAmount);
		
		decimalAmount = new JTextField();
		decimalAmount.setBounds(320, 100, 40, 35);
		decimalAmount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (decimalAmount.getText().length() >= 2)
					e.consume();
			}
		});
		
		this.add(withdrawal);
		this.add(decimal);
		this.add(withdrawalAmount);
		this.add(decimalAmount);
	}
	
	private void initConfirmButton() {
		JLabel confirmLabel = new JLabel("Confirm");
		confirmLabel.setBounds(227, 170, 100, 35);
		confirmLabel.setLabelFor(confirmButton);
		this.add(confirmLabel);
		
		confirmButton = new JButton();
		confirmButton.setBounds(200, 170, 100, 35);
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
			withdrawalAmount.setText("");
			decimalAmount.setText("");
		} else if (object.equals(confirmButton)) {
			String withdrawal1 = "0";
			String withdrawal2 = "00";
			
			if (!withdrawalAmount.getText().equals("")) {
				withdrawal1 = withdrawalAmount.getText();
			}
			if (!decimalAmount.getText().equals("")) {
				withdrawal2 = decimalAmount.getText();
			}
			
			String withdrawal = withdrawal1 + "." + withdrawal2;
			
			if (withdrawal.equals("0.00")) {
				errorLabel.setText("Nothing has been entered.");
				return;
			}
			
			double amount = 0;
			try {
				amount = Double.valueOf(withdrawal);
			} catch (NumberFormatException e1) {
				errorLabel.setText("Not a valid number.");
				return;
			}
			if (this.account == null) {
				errorLabel.setText("Account does not exist.");
				return;
			} else {
				int result = account.withdraw(amount);
				if (result != ATM.INVALID_AMOUNT && result != ATM.INSUFFICIENT_FUNDS) {
					manager.updateAccount(account);
					manager.setComponents("Home");
					withdrawalAmount.setText("");
					decimalAmount.setText("");
				} else if (result == ATM.INSUFFICIENT_FUNDS){
					errorLabel.setText("Insufficient funds.");
					return;
				} else {
					errorLabel.setText("Invalid amount.");
					return;
				}
			}
		}
	}
}