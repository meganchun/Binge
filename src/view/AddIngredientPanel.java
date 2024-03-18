package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Fonts;

/*
 * This class will be the panel where the rows of the ingredients, quantity and measurement will be added to.
 * This class is meant to be a child component to the main ingredient frame class.
 */
public class AddIngredientPanel extends JPanel {

	private Fonts fonts = new Fonts();
	
	//Initialize and declare the layout manager
	private GridBagConstraints c = new GridBagConstraints();
	
	//array list of the the different information to be collected
	private List<JTextField> ingredientFields = new ArrayList<JTextField>();
	private List<JTextField> quantityFields = new ArrayList<JTextField>();
	private List<JComboBox> measurementDropdowns = new ArrayList<JComboBox>();
	private List<JButton> deleteBtns = new ArrayList<JButton>();
	
	//list of drop down options for the measurement
	private String[] measurementList = {"Cup","Teaspoon","Tablespoon","Gram","Kilogram"};
	
	//constructor that will create an instance of the add ingredient panel
	public AddIngredientPanel() {
		
		setOpaque(false);
		setLayout(new GridBagLayout());
		this.setBorder(new EmptyBorder(0, 200, 0, 200));

	}
	
	//method to add rows into the panel
	public void addNewRow(int numRows, int currentRow) {
	
		// TODO Auto-generated method stub
		for (int row = 0; row < numRows; row++) {
			
			//add a new ingredient text field
			c.weightx = 0.9;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(20,0,0,20);
			c.gridx = 0;  //set the alignment
			c.gridy = currentRow + 1;
			c.gridwidth = 4;
			JTextField ingredient = new JTextField("Add ingredient...");
			ingredient.setBackground(new Color(243, 244, 245)); //change background
			ingredient.setForeground(new Color(105,105,105)); //change text colour
			ingredient.setHorizontalAlignment(JTextField.LEFT); //align text
			ingredient.setFont(Fonts.medium20); //set font
			ingredient.setBorder(BorderFactory.createEmptyBorder()); //create transparent border
			add(ingredient, c);
			
			//add the quantity text field
			c.insets = new Insets(20,50,0,20); //add padding to the right
			c.gridx = 5;  //set the alignment
			c.gridy = currentRow + 1;
			c.gridwidth = 1;
			JTextField quantity = new JTextField("...");
			quantity.setBackground(new Color(243, 244, 245)); //change background
			quantity.setHorizontalAlignment(JTextField.CENTER);
			quantity.setForeground(new Color(105,105,105)); //change text colour
			quantity.setHorizontalAlignment(JTextField.LEFT); //align text
			quantity.setFont(Fonts.medium20); //set font
			quantity.setBorder(BorderFactory.createEmptyBorder()); //create transparent border
			add(quantity, c);
			
			//add the measurement drop down option
			c.insets = new Insets(20,0,0,0); //add padding to the right
			c.gridx = 6;  //set the alignment
			c.gridy = currentRow + 1;
			c.gridwidth = 1;
			JComboBox measurementDropDown = new JComboBox(measurementList);
			measurementDropDown.setBackground(Color.WHITE); //change background
			measurementDropDown.setForeground(new Color(105,105,105)); //change text colour
			measurementDropDown.setFont(Fonts.medium20); //set font
			measurementDropDown.setBorder(BorderFactory.createEmptyBorder()); //create transparent border
			add(measurementDropDown, c);
			
			//add delete button
			c.insets = new Insets(20,0,0,0); //add padding to the right
			c.gridx = 7;  //set the alignment
			c.gridy = currentRow + 1;
			c.gridwidth = 1;
			JButton delete = new JButton(new ImageIcon("images/deleteBtn.png"));
			delete.setBorder(BorderFactory.createEmptyBorder()); //create transparent border
			add(delete, c);
		
			
			//add the new row of text fields to the hashmap
			ingredientFields.add(ingredient);
			quantityFields.add(quantity);
			measurementDropdowns.add(measurementDropDown);
			deleteBtns.add(delete);
			
			//increment the currentRow
			currentRow++;
			
		}
		
	}
	
	//Getters and Setters 
	public List<JButton> getDeleteBtn() {
		return deleteBtns;
	}

	public void setDeleteBtn(List<JButton> deleteBtn) {
		this.deleteBtns = deleteBtn;
	}

	public List<JTextField> getIngredientFields() {
		return ingredientFields;
	}

	public void setIngredientFields(List<JTextField> ingredientFields) {
		this.ingredientFields = ingredientFields;
	}

	public List<JComboBox> getMeasurementDropdowns() {
		return measurementDropdowns;
	}

	public void setMeasurementDropdowns(List<JComboBox> measurementDropdowns) {
		this.measurementDropdowns = measurementDropdowns;
	}

	public List<JTextField> getQuantityFields() {
		return quantityFields;
	}

	public void setQuantityFields(List<JTextField> quantityFields) {
		this.quantityFields = quantityFields;
	}
	
	
	
	
}
