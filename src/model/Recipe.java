package model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;

/*
 * This class will create a recipe object which will have attributes collected by the api recipe call. 
 * It extends the food item class and uses composition for the ingredients attribute
 */
public class Recipe extends FoodItem {

	// Declare the attributes for a recipe
	String cuisine;
	String mealType;
	String instructions;
	Ingredient[] ingredientList;
	int[] healthLabels;
	int numIngredients;
	double calorie;
	Image image;
	String url;
	HashMap<Nutrient, String> nutrients;

	// enum of nutrient constants 
	public enum Nutrient {
		VITC, VITB6A, VITD, K, CA, CHOCDF
	}

	// Constructor for recipes that have all the valid and required information
	public Recipe(String name, String cuisine, String mealType, String instructions, Ingredient[] ingredientList,
			int[] healthLabels, int numIngredients, double calorie, Image image, String url,
			HashMap<Nutrient, String> nutrients) {

		super(name);
		this.cuisine = cuisine;
		this.mealType = mealType;
		this.instructions = instructions;
		this.ingredientList = ingredientList;
		this.healthLabels = healthLabels;
		this.numIngredients = numIngredients;
		this.calorie = calorie;
		this.image = image;
		this.url = url;
		this.nutrients = nutrients;
	}

	// Constructor overloading that will be used when creating an temporary Recipes
	// to catch the invalid recipes
	// Constructor that will be used when an invalid request is sent to find a recipe
	public Recipe(String string) {
		super("empty");
	}

	// this method will get the vitamins written name based on the nutrient constant name
	public String getNurtientName(Nutrient n) {

		//check what the string of the nutrient is
		switch (n) {
		case VITC:
			return "Vitamin C";
		case VITB6A:
			return "Vitamin B6";
		case VITD:
			return "Vitamin D";
		case K:
			return "Potassium";
		case CA:
			return "Calcium";
		case CHOCDF:
			return "Carbs";

		}
		return "N/A";
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public Ingredient[] getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(Ingredient[] ingredientList) {
		this.ingredientList = ingredientList;
	}

	public int[] getHealthLabels() {
		return healthLabels;
	}

	public void setHealthLabels(int[] healthLabels) {
		this.healthLabels = healthLabels;
	}

	public int getNumIngredients() {
		return numIngredients;
	}

	public void setNumIngredients(int numIngredients) {
		this.numIngredients = numIngredients;
	}

	public double getCalorie() {
		return calorie;
	}

	public void setCalorie(double calordie) {
		this.calorie = calorie;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
