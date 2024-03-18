package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import model.Fonts;

/*
 * This class will create a frame where users can cick to either login or create an account
 */
public class IntroFrame extends JFrame {

	Fonts fonts = new Fonts();

	// GUI Components
	JLabel introImage = new JLabel(new ImageIcon("images/introImage.png"));
	JButton loginBtn = new JButton("Login");
	JButton signUpBtn = new JButton("Sign Up");

	// constructor will create a new instance of the intro frame
	public IntroFrame() {

		// Set up the frame
		setSize(1280, 720); // size
		setTitle("Login");
		getContentPane().setBackground(Color.WHITE); // background colour
		setLayout(null); // layout manager
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // location of frame

		// title image
		introImage.setBounds(0, 0, 1280, 519);
		add(introImage);

		// login button
		loginBtn.setBounds(328, 536, 300, 68);
		loginBtn.setBackground(new Color(238, 78, 88));
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setHorizontalAlignment(JTextField.CENTER);
		loginBtn.setFont(Fonts.medium25);
		loginBtn.setOpaque(true);
		loginBtn.setBorder(BorderFactory.createEmptyBorder());
		add(loginBtn);

		// sign up button
		signUpBtn.setBounds(652, 536, 300, 68);
		signUpBtn.setForeground(new Color(238, 78, 88));
		signUpBtn.setBackground(Color.WHITE);
		signUpBtn.setBorder(BorderFactory.createLineBorder(new Color(238, 78, 88), 2));
		signUpBtn.setHorizontalAlignment(JTextField.CENTER);
		signUpBtn.setFont(Fonts.medium25);
		signUpBtn.setOpaque(true);
		add(signUpBtn);

		setVisible(true);
	}

	// Getters and Setters
	public JButton getLoginBtn() {
		return loginBtn;
	}

	public void setLoginBtn(JButton loginBtn) {
		this.loginBtn = loginBtn;
	}

	public JButton getSignUpBtn() {
		return signUpBtn;
	}

	public void setSignUpBtn(JButton signUpBtn) {
		this.signUpBtn = signUpBtn;
	}

}
