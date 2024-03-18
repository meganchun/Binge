package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JOptionPane;

import model.User;
import view.CreateAccountFrame;
import view.IntroFrame;
import view.LoginFrame;

/*
 * The AccountController class will be responsible for handling all data that is required to add/create
 * any new additional info. This includes editing external files to update with the most relevant information,
 * validating any login or account creation.
 */
public class AccountController implements ActionListener, KeyListener {

	// Declare the account frames
	IntroFrame introFrame;
	LoginFrame loginFrame;
	CreateAccountFrame createFrame;

	// the constructor class will create the initialize intro frame that will allow
	// users to then proceed
	// to other frames (login or create account)
	public AccountController() {

		// create a new intro frame
		introFrame = new IntroFrame();
		addListeners();

	}

	// this method will add action listeners to the intro frame
	public void addListeners() {

		// intro frame action listeners
		introFrame.getLoginBtn().addActionListener(this);
		introFrame.getSignUpBtn().addActionListener(this);

	}

	// that are performed inside that frame including validating info inputed
	public void createUser() {

		// initialize the frame
		createFrame = new CreateAccountFrame();

		// add action listeners
		createFrame.getPassword().addKeyListener(this);
		createFrame.getConfirmPassword().addKeyListener(this);

		// add action listener for the logo button that will return back to the intro
		// frame
		createFrame.getLogo().addActionListener(new ActionListener() {

			// this method will handle the actions performed when the logo button is clicked
			public void actionPerformed(ActionEvent e) {

				introFrame = new IntroFrame();
				addListeners();
				createFrame.dispose();
			}
		});

		// add action listeners for the create user frame
		createFrame.getLoginBtn().addActionListener(new ActionListener() {

			// this method will handle the actions performed when the create account button
			// is clicked
			public void actionPerformed(ActionEvent e) {

				boolean exists = false;
				try {

					File myObj = new File("data/accounts.txt");
					Scanner myReader = new Scanner(myObj);

					// loop through the lines in the text file
					while (myReader.hasNextLine()) {

						User user = readFile(myReader);

						// if the username from the file matches the one entered in the text field
						if (createFrame.getUsername().getText().equals(user.getUsername())) {
							exists = true;
							break;
						}
					}
					myReader.close();

				} catch (FileNotFoundException e1) { // for errors or if there is no input
					System.out.println("File Error");
				}

				// if the account under the entered username does not exists yet
				if (!exists) {
					
					//check if the username is greater than 6 characters
					if (createFrame.getUsername().getText().length() >= 6) {

						// check to make sure password is valid, if it is:
						// more than 6 characters long, has at least one digit and one upper case letter
						if (createFrame.getPassword().getPassword().length >= 6
								&& String.valueOf(createFrame.getPassword().getPassword()).matches(".*\\d.*")
								&& String.valueOf(createFrame.getPassword().getPassword()).matches(".*[A-Z].*")) {
	
							// check to verify the confirm password and password match
							if (String.valueOf(createFrame.getPassword().getPassword())
									.equals(String.valueOf(createFrame.getConfirmPassword().getPassword()))) {
	
								User user = new User(createFrame.getNameField().getText(),
										createFrame.getUsername().getText(),
										String.valueOf(createFrame.getPassword().getPassword()), new ArrayList<String>());
	
								// close this frame and open and procced to the quiz frame
								MatchController mc = new MatchController(user);
								mc.createQuiz();
								createFrame.dispose();
	
							}
							// if the passwords don't match
							else {
	
								// reset the password fields
								createFrame.getPassword().setText("");
								createFrame.getConfirmPassword().setText("");
	
								JOptionPane.showMessageDialog(createFrame, "Passwords don't match. Please enter again.",
										"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
							}
	
						} else {
							// reset the password fields
							createFrame.getPassword().setText("");
							createFrame.getConfirmPassword().setText("");
	
							JOptionPane.showMessageDialog(createFrame,
									"Password must be at least 6 characters long, inlcude one digit and one upper case letter.",
									"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
						}
	
					} else {
	
						// reset the username and password fields
						createFrame.getUsername().setText("");
						createFrame.getPassword().setText("");
						createFrame.getConfirmPassword().setText("");
	

						JOptionPane.showMessageDialog(createFrame, "Username has to be at least 6 characters longs",
								"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
					}
				}
				else 
					JOptionPane.showMessageDialog(createFrame, "Username already taken. Please try again.",
							"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
			}
		});
		
	}

	// this method will handle all actions related to the login frame.
	// this includes validating the username provided to check if it exists and matching it
	// to the corresponding password
	public void login() {

		// new login frame
		loginFrame = new LoginFrame();
		loginFrame.getPassword().addKeyListener(this);

		//add and handle the action listener to the logo
		loginFrame.getLogo().addActionListener(new ActionListener() {

			// this method will handle the actions performed when the logo button is clicked
			public void actionPerformed(ActionEvent e) {

				introFrame = new IntroFrame();
				addListeners();
				loginFrame.dispose();
			}
		});

		// handle the actions when the user clicks the login button: validates response
		loginFrame.getLoginBtn().addActionListener(new ActionListener() {

			// this method will handle the action performed when the login button is clicked
			public void actionPerformed(ActionEvent e) {

				User user = null;
				boolean found = false;

				// check the accounts text file to see if there the username exists
				try {

					File myObj = new File("data/accounts.txt");
					Scanner myReader = new Scanner(myObj);

					// loop through the lines in the text file
					while (myReader.hasNextLine()) {

						user = readFile(myReader);

						// if the username from the file matches the one entered in the text field
						if (loginFrame.getUsername().getText().equals(user.getUsername())) {
							found = true;
							break;
						}
					}
					myReader.close();

				} catch (FileNotFoundException e1) { // for errors or if there is no input
					System.out.println("File Error");
				}

				// if the username exists
				if (found) {

					// if the password from the file matches the one in the password field
					if (user.getPassword().equals(String.valueOf(loginFrame.getPassword().getPassword()))) {

						new FoodController(user);
						switchDataSet(user);
						loginFrame.dispose();
					} else {
						JOptionPane.showMessageDialog(loginFrame, "Incorrect password or username. Please try again.",
								"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
					}
				}
				// if the account doesn't exists
				else {
					JOptionPane.showMessageDialog(loginFrame, "Incorrect password or username. Please try again.",
							"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
					;
				}
			}
		});

	}

	// this method will read the file in a line and return the information as a user object
	// this method will read a line in the data file and return a user
	public User readFile(Scanner myReader) {

		//read line and split the data by |
		String line = myReader.nextLine();
		String[] data = line.split("\\|", -2);

		// save the data of the user
		String nameData = data[0];
		String usernameData = data[1];
		String passwordData = data[2];

		String[] preferences = data[3].split("\\*", -2);

		ArrayList<String> preferenceList = new ArrayList<String>();

		//add the user's preferences to an array list
		for (String s : preferences) {

			preferenceList.add(s);

		}

		//return a user object
		return new User(nameData, usernameData, passwordData, preferenceList);

	}

	// this method will update the user's profile in our data file to reflect
	// any new changes made to their preference
	
	// this method will be used to update the external text file when changes to their data has been made
	public static void updateUserFile(User user) {

		try {

			//call a method to update the data in the account.txt file
			updateDataSetFile("accounts.txt", user.getUsername(), user);

			// write the new data into the file
			File file = new File("data/accounts.txt");
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);

			//get the formatted data that will be written in the account file
			String dataStr = getDataSetFormat("txt", user);

			br.write(user.getName() + "|" + user.getUsername() + "|" + user.getPassword() + "|"
					+ dataStr.substring(0, dataStr.length() - 1));

			br.close();
			fr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// this method will format the data set information based on the format type the
	// program needs to output to
	
	//this method will return a sting of the formatted version of the user's information for specific file types
	public static String getDataSetFormat(String formatType, User user) {

		String dataStr = "";

		// loop through every data that was saved in a row and split it by an asterisk for the accounts file
		// or split the data by a new line for the arff file
		for (int numData = 0; numData < user.getPreferenceData().size(); numData++) {

			if (formatType.equals("arff"))
				dataStr += "\n";

			dataStr += user.getPreferenceData().get(numData);
		
			if (formatType.equals("txt"))
				dataStr += "*";
		}

		return dataStr;

	}
	
	// this method will update the external file based on which file it is referencing
	// and the user it needs to update

	// this method will update the data set in either the arff file or accounts file
	// the format of the data of which it is outputed will differ based on the file
	// type
	public static void updateDataSetFile(String fileType, String flag, User user) {

		// update the file with new information
		try {
			// get the file that will be modified
			File inputFile = new File("data/" + fileType);
			File tempFile = new File("myTempFile.txt"); // temp file

			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			// string to signal which line to remove
			String lineToCheck = flag;
			String currentLine;

			// loop through all the lines in the file
			while ((currentLine = reader.readLine()) != null) {

				// removing the lines after @data
				if (fileType.equals("userPreferences.arff")) {
					// if the current line contains the user then edit the line
					if (currentLine.equals(flag)) {

						writer.write(flag);
						break;
					}
				}

				// removing the old location in the users file
				else {
					String[] values = currentLine.split("\\|", -2);

					// if the current line contains the user then edit the line
					if (values[1].equals(lineToCheck)) {
						
						// skip adding the line back to the file
						continue;
					}
				}

				//write the line into the new file
				writer.write(currentLine + "\r\n");
			}
			writer.close();
			reader.close();

			//change back the temp to the new file
			tempFile.renameTo(inputFile);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// this method will load on the unique data set based on the user's previously
	// chosen decision
	
	// this method will replace the previous data set in the arff file with the most recent user
	public void switchDataSet(User user) {
		
		try {
			//update the file to remove the previous's user's data from the file
			updateDataSetFile("userPreferences.arff", "@data", user);

			// write the new data into the file
			File file = new File("data/userPreferences.arff");
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);

			String dataStr = getDataSetFormat("arff", user);

			br.write(dataStr.substring(0, dataStr.length()));

			br.close();
			fr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// this method will handle the action performed in the initial intro screen
	
	// this method will handle the action performed in the initial intro 
	// screen to switch to login or create account frame
	@Override
	public void actionPerformed(ActionEvent e) {

		//open the login or account creation frame
		if (e.getSource() == introFrame.getLoginBtn()) {
			login();
			introFrame.dispose();
		}

		if (e.getSource() == introFrame.getSignUpBtn()) {
			createUser();
			introFrame.dispose();
		}
	}

	// this method will handle any actions if a key is typed
	
	//this method will handle is a key is typed
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	// this method will handle any key presses

	// this method will handle any actions if a key is pressed
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	//this method will handle the key events when the password text fields are typed on

	// this method will handle the temporary text set on the password fields
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		// if the user is typing in the password field remove the temporary text
		if (e.getComponent().getY() == 406)
			loginFrame.getTempPassword().setVisible(false);
		if (e.getComponent().getY() == 420)
			createFrame.getTempPassword().setVisible(false);
		if (e.getComponent().getY() == 495)
			createFrame.getTempConfirmPassword().setVisible(false);

	}
}
