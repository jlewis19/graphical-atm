package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import controller.ViewManager;
import data.Database;
import model.BankAccount;
import model.User;

@SuppressWarnings("serial")
public class CreateView extends JPanel implements ActionListener {
	
	private int newAccountNum;
	private Database db = new Database();
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JTextField fNameField;
	private JTextField lNameField;
	private JFormattedTextField yearField;
	private JFormattedTextField phoneNumField1;
	private JFormattedTextField phoneNumField2;
	private JFormattedTextField phoneNumField3;
	private JTextField addressField;
	private JTextField cityField;
	private JFormattedTextField zipField;
	private JPasswordField pinField;
	private JButton createButton;
	private JButton backButton;
	private JComboBox<String> month;
	private JComboBox<String> day;
	private JComboBox<String> state;
	private JLabel errorLabel = new JLabel("", javax.swing.SwingConstants.CENTER);
	private JFrame confirm;
	private JButton confirmButton;
	
	/**
	 * Constructs an instance (or object) of the CreateView class.
	 * 
	 * @param manager
	 * @throws ParseException 
	 */
	
	public CreateView(ViewManager manager) throws ParseException {
		super();
		
		this.manager = manager;
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the CreateView components.
	 */
	
	private void initialize() throws ParseException {
		this.setLayout(null);
		
		initFNameField();
		initLNameField();
		initDatePicker();
		initPhoneNumField();
		initAddressField();
		initCityField();
		initStateDropDown();
		initZipField();
		initPinField();
		initCreateButton();
		initBackButton();
	}
	
	private void initFNameField() {
		JLabel label = new JLabel("First Name", SwingConstants.RIGHT);
		label.setBounds(100, 20, 95, 35);
		label.setLabelFor(fNameField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		fNameField = new JTextField(20);
		fNameField.setBounds(205, 20, 200, 35);
		
		this.add(label);
		this.add(fNameField);
	}

	private void initLNameField() {
		JLabel label = new JLabel("Last Name", SwingConstants.RIGHT);
		label.setBounds(100, 60, 95, 35);
		label.setLabelFor(lNameField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		lNameField = new JTextField(20);
		lNameField.setBounds(205, 60, 200, 35);
		
		this.add(label);
		this.add(lNameField);
	}

	private void initDatePicker() throws ParseException {
		JLabel label = new JLabel("Date of Birth", SwingConstants.RIGHT);
		label.setBounds(85, 100, 110, 35);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		// month dropdown
		String[] months = {"Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		month = new JComboBox<String>(months);
		month.setBounds(205, 100, 80, 35);
		
		// day dropdown
		String[] days = {"Day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		day = new JComboBox<String>(days);
		day.setBounds(290, 100, 60, 35);
		
		// enter year
		MaskFormatter formatter = new MaskFormatter("####");
		formatter.setValidCharacters("1234567890");
		yearField = new JFormattedTextField(formatter);
		yearField.setBounds(355, 100, 50, 35);
		
		label.setLabelFor(month);
		this.add(label);
		this.add(month);
		this.add(day);
		this.add(yearField);
	}

	private void initPhoneNumField() throws ParseException {
		JLabel label = new JLabel("Phone Number", SwingConstants.RIGHT);
		label.setBounds(85, 140, 110, 35);
		label.setLabelFor(phoneNumField1);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		MaskFormatter formatter = new MaskFormatter("###");
		formatter.setValidCharacters("1234567890");
		phoneNumField1 = new JFormattedTextField(formatter);
		phoneNumField1.setBounds(205, 140, 55, 35);
		
		JLabel dash1 = new JLabel("-", SwingConstants.RIGHT);
		dash1.setBounds(265, 140, 5, 35);
		
		phoneNumField2 = new JFormattedTextField(formatter);
		phoneNumField2.setBounds(275, 140, 55, 35);
		
		JLabel dash2 = new JLabel("-", SwingConstants.RIGHT);
		dash2.setBounds(335, 140, 5, 35);
		
		MaskFormatter formatter2 = new MaskFormatter("####");
		formatter2.setValidCharacters("1234567890");
		phoneNumField3 = new JFormattedTextField(formatter2);
		phoneNumField3.setBounds(345, 140, 60, 35);
		
		this.add(label);
		this.add(phoneNumField1);
		this.add(dash1);
		this.add(phoneNumField2);
		this.add(dash2);
		this.add(phoneNumField3);
	}

	private void initAddressField() {
		JLabel label = new JLabel("Address", SwingConstants.RIGHT);
		label.setBounds(85, 180, 110, 35);
		label.setLabelFor(addressField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		addressField = new JTextField(20);
		addressField.setBounds(205, 180, 200, 35);
		
		this.add(label);
		this.add(addressField);
	}

	private void initCityField() {
		JLabel label = new JLabel("City", SwingConstants.RIGHT);
		label.setBounds(85, 220, 110, 35);
		label.setLabelFor(cityField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		cityField = new JTextField(20);
		cityField.setBounds(205, 220, 200, 35);
		
		this.add(label);
		this.add(cityField);
	}

	private void initStateDropDown() {
		JLabel label = new JLabel("State", SwingConstants.RIGHT);
		label.setBounds(85, 260, 110, 35);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		String[] states = {"none", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
		state = new JComboBox<String>(states);
		state.setBounds(205, 260, 55, 35);
		
		label.setLabelFor(state);
		this.add(label);
		this.add(state);
	}

	private void initZipField() throws ParseException {
		JLabel label = new JLabel("Zip", SwingConstants.RIGHT);
		label.setBounds(260, 260, 35, 35);
		label.setLabelFor(zipField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		MaskFormatter formatter = new MaskFormatter("#####");
		formatter.setValidCharacters("1234567890");
		zipField = new JFormattedTextField(formatter);
		zipField.setBounds(305, 260, 100, 35);
		
		this.add(label);
		this.add(zipField);
	}

	private void initPinField() {
		JLabel label = new JLabel ("PIN", SwingConstants.RIGHT);
		label.setBounds(85, 300, 110, 35);
		label.setLabelFor(pinField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		pinField = new JPasswordField(20);
		pinField.setBounds(205, 300, 200, 35);
		
		this.add(label);
		this.add(pinField);
	}

	private void initCreateButton() {
		createButton = new JButton();
		createButton.setBounds(126, 360, 248, 35);
		createButton.addActionListener(this);
		
		JLabel label = new JLabel("Create", SwingConstants.CENTER);
		label.setBounds(0, 360, 500, 35);
		label.setLabelFor(createButton);
		
		this.add(label);
		this.add(createButton);
	}

	private void initBackButton() {
		backButton = new JButton();
		backButton.setBounds(5, 5, 50, 50);
		backButton.addActionListener(this);
		
		JLabel label = new JLabel("Back", SwingConstants.CENTER);
		label.setBounds(5, 5, 50, 50);
		label.setLabelFor(backButton);
		
		this.add(label);
		this.add(backButton);
	}
	
	private void initAccountConfirm(int accountNum) {
		String accountNumMessage = "Your account number is " + accountNum + ". Please record this and press \"OK\" to login.";
		
		JOptionPane.showMessageDialog(this, accountNumMessage, "Confirm Account Number", JOptionPane.INFORMATION_MESSAGE);
		
		manager.login(String.valueOf(accountNum), pinField.getPassword());
		fNameField.setText("");
		lNameField.setText("");
		month.setSelectedItem("Month");
		day.setSelectedItem("Day");
		yearField.setText("");
		phoneNumField1.setText("");
		phoneNumField2.setText("");
		phoneNumField3.setText("");
		addressField.setText("");
		cityField.setText("");
		state.setSelectedItem("none");
		zipField.setText("");
		pinField.setText("");
	}

	/*
	 * CreateView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The CreateView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the CreateView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		errorLabel.setLabelFor(createButton);
		errorLabel.setBounds(0, 400, 500, 35);
		this.add(errorLabel);
		
		if (source.equals(backButton)) {
			manager.switchTo(ATM.LOGIN_VIEW);		
		} else if (source.equals(createButton)) {
			if (fNameField.getText().equals("")) {
				errorLabel.setText("First name missing.");
				return;
			} else if (lNameField.getText().equals("")) {
				errorLabel.setText("Last name missing.");
				return;
			}
			
			for (int i = 0; i < 4; i++) {
				if (yearField.getText().length() < 4) {
					errorLabel.setText("Year missing/too short.");
					return;
				} else if (yearField.getText().charAt(i) < '0' || yearField.getText().charAt(i) > '9') {
					errorLabel.setText("Year missing/too short.");
					return;
				} else if (Integer.valueOf(yearField.getText()) < 1900 || Integer.valueOf(yearField.getText()) > 2019) {
					errorLabel.setText("Year cannot be before 1900 or past 2019.");
					return;
				}
			}
			
			for (int i = 0; i < 9; i++) {
				if (i < 3) {
					if (phoneNumField1.getText().length() < 3) {
						errorLabel.setText("Phone number missing/too short.");
						break;
					} else if (phoneNumField1.getText().charAt(0) == '0') {
						errorLabel.setText("Phone number missing/too short.");
						break;
					} else if (phoneNumField1.getText().charAt(i) < '0' || phoneNumField1.getText().charAt(i) > '9') {
						errorLabel.setText("Phone number missing/too short.");
						return;
					}
				} else if (i < 6) {
					if (phoneNumField2.getText().length() < 3) {
						errorLabel.setText("Phone number missing/too short.");
						break;
					} else if (phoneNumField2.getText().charAt(i - 3) < '0' || phoneNumField2.getText().charAt(i - 3) > '9') {
						errorLabel.setText("Phone number missing/too short.");
						return;
					}
				} else {
					if (phoneNumField3.getText().length() < 4) {
						errorLabel.setText("Phone number missing/too short.");
						break;
					} else if (phoneNumField3.getText().charAt(i - 6) < '0' || phoneNumField3.getText().charAt(i - 6) > '9') {
						errorLabel.setText("Phone number missing/too short.");
						return;
					}
				}
			}
			
			for (int i = 0; i < 5; i++) {
				if (zipField.getText().length() < 5) {
					errorLabel.setText("Zip code missing/too short.");
					return;
				} else if (zipField.getText().charAt(i) < '0' || zipField.getText().charAt(i) > '9') {
					errorLabel.setText("Zip code missing/too short.");
					return;
				}
			}
			
			if (addressField.getText().equals("")) {
				errorLabel.setText("Address missing.");
				return;
			} else if (cityField.getText().equals("")) {
				errorLabel.setText("City missing.");
				return;
			} else if (month.getSelectedItem().toString().equals("Month")) {
				errorLabel.setText("Month missing.");
				return;
			} else if (day.getSelectedItem().toString().equals("Day")) {
				errorLabel.setText("Day missing.");
				return;
			} else if (state.getSelectedItem().toString().equals("none")) {
				errorLabel.setText("State missing.");
				return;
			} else {
				String dob = yearField.getText();
				switch (month.getSelectedItem().toString()) {
					case "January":
						dob += "01";
						break;
					case "February":
						dob += "02";
						if (Integer.valueOf(day.getSelectedItem().toString()) > 29) {
							errorLabel.setText("Invalid date.");
							return;
						}
						break;
					case "March":
						dob += "03";
						break;
					case "April":
						dob += "04";
						if (Integer.valueOf(day.getSelectedItem().toString()) > 30) {
							errorLabel.setText("Invalid date.");
							return;
						}
						break;
					case "May":
						dob += "05";
						break;
					case "June":
						dob += "06";
						if (Integer.valueOf(day.getSelectedItem().toString()) > 30) {
							errorLabel.setText("Invalid date.");
							return;
						}
						break;
					case "July":
						dob += "07";
						break;
					case "August":
						dob += "08";
						break;
					case "September":
						dob += "09";
						if (Integer.valueOf(day.getSelectedItem().toString()) > 30) {
							errorLabel.setText("Invalid date.");
							return;
						}
						break;
					case "October":
						dob += "10";
						break;
					case "November":
						dob += "11";
						if (Integer.valueOf(day.getSelectedItem().toString()) > 30) {
							errorLabel.setText("Invalid date.");
							return;
						}
						break;
					case "December":
						dob += "12";
						break;
				}
				if (Integer.valueOf(day.getSelectedItem().toString()) < 10) {
					dob += "0" + day.getSelectedItem().toString();
				} else {
					dob += day.getSelectedItem().toString();
				}
				
				if (pinField.getPassword().length != 4) {
					errorLabel.setText("PIN must be 4 numbers.");
					return;
				} else {
					for (int i = 0; i < 4; i++) {
						if (String.valueOf(pinField.getPassword()).charAt(i) < '0' || String.valueOf(pinField.getPassword()).charAt(i) > '9') {
							errorLabel.setText("PIN must be 4 numbers.");
							return;
						}
					}
				}
				
				String phoneNum = phoneNumField1.getText() + phoneNumField2.getText() + phoneNumField3.getText();
				User user = new User(Integer.valueOf(String.valueOf(pinField.getPassword())), Integer.valueOf(dob), Long.valueOf(phoneNum), fNameField.getText(), lNameField.getText(), addressField.getText(), cityField.getText(), state.getSelectedItem().toString(), zipField.getText());
				
				BankAccount checkAccount;
				int numCheck = 100000000;
				do {
					numCheck++;
					checkAccount = db.getAccount(numCheck);
				} while (checkAccount != null);
				
				if (db.insertAccount(new BankAccount('Y', numCheck, 0.0, user))) {
					initAccountConfirm(numCheck);
					newAccountNum = numCheck;
				} else {
					errorLabel.setText("Creation failed.");
					return;
				}
			}
		}
	}
}