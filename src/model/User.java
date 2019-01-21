package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.Database;

public class User {
	
	private int pin;					// the user's PIN (a 4-digit between between 0000 and 9999)
	private int dob;					// the user's date of birth (an 8-digit number stored as YYYYMMDD)
	private long phone;					// the user's phone number (a 10-digit number)
	private String firstName;			// the user's first name
	private String lastName;			// the user's last name
	private String streetAddress;		// the user's street address (i.e., 123 Main Street)
	private String city;				// the user's city of residence
	private String state;				// the user's state of residence (2-letter abbreviation)
	private String zip;					// the user's zip code (a 5-digit number, stored as a String)
	
	/**
	 * Constructs an instance (or object) of the User class.
	 * 
	 * @param pin
	 * @param dob
	 * @param phone
	 * @param first
	 * @param last
	 * @param address
	 * @param city
	 * @param state
	 * @param zip
	 */
	
	public User(int pin, int dob, long phone, String firstName, String lastName, String streetAddress, String city, String state, String zip) {
		this.pin = pin;
		this.dob = dob;
		this.phone = phone;
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	/**
	 * Constructs an instance (or object) of the User class from a ResultSet.
	 * 
	 * @param rs
	 * @throws SQLException 
	 */
	
	public User(ResultSet rs) throws SQLException {
		this.pin = rs.getInt(Database.PIN);
		this.dob = rs.getInt(Database.DOB);
		this.phone = rs.getLong(Database.PHONE);
		this.firstName = rs.getString(Database.FIRST_NAME);
		this.lastName = rs.getString(Database.LAST_NAME);
		this.streetAddress =rs.getString(Database.STREET_ADDRESS);
		this.city = rs.getString(Database.CITY);
		this.state = rs.getString(Database.STATE);
		this.zip = rs.getString(Database.ZIP);
	}
	
	/////////////////////////////////// GETTERS AND SETTERS ///////////////////////////////////
	
	/**
	 * Retrieves the user's PIN.
	 * 
	 * @return pin
	 */
	
	public int getPin() {
		return pin;
	}
	
	/**
	 * Retrieves the user's date of birth.
	 * 
	 * @return dob
	 */
	
	public int getDob() {
		return dob;
	}
	
	/**
	 * Retrieves the user's phone number.
	 * 
	 * @return phone
	 */
	
	public long getPhone() {
		return phone;
	}
	
	/**
	 * Retrieves the user's first name.
	 * 
	 * @return firstName
	 */
	
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Retrieves the user's last name.
	 * 
	 * @return lastName
	 */
	
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Retrieves the user's street address.
	 * 
	 * @return streetAddress
	 */
	
	public String getStreetAddress() {
		return streetAddress;
	}
	
	/**
	 * Retrieves the user's city of residence.
	 * 
	 * @return city
	 */
	
	public String getCity() {
		return city;
	}
	
	/**
	 * Retrieves the user's state of residence.
	 * 
	 * @return state
	 */
	
	public String getState() {
		return state;
	}
	
	/**
	 * Retrieves the user's postal code.
	 * 
	 * @return zip
	 */
	
	public String getZip() {
		return zip;
	}
	
	/**
	 * Updates the user's PIN.
	 * 
	 * @param current
	 * @param pin
	 */
	
	public void setPin(int current, int pin) {
		if (isValidPin(pin, current)) {
			this.pin = pin;
		}
	}
		
	/**
	 * Updates the user's phone number.
	 * 
	 * @param phone
	 */
	
	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	/**
	 * Updates the user's street address.
	 * 
	 * @param streetAddress
	 */
	
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	/**
	 * Updates the user's city of residence.
	 * 
	 * @param city
	 */
	
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Updates the user's state of residence.
	 * 
	 * @param state
	 */
	
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Updates the user's postal code.
	 * 
	 * @param zip
	 */
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	/////////////////////////////////// INSTANCE METHODS ///////////////////////////////////
	
	/**
	 * Formats first and last names as a full name.
	 *  
	 * @param firstName
	 * @param lastLast
	 * @return the user's full name (first and last)
	 */
	
	public String getName() {
		return firstName + " " + lastName;
	}
	
	/**
	 * Formats raw phone numbers as (555) 555-5555.
	 * 
	 * @param phone
	 * @return the user's phone number formatted as (###) ###-####.
	 */
	
	public String getFormattedPhone() {
		return "(" + Integer.parseInt(String.valueOf(phone).substring(0, 3)) + ") " +
			Integer.parseInt(String.valueOf(phone).substring(3, 6)) + "-" +
			Integer.parseInt(String.valueOf(phone).substring(6, 10));
	}
	
	/**
	 * Formats raw date values as string representations.
	 * 
	 * @param dob
	 * @return the user's date of birth formatted as Month DD, YYYY
	 */
	
	public String getFormattedDob() {
		int year = Integer.parseInt(String.valueOf(dob).substring(0, 4));
		int month = Integer.parseInt(String.valueOf(dob).substring(4, 6));
		int day = Integer.parseInt(String.valueOf(dob).substring(6, 8));
		
		return getMonth(month) + " " + day + ", " + year;
	}
	
	/**
	 * Formats the city, state, and postal code portion of an address.
	 * 
	 * @return the user's address formatted as City, State Zip.
	 */
	
	public String getFormattedAddress() {
		return city + ", " + state + " " + zip;
	}
	
	/////////////////////////////////// PRIVATE METHODS ///////////////////////////////////
	
	/**
	 * Determines the validity of a user's PIN.
	 * 
	 * @param pin
	 * @return true if the PIN is valid; false otherwise.
	 */
	
	private boolean isValidPin(int pin, int current) {
		if (pin < 0 || pin > 9999) {
			return false;
		}
		
		if (this.pin != current) {
			return false;
		}
		
		return true;
	}
	/*
	 * Converts a numeric month to its string equivalent.
	 * 
	 * @param month
	 * @return a String representation of the month.
	 */
	
	private String getMonth(int month) {
		switch (month) {
			case  1: return "January";
			case  2: return "February";
			case  3: return "March";
			case  4: return "April";
			case  5: return "May";
			case  6: return "June";
			case  7: return "July";
			case  8: return "August";
			case  9: return "September";
			case 10: return "October";
			case 11: return "November";
			case 12: return "December";
		}
		
		return null;
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////

	/*
	 * Generates a String representation of the User
	 * 
	 * @return a String representation of this class
	 */
	
	@Override
	public String toString() {
		return "{ Name: " + getName() + ", DOB: " + getFormattedDob() + " }";	// modify as needed
	}
}