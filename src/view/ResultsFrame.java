package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.FoodController;
import model.Fonts;
import model.Ingredient;
import model.Recipe;
import model.User;

/*
 * This class will create a frame of the results of the ingredients entered by the user 
 * It will have three recipes (bigger) that are most likely to be the user's match and four other recipes
 * that also match with their search.
 */
public class ResultsFrame extends JFrame implements ActionListener {

	Fonts font = new Fonts();
	User user;

	// array list of all the recipe panels
	ArrayList<RecipeResultPanel> recipePanels;

	// GUI components
	JButton logo = new JButton(new ImageIcon("images/bingeLogo.png"));
	JLabel header = new JLabel("Your top results");
	JLabel alternativeHeader = new JLabel("Other suggestions");
	JLabel subheader;

	// constructor that will create an instance of the result frame and has a list
	// of recipes, ingredients
	// and user object as parameters that will determine the information displayed
	public ResultsFrame(ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredient, User user) {

		this.user = user;

		// Set up the frame
		setSize(1280, 720); // size
		setTitle("Recipe Results");
		getContentPane().setBackground(Color.WHITE); // background colour
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null); // layout manager
		setLocationRelativeTo(null); // location of frame

		// add the logo
		logo.setBounds(0, 28, 1280, 52);
		logo.setHorizontalAlignment(JLabel.CENTER);
		logo.setBorder(BorderFactory.createEmptyBorder());
		add(logo);

		// header
		header.setBounds(80, 101, 591, 35);
		header.setHorizontalAlignment(JLabel.LEFT);
		header.setFont(Fonts.semibold25);
		add(header);

		// sub header with ingredient input
		String ing = "Showing results related to: ";

		// create the string of the ingredients
		for (int i = 0; i < ingredient.size(); i++) {

			// add a comma if it is not the last ingredient
			if (i != ingredient.size() - 1)
				ing += ingredient.get(i).getIngredient() + ", ";
			else
				ing += ingredient.get(i).getIngredient();
		}

		// subheader of the frame
		subheader = new JLabel(ing);
		subheader.setBounds(80, 135, 1000, 30);
		subheader.setHorizontalAlignment(JLabel.LEFT);
		subheader.setFont(Fonts.medium20);
		subheader.setForeground(new Color(157, 157, 157));
		add(subheader);

		// initialize the recipe array list
		recipePanels = new ArrayList<RecipeResultPanel>();

		int index = 0;

		// add top results (bigger panel)
		for (int x = 79; x <= 841; x += 381) {

			RecipeResultPanel rp = new RecipeResultPanel(recipes.get(index), "large");
			rp.setBounds(x, 184, 359, 231);
			add(rp);
			recipePanels.add(rp);

			index++;
		}

		// add other suggestions (smaller panel)
		for (int x = 79; x <= 934; x += 285) {

			RecipeResultPanel rp = new RecipeResultPanel(recipes.get(index), "small");
			rp.setBounds(x, 464, 269, 200);
			add(rp);
			recipePanels.add(rp);

			index++;
		}

		setVisible(true);

	}

	public ArrayList<RecipeResultPanel> getRecipePanels() {
		return recipePanels;
	}

	public void setRecipePanels(ArrayList<RecipeResultPanel> recipePanels) {
		this.recipePanels = recipePanels;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == logo) {
			new FoodController(user);
			dispose();
		}
	}

}
