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
public class ProjectileFactory
{
	Image img;
	double x;
	double y;
	double angle;
	public ProjectileFactory(){
	}
	public Projectile createproj(String type,double xpos, double ypos,double angle) {
		Projectile proj = null;
		if(type.equals("fast")) {
			proj = new fastProj(xpos,ypos,angle);
		}
		else if(type.equals("slow")) {
			proj = new slowProj(xpos,ypos,angle);
		}
		return proj;
	}
}
