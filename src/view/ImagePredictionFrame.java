package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import model.Fonts;
import model.ImagePrediction;

/*
 * This class will create a window with an image that was uploaded previously and display 
 * the image along with the predictions outlined on the screen, the label/name and the confidence level.
 * Each object prediction will also have a add button to add the ingredient to the ingredient panel
 */
public class ImagePredictionFrame extends JFrame {

	Fonts font = new Fonts();

	// GUI elements
	JLabel background;
	JPanel[] bounds;
	JButton logo = new JButton(new ImageIcon("images/bingeLogo.png"));
	JLabel[] labels;
	JButton[] addBtns;
	ArrayList<ImagePrediction> predictions;

	public ImagePredictionFrame(ArrayList<ImagePrediction> predictions, ImageIcon image) {

		this.predictions = predictions;

		// Set up the frame
		setSize(image.getIconWidth() + 50, image.getIconHeight() + 100); // size
		setTitle("Uploaded Image Predictions");
		setLayout(null); // layout manager
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // location of frame

		// add logo
		logo.setBounds(0, 15, image.getIconWidth() + 50, 52);
		logo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(logo);

		// addBtns the background
		background = new JLabel(image);
		background.setBounds(25, 75, image.getIconWidth(), image.getIconHeight());
		add(background);

		// create new components for the corresponding number of predictions
		bounds = new JPanel[predictions.size()];
		labels = new JLabel[predictions.size()];
		addBtns = new JButton[predictions.size()];

		// loop through each prediction and create a border with label and addBtns
		// button
		for (int i = 0; i < predictions.size(); i++) {

			// get the width and height of the prediction area
			int width = predictions.get(i).getX2() - predictions.get(i).getX1();
			int height = predictions.get(i).getY2() - predictions.get(i).getY1();

			// create a new panel for the prediction
			bounds[i] = new JPanel();
			bounds[i].setOpaque(false);
			bounds[i].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
			bounds[i].setBounds(predictions.get(i).getX1(), predictions.get(i).getY1(), width, height);
			background.add(bounds[i]);

			// addBtns the label
			labels[i] = new JLabel(
					predictions.get(i).getLabel() + " (" + (predictions.get(i).getConfidence() * 100) + "%)");
			labels[i].setFont(font.medium16);
			labels[i].setForeground(Color.WHITE);
			labels[i].setOpaque(true);
			labels[i].setBackground(new Color(238, 79, 88));
			bounds[i].add(labels[i]);

			// addBtns the addBtns button to the panel
			addBtns[i] = new JButton(new ImageIcon("images/addBtn.png"));
			addBtns[i].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			bounds[i].add(addBtns[i]);

		}

		setVisible(true);
	}

	// Getters and Setters
	public ArrayList<ImagePrediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(ArrayList<ImagePrediction> predictions) {
		this.predictions = predictions;
	}

	public JPanel[] getJPanelBounds() {
		return bounds;
	}

	public void setJPanelBounds(JPanel[] bounds) {
		this.bounds = bounds;
	}

	public JLabel[] getLabels() {
		return labels;
	}

	public void setLabels(JLabel[] labels) {
		this.labels = labels;
	}

	public JButton[] getaddBtns() {
		return addBtns;
	}

	public void setaddBtns(JButton[] addBtns) {
		this.addBtns = addBtns;
	}

}
