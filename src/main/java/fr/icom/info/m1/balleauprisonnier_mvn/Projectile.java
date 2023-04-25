package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * 
 * Classe gerant un joueur
 *
 */
public class Projectile 
{
	ImageView img;
	double x;
	double y;
	double angle;
	double side;
	public Projectile(ImageView tilesheetImage, double xpos, double ypos,double direction){
		this.img = tilesheetImage; 
		this.img.setX(xpos);
		this.img.setY(ypos);
		this.angle=direction;
		
	}
}
