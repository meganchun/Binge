package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import model.Ingredient;
import model.Recipe;
import model.User;
import view.IntroFrame;
import view.QuizFrame;
import view.QuizRecipeInfo;
import view.ResultsFrame;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Debug.Random;
import weka.core.converters.ConverterUtils.DataSource;

/*
 * The match controller will be responsible for building the machine learning decision trees that will be 
 * used to create predictions to whether a recipe is most likely to be liked or disliked by the user. The program 
 * will use Weka's tree classifier to build a tree and another algorithm will convert the tree into a functional
 * tree that can be used to test new upcoming data.
 */
public class MatchController implements ActionListener {
	
	//Declare controller
	public static APIController api;
	
	//Delcare frames
	public QuizFrame quiz;
	public User user;
	
	//counter that will used to keep track of the number of times the quiz appears
	public static int counter;

	//Data constants
	private static final String[] cuisines = {"American", "Asian","British", "Caribbean", "Central Europe", "Chinese", "Eastern Europe",
			"French", "Greek", "Indian", "Italian", "Japanese", "Korean", "Kosher", "Mediterranean", "Mexican", "Middle Eastern", "Nordic", "South American", "South East Asian", "World"};

	private static final String[] healthLabelNames = {"alcohol-free", "dairy-free", "egg-free", "gluten-free", "keto-friendly",
			"kosher", "low-sugar", "paleo", "peanut-free", "vegan", "vegetarian"};
	
	private static final String[] mealTypes =  {"Breakfast", "Lunch", "Dinner", "Snack", "Teatime"};
	
	//Constructor class will create a new instance of the match controller and set the user in the parameter
	//to the user delcared in the top of the class
	public MatchController(User user) {
		
		this.user = user;
	}
	
