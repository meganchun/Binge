package view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Fonts;
import model.User;

/*
 * This class will create a frame that will hold information for a recipe and features two buttons
 * that users can click to help build their profile but also will signal for the recipe info to change
 */
public class QuizFrame extends JFrame {

	Fonts fonts = new Fonts();

	// GUI Components
	JLabel nameLabel;
	JLabel subheader = new JLabel("To build a profile curated for you, take this 10 question quiz!");
	JLabel questionTitle = new JLabel("Choose your preference...");
	JButton likeBtn = new JButton(new ImageIcon("images/likeBtn.png"));
	JButton dislikeBtn = new JButton(new ImageIcon("images/dislikeBtn.png"));

	QuizRecipeInfo QuizRecipeInfo;

	// constructor that creates an instance of the quiz frame with a user object as a parameter
	public QuizFrame(User user) {

		// Set up the frame
		setSize(1280, 720); // size
		setTitle("Quiz");
		getContentPane().setBackground(Color.WHITE); // background colour
		setLayout(null); // layout manager
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // location of frame

		// label that displays the users name
		nameLabel = new JLabel("Hi, " + user.getName());
		nameLabel.setBounds(80, 85, 137, 39);
		nameLabel.setFont(Fonts.medium25);
		add(nameLabel);

		// subheader text of the frame
		subheader.setForeground(new Color(157, 157, 157));
		subheader.setFont(Fonts.medium20);
		subheader.setHorizontalAlignment(JLabel.RIGHT);
		subheader.setBounds(500, 95, 700, 26);
		add(subheader);

		// question title
		questionTitle.setForeground(new Color(157, 157, 157));
		questionTitle.setFont(Fonts.medium25);
		questionTitle.setHorizontalAlignment(JLabel.CENTER);
		questionTitle.setBounds(0, 510, 1280, 30);
		add(questionTitle);

		likeBtn.setBounds(390, 576, 176, 71);
		add(likeBtn);

		dislikeBtn.setBounds(607, 576, 282, 71);
		add(dislikeBtn);

		setVisible(true);
	}

	// Getters and Setters
	public QuizRecipeInfo getQuizRecipeInfo() {
		return QuizRecipeInfo;
	}

	public void setQuizRecipeInfo(QuizRecipeInfo quizRecipeInfo) {
		QuizRecipeInfo = quizRecipeInfo;
	}

	public JButton getLikeBtn() {
		return likeBtn;
	}

	public void setLikeBtn(JButton likeBtn) {
		this.likeBtn = likeBtn;
	}

	public JButton getDislikeBtn() {
		return dislikeBtn;
	}

	public void setDislikeBtn(JButton dislikeBtn) {
		this.dislikeBtn = dislikeBtn;
	}

}
