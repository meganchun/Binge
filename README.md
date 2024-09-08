# Binge

Your all-in-one recipe prediction tool powered by machine learning! Binge helps you find recipes based on the ingredients you have, whether entered manually or uploaded via photo. The more you use it, the smarter it gets, thanks to its dynamic learning algorithms.
Binge leverages advanced machine learning algorithms to suggest recipes based on your available ingredients. You can enter ingredients manually or upload a photo for ingredient detection. The app's algorithm refines its predictions based on user feedback and input, allowing for more accurate and personalized recipe recommendations. The quiz feature further enhances the algorithm’s accuracy by incorporating additional user data.

## Features

- **Image Upload for Ingredient Detection:**
  - Detects ingredients from uploaded photos.
  - Outlines detected labels and provides predicted names with confidence levels.
  - Filters out irrelevant detections (e.g., non-food items like bottles or ties).

- **Ingredient Input Panel:**
  - Smart auto-suggestions for ingredient input, sourced from an external food database.
  - Dynamic row addition/removal for ingredient input.
  - Validates ingredient entries to ensure accuracy (e.g., rejects invalid entries like "bottle").
  - Automatic conversion of ingredient measurements to identify the most abundant ingredients, affecting the recipe results.

- **Machine Learning Preference:**
  - Develops a personalized machine learning classifier that updates based on user feedback (likes/dislikes).
  - Provides real-time predictions using a decision tree built from the J48 algorithm.
  - Continuously improves prediction accuracy with increased user data.

- **User-Based Application:**
  - Allows account creation to save and continue user data across sessions.
  - Keeps user data updated with changes to their account.

## Major Skills

- **Object-Oriented Programming (OOP):**
  - **Inheritance:** `Ingredient` and `Recipe` classes are inherited by the `FoodItem` class.
  - **Composition:** `Recipe` requires `Ingredient` objects as attributes.
  - **Enum:** Used for measurement and nutrition data.

- **Data Structures:**
  - Utilizes `ArrayList`, arrays, `HashMap`, and `HashSet`.

- **APIs:**
  - **GET and POST Requests:**
    - Recipe search
    - Ingredient search and validation
    - Ingredient conversion
    - Image label analysis
  - **JSON Data Handling:**
    - Reading and processing data from JSON format.
   
![bingeCard](https://github.com/user-attachments/assets/bf41be06-9354-42fa-a3c5-0dcce5da253f)
