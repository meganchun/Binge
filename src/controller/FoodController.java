package controller;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import model.ImagePrediction;
import model.Ingredient;
import model.Recipe;
import model.User;
import model.Ingredient.Measurement;
import view.ImagePredictionFrame;
import view.IngredientFrame;
import view.RecipeResultPanel;
import view.ResultsFrame;
import weka.classifiers.trees.J48;

/*
 * The food controller class will handle the events occurring in the ingredient frame. This inlcudes
 * when the user adds/deletes a row, when retrieving data from their entry after validating their info,
 * providing suggestions based on the text field and handling image uploads for image recognition software
 */
public class FoodController implements ActionListener, KeyListener {
	
	//Declare the frames asscoicated with the ingredients
	private IngredientFrame ingredientFrame;
	private ImagePredictionFrame predictionFrame = null;
	
	//Counter to keep track of which row the frame is on
	private int currentRow = 0;
	
	//Declare controllers to make calls to methods within their classes
	public APIController api;
	public AccountController ac;
	public User user;
	
	//constructor that will create a new ingredients frame and add the action listeners and one row
	public FoodController(User user) {
		
		this.ac = ac;
		this.user = user;
		
		try {
			
			//create a new api controller
			api = new APIController();
			
			//new ingredients frame
			ingredientFrame = new IngredientFrame();
			
			//add action listeners to the updated rows
			addActionListeners();
			addRow(1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//this method will add another row to the ingredient frame
	private void addRow(int addNumRow) {
		
		//call method in the add ingredient panel 
		ingredientFrame.getAddIngredientPanel().addNewRow(addNumRow, getCurrentRow());
		
		//adjust the height of the screen to make room for the new row
		ingredientFrame.getPanel().setPreferredSize(new Dimension(1280, ingredientFrame.getPanel().getHeight()+49*addNumRow));
		ingredientFrame.getAddIngredientPanel().setSize(1280, ingredientFrame.getAddIngredientPanel().getHeight()+49*addNumRow);
		ingredientFrame.getAddMoreBtn().setBounds(535, ingredientFrame.getAddMoreBtn().getY() + 49*addNumRow, 214, 53);
		ingredientFrame.getFindRecipeBtn().setBounds(474, ingredientFrame.getFindRecipeBtn().getY()+ 49*addNumRow, 331, 65);
		
		//refresh the panels to update the GUI witht the new rows
		ingredientFrame.getAddIngredientPanel().repaint();
		ingredientFrame.getAddIngredientPanel().revalidate();
		
		//refresh frame
		ingredientFrame.getPanel().repaint();
		ingredientFrame.getPanel().revalidate();
	
		// boolean to flag when add action listener to newly added rows
		boolean add = false;
		
		// using for-each loop for iteration over the hashmap of text fields
		for (int i = 0; i < ingredientFrame.getAddIngredientPanel().getIngredientFields().size(); i++) {
			
    		ingredientFrame.getAddIngredientPanel().getIngredientFields().get(i).addActionListener(this);
    		ingredientFrame.getAddIngredientPanel().getIngredientFields().get(i).addKeyListener(this);
    		ingredientFrame.getAddIngredientPanel().getQuantityFields().get(i).addActionListener(this);
    		ingredientFrame.getAddIngredientPanel().getMeasurementDropdowns().get(i).addActionListener(this);
    		ingredientFrame.getAddIngredientPanel().getDeleteBtn().get(i).addActionListener(this);
        	
        }
		
		//update the current row after adding more rows
		setCurrentRow(getCurrentRow() + addNumRow);
	
	}
	
	// this method will delete a row from the add ingredient panel 
	private void deleteRow(int rowToRemove) {
	
		for (int i = 0; i < ingredientFrame.getAddIngredientPanel().getIngredientFields().size(); i++) {
        	
			if (i == rowToRemove) {

        		//remove the row from the GUI
        		ingredientFrame.getAddIngredientPanel().getIngredientFields().get(i).setVisible(false);
        		ingredientFrame.getAddIngredientPanel().getQuantityFields().get(i).setVisible(false);
        		ingredientFrame.getAddIngredientPanel().getMeasurementDropdowns().get(i).setVisible(false);
        		ingredientFrame.getAddIngredientPanel().getDeleteBtn().get(i).setVisible(false);
        		
        		//remove the data from the back end
        		ingredientFrame.getAddIngredientPanel().getIngredientFields().remove(i);
        		ingredientFrame.getAddIngredientPanel().getQuantityFields().remove(i);
        		ingredientFrame.getAddIngredientPanel().getMeasurementDropdowns().remove(i);
        		ingredientFrame.getAddIngredientPanel().getDeleteBtn().remove(i);
        		
        		//adjust the height of the screen to account for the one less row
        		ingredientFrame.getPanel().setPreferredSize(new Dimension(1280, ingredientFrame.getPanel().getHeight()-49));
        		ingredientFrame.getAddIngredientPanel().setSize(1280, ingredientFrame.getAddIngredientPanel().getHeight()-49);
        		ingredientFrame.getAddMoreBtn().setBounds(535, ingredientFrame.getAddMoreBtn().getY() - 49, 214, 53);
        		ingredientFrame.getFindRecipeBtn().setBounds(474, ingredientFrame.getFindRecipeBtn().getY()- 
        				49, 331, 65);
        	}

        }
        
        //refresh the panels to update the GUI witht the new rows
		ingredientFrame.getAddIngredientPanel().repaint();
		ingredientFrame.getAddIngredientPanel().revalidate();
      		
		//refresh screen
    	ingredientFrame.getPanel().repaint();
		ingredientFrame.getPanel().revalidate();
            
	}
	
	// this method will add action listeners to the ingredient frame
	//this method will add action listeners to the ingredient frame
	private void addActionListeners() {
		
		ingredientFrame.getAddMoreBtn().addActionListener(this);
		ingredientFrame.getFindRecipeBtn().addActionListener(this);
		ingredientFrame.getUploadPhotoBtn().addActionListener(this);
		ingredientFrame.getBuildProfileBtn().addActionListener(this);
		ingredientFrame.getLogoutBtn().addActionListener(this);
		
	}
	
	//this method will handle any action performed in the ingredient panel
			

	//this method will create the image analysis frame with the predicted objects from the uploaded photo
	private void generateImagePredictionFrame(String filePath) {
	
	    //return an array list of image predictions
	    ArrayList<ImagePrediction> predictions = api.predictImage(filePath);
	    
	    //create an image from the file path
	    ImageIcon image = new ImageIcon(filePath);
	    
	    //create a frame of the photo
	    predictionFrame = new ImagePredictionFrame(predictions, image);
	    	    
	    //add action listener to all the add buttons on the prediction frame
	    for (int i = 0; i < predictions.size(); i++) {
	    	
	    	//add action listener for every prediction on the frame
	    	predictionFrame.getaddBtns()[i].addActionListener(new ActionListener() { 
    			public void actionPerformed(ActionEvent e) { 
    				
    				//loop through the prediction panels again because the action performed method can no longer
    				//access the prediction index since it is out of its scope
    				for (int i = 0; i < predictions.size(); i++) {
    					
	        			if (e.getSource() == predictionFrame.getaddBtns()[i]) {
						
							//add a new row for the new ingredient
							addRow(1);
							
							//get the number of the newest row
							int rowToChange = getCurrentRow()-1;
							String ingredient = predictionFrame.getPredictions().get(i).getLabel();
							
							//change the text in the ingredient field
							ingredientFrame.getAddIngredientPanel().getIngredientFields().get(rowToChange).setText(ingredient);
								
	        			}
    				}
    			}
	    	});
	    }
	}
	
	//this method will create the results frame and handle any actions that are performed in the results frame 
	private void generateResults(String query, HashSet<Ingredient> ingredientsList) {
		
		//create an array that holds the most possible search results - 10
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		
		//create an arraylist from the hashset so it can use Collections methods to sort by object's attributes
		ArrayList<Ingredient> ingList = new ArrayList<>(ingredientsList);
		
		//convert all the ingredient measurement to cups
		for (Ingredient i : ingList) {
			i.setQuantity(api.convertAmount(i, "cups"));
			i.setMeasurement(Measurement.CUP);
		}
    	
		//create a new match controller and build the decision making model
	 	MatchController mc = new MatchController(user);
	 	J48 t = mc.buildTree();
	 		 	
	 	//search for the ingredient with the smallest quantity to largest quantity
		Collections.sort(ingList, Comparator.comparing(Ingredient :: getQuantity));
	 	
		//build the list of recipes 
		//first add recipes with the most abundant ingredient to ensure there is at least 7 results to show in the results page
	 	for (int i = 0; i < 7; i++) { 
    		
	 		Recipe recipe = api.findRecipe(ingList.get(ingList.size()-1).getIngredient(), i);
    		
    		//if the recipe was not an empty recipe (i.e. returned a valid recipe)
    		if (!recipe.getName().equals("empty") && !repeat(recipes, recipe)) {
    			recipes.add(recipe);
    		}
    	}
	 		
    	//next search for more specific query, continue removing ingredients until there are 10 recipes s
    	//search a maximum of 10 times per query because of API limitations or else it will exceed max calls
    	for (int i = 0; i < 10; i++) { 
    		
    		Recipe recipe = api.findRecipe(query, i);
    		
    		//if the recipe was not an empty recipe (i.e. returned a valid recipe)
    		if (!recipe.getName().equals("empty") && !repeat(recipes, recipe)) 
    			recipes.add(recipe);
  
    		//once one invalid request is returned there will be no more valid ones, break this loop
    		else {
    			
    			//reset the query
    			query = "";
    			
    			//loop through the ingredients list but omit the smallest quantity ingredient 
    			for (int j = 1; j < ingList.size(); j++) {
    				
    				query += ingList.get(j).getIngredient() + ",";
    			}
    		}
    	}

    	//organize the recipes based on 
    	recipes = mc.organizeRecipesByPreference(recipes);
    	
    	//create a new result frame
    	ResultsFrame resultsFrame = new ResultsFrame(recipes, ingList, user);
    	
    	//add action listener to every image button in the results frame
    	for (RecipeResultPanel resultsPanel : resultsFrame.getRecipePanels())  {
    		
    		//add action listener to the like button
    		resultsPanel.getLikeBtn().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					handlePreferenceBtn(resultsFrame, resultsPanel,"like",e,mc);
				} 
    			
    		});

    		//add action listener to the dislike button
    		resultsPanel.getDislikeBtn().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					handlePreferenceBtn(resultsFrame, resultsPanel,"dislike",e,mc);
				} 
    			
    		});
    		
    		//add action listener to the image of the recipe
    		resultsPanel.getImageBtn().addActionListener(new ActionListener() { 
    			
    			public void actionPerformed(ActionEvent e) { 
      			
  				  //have to loop through buttons again to create a local
  				  //variable within the action performed method to get the corresponding
  				  //URL for the same recipe index 
  				 
  				  for (int i = 0; i < resultsFrame.getRecipePanels().size(); i++) {
  					  if (resultsFrame.getRecipePanels().get(i).getImageBtn() == e.getSource()) {
  			
  	      				  try {  
  	      					//open the link in browser
  	    					Desktop.getDesktop().browse(new URI(resultsFrame.getRecipePanels().get(i).getRecipe().getUrl()));
  	    				} catch (IOException | URISyntaxException e1) {
  	    					// TODO Auto-generated catch block
  	    					e1.printStackTrace();
  	    			   }
  				  }
  				 }
    			} 
    		});
    	}
	}
	
	// this method will handle the events that occur when the like/dislike button is
	// clicked in the results panel
	private void handlePreferenceBtn(ResultsFrame resultsFrame, RecipeResultPanel panel, String preference, ActionEvent e, MatchController mc) {
		
		//loop through the recipes in the results frame
		for (int i = 0; i < resultsFrame.getRecipePanels().size(); i++) {
			
			if (panel == resultsFrame.getRecipePanels().get(i)) {
				
				try {
					//add the new data to the file if clicked
						mc.addData(panel.getRecipe(),preference,user);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//remove the like and dislike buttons
					panel.getLikeBtn().setVisible(false);
					panel.getDislikeBtn().setVisible(false);
			}
			
		}
	
	}
	
	//this method will check if a recipe has already existed in an arraylist of recipes
	
	//this method will check for any repeated recipes in the exisitng list
	private boolean repeat(ArrayList<Recipe> recipes, Recipe recipe) {
	
		//for each recipe in the array of recipes check if it aleady appears
		for (Recipe r : recipes) {	
			if (r.getName().equals(recipe.getName())) {
				return true;
			}
		}
		return false;
	}

	
	// this method will handle the events that occur in the ingredients frame
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//if the user clicks the quiz button
		if (e.getSource() == ingredientFrame.getBuildProfileBtn()) {
			MatchController mc = new MatchController(user);
			mc.createQuiz();
		}
		
		//if the user clicks the logout button
		if (e.getSource() == ingredientFrame.getLogoutBtn()) {
			
			new AccountController();
			ingredientFrame.dispose();
		}
		
		//How to let the user upload a file
		//https://stackoverflow.com/questions/14142932/gui-with-java-gui-builder-for-uploading-an-image-and-displaying-to-a-panelinsid
		//if the user uploads a photo to get ingredients
		if (e.getSource() == ingredientFrame.getUploadPhotoBtn()) {
						
			//Create a file chooser
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
		    File f = chooser.getSelectedFile();
		    String filePath = f.getAbsolutePath();
		    
		    //create the image prediction
		    generateImagePredictionFrame(filePath);
		   
		}
		
		//if the user clicks the add more button 
		if (e.getSource() == ingredientFrame.getAddMoreBtn())  {
			addRow(1);
		}
		//if the user clicks the find recipe button
		if (e.getSource() == ingredientFrame.getFindRecipeBtn()) {
		
			//check if there is at least one ingredient
			if (ingredientFrame.getAddIngredientPanel().getIngredientFields().size() > 0) {
	
				String query = "";
				boolean flagged = false;
				int index = 0;
				
				//array list to store the ingredients the user entered
				HashSet<Ingredient> ingredientsList = new HashSet<Ingredient>();
				
				//using for-each loop over the text fields
				for (int i = 0; i < ingredientFrame.getAddIngredientPanel().getIngredientFields().size(); i++) {
					
					JTextField ingName = ingredientFrame.getAddIngredientPanel().getIngredientFields().get(i);
					JTextField ingQun = ingredientFrame.getAddIngredientPanel().getQuantityFields().get(index);
		        	
						//check the ingredient to make sure its not blank
		        		if (ingName.getText().isBlank() || !api.isFood(ingName.getText())) {
		        			ingName.setBorder(new LineBorder(Color.red,1));
	        				flagged = true;
		        		}
		        		else 
		        			ingName.setBorder(null);
		        		
		        		//check the quantity field is a whole number or decimal
		        		if (!Pattern.matches("[0-9]+", ingQun.getText()) && !Pattern.matches("^\\d*\\.\\d+|\\d+\\.\\d*$", ingQun.getText())) {
		    
		        				ingQun.setBorder(new LineBorder(Color.red,1));
		        				flagged = true;
		        		
		        		}
		        		//create an ingredient object even if row is flagged because it will be stopped by the flag check
		        		//after the for loop but only add if the digit is not flagged because creating an ingredient object 
		        		//with a non numeric value for the quantity results in a number format error which would terminate the program
		        		else  {
		        			ingQun.setBorder(null);
	
		        			//create an ingredient object even if row is flagged because it stopped by the flag check
			        		//after the for loop
				        	Ingredient ing = new Ingredient(ingName.getText(),Float.parseFloat(ingQun.getText()), 
				        			Ingredient.getMeasurementConstant(ingredientFrame.getAddIngredientPanel()
				        					.getMeasurementDropdowns().get(index).getSelectedItem().toString()));
				        	ingredientsList.add(ing);
				        	
				        	//update the new query 
				        	query += ingName.getText() + ",";
		        		}
		        		
		        		index++;
		        }
				//if the something was flagged for incorrect data/format
		        if (flagged)
		        	JOptionPane.showMessageDialog(ingredientFrame,
							"Invalid Data. Please enter valid information.",
							"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
		       
		        else
		        	generateResults(query, ingredientsList);
		        
			}
			else {
				JOptionPane.showMessageDialog(ingredientFrame, "No Ingredients Found.",
						"Unable To Process Request", JOptionPane.ERROR_MESSAGE);
			}
		} 
		int index = 0;
		
		//check if the user clicks the remove button to remove a row
		for (JButton b : ingredientFrame.getAddIngredientPanel().getDeleteBtn()) {
			
			if (e.getSource() == b) {
				deleteRow(index);
				break;
			}
			index++;
		}
		
	}
	
	//this method will handle the events if a key is typed
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	//this method will handle events if a key is clicked
	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	//this method will handle if a key is pressed and if it is pressed in the 
	// ingredients field it will provide a text suggestion
	//this method will handle the key typing actions in the ingredient text field which will
	//create the auto suggestions
	@Override
	public void keyReleased(KeyEvent e) {
			
		//without the empty if statement to check for backspace
		//the program would not allow the text field to delete any characters
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			
		}
		
		//https://www.youtube.com/watch?v=DwanDZuC3_Q (auto suggestions in a text field)
		//create auto suggestions
		else {
			
			//check which text field is being typed on
			for (int i = 0; i < ingredientFrame.getAddIngredientPanel().getIngredientFields().size(); i++) {
				
				JTextField ingName = ingredientFrame.getAddIngredientPanel().getIngredientFields().get(i);
				
				if (e.getSource() == ingName) {
				
					String strCheck = ingName.getText();
					int checkLength = strCheck.length();
					
					String str = api.findSuggestions(strCheck);
					
					if (str == null) {
						str = "";
					}
						
					String strSuggestion = "";
					
					//loop through every character in the textfield
					for (int j = 0; j < checkLength; j++) {
						
						//check if the length of the current word in text field is less 
						//or equal to the length of the possible suggestion
						if (checkLength <= str.length()) 
							strSuggestion += str.charAt(j);
					}
					
					if (strSuggestion.equals(strCheck)) {
						
						ingName.setText(str);
						ingName.setSelectionStart(checkLength);
						ingName.setSelectionEnd(str.length());
					}
		
				}
			}
		}
		
	}

	
	//Getters and Setters
	public IngredientFrame getIngredientFrame() {
		return ingredientFrame;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public void setIngredientFrame(IngredientFrame ingredientFrame) {
		this.ingredientFrame = ingredientFrame;
	}
	
	
}
