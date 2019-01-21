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
import model.BankAccount;
import model.User;

public class DepositView extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ViewManager manager;
	
	private JButton backButton;
	private JTextField depositAmount;
	private JTextField decimalAmount;
	private JButton confirmButton;
	private JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
	private BankAccount account;

	public DepositView(ViewManager manager) throws ParseException {
		super();
		
		this.manager = manager;
		initialize();
	}

	private void initialize() throws ParseException {
		initBackButton();
		initDepositField();
		initConfirmButton();
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

	private void initDepositField() throws ParseException {
		JLabel deposit = new JLabel("Enter the deposit amount: $", SwingConstants.LEFT);
		deposit.setBounds(70, 100, 160, 35);
		deposit.setLabelFor(depositAmount);
		
		depositAmount = new JTextField();
		depositAmount.setBounds(230, 100, 80, 35);
		depositAmount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (depositAmount.getText().length() >= 9)
					e.consume();
			}
		});
		
		JLabel decimal = new JLabel(".", SwingConstants.LEFT);
		decimal.setBounds(313, 100, 5, 35);
		deposit.setLabelFor(decimalAmount);
		
		decimalAmount = new JTextField();
		decimalAmount.setBounds(320, 100, 40, 35);
		decimalAmount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (decimalAmount.getText().length() >= 2)
					e.consume();
			}
		});
		
		this.add(deposit);
		this.add(decimal);
		this.add(depositAmount);
		this.add(decimalAmount);
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
			depositAmount.setText("");
			decimalAmount.setText("");
		} else if (object.equals(confirmButton)) {
			String deposit1 = "0";
			String deposit2 = "00";
			
			if (!depositAmount.getText().equals("")) {
				deposit1 = depositAmount.getText();
			}
			if (!decimalAmount.getText().equals("")) {
				deposit2 = decimalAmount.getText();
			}
			
			String deposit = deposit1 + "." + deposit2;
			
			if (deposit.equals("0.00")) {
				errorLabel.setText("Nothing has been entered.");
				return;
			}
			
			double amount = 0;
			try {
				amount = Double.valueOf(deposit);
			} catch (NumberFormatException e1) {
				errorLabel.setText("Not a valid number.");
				return;
			}
			if (this.account == null) {
				errorLabel.setText("Account does not exist.");
				return;
			} else {
				if (account.deposit(amount) != ATM.INVALID_AMOUNT) {
					manager.updateAccount(account);
					manager.setComponents("Home");
					depositAmount.setText("");
					decimalAmount.setText("");
				} else {
					errorLabel.setText("Invalid amount.");
					return;
				}
			}
		}
	}
}