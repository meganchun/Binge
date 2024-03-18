package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.Fonts;
import model.Recipe;

/*
 * This class will create a panel that will have an image that when clicked links to the a website and buttons
 * that will signal if the user likes or dislike a recipe that will disappear after clicking it
 */
public class RecipeResultPanel extends JPanel {
	
	Fonts fonts = new Fonts();

	Recipe recipe;
	
	//Declare attributes of the panel
	int padding;
	int imageWidth;
	int imageHeight;
	Font titleFont;
	Font subFont;
	
	//GUI Components
	int textSize;
	ImageIcon viewBtn;
	JButton imageBtn;
	JLabel recipeName;
	JLabel calorie;
	JButton likeBtn = new JButton(new ImageIcon("images/smallLikeBtn.png"));
	JButton dislikeBtn = new JButton(new ImageIcon("images/smallDislikeBtn.png"));
	JLabel imageContainer = new JLabel(new ImageIcon("images/container.png"));
	
	//constructor that will create an instance of the panel with a recipe and size indicator as parameterss
	public RecipeResultPanel(Recipe recipe, String size) {
	
		this.recipe = recipe;
		
		//panel set up
		setOpaque(true);
		setLayout(null);
		
		//if the size of the panel is the smaller recipe panel
		if (size.equals("small")) {
			
			//set the information unique to a small panel
			setSize(269,185);
			padding = 12;
			likeBtn.setIcon(new ImageIcon("images/extraSmallLikeBtn.png"));
			dislikeBtn.setIcon(new ImageIcon("images/extraSmallDislikeBtn.png"));
			imageWidth = 244;
			imageHeight = 113;
			titleFont = Fonts.semibold14;
			subFont = Fonts.medium10;
		}
		//if the size of the panel is the larger recipe panel
		else {
		
			//set the information unique to a large panel
			setSize(359,250);
			padding = 18;
			imageWidth = 326;
			imageHeight = 150;
			titleFont = Fonts.semibold18;
			subFont = Fonts.medium12;
		}
		
		//container to hold all the components
		imageContainer.setBounds(padding, padding, imageWidth, imageHeight);
		add(imageContainer);
		
		//scale the image
		Image newImage = recipe.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
		imageBtn = new JButton(new ImageIcon(newImage));
		imageBtn.setOpaque(false);
		imageBtn.setBorder(BorderFactory.createEmptyBorder());
		imageBtn.setBounds(0, 0, imageWidth, imageHeight);
		imageContainer.add(imageBtn);

		//add recipe name
		recipeName = new JLabel(recipe.getName());
		recipeName.setFont(Fonts.semibold18);
		recipeName.setBounds(padding, imageHeight + 20, imageWidth, 30);
		add(recipeName);
		
		//add the calories
		calorie = new JLabel(((int) recipe.getCalorie()) + " Cal");
		calorie.setForeground(new Color(157,157,157));
		calorie.setFont(Fonts.medium12);
		calorie.setBounds(padding, imageHeight + 50, 150, 15);
		add(calorie);
		
		//add the like/dislike buttons
		likeBtn.setBounds(imageWidth - 60, imageHeight + 40, 34, 34);
		add(likeBtn);
		
		dislikeBtn.setBounds(imageWidth - 20, imageHeight + 40, 34, 34);
		add(dislikeBtn);
		
	}

	public JButton getImageBtn() {
		return imageBtn;
	}

	public void setImageBtn(JButton imageBtn) {
		this.imageBtn = imageBtn;
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

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
