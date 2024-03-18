package model;

/*
 * This class is an abstract class that will be extended by any object that 
 * is related to food item. It is not a concrete class because there is not enough
 * data/specific enough to make its own class.
 */
public abstract class FoodItem {

	// attributes
	String name;

	// constructor that will create an instance of a food item with a name as the parameter
	public FoodItem(String name) {
		this.name = name;
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
