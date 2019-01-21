package view;

import java.awt.Font;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import controller.ViewManager;
import model.BankAccount;
import model.User;

public class InfoView extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ViewManager manager;
	
	private boolean inEdit = false;
	private JButton backButton;
	private JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
	private BankAccount account;
	private JLabel accountNumLabel;
	private JLabel accountNum;
	private JLabel nameLabel;
	private JLabel name;
	private JLabel addressLabel;
	private JLabel address;
	private JLabel cityLabel;
	private JLabel city;
	private JLabel stateLabel;
	private JLabel state;
	private JLabel zipLabel;
	private JLabel zip;
	private JLabel birthLabel;
	private JLabel birth;
	private JLabel phoneNumLabel;
	private JLabel phoneNum;
	private JTextField addressField;
	private JTextField cityField;
	private JComboBox<String> stateField;
	private JFormattedTextField zipField;
	private JFormattedTextField phoneNumField;
	private JPasswordField pinField;
	private JButton editButton;
	private JLabel editLabel;
	private JButton cancelButton;
	private JLabel cancelLabel;
	private JButton pinButton;
	private JLabel pinLabel;

	public InfoView(ViewManager manager) throws ParseException {
		super();
		
		this.manager = manager;
		initialize();
	}

	private void initialize() throws ParseException {
		initBackButton();
		initLabels();
		initInfo();
		initEditButton();
		initCancelButton();
	}
	
	private void initPinView() {
		pinField = new JPasswordField(4);
		boolean isValid = true;
		boolean equals = false;
		String checkPass = "";
		String newPass = "";
		
		do {
			if (JOptionPane.showConfirmDialog(null, pinField, "Enter your current pin: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
				checkPass = String.valueOf(pinField.getPassword());
				pinField.setText("");
				if (checkPass.equals(String.valueOf(account.getUser().getPin()))) {
					equals = true;
					do {
						pinField.setText("");
						isValid = true;
						
						if (JOptionPane.showConfirmDialog(null, pinField, "Enter your new 4-digit pin: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.CANCEL_OPTION) {
							return;
						}
						
						newPass = String.valueOf(pinField.getPassword());
						if (newPass.length() != 4) {
							isValid = false;
						} else {
							for (int i = 0; i < 4; i++) {
								if (String.valueOf(pinField.getPassword()).charAt(i) < '0' || String.valueOf(pinField.getPassword()).charAt(i) > '9') {
									isValid = false;
								}
							}
						}
					} while (!isValid);
				}
			} else {
				return;
			}
		} while (!equals);
		
		account.getUser().setPin(Integer.valueOf(checkPass), Integer.valueOf(newPass));
	}

	private void initEditButton() {
		editButton = new JButton();
		editButton.setBounds(150, 350, 100, 50);
		editButton.addActionListener(this);
		
		editLabel = new JLabel("Edit Info", SwingConstants.CENTER);
		editLabel.setBounds(150, 350, 100, 50);
		editLabel.setLabelFor(editButton);
		
		this.add(editLabel);
		this.add(editButton);
	}
	
	private void initCancelButton() {
		cancelButton = new JButton();
		cancelButton.setBounds(250, 350, 100, 50);
		cancelButton.addActionListener(this);
		
		cancelLabel = new JLabel("Change PIN", SwingConstants.CENTER);
		cancelLabel.setBounds(250, 350, 100, 50);
		cancelLabel.setLabelFor(cancelButton);
		
		this.add(cancelLabel);
		this.add(cancelButton);
	}

	private void initInfo() {
		if (this.account == null) {
			return;
		}
		
		accountNum = new JLabel(String.valueOf(account.getAccountNumber()), SwingConstants.LEFT);
		accountNum.setBounds(250, 20, 100, 35);
	
		name = new JLabel(account.getUser().getFirstName() + " " + account.getUser().getLastName(), SwingConstants.LEFT);
		name.setBounds(250, 60, 100, 35);
		
		address = new JLabel(account.getUser().getStreetAddress(), SwingConstants.LEFT);
		address.setBounds(250, 100, 200, 35);
		
		city = new JLabel(account.getUser().getCity(), SwingConstants.LEFT);
		city.setBounds(250, 140, 100, 35);
		
		state = new JLabel(account.getUser().getState(), SwingConstants.LEFT);
		state.setBounds(250, 180, 100, 35);
		
		zip = new JLabel(account.getUser().getZip(), SwingConstants.LEFT);
		zip.setBounds(250, 220, 100, 35);
		
		birth = new JLabel(account.getUser().getFormattedDob(), SwingConstants.LEFT);
		birth.setBounds(250, 260, 100, 35);
		
		phoneNum = new JLabel(String.valueOf(account.getUser().getFormattedPhone()), SwingConstants.LEFT);
		phoneNum.setBounds(250, 300, 100, 35);
		
		this.add(accountNum);
		this.add(name);
		this.add(address);
		this.add(city);
		this.add(state);
		this.add(zip);
		this.add(birth);
		this.add(phoneNum);
	}

	private void initLabels() {
		accountNumLabel = new JLabel("Account Number: ", SwingConstants.RIGHT);
		accountNumLabel.setBounds(50, 20, 200, 35);
		accountNumLabel.setLabelFor(accountNum);
		accountNumLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		nameLabel = new JLabel("Name: ", SwingConstants.RIGHT);
		nameLabel.setBounds(150, 60, 100, 35);
		nameLabel.setLabelFor(name);
		nameLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		addressLabel = new JLabel("Street Address: ", SwingConstants.RIGHT);
		addressLabel.setBounds(50, 100, 200, 35);
		addressLabel.setLabelFor(address);
		addressLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		cityLabel = new JLabel("City: ", SwingConstants.RIGHT);
		cityLabel.setBounds(150, 140, 100, 35);
		cityLabel.setLabelFor(city);
		cityLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		stateLabel = new JLabel("State: ", SwingConstants.RIGHT);
		stateLabel.setBounds(150, 180, 100, 35);
		stateLabel.setLabelFor(state);
		stateLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		zipLabel = new JLabel("Postal Code: ", SwingConstants.RIGHT);
		zipLabel.setBounds(50, 220, 200, 35);
		zipLabel.setLabelFor(zip);
		zipLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		birthLabel = new JLabel("Date of Birth: ", SwingConstants.RIGHT);
		birthLabel.setBounds(50, 260, 200, 35);
		birthLabel.setLabelFor(birth);
		birthLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		phoneNumLabel = new JLabel("Phone Number: ", SwingConstants.RIGHT);
		phoneNumLabel.setBounds(50, 300, 200, 35);
		phoneNumLabel.setLabelFor(phoneNum);
		phoneNumLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		this.add(accountNumLabel);
		this.add(nameLabel);
		this.add(addressLabel);
		this.add(cityLabel);
		this.add(stateLabel);
		this.add(zipLabel);
		this.add(birthLabel);
		this.add(phoneNumLabel);
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
		
		initInfo();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		errorLabel.setText("");
		errorLabel.setBounds(150, 250, 200, 35);
		this.add(errorLabel);
		
		if (object.equals(backButton)) {
			manager.switchTo(ATM.HOME_VIEW);
			if (this.account != null) {
				accountNum.setText("");
				name.setText("");
				address.setText("");
				city.setText("");
				state.setText("");
				zip.setText("");
				birth.setText("");
				phoneNum.setText("");
			}
			if (inEdit) {
				this.remove(addressField);
				this.remove(cityField);
				this.remove(stateField);
				this.remove(zipField);
				this.remove(phoneNumField);
				this.revalidate();
				this.repaint();
				
				address.setText(account.getUser().getStreetAddress());
				city.setText(account.getUser().getCity());
				state.setText(account.getUser().getState());
				zip.setText(account.getUser().getZip());
				phoneNum.setText(account.getUser().getFormattedPhone());
				
				cancelLabel.setText("Change PIN");
				editLabel.setText("Edit Info");
				inEdit = false;
			}
		} else if (object.equals(cancelButton)) {
			if (!inEdit) {
				initPinView();
				return;
			}
			
			this.remove(addressField);
			this.remove(cityField);
			this.remove(stateField);
			this.remove(zipField);
			this.remove(phoneNumField);
			this.revalidate();
			this.repaint();
			
			this.add(address);
			this.add(city);
			this.add(state);
			this.add(zip);
			this.add(phoneNum);
			
			address.setText(account.getUser().getStreetAddress());
			city.setText(account.getUser().getCity());
			state.setText(account.getUser().getState());
			zip.setText(account.getUser().getZip());
			phoneNum.setText(account.getUser().getFormattedPhone());
			
			cancelLabel.setText("Change PIN");
			editLabel.setText("Edit Info");
			inEdit = false;
			return;
		} else if (object.equals(editButton)) {
			if (inEdit) {
				// set new values
				if (!addressField.getText().equals("")) {
					account.getUser().setStreetAddress(addressField.getText());
				}
				if (!cityField.getText().equals("")) {
					account.getUser().setCity(cityField.getText());
				}
				account.getUser().setState(String.valueOf(stateField.getSelectedItem()));
				boolean validZip = true;
				for (int i = 0; i < 5; i++) {
					if (zipField.getText().charAt(i) == '_') {
						validZip = false;
					}
				}
				if (validZip) {
					account.getUser().setZip(zipField.getText());
				}
				String findPhoneNum = "";
				boolean setNewPhone = true;
				for (int i = 0; i < phoneNumField.getText().length(); i++) {
					if (phoneNumField.getText().charAt(i) == '_') {
						i = phoneNumField.getText().length() + 1;
						setNewPhone = false;
					} else if (phoneNumField.getText().charAt(i) != '(' && phoneNumField.getText().charAt(i) != ')' && phoneNumField.getText().charAt(i) != ' ' && phoneNumField.getText().charAt(i) != '-') {
						findPhoneNum += phoneNumField.getText().charAt(i);
					}
				}
				if (setNewPhone) {
					account.getUser().setPhone(Long.valueOf(findPhoneNum));
				}
				
				this.remove(addressField);
				this.remove(cityField);
				this.remove(stateField);
				this.remove(zipField);
				this.remove(phoneNumField);
				this.revalidate();
				this.repaint();
				
				this.add(address);
				this.add(city);
				this.add(state);
				this.add(zip);
				this.add(phoneNum);
				
				address.setText(account.getUser().getStreetAddress());
				city.setText(account.getUser().getCity());
				state.setText(account.getUser().getState());
				zip.setText(account.getUser().getZip());
				phoneNum.setText(account.getUser().getFormattedPhone());
				
				cancelLabel.setText("Change PIN");
				editLabel.setText("Edit Info");
				inEdit = false;
				return;
			}
			
			cancelLabel.setText("Cancel");
			editLabel.setText("Save");
			
			MaskFormatter phoneFormatter;
			try {
				phoneFormatter = new MaskFormatter("(###) ###-####");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
			
			MaskFormatter zipFormatter;
			try {
				zipFormatter = new MaskFormatter("#####");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
			
			addressField = new JTextField();
			addressField.setText(account.getUser().getStreetAddress());
			addressField.setBounds(250, 100, 200, 35);
			this.add(addressField);
			this.remove(address);
			
			cityField = new JTextField();
			cityField.setText(account.getUser().getCity());
			cityField.setBounds(250, 140, 200, 35);
			this.add(cityField);
			this.remove(city);
			
			String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
			stateField = new JComboBox<String>(states);
			stateField.setSelectedItem(account.getUser().getState());
			stateField.setBounds(250, 180, 50, 35);
			this.add(stateField);
			this.remove(state);
			
			zipFormatter.setValidCharacters("1234567890");
			zipFormatter.setAllowsInvalid(false);
			zipFormatter.setPlaceholderCharacter('_');
			zipField = new JFormattedTextField(zipFormatter);
			zipField.setText(account.getUser().getZip());
			zipField.setBounds(250, 220, 100, 35);
			this.add(zipField);
			this.remove(zip);
			
			phoneFormatter.setValidCharacters("1234567890");
			phoneFormatter.setAllowsInvalid(false);
			phoneFormatter.setPlaceholderCharacter('_');
			phoneNumField = new JFormattedTextField(phoneFormatter);
			phoneNumField.setText(account.getUser().getFormattedPhone());
			phoneNumField.setBounds(250, 300, 100, 35);
			this.add(phoneNumField);
			this.remove(phoneNum);
			
			this.revalidate();
			this.repaint();
			inEdit = true;
		} else if (object.equals(pinButton)) {
			initPinView();
		}
	}
}