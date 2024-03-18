package model;
/* Megan Chun
 * 
 * External Source: //https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
 */

import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.awt.Font;

import javax.swing.JFrame;

public class Fonts extends JFrame {

	//graphics environment 
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	
	//fonts variables
	public static Font regular16;
	public static Font medium10;
	public static Font medium12;
	public static Font medium16;
	public static Font medium20;
	public static Font medium25;
	public static Font medium50;
	public static Font semibold14;
	public static Font semibold18;
	public static Font semibold25;
	public static Font semibold30;
	public static Font semibold50;

	public Fonts() {
		
		//create the font
		try {
		    //create regular size 16 
			regular16 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSans.ttf")).deriveFont(16f);
		    ge.registerFont(regular16); 
		    
		    //create medium size 10
		    medium10 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansMedium.ttf")).deriveFont(10f);
		    ge.registerFont(medium10); 
		    
		    //create medium size 12
		    medium12 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansMedium.ttf")).deriveFont(12f);
		    ge.registerFont(medium12); 
		    
		    //create medium size 16
		    medium16 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansMedium.ttf")).deriveFont(16f);
		    ge.registerFont(medium16); 
		    
		    //create medium size 20
		    medium20 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansMedium.ttf")).deriveFont(20f);
		    ge.registerFont(medium20); 
		    
		    //create medium size 25 
		    medium25 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansMedium.ttf")).deriveFont(25f);
		    ge.registerFont(medium25); 
		    
		    //create medium size 50 
		    medium50 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansMedium.ttf")).deriveFont(50f);
		    ge.registerFont(medium50);
		   
		    //create semi bold size 14
		    semibold14 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansSemiBold.ttf")).deriveFont(14f);
		    ge.registerFont(semibold14); 
		    
		    //create semi bold size 18
		    semibold18 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansSemiBold.ttf")).deriveFont(18f);
		    ge.registerFont(semibold18); 
		    
		    //create semi bold size 25 
		    semibold25 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansSemiBold.ttf")).deriveFont(25f);
		    ge.registerFont(semibold25); 
		    
		    //create semi bold size 30 
		    semibold30 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansSemiBold.ttf")).deriveFont(30f);
		    ge.registerFont(semibold30); 
		    
		    //create semi bold size 50 
		    semibold50 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/DMSansSemiBold.ttf")).deriveFont(50f);
		    ge.registerFont(semibold50); 
		     
		    
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}


	}
}
