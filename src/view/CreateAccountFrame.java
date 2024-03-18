package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Fonts;
import model.User;

/*
 * This class will create the frame that will allow user to create a new account by 
 * entering a name, username, password and a confirmation of their password.
 */
public class CreateAccountFrame extends JFrame {

	// create an instance of Fonts
	Fonts fonts = new Fonts();

	// User for the frame
	User user;

	JPanel infoPanel = new JPanel();

	// create the GUI components for the frame
	JButton logo = new JButton(new ImageIcon("images/bingeLogo.png"));
	JLabel sideImage = new JLabel(new ImageIcon("images/sideImage1.png"));
	JLabel usernameRulesLabel = new JLabel("Username must be greater than 6 characters.");
	JTextArea passwordRulesLabel = new JTextArea(
			"Password must be greater than 6 characters, have 1 digit \nand 1 upper case letter.");

	// text fields for the sign up info
	private JTextField nameField = new JTextField("  Name");
	private JTextField username = new JTextField("  Username");
	private JPasswordField password = new JPasswordField("");
	private JPasswordField confirmPassword = new JPasswordField("");

	// temporary text labels for the passwords
	JLabel tempPassword = new JLabel("Password");
	JLabel tempConfirmPassword = new JLabel("Confirm Password");

	JButton loginBtn = new JButton("Sign Up");
	JButton visible = new JButton(new ImageIcon("images/showFalse.png"));

	// set variables to check for conditions
	Boolean isVisible = false;
	Boolean userExists = false;

	// create an instance of the create account frame
	public CreateAccountFrame() {

		// Set up the frame
		setSize(1280, 720); // size
		setTitle("Login");
		getContentPane().setBackground(Color.WHITE); // background colour
		setLayout(null); // layout manager
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // location of frame

		sideImage.setBounds(0, 0, 689, 720);
		add(sideImage);

		// panel with the text fields
		infoPanel.setBounds(689, 0, 591, 720);
		infoPanel.setLayout(null);
		infoPanel.setBackground(Color.WHITE);
		add(infoPanel);

		// add the logo
		logo.setBounds(0, 78, 591, 52);
		logo.setHorizontalAlignment(JButton.CENTER);
		logo.setBorder(BorderFactory.createEmptyBorder());
		infoPanel.add(logo);

		// add the title
		JLabel title = new JLabel("Join our community!");
		title.setBounds(0, 173, 591, 25);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(Fonts.semibold25);
		infoPanel.add(title);

		// add the header
		JLabel head = new JLabel("Please enter your details");
		head.setBounds(0, 212, 591, 20);
		head.setHorizontalAlignment(JLabel.CENTER);
		head.setFont(Fonts.medium20);
		head.setForeground(new Color(157, 157, 157));
		infoPanel.add(head);

		// name textfield
		nameField.setBounds(162, 255, 278, 50);
		nameField.setBackground(new Color(243, 244, 245)); // change background
		nameField.setForeground(new Color(105, 105, 105)); // change text colour
		nameField.setHorizontalAlignment(JTextField.LEFT); // align text
		nameField.setFont(Fonts.medium20); // set font
		nameField.setBorder(BorderFactory.createEmptyBorder()); // create transparent border
		infoPanel.add(nameField);

		// username rules
		usernameRulesLabel.setBounds(162, 315, 278, 15);
		usernameRulesLabel.setFont(Fonts.medium10);
		usernameRulesLabel.setForeground(new Color(157, 157, 157));
		usernameRulesLabel.setHorizontalAlignment(JLabel.LEFT);
		infoPanel.add(usernameRulesLabel);

		// username textfield
		username.setBounds(162, 330, 278, 50);
		username.setBackground(new Color(243, 244, 245)); // change background
		username.setForeground(new Color(105, 105, 105)); // change text colour
		username.setHorizontalAlignment(JTextField.LEFT); // align text
		username.setFont(Fonts.medium20); // set font
		username.setBorder(BorderFactory.createEmptyBorder()); // create transparent border
		infoPanel.add(username);

		// password rules
		passwordRulesLabel.setBounds(162, 390, 278, 30);
		passwordRulesLabel.setFont(Fonts.medium10);
		passwordRulesLabel.setForeground(new Color(157, 157, 157));
		passwordRulesLabel.setEditable(false);
		infoPanel.add(passwordRulesLabel);

		// password textfield
		password.setEchoChar('•');
		password.setBounds(162, 420, 278, 50);
		password.setBackground(new Color(243, 244, 245)); // change background
		password.setForeground(new Color(105, 105, 105)); // change text colour
		password.setHorizontalAlignment(JTextField.LEFT); // align text
		password.setFont(Fonts.medium20); // set font
		password.setBorder(BorderFactory.createEmptyBorder()); // create transparent border
		infoPanel.add(password);

		// confirm password textfield
		confirmPassword.setEchoChar('•');
		confirmPassword.setBounds(162, 495, 278, 50);
		confirmPassword.setBackground(new Color(243, 244, 245)); // change background
		confirmPassword.setForeground(new Color(105, 105, 105)); // change text colour
		confirmPassword.setHorizontalAlignment(JTextField.LEFT); // align text
		confirmPassword.setFont(Fonts.medium20); // set font
		confirmPassword.setBorder(BorderFactory.createEmptyBorder()); // create transparent border
		infoPanel.add(confirmPassword);

		// temporary place holder
		tempPassword.setForeground(new Color(105, 105, 105));
		tempPassword.setFont(Fonts.medium20);
		tempPassword.setBounds(10, 0, 500, 50);
		password.add(tempPassword);

		// temporary place holder
		tempConfirmPassword.setForeground(new Color(105, 105, 105));
		tempConfirmPassword.setFont(Fonts.medium20);
		tempConfirmPassword.setBounds(10, 0, 500, 50);
		confirmPassword.add(tempConfirmPassword);

		// login button
		loginBtn.setBounds(162, 577, 278, 50);
		loginBtn.setBackground(new Color(238, 78, 88));
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setHorizontalAlignment(JTextField.CENTER);
		loginBtn.setFont(Fonts.medium20);
		loginBtn.setOpaque(true);
		loginBtn.setBorder(BorderFactory.createEmptyBorder());
		infoPanel.add(loginBtn);

		setVisible(true);

	}

	public JTextField getNameField() {
		return nameField;
	}

	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}

	public JTextField getUsername() {
		return username;
	}

	public void setUsername(JTextField username) {
		this.username = username;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public void setPassword(JPasswordField password) {
		this.password = password;
	}

	public JPasswordField getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(JPasswordField confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public JButton getLoginBtn() {
		return loginBtn;
	}

	public void setLoginBtn(JButton loginBtn) {
		this.loginBtn = loginBtn;
	}

	public JButton getLogo() {
		return logo;
	}

	public void setLogo(JButton logo) {
		this.logo = logo;
	}

	public JLabel getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(JLabel tempPassword) {
		this.tempPassword = tempPassword;
	}

	public JLabel getTempConfirmPassword() {
		return tempConfirmPassword;
	}

	public void setTempConfirmPassword(JLabel tempConfirmPassword) {
		this.tempConfirmPassword = tempConfirmPassword;
	}

}