	//this method will organize an unsorted recipe array list into one that sorts it 
	//by most likely to like then proceeding down becomes less probable recipes
	public ArrayList<Recipe> organizeRecipesByPreference(ArrayList<Recipe> recipes) {
		
		//array to hold the sorted array list
		ArrayList<Recipe> sortedRecipes = new ArrayList<Recipe>();
		
		//temporary array list to hold the recipe the user is predicted to like
		ArrayList<Recipe> likeRecipes = new ArrayList<Recipe>();
		
		try {
			
			api = new APIController();
			
			//build the decision tree based on the users preferences
			J48 c = buildTree();
			
			//loop through all the recipes to check which recipes are 
			//predicted to be liked
			for (Recipe r : recipes) {
				if (predictPreference(c, r).equals("like")) 
					likeRecipes.add(r);
			}
			
			//remove the liked recipes from the orignal recipe list
			recipes.removeAll(likeRecipes);
			
			//first add the recipes that were flagged as like
			for (Recipe r : likeRecipes) {
				sortedRecipes.add(r);
			}
			
			//add the remaining recipes that weren't flagged as like
			for (Recipe r : recipes) {
				sortedRecipes.add(r);
			}
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sortedRecipes;
	
	}
	
	//this method will build a J48 tree classifier using a given data set
	public static J48 buildTree() {
	
		try {
			
			// Specifying the data s
		    DataSource dataSource = new DataSource("data/userPreferences.arff");
		    
		    // Loading the dataset
		    Instances dataInstances = dataSource.getDataSet();
		   
		    // Identifying the label index
		    dataInstances.setClassIndex(dataInstances.numAttributes() - 1);
		    
		    // Creating a decision tree classifier
		    J48 treeClassifier = new J48();
	    	treeClassifier.setOptions(new String[] { "-U" });
	    	treeClassifier.buildClassifier(dataInstances);

			return treeClassifier;
			
		}	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
 
	}
	
	//this method returns "like" or "dislike" based on J48 classifier and a recipe
	public static String predictPreference(J48 tc, Recipe recipe) {
		
		//split the J48 by lines and return the lines as a string array
		String[] result = tc.toString().split("\n",-2);
		
		//create a new stack
		Queue<String> nodes = new PriorityQueue<String>();
		
		String maxLine = "|   ";
		int numLevel = 1;
		
		//determine number of levels
		for (int i = 3; i < result.length - 5; i++) { 
			
			//if the new line has more indents in the previously greatest indent
			if (result[i].contains(maxLine+"|   ")) {
				
				//increase the max line indent characters
				maxLine += "|   ";
				
				//increment num level counter
				numLevel++;
			}
		}	
	
		int row = 0;
		
		//loop through the number of levels in the tree
		for (int level = 0; level < numLevel; level++) {
			
			HashMap<String, Integer> ingredientsTextField = new HashMap<String, Integer>();
			
			//loop through every line of the generated tree
			for (int i = 3; i < result.length - 5; i++) {
					
				//determine if the line matches the current indent line
				int count = 0;
				
			    //iterate through the characters in the line
			    for (int c = 0; c < result[i].length(); c++) {
		
			    	if(result[i].charAt(c) == '|')
			            count++;
			    }
			   
			    //if the current line is in the correct decision level
			    if (count == level) {
			    	
			    	nodes.add(result[i]);
			    	
			    	//get the data for the "questions" using regex 
			    	Pattern dataPattern = Pattern.compile("[a-zA-Z--]+");
			    	Matcher match1 = dataPattern.matcher(result[i]);
			    	
			    	Pattern opPattern = Pattern.compile("[<>=]+");
			    	Matcher match2 = opPattern.matcher(result[i]);
			    	
			    	Pattern valuePattern = Pattern.compile("[0-9]+");
			    	Matcher match3 = valuePattern.matcher(result[i]);
			    	
			    	String data = "";
			    	String operator = "";
			    	String value = "";
			    	String preference = "";
			    	
			    	//if all values are found
			    	if (match1.find() && match2.find() && match3.find()) {
			    	  
			    		data = match1.group(0);
			    		operator = match2.group(0);
			    		value = match3.group(0);
			    	}
			    	
			    	//determine if the result is like or dislike
			    	if (result[i].contains("like"))
			    		preference = "like";
			    	else if (result[i].contains("dislike"))
			    		preference = "dislike";
			    			    		
			    	//if the answer returns true and it is in a row below the previous question's level
			    	if(getAnswer(data, operator, value, recipe) && i > row) {
			   		    
			    		//if it is the final node/option return the predicted preference
			    		if (!preference.equals("")) {
				    		return preference;
				    	}
			    		
			    		//reset the row 
			    		row = i;
			    		break;
			    	}
			 
			    }
				
			}
			
		}
		return "not found";
 		
	}
		
	//this method will determine which node/pathway to proceed to next
	private static boolean getAnswer(String data, String operator, String value, Recipe recipe)  {
	
		//Check every possibility of potential data 
		
		//check if it is checking the cuisine
		if (data.equals("cuisine")) {
			
			if (recipe.getCuisine().equalsIgnoreCase(cuisines[Integer.parseInt(value)])) {
				System.out.println("match");
			}
		
		}
		
		//check if it is checking the meal type
		if (data.equals("mealType")) {
			
			if (value.equals(recipe.getMealType())) {
				
			}
		}
		
		//check if it is checking the number of ingredients or calorie count
		if (data.equals("numIngredients") || data.equals("calorie")) {
			
			double dataToCheck;
			
			if (data.equals("numIngredients"))
				dataToCheck = recipe.getNumIngredients();
			else 
				dataToCheck = recipe.getCalorie();
			
			//use if statements to determine the operator
			//and check the data with the corresponding data
			if (operator.equals("=")) {
				if (dataToCheck == Integer.parseInt(value))
					return true;
				else 
					return false;
			}
			else if (operator.equals("<=")) {
				
				if (dataToCheck <= Integer.parseInt(value))
					return true;
				else 
					return false;
			}
			else if (operator.equals(">=")) {
				
				if (dataToCheck >= Integer.parseInt(value))
					return true;
				else 
					return false;
			}
			else if (operator.equals(">")) {
				
				if (dataToCheck > Integer.parseInt(value))
					return true;
				else 
					return false;
			}
			else if (operator.equals("<")) {
				
				if (dataToCheck < Integer.parseInt(value))
					return true;
				else 
					return false;
			}
		}
		
		//check if it comparing health concerns
		for (int i = 0; i < healthLabelNames.length; i++) {
		
			//check which health concern data it is comparing
			if (data.equalsIgnoreCase(healthLabelNames[i])) {
				
				//if it is checking for yes/no
				if (operator.equals("=")) {
					
					if (recipe.getHealthLabels()[i] == Integer.parseInt(value)) 
						return true;
					else 
						return false;
					
				}
			}
		}
		
		return false;
		
	}
	
	//this method will add the user's preference for a specific recipe into an external file
	public static void addData(Recipe recipe, String preference, User user) throws Exception {
		
		int cuisine = 0;
		String mealType = recipe.getMealType();
		int numIngredients = recipe.getNumIngredients();
		double calorie = recipe.getCalorie();
		String healthLabels = "";
		String preferences = preference;
		
		//determine the numeric representation of the corresponding cuisine
		
		for (int i = 0; i < cuisines.length; i++) {
			if (recipe.getCuisine().contains(cuisines[i]))
				cuisine = i;
		}

		//create a string of the health labels
		for (int i = 0; i < 11; i++) {
			
			if (recipe.getHealthLabels()[i] == 0) 
				healthLabels += ",0";
		
			else 
				healthLabels += ",1";
		}
		
		
		File file = new File("data/userPreferences.arff");
	    FileWriter fr = new FileWriter(file, true);
	    BufferedWriter br = new BufferedWriter(fr);
	    
	    //add to the user's object
	    String data = cuisine+","+mealType+","+numIngredients+","+calorie+healthLabels+","+preferences;
	    user.getPreferenceData().add(data);
	    
	    AccountController.updateUserFile(user);
	    
	   // System.out.println("\n"+cuisine+","+ mealType +"," +numIngredients+","+calorie+healthLabels+","+preferences);
	    br.write("\n"+cuisine+","+mealType+","+numIngredients+","+calorie+healthLabels+","+preferences);

	    br.close();
	    fr.close();
	}
	
	//this method will create the quiz frame
	public void createQuiz() {
		
		//initialize a new counter to only display 10 questions
		counter = 0;
		
		try { 
			//create a new quiz frame and api controller
			quiz = new QuizFrame(user);
			api = new APIController();
			
			Recipe r;
			
			//continue to find a valid ingredient if the request returns an empty/invalid recipe
			do {
				
				r = api.findRecipe(api.getRandomIngredientQuery(), 0);
				
			} while (r.getName().equals("empty"));
			
			//add a new recipe to the screen
			newRecipe(quiz,r);
			
			quiz.getDislikeBtn().addActionListener(this);
			quiz.getLikeBtn().addActionListener(this);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//this method will create a new recipe in the quiz frame 
	private void newRecipe(QuizFrame quiz, Recipe recipe) {
		
		//create a new recipe info panel
		QuizRecipeInfo info = new QuizRecipeInfo(recipe);

		//set to the new panel
		quiz.setQuizRecipeInfo(info);
		
		info.setBounds(79, 151, 1121, 336);
		quiz.add(info);
		
		//revalidate the new info
		quiz.getQuizRecipeInfo().repaint();
		quiz.getQuizRecipeInfo().revalidate();
		
	}
	
	//this method will handle any events that occur in the quiz frame (presses like or dislike button)
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//if the user clicks the like or dislike button
		if (e.getSource() == quiz.getDislikeBtn() || e.getSource() == quiz.getLikeBtn()) {
			
			String preference = "";
			counter++; //increment counter
		
			//if there hasn't been 10 recipes shown already
			if (counter < 10) {
				
				//set preference depending on the button clikedd
				if (e.getSource() == quiz.getDislikeBtn())
					preference = "dislike";
				else
					preference = "like";
				
				//add a new recipe panel
				try {
					
					//add the new data to the arff file
					addData(quiz.getQuizRecipeInfo().getRecipe(), preference, user);
					
					//remove the previous recipe
					quiz.remove(quiz.getQuizRecipeInfo());
					
					Recipe r;
					
					//continue to find a valid ingredient
					do {
						
						// create a randow object to get a random between 0-9
						Random rand = new Random();
						int randResultNum = rand.nextInt(10);
						
						r = api.findRecipe(api.getRandomIngredientQuery(), randResultNum);
						
					} while (r.getName().equals("empty"));
					
					//add a new recipe
					newRecipe(quiz, r);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				
				//close the quiz 
				quiz.dispose();
				
				//open a new ingredient frame by creating a new instancee of the food controlle class
				new FoodController(user);
			}
			
			
		}
	}
	
}
