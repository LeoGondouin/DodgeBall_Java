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
public class Player 
{
	  double x;       // position horizontale du joueur
	  final double y; 	  // position verticale du joueur
	  double angle = 90; // rotation du joueur, devrait toujour être en 0 et 180
	  double step;    // pas d'un joueur
	  String playerColor;
	  String ballType="";
	  // On une image globale du joueur 
	  Image directionArrow;
	  Sprite sprite;
	  ImageView PlayerDirectionArrow;
	  String strat="blend";
	  GraphicsContext graphicsContext;
	  String side;
	  Boolean isRunning=false;
	  Boolean isAlive = true;
	  
	  /**
	   * Constructeur du Joueur
	   * 
	   * @param gc ContextGraphic dans lequel on va afficher le joueur
	   * @param color couleur du joueur
	   * @param yInit position verticale
	   */
	  Player(GraphicsContext gc, String color, int xInit, int yInit, String side)
	  {
		// Tous les joueurs commencent au centre du canvas, 
	    x = xInit;               
	    y = yInit;
	    graphicsContext = gc;
	    playerColor=color;
	    this.side= side;
	    angle = 0;

	    // On charge la representation du joueur
        Image tilesheetImage;
        if(side=="top"){
        	tilesheetImage = new Image("assets/PlayerBlue.png");
        	directionArrow = new Image("assets/PlayerArrowDown.png");
		}
		else{
			tilesheetImage = new Image("assets/PlayerRed.png");
			directionArrow = new Image("assets/PlayerArrowUp.png");
		}
        
        PlayerDirectionArrow = new ImageView();
        PlayerDirectionArrow.setImage(directionArrow);
        PlayerDirectionArrow.setFitWidth(10);
        PlayerDirectionArrow.setPreserveRatio(true);
        PlayerDirectionArrow.setSmooth(true);
        PlayerDirectionArrow.setCache(true);
        
        sprite = new Sprite(tilesheetImage, 0,0, Duration.seconds(.2), side);
        sprite.setX(x);
        sprite.setY(y);
        //directionArrow = sprite.getClip().;

	    // Tous les joueurs ont une vitesse aleatoire entre 0.0 et 1.0
        // Random randomGenerator = new Random();
        // step = randomGenerator.nextFloat();

        // Pour commencer les joueurs ont une vitesse / un pas fixe
        Random r = new Random();
        step = r.nextDouble(1,2);
	    System.out.print(step+"\n");
	  }

	  /**
	   *  Affichage du joueur
	   */
	  void display()
	  {
		  graphicsContext.save(); // saves the current state on stack, including the current transform
	      rotate(graphicsContext, angle, x + directionArrow.getWidth() / 2, y + directionArrow.getHeight() / 2);
		  graphicsContext.drawImage(directionArrow, x, y);
		  graphicsContext.restore(); // back to original state (before rotation)
	  }

	  private void rotate(GraphicsContext gc, double angle, double px, double py) {
		  Rotate r = new Rotate(angle, px, py);
		  gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	  }
	  
	  /**
	   *  Deplacement du joueur vers la gauche, on cantonne le joueur sur le plateau de jeu
	   */
	 
	  void moveLeft() 
	  {	    
	    if (x > 1 && x < 550) 
	    {
	    	spriteAnimate();
		    x -= step;
	    }
		else if (x<1){
			x=2;
		}
	  }

	  /**
	   *  Deplacement du joueur vers la droite
	   */
	  void moveRight() 
	  {
	    if (x > 1 && x <550) 
	    {
    		spriteAnimate();
		    x += step;
	    }
		else if(x>550){
			x = 549;
		}
	  }

	  
	  /**
	   *  Rotation du joueur vers la gauche
	   */
	  void turnLeft() 
	  {
	    if (angle > 0 && angle < 180) 
	    {
	    	angle += 1;
	    }
	    else {
	    	angle += 1;
	    }

	  }

	  
	  /**
	   *  Rotation du joueur vers la droite
	   */
	  void turnRight() 
	  {
	    if (angle > 0 && angle < 180) 
	    {
	    	angle -=1;
	    }
	    else {
	    	angle -= 1;
	    }
	  }


	  void shoot(){
	  	sprite.playShoot();
	  }
	  
	  void FollowStrat(){
		  this.strat="follow";
		  sprite.playFollowStrat();
	  }
	  
	  void BlendStrat(){
		  this.strat="blend";
		  sprite.playBlendStrat();
	  }
	  
	  /**
	   *  Deplacement en mode boost
	   */
	  void boost() 
	  {
		  x += step*2;
		  spriteAnimate();
	  }

	  void spriteAnimate(){
	  	  //System.out.println("Animating sprite");
		  if(!sprite.isRunning) {sprite.playContinuously();}
		  sprite.setX(x);
		  sprite.setY(y);
	  }
}
