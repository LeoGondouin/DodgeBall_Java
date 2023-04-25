package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.Random;

import fr.icom.info.m1.balleauprisonnier_mvn.Projectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class fastProj extends Projectile
{
	String side;
	Boolean launched=false;
	long cur=0;
	String type="fast";
	public fastProj(double xpos, double ypos,double direction){
		super(new ImageView("assets/ball_small.png"), xpos, ypos, direction);
	}
	public void adjustBall() {
		if(this.side.equals("bottom")) {
			this.x+=30;
			this.y+=32;
			this.img.setX(x);
			this.img.setY(y);
		}
		else {
			this.x+=7;
			this.y+=33;
			this.img.setX(x);
			this.img.setY(y);			
		}
	}
	public void throw_(/*double angle*/) {
		if(System.currentTimeMillis()-cur<1) {
			return;
		}
		cur = System.currentTimeMillis();
		if(this.side.equals("bottom")) {
			this.y-=6;
			this.img.setY(this.y);
			/*this.x += Math.cos(this.angle) * 5;
			this.y += Math.sin(this.angle) * 5;
			this.img.setX(this.x);
			this.img.setY(this.y);*/
		}
		else {
			this.y+=6;
			this.img.setY(this.y);
			/*this.x -= Math.cos(this.angle) * 5;
			this.y -= Math.sin(this.angle) * 5;	
			this.img.setX(this.x);
			this.img.setY(this.y);*/
		}
	}	
	public boolean isLaunched() {
		return launched;
	}
	public void launch() {
		launched=true;
	}
	
}

