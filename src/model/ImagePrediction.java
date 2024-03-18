//Megan Chun
package model;

/*
 * This class will create an image prediction object which will hold data including it's location
 * in an image, the name of the predicted object and the confidence level.
 */
public class ImagePrediction {

	// attributes for an object prediction
	int x1;
	int x2;
	int y1;
	int y2;
	double confidence;
	String label;

	// Constructor that will create an instance of the image prediction with the
	// coordinates of prediction, the confidence and the label
	public ImagePrediction(int x1, int x2, int y1, int y2, double confidence, String label) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.confidence = confidence;
		this.label = label;
	}

	// Getters and Setters
	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
