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
public class Bot extends Player
{
	public Bot(GraphicsContext gc, String color, int xInit, int yInit, String side){
		  super(gc, color, xInit, yInit, side);
	}
	public void randomMoves(){
		//long lastTurn = System.currentTimeMillis();
		//while(System.currentTimeMillis() - lastTurn >= 3000) {
		Random rand = new Random();
		if(rand.nextInt(5) == 0){
		    this.moveRight();
		}
		if(rand.nextInt(5) == 1){
			this.moveLeft();
		}
		
		if(rand.nextInt(5) == 2){
			this.turnLeft();
		}
		if(rand.nextInt(5) == 3){
			this.turnRight();
		}
		if(rand.nextInt(5) == 4){
			this.shoot();
		}
			//lastTurn = System.currentTimeMillis();
	}
}
