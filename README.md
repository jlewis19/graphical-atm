# Graphical ATM

Just when you thought you were done with that pesky ATM... Welcome to what is likely your first introduction to graphics, where you'll implement a graphical user interface (i.e., GUI) to manage the underlying logice you already implemented for the Enhanced ATM. Rather than interacting with your program through the terminal window (i.e., a command line interface or CLI), you'll be creating buttons and textboxes to build something closer to a real ATM. Along with a written test, this assignment will count towards your midterm exam score.

## Getting Started

To get started, you'll need to create a [GitHub](https://github.com/) repository to store your Graphical ATM code. After cloning my skeleton repository, you'll need to setup a remote to push your code to your repository instead of mine. Steps to accomplish this are outlined below.

### Setup

01. Login to your [GitHub](https://github.com/) account and create a new repository named `graphical-atm`.
02. In GitBash, navigate to your `APCSA` folder.
```
cd ~/Desktop/APCSA
```
03. Clone my skeleton repository from [GitHub](https://github.com/). This will make a copy of my repository and store it locally.
```
git clone git@github.com:rwilson-ucvts/java-graphical-atm-skeleton.git
```
04. The cloning process will have created a folder named `java-graphical-atm-skeleton`. Rename this folder to `graphical-atm`.
```
mv java-graphical-atm-skeleton graphical-atm
```
05. Change directories to get into your `graphical-atm` folder.
```
cd graphical-atm
```
06. Originally, the remote will be pointing at my repository. We need to overwrite this.
```
git remote rename origin upstream
```
07. Lastly, we need to add a new remote that points at the repository you created earlier. Make sure you replace `YOUR-USERNAME` with your actual username.
```
git remote add origin git@github.com:YOUR-USERNAME/graphical-atm.git
```
08. Launch Eclipse and set the `Workspace` to the `APCSA` folder you created on your `Desktop`. Make sure you're using your `Workspace`, as others' will be similarly named.
09. From within the `Package Explorer` (the left-most panel), right-click and select `Import...`.
10. Select `Git > Projects from Git`, and click `Next >`.
11. Select `Existing local repository` and click `Next >`.
12. Click the `Add...` button, and then the `Browse...` button.
13. Navigate to the `APCSA` folder on your `Desktop`, click the `graphical-atm` project folder, and click `Open`.
14. Select the checkbox next to your project and click `Finish`.
15. Now that we've imported the Git project, we can click `Next >`, `Next >`, and `Finish` once more.

You should now see a `Project` in the `Package Explorer` in Eclipse.

16. Expand the `Project` folder. You should see the `JRE System Library` folder, as well as the `src` folder.
17. Expand the `src` folder. You should see several source files that you'll need to implement to complete this program.

## Requirements

Since you've already built the underlying logic of the ATM (i.e., depositing, withdrawing, transferring, opening and closing an account, etc.), the only requirement is to build the GUI according to the provided specifications. 

### LoginView

The `LoginView` should be the first thing a user sees when he or she starts the application. It should provide the following components.
* a textbox where the user can enter an account number
   - account numbers should be plaintext
* a textbox where the user can enter a PIN
   - PINs should be masked (much like a typical password field in a mobile/web application)
* a button to login to the ATM
* a button to create a new account

Assuming the credentials match an existing account, users should be redirected to the `HomeView` after clicking the `Login` button. If the user enters an invalid account number and/or PIN, display an appropriate error message in the `LoginView`.

Users should be redirected to the `CreateView` after clicking the `Open an Account` button.

### CreateView

The `CreateView` should provide a menu interface where the user can enter his or her personal information. It should provide the following components.
* a textbox to enter his or her first name
* a textbox to enter his or her last name
* a date picker to select his or her date of birth
* a textbox to enter his or her phone number
   - this should be separated into the 3 segments of a phone number
* a textbox to enter his or her street address
* a textbox to enter his or her city
* a dropdown menu to select his or her state
* a textbox to enter his or her postal code
* a textbox to enter his or her desired PIN
   - this should be masked (much like a typical password field in a mobile/web application)
* a button to create the account
* a button to cancel and return to the `LoginView`

Assuming all information is entered correctly, users should be redirected to the `HomeView` after clicking the `Create Account` button. If the user enters an invalid piece of information, display an appropraite error message in the `CreateView`.

Users should be redirected to the `LoginView` after clicking the `Cancel` button. All fields, if edited, should be cleared.

### HomeView

The `HomeView` is where the majority of the banking actions will be initiated. It should provide the following components.
* a welcome message unique to the user (first and last name)
* a label indicating the user's current account balance (formatted as $###,###.##)
* a button to initiate a deposit
* a button to initiate a withdrawal
* a button to initiate a transfer
* a button to view/edit his or her personal information
* a button to close the account
* a button to logout

Users should be redirected to the `DesositView`, `WithdrawalView`, `TransferView`, or `InformationView`, after clicking the `Deposit`, `Withdraw`, `Transfer`, or `View/Edit Personal Information` button, respectively.

Users should be redirected to the `LoginView` after clicking either the `Close Account` or `Logout` buttons. In the case of closing an account, you need to actually close the account before redirecting him or her to the `LoginView`. In both cases, your program should confirm these requests before honoring them.

### DepositView

The `DepositView` is where a user can put money into his or her account. It should provide the following components.
* a textbox to enter the amount of money to deposit
* a button to finalize the deposit
* a button to cancel the deposit and return the `HomeView`

Users should be redirected to the `HomeView` after successfully making a deposit, where the updated balance should be reflected. The user enters an invalid amount, display an appropriate error message in the `DepositView`. Invalid amounts are defined as follows:
* an amount less than $0.01
* a non-numeric value

Users should be redirected to the `HomeView` after clicking `Cancel`. Even if the user entered an amount in the applicable textbox, no funds should be deposited in this case.

### WithdrawView

The `WithdrawView` is where a user can retrieve money from his or her account. It should provide the following components.
* a textbox to enter the amount of money to withdraw
* a button to finalize the withdrawal
* a button to cancel the withdrawal

Users should be redirected to the `HomeView` after successfully making a withdrawal, where the updated balance should be reflected. The user enters an invalid amount, display an appropriate error message in the `WithdrawalView`. Invalid amounts are defined as follows:
* an amount less than $0.01
* an amount that exceeds the account balance
* a non-numeric value

Users should be redirected to the `HomeView` after clicking `Cancel`. Even if the user entered an amount in the applicable textbox, no funds should be withdrawn in this case.

### TransferView

The `TrasnferView` is where a user can move money from his or her account to another account. It should provide the following components.
* a textbox to enter the destination account number
* a textbox to enter the amount of money to transfer
* a button to finalize the transfer
* a button to cancel the transfer

Users should be redirected to the `HomeView` after successfully making a withdrawal, where the updated balance should be reflected. The user enters an invalid amount, display an appropriate error message in the `WithdrawalView`. Invalid amounts are defined as follows:
* an amount less than $0.01
* an amount that exceeds the account balance of the origin account
* a non-numeric value

Users should be redirected to the `HomeView` after clicking `Cancel`. Even if the user entered an amount in the applicable textbox, no funds should be transferred in this case.

### InformationView

The `InformationView` is where a user can view and edit his or her information. It should provide the following components.
* an uneditable textbox showing the user's account number
* an uneditable textbox showing the user's first name
* an uneditable textbox showing the user's last name
* an uneditable textbox showing the user's street address
* an uneditable textbox showing the user's city
* an uneditable textbox showing the user's state
* an uneditable textbox showing the user's postal code
* an uneditable textbox showing the user's date of birth
* an uneditable textbox showing the user's phone number
* a button to edit the personal information
* a button to return to the previous screen

Users should be redirected to the `HomeView` (without making any changes) after clicking the `Back` button.

The following previously uneditable fields should become editable after clicking the `Edit` button.
* mailing address (street address, city, state, and postal code)
* phone number
* PIN

Additionally, the following components should now be available after clicking the `Edit` button.
* a button to cancel the edit
* a button to save changes (replacing the `Edit` button itself)
* an editable textbox showing the user's current street address (allowing the user to edit this value)
* an editable textbox showing the user's current city (allowing the user to edit this value)
* a dropdown menu showing the user's current state (allowing the user to select a new value)
* an editable textbox showing the user's current postal code (allowing the user to edit this value)

Assuming all information entered is valid, users should see the `InformationView` revert to its original (non-editing) state after clicking the `Save` button. If the user enters an invalid piece of information, display an appropriate error message in the `InformationView` without reverting to its non-editing state.

Users should see the `InformationView` revert to its original (non-editing) state after clicking the `Cancel` button. In this case, no edits should persist (even if the user modified a value in one or more of the fields).

## Deadline

Your Canvas submission is due at or before 11:59pm on TBD (pending midterm schedule).

### Submission Requirements

All that is required for submission is the URL to your [GitHub](https://github.com/) repository for this problem set.
