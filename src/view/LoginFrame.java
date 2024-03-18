package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Fonts;
import model.User;

/*
 * The login frame will allow users to enter a preivously created account
 * and retrive data based on their previous interactions with the application.
 * The frame will provide users will the fields to enter their username and passwords.
 */
public class LoginFrame extends JFrame {

	// create an instance of Fonts
	Fonts fonts = new Fonts();

	// User for the frame
	User user;

	JPanel infoPanel = new JPanel();

	// create the GUI components for the frame
	JButton logo = new JButton(new ImageIcon("images/bingeLogo.png"));
	JLabel sideImage = new JLabel(new ImageIcon("images/sideImage1.png"));
	JTextField username = new JTextField("  Username");
	JPasswordField password = new JPasswordField("");
	JLabel tempPassword = new JLabel("Password");
	JButton loginBtn = new JButton("Login");
	JButton visible = new JButton(new ImageIcon("images/showFalse.png"));

	// set variables to check for conditions
	Boolean isVisible = false;
	Boolean userExists = false;

	// constructor that will create an instance of the login frame
	public LoginFrame() {

		// Set up the frame
		setSize(1280, 720); // size
		setTitle("Login");
		getContentPane().setBackground(Color.WHITE); // background colour
		setLayout(null); // layout manager
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // location of frame

		sideImage.setBounds(0, 0, 689, 720);
		add(sideImage);

		infoPanel.setBounds(689, 0, 591, 720);
		infoPanel.setLayout(null);
		infoPanel.setBackground(Color.WHITE);
		add(infoPanel);

		// add the logo
		logo.setBounds(0, 128, 591, 52);
		logo.setHorizontalAlignment(JButton.CENTER);
		logo.setBorder(BorderFactory.createEmptyBorder());
		infoPanel.add(logo);

		// add the title
		JLabel title = new JLabel("Welcome back!");
		title.setBounds(0, 223, 591, 25);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(Fonts.semibold25);
		infoPanel.add(title);

		// add the header
		JLabel head = new JLabel("Please enter your details");
		head.setBounds(0, 262, 591, 20);
		head.setHorizontalAlignment(JLabel.CENTER);
		head.setFont(Fonts.medium20);
		head.setForeground(new Color(157, 157, 157));
		infoPanel.add(head);

		// username textfield
		username.setBounds(162, 331, 278, 50);
		username.setBackground(new Color(243, 244, 245)); // change background
		username.setForeground(new Color(105, 105, 105)); // change text colour
		username.setHorizontalAlignment(JTextField.LEFT); // align text
		username.setFont(Fonts.medium20); // set font
		username.setBorder(BorderFactory.createEmptyBorder()); // create transparent border
		infoPanel.add(username);

		// password textfield
		password.setEchoChar('•');
		password.setBounds(162, 406, 278, 50);
		password.setBackground(new Color(243, 244, 245)); // change background
		password.setForeground(new Color(105, 105, 105)); // change text colour
		password.setHorizontalAlignment(JTextField.LEFT); // align text
		password.setFont(Fonts.medium20); // set font
		password.setBorder(BorderFactory.createEmptyBorder()); // create transparent border
		infoPanel.add(password);

		// temporary place holder
		tempPassword.setForeground(new Color(105, 105, 105));
		tempPassword.setFont(Fonts.medium20);
		tempPassword.setBounds(10, 0, 500, 50);
		password.add(tempPassword);

		// add the visibility changing button
		visible.setBounds(435, 20, 30, 26);
		visible.setBorder(BorderFactory.createEmptyBorder());
		password.add(visible);

		// login button
		loginBtn.setBounds(162, 533, 278, 50);
		loginBtn.setBackground(new Color(238, 78, 88));
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setHorizontalAlignment(JTextField.CENTER);
		loginBtn.setFont(Fonts.medium20);
		loginBtn.setOpaque(true);
		loginBtn.setBorder(BorderFactory.createEmptyBorder());
		infoPanel.add(loginBtn);

		setVisible(true);
	}

	// Getters and Setters
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

}
