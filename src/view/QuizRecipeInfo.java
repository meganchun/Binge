package view;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Fonts;
import model.Recipe;

/*
 * This class will create a panel that displays data about a recipe. It is meant to be a
 * child component to the quiz frame and will change based on which recipe is added as the argument
 */
public class QuizRecipeInfo extends JPanel {

	Fonts fonts = new Fonts();
	Recipe recipe;

	// GUI Components
	JLabel image;
	JLabel imageShadow;
	JLabel recipeName;
	String ing = "";
	JLabel ingredients;
	JLabel recipeCalorie;

	// constructor that creates an instance of the class and has a recipe object ass a parameter
	public QuizRecipeInfo(Recipe recipe) {

		this.recipe = recipe;

		// create the image
		image = new JLabel(new ImageIcon(recipe.getImage()));
		int width = recipe.getImage().getWidth(image);
		int height = recipe.getImage().getHeight(image);

		// get the scale factor
		int scale = 1121 / width;

		// create a scaled image
		Image dimg = recipe.getImage().getScaledInstance(1121, height * scale, Image.SCALE_SMOOTH);
		image = new JLabel(new ImageIcon(dimg));
		image.setBounds(0, 0, 1121, 336);
		add(image);

		// add the black gradient shadow on the image
		imageShadow = new JLabel(new ImageIcon("images/imageShadow.png"));
		imageShadow.setBounds(0, 0, 1121, 336);
		image.add(imageShadow);

		// add the recipe name
		recipeName = new JLabel(recipe.getName());
		recipeName.setForeground(Color.white);
		recipeName.setFont(Fonts.semibold30);
		recipeName.setBounds(61, 223, 904, 40);
		recipeName.setHorizontalAlignment(JLabel.LEFT);
		imageShadow.add(recipeName);

		// determine a short description of ingredients for the recipe
		for (int i = 0; i < recipe.getIngredientList().length; i++) {

			if (i != recipe.getIngredientList().length - 1)
				ing += recipe.getIngredientList()[i].getIngredient() + ", ";
				else
					ing += recipe.getIngredientList()[i].getIngredient();
		}

		// add the short description of ingredients
		ingredients = new JLabel(ing);
		ingredients.setForeground(Color.white);
		ingredients.setFont(Fonts.medium16);
		ingredients.setBounds(61, 272, 700, 30);
		ingredients.setHorizontalAlignment(JLabel.LEFT);
		imageShadow.add(ingredients);

		// add the calories
		recipeCalorie = new JLabel(((int) recipe.getCalorie()) + " Cal");
		recipeCalorie.setForeground(Color.white);
		recipeCalorie.setFont(Fonts.semibold25);
		recipeCalorie.setBounds(955, 264, 150, 35);
		recipeCalorie.setHorizontalAlignment(JLabel.LEFT);
		imageShadow.add(recipeCalorie);

	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
