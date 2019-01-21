package view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.BankAccount;
import model.User;

@SuppressWarnings("serial")
public class HomeView extends JPanel implements ActionListener {
	
	private JButton logoutButton;
	private BankAccount account;
	private User user;
	private JLabel name = new JLabel("", SwingConstants.LEFT);
	private JLabel accountNum = new JLabel("", SwingConstants.LEFT);
	private JLabel balance = new JLabel("", SwingConstants.LEFT);
	private JButton depositButton;
	private JButton withdrawButton;
	private JButton transferButton;
	private JButton infoButton;
	private JButton closeButton;
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	
	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public HomeView(ViewManager manager) {
		super();
		
		this.manager = manager;
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	private void initialize() {
		initLogoutButton();
		initButtons();
		initLabels();
	}

	private void initButtons() {
		depositButton = new JButton();
		depositButton.setBounds(100, 60, 300, 60);
		depositButton.addActionListener(this);
		
		JLabel depositLabel = new JLabel("Deposit", SwingConstants.CENTER);
		depositLabel.setBounds(100, 60, 300, 60);
		depositLabel.setLabelFor(depositButton);
		
		withdrawButton = new JButton();
		withdrawButton.setBounds(100, 140, 300, 60);
		withdrawButton.addActionListener(this);
		
		JLabel withdrawLabel = new JLabel("Withdraw", SwingConstants.CENTER);
		withdrawLabel.setBounds(100, 139, 310, 60);
		withdrawLabel.setLabelFor(withdrawButton);
		
		transferButton = new JButton();
		transferButton.setBounds(100, 220, 300, 60);
		transferButton.addActionListener(this);
		
		JLabel transferLabel = new JLabel("Transfer", SwingConstants.CENTER);
		transferLabel.setBounds(100, 219, 310, 60);
		transferLabel.setLabelFor(transferButton);
		
		infoButton = new JButton();
		infoButton.setBounds(100, 300, 300, 60);
		infoButton.addActionListener(this);
		
		JLabel infoLabel = new JLabel("Personal Information", SwingConstants.CENTER);
		infoLabel.setBounds(100, 300, 310, 60);
		infoLabel.setLabelFor(infoLabel);
		
		closeButton = new JButton();
		closeButton.setBounds(100, 380, 300, 60);
		closeButton.addActionListener(this);
		closeButton.setText("Close Account");
		
		this.add(depositLabel);
		this.add(withdrawLabel);
		this.add(transferLabel);
		this.add(infoLabel);
		this.add(depositButton);
		this.add(withdrawButton);
		this.add(transferButton);
		this.add(infoButton);
		this.add(closeButton);
	}

	public void initLabels() {
		DecimalFormat format = new DecimalFormat("###,###,###,##0.00");
		
		if (user != null) {
			name.setText("Name: " + user.getFirstName() + " " + user.getLastName());
		}
		if (account != null) {
			accountNum.setText("Account Number: " + String.valueOf(account.getAccountNumber()));
			balance.setText("Current Balance: " + format.format(account.getBalance()));
		}
		
		name.setBounds(60, 30, 400, 35);
		balance.setBounds(60, 15, 400, 35);
		accountNum.setBounds(60, 0, 200, 35);
		
		this.add(name);
		this.add(accountNum);
		this.add(balance);
	}

	private void initLogoutButton() {
		logoutButton = new JButton();
		logoutButton.setBounds(5, 5, 50, 50);
		logoutButton.addActionListener(this);
		this.setLayout(null);
		
		JLabel label = new JLabel("Logout", SwingConstants.LEFT);
		label.setBounds(10, 5, 50, 50);
		label.setLabelFor(logoutButton);
		
		this.add(label);
		this.add(logoutButton);
	}
	
	public void setLabels(BankAccount account) {
		this.account = account;
		this.user = account.getUser();
		
		DecimalFormat format = new DecimalFormat("###,###,###,##0.00");
		balance.setText("Current Balance: " + format.format(account.getBalance()));
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The HomeView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the HomeView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		
		if (object.equals(logoutButton)) {
			Object[] options = {"YES", "NO"};
			int selection = JOptionPane.showOptionDialog(null, "Do you wish to logout?", "Confirm Logout", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (selection == JOptionPane.OK_OPTION) {
				manager.logout();
			}
		} else if (object.equals(depositButton)) {
			manager.setComponents("Deposit");
		} else if (object.equals(withdrawButton)) {
			manager.setComponents("Withdraw");
		} else if (object.equals(transferButton)) {
			manager.setComponents("Transfer");
		} else if (object.equals(infoButton)) {
			manager.setComponents("Info");
		} else if (object.equals(closeButton)) {
			Object[] options = {"CONFIRM", "CANCEL"};
			int selection = JOptionPane.showOptionDialog(null, "If you hit \"Confirm,\" your account will be permanently deactivated.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			if (selection == JOptionPane.OK_OPTION) {
				account.setStatus('N');
				manager.close(this.account);
				manager.logout();
			}
		}
	}
}