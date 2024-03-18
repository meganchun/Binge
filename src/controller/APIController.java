package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;

import clarifai2.internal.grpc.api.V2Grpc;
import model.ImagePrediction;
import model.Ingredient;
import model.Ingredient.Measurement;
import model.Recipe;
import model.Recipe.Nutrient;
import view.IngredientFrame;
import weka.core.Debug.Random;

/*
 * The APIController class will manage all outgoing GET and POST request for the recipes and image recognition software
 * All request made to API's will be returned into a JSON format which then will be filtered for only specific info.
 * Some of the request made in this class are returning recipes and auto suggestions for the adding ingredient frame.
 */
public class APIController {

	// health labels constant
	private final String[] healthLabelNames = { "alcohol-free", "dairy-free", "egg-free", "gluten-free",
			"keto-friendly", "kosher", "low-sugar", "paleo", "peanut-free", "vegan", "vegetarian" };

	//the constructor will create an instance of the API Controller class but won't call any methods 
	public APIController() throws Exception {
		
	}
	
	// this method will make a get request to a HTTP url and return the data in JSON format
	public Object getJSON(String query, String type) {

		// https://youtu.be/NyPp3dAxb0I?si=Gy8j2LXSss8ath93 (sending HTTP get request)
		try {
			// string URL of the get request
			HttpURLConnection con = getURLCon(type, query);

			// set the request methtod
			con.setRequestMethod("GET");

			con.setRequestProperty("Content-Type", "application/json");

			//if the request is a bad URL (code 400)
			if (con.getResponseCode() == 400) {

				//return an empty JSONObject
				return new JSONObject();
			} 
			
			//if it is a valid URL request
			else {
				// buffered reader to get the input from the request
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

				// string to hold the next line
				String inputLine;

				String response = "";

				// while the request still has lines of data continue adding it to the string
				// buffer
				while ((inputLine = in.readLine()) != null) {
					response += (inputLine);
				}
				
				// close the buffered reader
				in.close();

				//change the type of return data depending on what data the program needs
				//the text field auto complete suggestions returns in array format
				if (type.equals("suggestion"))
					return new JSONArray(response);
				else
					return new JSONObject(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//this method will create a URL connection based on the which request the program is trying to make
	private HttpURLConnection getURLCon(String type, String query) {
		
		try {	
			// string URL of the get request
			String url = "";
		
			//determine the URL link depending on the type of request that was sent in the parameter
			if (type.equals("recipe"))
				url = "https://api.edamam.com/search?q=" + query
						+ "&field=mealType,image&app_id=0a1a17d6&app_key=005d8f620fe9335e07546137c5ad37ea";
			else if (type.equals("suggestion"))
				url = "https://api.edamam.com/auto-complete?app_id=b3a55c98&app_key=39ac58e6b1bd8b22327a1a5b87aee6ee&q="
						+ query + "&limit=1";
			else if (type.equals("imageAnalyze"))
				url = "https://api.api-ninjas.com/v1/objectdetection";
			else if (type.equals("checkFood"))
				url = "https://api.spoonacular.com/food/detect";
			else if (type.equals("convertAmount")) {
				url = "https://api.spoonacular.com/recipes/convert?"+query+"&apiKey=9afda2b3501448af98e197f075bfd0e5";
			}
		
			//create the URL and HTTP connection
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			return con;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	// this method will return a recipe based on a query keyword and number which represent which result of the ten
	// possible results you would be collecting data for 
	public Recipe findRecipe(String query, int num) {

		try {
			// https://stackoverflow.com/questions/29182842/how-to-convert-java-string-to-json-object
			// (converting buffered string to string for json format)
			JSONObject json = (JSONObject) getJSON(query, "recipe");
			
			if (json.length() == 0) {
			
				Recipe emptyRecipe = new Recipe("empty");
				return emptyRecipe;
			}
			else {

				//create a JSON array of the "hits"
				JSONArray hits = json.getJSONArray("hits");

				//if the index of the result + 1 (plus one accounts for the number of the search rather than the index
				//which begins at 0) of the which result to search from the hits is NOT greater than 
				//the number of recipes retrieved then continue to get the recipe
				if (hits.length() >= num + 1) {

					// Get the object that contains the data
					JSONObject recipes = hits.getJSONObject(num);

					// Get the recipe data
					JSONObject recipe = recipes.getJSONObject("recipe");
					
					return getRecipeData(recipe);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		Recipe emptyRecipe = new Recipe("empty");
		return emptyRecipe;

	}

	//this method will get the data and attributes for a recipe recieved from the GET request
	private Recipe getRecipeData(JSONObject recipe) throws Exception {

		// https://stackoverflow.com/questions/20899839/retrieving-values-from-nested-json-object
		// (nested JSON values)

		// declare the attributes for a recipe
		String name;
		String cuisine;
		String mealType;
		String instructions;
		Ingredient[] ingredientList;
		int[] healthLabels;
		int numIngredients;
		double calorie;
		Image image = null;
		String url;

		// Get the recipe name
		name = recipe.get("label").toString();
	
		// Get the cuisine type
		cuisine = recipe.get("cuisineType").toString();

		// Get the meal type
		String tempMealType;
		mealType = "";
		tempMealType = recipe.get("mealType").toString();

		// read for any patterns of "MEALTYPE" if there are multiple
		Pattern pattern = Pattern.compile("[a-zA-Z]+");

		Matcher matcher = pattern.matcher(tempMealType);

		// while there is a match
		while (matcher.find()) {
			mealType = matcher.group(0);
		}

		// Get the instructions
		if (recipe.has("instructions"))
			instructions = recipe.get("instructions").toString();
		else
			instructions = "n/a";

		
		// Get the ingredients
		JSONArray ingList = recipe.getJSONArray("ingredients");

		// create a new ingredient array list
		ingredientList = new Ingredient[ingList.length()];

		// Iterate through all the ingredients in the list
		for (int i = 0; i < ingList.length(); i++) {

			JSONObject ingredient = ingList.getJSONObject(i);

			// save the data of each ingredient
			String ingName = ingredient.get("food").toString();
			float ingQuan = Float.parseFloat(ingredient.get("quantity").toString());		
			Measurement ingMeasure = Ingredient.getMeasurementConstant(ingredient.get("measure").toString());
						
			// create a new ingredient object
			ingredientList[i] = new Ingredient(ingName, ingQuan, ingMeasure);
		}

		// Get the health labels
		JSONArray healthLabelsArr = recipe.getJSONArray("healthLabels");

		healthLabels = new int[healthLabelNames.length];

		// set true or false for the health labels
		// iterate through each flagged health label in the recipe
		for (int i = 0; i < healthLabelsArr.length(); i++) {

			// iterate through each potential health label
			for (int j = 0; j < healthLabelNames.length; j++) {

				// if the flagged and the potential match
				if (healthLabelsArr.get(i).toString().equalsIgnoreCase(healthLabelNames[j])) {

					// set the health label at that index to true
					healthLabels[j] = 1;
				}
			}
		}
		
		// Get number of ingredients
		numIngredients = ingList.length();

		// Get recipe calorie
		calorie = Float.valueOf(recipe.get("calories").toString());

		// Get image
		String path = recipe.get("image").toString();
		URL imageUrl = new URL(path);
		image = ImageIO.read(imageUrl);

		// Get the recipe url
		url = recipe.get("url").toString();
		
		// Get the nurtient data 
		JSONObject nurtient = recipe.getJSONObject("totalDaily");
		
		// Hash map that stores the nurtient as the key and amount and unit as the value
		HashMap<Nutrient, String> n = new HashMap<Nutrient, String>();
		
		//loop through all the types of nutrients
		for (Nutrient nValue : Nutrient.values()) {
			
			JSONObject nutrientInfo = nurtient.getJSONObject(nValue.name());
	
			String info = nutrientInfo.get("quantity").toString() + " " + nutrientInfo.get("unit").toString();	
			n.put(nValue, info);
		}

		//return the recipe with the collected data
		return new Recipe(name, cuisine, mealType, instructions, ingredientList, healthLabels,
				numIngredients, calorie, image, url, new HashMap<Nutrient, String>());

	}

	//this method will return a suggestion for the user's entered string text
	public String findSuggestions(String query) {

		try {

			JSONArray jsonArr = (JSONArray) getJSON(query, "suggestion");
			
			// check if the suggestion is a bad request and filter before sending the
			// suggestion back
			if (jsonArr.length() == 0) {
				return "";
			} else {
				return jsonArr.get(0).toString();
			}

		} catch (Exception e) {
			return null;
		}

	}

	// this method will make prediction of what an object is in a photo uploaded by the user
	public ArrayList<ImagePrediction> predictImage(String filePath) {

		try {
			//https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java (making a POST request)
			HttpURLConnection con = getURLCon("imageAnalyze","");

			con.setRequestMethod("POST"); // set request to POST
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + "*****"); // multi-part will allow for file upload
			con.setRequestProperty("X-Api-Key", "5jymRZolSLDNsDNqMTyQjw==L32hXWvCmCy4yMfb"); // add my API key as a header

			// enable input/output streams for writing and reading the data
			con.setDoOutput(true);
			con.setDoInput(true);

			// create a multipart request
			try (OutputStream os = con.getOutputStream(); DataOutputStream dos = new DataOutputStream(os)) {

				// add the API key to authorize
				dos.writeBytes("--*****\r\n");
				dos.writeBytes("Content-Disposition: form-data; name=\"Api-Key\"\r\n");
				dos.writeBytes("\r\n");
				dos.writeBytes("5jymRZolSLDNsDNqMTyQjw==L32hXWvCmCy4yMfb");
				dos.writeBytes("\r\n");

				// add the image
				dos.writeBytes("--*****\r\n");
				dos.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"example.png\"\r\n");
				dos.writeBytes("Content-Type: image/png\r\n");
				dos.writeBytes("\r\n");

				// read the image file
				try (FileInputStream fis = new FileInputStream(filePath)) {
					byte[] buffer = new byte[1024];
					int bytesRead;
					while ((bytesRead = fis.read(buffer)) != -1) {
						dos.write(buffer, 0, bytesRead);
					}
				}

				// signal the end of the multipart request
				dos.writeBytes("\r\n--*****--\r\n");
			}

			// get the response returned from the request
			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String inputLine;
				String response = "";

				while ((inputLine = in.readLine()) != null) {
					response += (inputLine);
				}

				JSONArray jsonArr = new JSONArray(response);

				// create an arraylist of the predictions
				ArrayList<ImagePrediction> predictions = new ArrayList<ImagePrediction>();

				//loop through all the given predicted objects
				for (int i = 0; i < jsonArr.length(); i++) {

					int x1;
					int x2;
					int y1;
					int y2;
					double confidence;
					String label;

					JSONObject prediction = jsonArr.getJSONObject(i);

					// get the coordinates of the prediction
					JSONObject coor = prediction.getJSONObject("bounding_box");

					//save the coordinates
					y1 = Integer.parseInt(coor.get("y1").toString());
					x1 = Integer.parseInt(coor.get("x1").toString());
					y2 = Integer.parseInt(coor.get("y2").toString());
					x2 = Integer.parseInt(coor.get("x2").toString());

					// get the confidence
					confidence = Double.parseDouble(prediction.get("confidence").toString());

					// get the label
					label = prediction.get("label").toString();

					
					// call the isFood method to check whether the labeled object is detected to be a food
					if (isFood(label)) {
						// create a image prediction object and add to the array list
						predictions.add(new ImagePrediction(x1, x2, y1, y2, confidence, label));
					}

				}
				return predictions;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// this method will check if a label is a type of food by calling a POST request
	// to an API to make a Named Entity Recognition (NER) request. If any results are returned
	// for food the method will return true, else it would return false
	public boolean isFood(String label) {

		try {
			
			HttpURLConnection connection = getURLCon("checkFood","");

			// Set the request method to POST
			connection.setRequestMethod("POST");

			// Enable input and output streams
			connection.setDoOutput(true);

			// Set request headers (optional)
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("x-api-key", "9afda2b3501448af98e197f075bfd0e5");

			// Prepare the request body with a text parameter
			String textParameter = label;
			String requestBody = "text=" + URLEncoder.encode(textParameter, "UTF-8");

			// Get the output stream and write the request body to it
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = requestBody.getBytes("utf-8");
				os.write(input, 0, input.length);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Get the response code
			int responseCode = connection.getResponseCode();

			// Read the response from the server
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String line;
				String response = new String();
				while ((line = reader.readLine()) != null) {
					response += (line);
				}

				JSONObject ob = new JSONObject(response);

				JSONArray arr = ob.getJSONArray("annotations");

				// Close the connection
				connection.disconnect();

				if (arr.length() > 0)
					return true;
				else
					return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//this method will get a random ingredient from the spoonacular database
	// this method will get a random query for the quizzes and gets the information
	// for a pre-made data set from spoonacular
	public String getRandomIngredientQuery() {

		try {

			// create a randow object to get a random between 0-1000
			Random rand = new Random();
			int randIngNum = rand.nextInt(1001);

			// create a file reader from the externally sourced ingredient file
			// https://spoonacular.com/food-api/docs#List-of-Ingredients (list of
			// ingredients)
			FileInputStream fs = new FileInputStream("data/ingredients.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));

			// iterate through all the ingredients
			for (int i = 0; i < 1000; ++i) {

				// return the ingredient when row matches random number
				if (i == randIngNum) {

					String ing = br.readLine();
					return ing.split(";")[0];
				}

				br.readLine();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	//this method will convert 
	public double convertAmount(Ingredient ing, String targetUnit) {
		
		try {
			
			//create a query that follows the format ingredientName=CHANGE&sourceAmount=CHANGE&sourceUnit=CHANGE&targetUnit=CHANGE
			String query = "ingredientName="+ing.getName()+"&sourceAmount="+ing.getQuantity()+"&sourceUnit="+ing.getMeasurement()+"&targetUnit="+targetUnit;
			
			JSONObject json = (JSONObject) getJSON(query, "convertAmount");
			
			// check if the conversion request was an error and if it is send back the original result
			if (json.length() == 0) {
				return ing.getQuantity();
			} else {
				
				return Double.parseDouble(json.get("targetAmount").toString());
			}

		} catch (Exception e) {
			return ing.getQuantity();
		}
		
	}
}
