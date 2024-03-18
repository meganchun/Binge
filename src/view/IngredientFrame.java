package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Fonts;

/*
 * This class will create a frame where users will be able to add ingredients, upload an image to get the ingredients,
 * buttons to add more rows and show results. It will also have buttons that will lead to a new quiz frame and also 
 * a button where users can sign out.
 */
public class IngredientFrame extends JFrame {

	Fonts fonts = new Fonts();

	// GUI components
	private JLabel ingredientTitle = new JLabel("Ingredients");
	private JLabel quantity = new JLabel("Quantity");
	private JLabel measurement = new JLabel("Measurement");
	private JLabel introImage = new JLabel(new ImageIcon("images/ingredientTitleImage.png"));
	private JLabel subheader;
	private JButton logoutBtn = new JButton(new ImageIcon("images/logoutBtn.png"));
	private JButton buildProfileBtn = new JButton(new ImageIcon("images/buildProfileBtn.png"));
	private JButton uploadPhotoBtn = new JButton(new ImageIcon("images/uploadPhoto.png"));
	private JButton addMoreBtn = new JButton(new ImageIcon("images/addMoreBtn.png"));
	private JButton findRecipeBtn = new JButton(new ImageIcon("images/findRecipeBtn.png"));

	// JPanels for the frame
	private AddIngredientPanel addIngredientPanel;

	// scroll pane
	JScrollPane scrPane = new JScrollPane();
	JPanel panel = new JPanel();

	// constructor will create a new instance of the ingredient frame
	public IngredientFrame() {

		// Set up the frame
		setSize(1280, 720); // size
		setTitle("Ingredient");
		setLayout(null); // layout manager
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // location of frame

		// panel to hold the components for the scroll pane
		panel.setPreferredSize(new Dimension(1280, 1050));
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		// logout button
		logoutBtn.setBounds(1137, 43, 95, 34);
		panel.add(logoutBtn);

		// quiz button
		buildProfileBtn.setBounds(965, 43, 155, 34);
		panel.add(buildProfileBtn);

		// title image
		introImage.setBounds(264, 101, 740, 456);
		panel.add(introImage);

		// sub header title
		subheader = new JLabel("To begin: Enter the ingredients in your fridge");
		subheader.setBounds(0, 560, 1280, 30);
		subheader.setHorizontalAlignment(JLabel.CENTER);
		subheader.setFont(Fonts.medium20);
		subheader.setForeground(Color.black);
		panel.add(subheader);

		// button to add another row
		uploadPhotoBtn.setBounds(518, 625, 239, 53);
		uploadPhotoBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(uploadPhotoBtn);

		// panel with the rows of text fields
		addIngredientPanel = new AddIngredientPanel();
		addIngredientPanel.setBounds(0, 650, 1280, 200);
		panel.add(addIngredientPanel);

		// button to add another row
		addMoreBtn.setBounds(535, 850, 214, 53);
		addMoreBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(addMoreBtn);

		// button to find recipes
		findRecipeBtn.setBounds(474, 950, 331, 65);
		findRecipeBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(findRecipeBtn);

		// scroll pane
		scrPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrPane.setBackground(Color.WHITE);
		scrPane.setBounds(0, 0, 1280, 720);
		add(scrPane);

		setVisible(true);

	}

	// Getters and Setters
	public AddIngredientPanel getAddIngredientPanel() {
		return addIngredientPanel;
	}

	public void setAddIngredientPanel(AddIngredientPanel addIngredientPanel) {
		this.addIngredientPanel = addIngredientPanel;
	}

	public JScrollPane getScrPane() {
		return scrPane;
	}

	public void setScrPane(JScrollPane scrPane) {
		this.scrPane = scrPane;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JButton getAddMoreBtn() {
		return addMoreBtn;
	}

	public void setAddMoreBtn(JButton addMoreBtn) {
		this.addMoreBtn = addMoreBtn;
	}

	public JButton getFindRecipeBtn() {
		return findRecipeBtn;
	}

	public void setFindRecipeBtn(JButton findRecipeBtn) {
		this.findRecipeBtn = findRecipeBtn;
	}

	public JButton getUploadPhotoBtn() {
		return uploadPhotoBtn;
	}

	public void setUploadPhotoBtn(JButton uploadPhotoBtn) {
		this.uploadPhotoBtn = uploadPhotoBtn;
	}

	public JButton getLogoutBtn() {
		return logoutBtn;
	}

	public void setLogoutBtn(JButton logoutBtn) {
		this.logoutBtn = logoutBtn;
	}

	public JButton getBuildProfileBtn() {
		return buildProfileBtn;
	}

	public void setBuildProfileBtn(JButton buildProfileBtn) {
		this.buildProfileBtn = buildProfileBtn;
	}

}
