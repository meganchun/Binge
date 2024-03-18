package model;

import java.util.HashMap;
import java.util.List;

/*
 * This class will create an ingredient object that extends the food item class and also will be used 
 * by composition with the recipe class
 */
public class Ingredient extends FoodItem {

	// attributes of a ingredient
	double quantity;
	Measurement measurement;

	// enum that will contain the constants of the measurement types
	public enum Measurement {
		CUP, TEASPOON, TABLESPOON, GRAM, KILOGRAM
	}

	// Ccnstructor that will create an instance of the ingredient with the name,
	// quantity and measurement in the parameter
	public Ingredient(String name, float quantity, Measurement measurement) {

		super(name);
		this.quantity = quantity;
		this.measurement = measurement;
	}

	// Getters and Setters
	public String getIngredient() {
		return name;
	}

	public void setIngredient(String name) {
		this.name = name;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	// this method will return the measurement constant of the string
	// version of the corresponding measurement type
	public static Measurement getMeasurementConstant(String measurementStr) {

		// check which measurement it is and check for different different case
		// sensitivity
		switch (measurementStr) {
		case "cup":
		case "Cup":
			return Measurement.CUP;
		case "teaspoon":
		case "Teaspoon":
			return Measurement.TEASPOON;
		case "tablespoon":
		case "Tablespoon":
			return Measurement.TABLESPOON;
		case "kilogram":
		case "Kilogram":
			return Measurement.KILOGRAM;
		case "gram":
		case "Gram":
			return Measurement.GRAM;

		}
		return null;
	}

}
