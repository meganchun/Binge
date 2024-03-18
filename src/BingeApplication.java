/* Megan Chun
 * 
 * January 19, 2024
 * 
 * ICS4U1-04 - Mr.Fernandes
 * 
 * Binge 
 * 
 * Description: ------------
 * Binge is an all in one tool that uses machine learning algorithm to create predictions on recipes suggested to you.
 * The app allows user to enter data about their available ingredients or upload a photo to add ingredients. The photo 
 * upload will make predictions with the analyzed image and states its predictions. Additionally, the machine learning 
 * algorithm will dynamically update as the user favors or dislikes the recipes in the results page or in the quiz. The 
 * quiz feature will allow the users to build and curate a more comprehensive algorithm as the more data inputed will result
 * in more accurate predictions.
 * 
 * Features: ------------
 * Image upload for ingredient detection
 * 		- outlines the corresponding label detection 
 * 		- displays the predicted name and confidence level
 * 		- filters out any predictions that aren't relevant to food/ingredient
 * 				- e.g. if it detects a bottle or tie, it will not display the detection to the user
 * 
 * Ingredient input panel 
 * 		- Smart auto suggestion in the text field that is dynamically collected from external food data base
 * 		- add/remove rows dynamically
 * 		- checks if the user entered a valid ingredient
 * 				- e.g. rejects the request if they enter "bottle"
 * 		- features automatic ingredient measurement conversion to check for which ingredient is the most abundant 
 * 		  which will impact how the order of the results 
 * 
 * Machine learning preference 
 * 		- builds a unique machine learning classifier that dynamically updates as user inputs more data (like/dislike a recipe)
 * 		- makes real time predictions 
 * 		- reads the created J48 object to create a algorithm from the returned decision tree that allows the program to make real time predictions
 * 
 * User based application 
 * 		- Allows user to create a unique account that will save their data that can be continued after the program ends
 * 		- Their data and saved is updated whenever they make changes to their account
 * 
 * 
 * Major Skills: ------------
 * 
 * OOP
 * - Inheritance (Ingredient and Recipe classes are inherited by the FoodItem class)
 * - Composition (Recipe requires Ingredient objects as one of its attributes)
 * - Enum (Measurement and Nutrition data)
 * 
 * Data Structures 
 * - ArrayList, Arrays, Hashmap, Hashset
 * 
 * APIs
 * - GET and POST request
 * 		- Recipe search
 * 		- Ingredient search and validation
 * 		- Ingredient conversion
 * 		- Image label analysis
 * - Reading and collecting data from JSON format
 * 
 * Concerns: ------------
 * - The machine learning software that I am using may not be compatible with my version of Java which results
 * 	 in an error message "java.lang.reflect.InaccessibleObjectException"  when building the classifier for the 
 * 	 decision tree. However this does not effect the classifier being built because it is still able to produce a valid 
 *   decision tree which can be seen when displayed onto the console.
 *   
 * API limitations
 * - Spoonacular (160 request/day)
 * - Edamam Recipe (10 calls/minute)
 * 		- Due to the lack of request that can be made, the range of recipes collected to be displayed may lack in its diversity
 * - Edamam Auto-Complete (35/minute)
 * - API Ninjas (100000 calls/month)
 * 
 * Required Libraries
 * - core-2.8.1.jar
 * - gson-2.7.jar
 * - httpclient-4.5.14.jar
 * - json-20140107.jar
 * - json-simple-1.1.1.jar
 * - jsoup-1.13.1.jar
 * - remoteExperimentServer.jar
 * - weka.jar
 * - weka-src.jar
 * 
 */
import controller.APIController;
import controller.AccountController;
import controller.FoodController;
import controller.MatchController;
import view.IngredientFrame;
import view.QuizFrame;

/*
 * The BingeApplication class will create a new instance of the AccountController that will handle the programs actions.
 */
public class BingeApplication {

	//main method to create an instance of AccountController
	public static void main(String[] args) {
		
		try {
			new AccountController();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
