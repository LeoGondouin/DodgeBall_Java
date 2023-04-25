package fr.icom.info.m1.balleauprisonnier_mvn;


import java.awt.Rectangle;
import java.util.ArrayList;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import fr.icom.info.m1.balleauprisonnier_mvn.ProjectileFactory;
import fr.icom.info.m1.balleauprisonnier_mvn.slowProj;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import java.util.logging.*;

import javax.sound.midi.SysexMessage;
/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class Field extends Canvas {
	
	/** Joueurs */
	private static Field instance;
	Player [] team1 = new Player[3];
	Player [] team2 = new Player[3];
	slowProj slowProj;
	fastProj fastProj;
	Boolean paused=false;
	Boolean notstarted = true;
	final long cur = System.currentTimeMillis();
	/** Couleurs possibles */
	String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};
	/** Tableau traçant les evenements */
    ArrayList<String> input = new ArrayList<String>();
    

    final GraphicsContext gc;
    final int width;
    final int height;
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * 
     * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
	private Field(Scene scene, int w, int h) 
	{
		super(w, h); 
		width = w;
		height = h;
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);
		ProjectileFactory pf = new ProjectileFactory();
        gc = this.getGraphicsContext2D();
        /** On initialise le terrain de jeu */
        
        //Init team 1
    	team1[0] = new Player(gc, colorMap[0], w/2, h-50, "bottom");
    	((Player)team1[0]).display();
    	
    	team1[1] = new Bot(gc, colorMap[1], w/3, h-50, "bottom");
    	((Bot)team1[1]).display();
    	
    	team1[2] = new Bot(gc, colorMap[1], w/4, h-50, "bottom");
    	((Bot)team1[2]).display();
    	
    	
    	//Init team 2
    	team2[0] = new Player(gc, colorMap[1], w/2, 20, "top");
    	((Player)team2[0]).display();
    	
    	team2[1] = new Bot(gc, colorMap[1], w/3, 20, "top");
    	((Bot)team2[1]).display();
    	
    	team2[2] = new Bot(gc, colorMap[1], w/4, 20, "top");
    	((Bot)team2[2]).display();
    	
    	Random r = new Random();
    	int randN = r.nextInt(0,2);
    	System.out.print(r.nextInt(0,2));
    	this.slowProj=(slowProj)pf.createproj("slow",0,0,0);  
    	this.fastProj=(fastProj)pf.createproj("fast",0,0,0);    
    	if(randN==1) {
    		slowProj.x=team1[0].x;
    		slowProj.y=((Player)team1[0]).y; 
    		slowProj.side=((Player)team1[0]).side;
    		((Player)team1[0]).ballType = "slow";
    		slowProj.adjustBall();
    		
    		fastProj.x=((Player)team2[0]).x;
    		fastProj.y=((Player)team2[0]).y;
    		fastProj.side=((Player)team2[0]).side;
    		((Player)team2[0]).ballType = "fast";
    		fastProj.adjustBall();
    	
    	}
    	else {
    		fastProj.side=((Player)team1[0]).side;
    		fastProj.x=((Player)team1[0]).x;
    		fastProj.y=((Player)team1[0]).y;
    		((Player)team1[0]).ballType = "fast";
    		fastProj.adjustBall();
    		
    		slowProj.side=((Player)team2[0]).side;
    		slowProj.x=((Player)team2[0]).x;
    		slowProj.y=((Player)team2[0]).y; 
    		((Player)team2[0]).ballType = "slow";
    		slowProj.adjustBall();
    		/*
    		slowProj.x=joueurs[0].x;
    		slowProj.y=joueurs[0].y; 	
    		slowProj.img.setX(joueurs[0].x+30);
    		slowProj.img.setY(joueurs[0].y-10);*/
    		/*slowProj.x=joueurs[0].x;
    		slowProj.y=joueurs[0].y; */	
    		//slowProj.adjustBall(joueurs[0].side);
    	}
    	System.out.print(slowProj.side);
    	System.out.print(fastProj.side);
     	System.out.print(slowProj.side);
    	System.out.print(fastProj.side);
    	
	    /** 
	     * Event Listener du clavier 
	     * quand une touche est pressee on la rajoute a la liste d'input
	     *   
	     */
	    this.setOnKeyPressed(
	    		new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            // only add once... prevent duplicates
	    	            if ( !input.contains(code) ) {
	    	                input.add( code );
	    	            }
	    	        }
	    	    });

	    /** 
	     * Event Listener du clavier 
	     * quand une touche est relachee on l'enleve de la liste d'input
	     *   
	     */
	    this.setOnKeyReleased(
	    	    new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            input.remove( code );
	    	        }
	    	    });
	    
	    /** 
	     * 
	     * Boucle principale du jeu
	     * 
	     * handle() est appelee a chaque rafraichissement de frame
	     * soit environ 60 fois par seconde.
	     * 
	     */
	    new AnimationTimer() 
	    {
	        public void handle(long currentNanoTime)
	        {
	        	if(!paused&&!notstarted) {
	            // On nettoie le canvas a chaque frame
	            gc.setFill( Color.LIGHTGRAY);
	            gc.fillRect(0, 0, 800, 800);
	            
	            // Deplacement et affichage des joueurs
	        	for (int i = 0; i < team1.length; i++) 
	    	    {
	        		if (input.contains("LEFT"))
	        		{
	        			((Player)team1[0]).moveLeft();
	        			if(((Player)team1[i]).ballType.equals("fast")) {
		            		fastProj.x=((Player)team1[i]).x;
		            		fastProj.y=((Player)team1[i]).y; 
		            		fastProj.adjustBall();
	        			}
	        			if(((Player)team1[i]).ballType.equals("slow")) {
		            		slowProj.x=((Player)team1[i]).x;
		            		slowProj.y=((Player)team1[i]).y; 
		            		slowProj.adjustBall();
	        			}
	        			if(((Player)team1[0]).strat.equals("follow")) {
	        				((Bot)team1[1]).moveLeft();
	        				((Bot)team1[2]).moveLeft();
		        		} 
	    	    	}
	        		if (input.contains("RIGHT")) 
	        		{
	        			((Player)team1[0]).moveRight();
	        			if(((Player)team1[i]).ballType.equals("fast")) {
		            		fastProj.x=((Player)team1[i]).x;
		            		fastProj.y=((Player)team1[i]).y; 
		            		fastProj.adjustBall();
	        			}
	        			if(((Player)team1[i]).ballType.equals("slow")) {
		            		slowProj.x=((Player)team1[i]).x;
		            		slowProj.y=((Player)team1[i]).y; 
		            		slowProj.adjustBall();
	        			}
	        			if(((Player)team1[0]).strat.equals("follow")) {
	        				((Bot)team1[1]).moveRight();
	        				((Bot)team1[2]).moveRight();
		        		} 
	        		}
	        		if (input.contains("UP"))
	        		{
	        			((Player)team1[0]).turnRight();
	        			
	        			if(((Player)team1[i]).strat.equals("follow")) {
	        				((Bot)team1[1]).turnRight();
	        				((Bot)team1[2]).turnRight();
		        		} 
	        		} 
	        		if (input.contains("DOWN")) 
	        		{
	        			((Player)team1[0]).turnLeft();
	        			if(((Player)team1[i]).strat.equals("follow")) {
	        				((Bot)team1[1]).turnLeft();
	        				((Bot)team1[2]).turnLeft();
		        		}        			
	        		}
	        		/*if(fastProj.isLaunched()) {
		        			if((fastProj.x>=0 && fastProj.x<=w)&&(fastProj.y>=0 && fastProj.y<=h)){
		        				fastProj.throw_();
		        				joueurs[i].ballType="";
		        			}	        			
		        			else {
		        				respawnBall("fast");
		        				fastProj.launched=false;
		        			}
        				*/
	        		if(slowProj.isLaunched()) {
	        			if((slowProj.x>=0 && slowProj.x<=600)&&(slowProj.y>=0 && slowProj.y<=635)){
	        				slowProj.throw_();
/*	        				if(i==0) {F
	        					((Player)team1[0]).ballType="";
	        				}
	        				else {*/
	        				if(slowProj.side.equals("bottom")){
		        				if(checkForCollision(2,"slow")) {
		        					respawnBall("slow");
		        				}
	        				}
	        				else {
		        				if(checkForCollision(1,"slow")) {
		        					respawnBall("slow");
		        				}
	        				}
	        				/*if(slowProj.side.equals("bottom")) {
	        					((Player)team1[i]).ballType="";
	        				}*/
	        				//}
	        			}	        			
	        			else {
	        				respawnBall("slow");
	        				slowProj.launched=false;
	        			}
        			}
	        		if(fastProj.isLaunched()) {
	        			if((fastProj.x>=0 && fastProj.x<=623)&&(fastProj.y>=0 && fastProj.y<=623)){
	        				fastProj.throw_();
	        				if(fastProj.side.equals("bottom")){
		        				if(checkForCollision(2,"fast")) {
		        					respawnBall("fast");
		        				}
	        				}
	        				else {
		        				if(checkForCollision(1,"fast")) {
		        					respawnBall("fast");
		        				}
	        				}
	        			}	        			
	        			else {
	        				respawnBall("fast");
	        				fastProj.launched=false;
	        			}		        			
	        		}
//	        		System.out.print(fastProj.y);
	        		if (input.contains("NUMPAD0")){
	        			((Player)team1[i]).shoot();
	        			if(((Player)team1[i]).ballType.equals("slow")) {
	        				slowProj.launch();
		        			team1[i].ballType="";
	        			}
	        			else if(((Player)team1[i]).ballType.equals("fast")){
	        				fastProj.launch();
		        			team1[i].ballType="";
	        			}

	        			 // can be also 1 and not 0.2f. in 0.2f, the position X will move in 1 pixel per 5*TIMEOUT ms.
/*	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[2].shoot();
	        				bots[3].shoot();
		        		}   */
	        			//String randType = arrType[r.nextInt(0,2)];
	        			//System.out.print(randType);
	        			//slowProj ok = (slowProj)pf.createproj("slow",joueurs[i].x,joueurs[i].y+50,joueurs[i].angle);

					}
	        		if (input.contains("ADD")) 
	        		{
	        			((Player)team1[i]).FollowStrat();
	            		slowProj.x=((Player)team1[i]).x;
	            		slowProj.y=((Player)team1[i]).y; 
	            		slowProj.adjustBall();
					}
	        		if (input.contains("SUBTRACT")) 
	        		{
	        			((Player)team1[i]).BlendStrat();
	            		slowProj.x=((Player)team1[i]).x;
	            		slowProj.y=((Player)team1[i]).y; 
	            		slowProj.adjustBall();
					}
	        		if (((Player)team1[0]).strat.equals("blend")){
	        			
	        			((Bot)team1[1]).randomMoves();
	        			((Bot)team1[2]).randomMoves();
	        			if(((Player)team1[i]).ballType.equals("fast")) {
		            		fastProj.x=((Player)team1[i]).x;
		            		fastProj.y=((Player)team1[i]).y; 
		            		fastProj.adjustBall();
	        			}
	        			if(((Player)team1[i]).ballType.equals("slow")) {
		            		slowProj.x=((Player)team1[i]).x;
		            		slowProj.y=((Player)team1[i]).y; 
		            		slowProj.adjustBall();
	        			}
					}
        			if (((Player)team1[i]).isAlive){
	        			((Player)team1[i]).display();
        			}
	    	    }
	        	for(int i=0; i<team2.length;i++) {
	        		if (input.contains("Q"))
	        		{
	        			((Player)team2[0]).moveLeft();
	        			if(((Player)team2[i]).ballType.equals("fast")) {
		            		fastProj.x=((Player)team2[i]).x;
		            		fastProj.y=((Player)team2[i]).y; 
		            		fastProj.adjustBall();
	        			}
	        			if(((Player)team2[i]).ballType.equals("slow")) {
		            		fastProj.x=((Player)team2[i]).x;
		            		fastProj.y=((Player)team2[i]).y; 
		            		fastProj.adjustBall();
	        			}
	        			if(((Player)team2[0]).strat.equals("follow")) {
	        				((Bot)team2[1]).moveLeft();
	        				((Bot)team2[2]).moveLeft();
		        		} 
	        		} 
	        		if (input.contains("D")) 
	        		{
	        			((Player)team2[0]).moveRight();
	        			System.out.print(team2[0].ballType);
	        			if(((Player)team2[i]).ballType.equals("fast")) {
		            		fastProj.x=((Player)team2[i]).x;
		            		fastProj.y=((Player)team2[i]).y; 
		            		fastProj.adjustBall();
	        			}
	        			if(((Player)team2[i]).ballType.equals("slow")) {
		            		slowProj.x=((Player)team2[i]).x;
		            		slowProj.y=((Player)team2[i]).y; 
		            		slowProj.adjustBall();
	        			}
	        			if(((Player)team2[0]).strat.equals("follow")) {
	        				((Bot)team2[1]).moveRight();
	        				((Bot)team2[2]).moveRight();
		        		}     			
	        		}
	        		if (input.contains("Z"))
	        		{
	        			((Player)team2[0]).turnRight();
	        			
	        			if(((Player)team2[0]).strat.equals("follow")) {
	        				((Bot)team2[1]).turnRight();
	        				((Bot)team2[2]).turnRight();
		        		} 
	        		} 
	        		if (input.contains("S")) 
	        		{
	        			((Player)team2[0]).turnLeft();
	        			if(((Player)team2[0]).strat.equals("follow")) {
	        				((Bot)team2[1]).turnLeft();
	        				((Bot)team2[2]).turnLeft();
		        		}               			
	        		}
	        		/*if(slowProj.isLaunched()) {
        				if(checkForCollision(1,"slow")) {
        					respawnBall("slow");
        				}
	        			if((slowProj.x>=0 && slowProj.x<=w)&&(slowProj.y>=0 && slowProj.y<=600)){
	        				slowProj.throw_();*/
/*	        				if(i==0) {F
	        					((Player)team1[0]).ballType="";
	        				}
	        				else {*/
	        				/*if(checkForCollision(1,"slow")) {
	        					respawnBall("fast");
	        				}
	        				if(slowProj.side=="top") {
	        					((Player)team2[0]).ballType="";
	        				}
	        				//}
	        			}	        			
	        			else {
	        				respawnBall("slow");
	        				slowProj.launched=false;
	        			}
        			}
	        		else if(fastProj.isLaunched()) {
	        			if((fastProj.x>=0 && fastProj.x<=w)&&(fastProj.y>=0 && fastProj.y<=600)){
	        				fastProj.throw_();
	        				if(checkForCollision(1,"fast")) {
	        					respawnBall("fast");
	        				}
        					((Player)team2[0]).ballType="";
	        			}	        			
	        			else {
	        				respawnBall("fast");
	        				fastProj.launched=false;
	        			}		        			
	        		}*/
	        		if (input.contains("ALT")){
	        			((Player)team2[i]).shoot();
	        			//joueurs[i].ballType="";
	        			if(((Player)team2[i]).ballType.equals("slow")) {
	        				slowProj.launch();
		        			team2[i].ballType="";
	        			}
	        			else if(((Player)team2[i]).ballType.equals("fast")){
	        				fastProj.launch();
		        			team2[i].ballType="";
	        			}
	        			//System.out.print(arrType[r.nextInt(0,2)]);
	        			if(((Player)team2[0]).strat.equals("follow")) {
	        				((Bot)team2[1]).shoot();
	        				((Bot)team2[2]).shoot();
		        		}   
					}
	        		if (input.contains("E")){
	        			((Player)team2[0]).FollowStrat();
					}
	        		if (input.contains("A")){
	        			((Player)team2[0]).BlendStrat();
					}
	        		if (((Player)team2[0]).strat.equals("blend")){	
    					((Bot)team2[1]).randomMoves();
    					((Bot)team2[2]).randomMoves();
	        			if(((Player)team2[i]).ballType!="") {
		            		fastProj.x=((Player)team2[i]).x;
		            		fastProj.y=((Player)team2[i]).y; 
		            		fastProj.adjustBall();
	        			}
	        					
    				}
        			if (((Player)team2[i]).isAlive){
	        			((Player)team2[i]).display();
        			}
	        		
	    	    }
	    	}
	        }}.start(); // On lance la boucle de rafraichissement
	     
	}
    public static Field FieldSingleTonManager(Scene scene, int w, int h) {
        if (instance == null) {
            instance = new Field(scene, w, h);
        }
        else {
        	Logger logger = Logger.getLogger(Field.class.getSimpleName());
        	logger.warning("Votre objet n\'a pas ete cree car une instance existe deja !");
        }
        return instance;
    	
    }
    public ArrayList<Player> getNotDeadandNoBall(Player[] team){
    	ArrayList<Player> list = new ArrayList<>();
    	for(Player p : team) {
    		if(p.isAlive&&p.ballType=="") {
    			list.add(p);
    		}
    	}
    	return list;   			
    }
    public void respawnBall(String type){
		Random r = new Random();
		ArrayList<Player> arrNotDeadNoBall; 
    	if(type=="slow") {
    		if(slowProj.side.equals("bottom")){
        		/*slowProj.x=((Player)team2[randN]).x;
        		slowProj.y=((Player)team2[randN]).y;
        		slowProj.side="top";
        		slowProj.adjustBall();
        		((Player)team2[randN]).ballType = "slow";*/
        		arrNotDeadNoBall = getNotDeadandNoBall(team2);
        		int randN = r.nextInt(0,(arrNotDeadNoBall.size()==0) ? 1 : arrNotDeadNoBall.size());
    			for (int i=0;i<arrNotDeadNoBall.size();i++) {
    				slowProj.x=arrNotDeadNoBall.get(randN).x;
            		slowProj.y=arrNotDeadNoBall.get(randN).y;
            		slowProj.side="top";
            		slowProj.adjustBall();
            		arrNotDeadNoBall.get(randN).ballType = "slow";;
    			}
    		}
    		else{			
        		arrNotDeadNoBall = getNotDeadandNoBall(team1);
        		int randN = r.nextInt(0,(arrNotDeadNoBall.size()==0) ? 1 : arrNotDeadNoBall.size());
    			for (int i=0;i<arrNotDeadNoBall.size();i++) {
    				slowProj.x=arrNotDeadNoBall.get(randN).x;
            		slowProj.y=arrNotDeadNoBall.get(randN).y;
            		slowProj.side="bottom";
            		slowProj.adjustBall();
            		arrNotDeadNoBall.get(randN).ballType = "slow";
    			}
    		}
    	}
    	else {
    		if(fastProj.side.equals("bottom")){  			
        		arrNotDeadNoBall = getNotDeadandNoBall(team2);
        		int randN = r.nextInt(0,(arrNotDeadNoBall.size()==0) ? 1 : arrNotDeadNoBall.size());
    			for (int i=0;i<arrNotDeadNoBall.size();i++) {
    				fastProj.x=arrNotDeadNoBall.get(randN).x;
    				fastProj.y=arrNotDeadNoBall.get(randN).y;
    				fastProj.side="top";
    				fastProj.adjustBall();
            		arrNotDeadNoBall.get(randN).ballType = "fast";
    			}
    		}
    		else{
        		arrNotDeadNoBall = getNotDeadandNoBall(team1);
        		int randN = r.nextInt(0,(arrNotDeadNoBall.size()==0) ? 1 : arrNotDeadNoBall.size());
    			for (int i=0;i<arrNotDeadNoBall.size();i++) {
    				fastProj.x=arrNotDeadNoBall.get(randN).x;
    				fastProj.y=arrNotDeadNoBall.get(randN).y;
    				fastProj.side="bottom";
    				fastProj.adjustBall();
            		arrNotDeadNoBall.get(randN).ballType = "fast";
    			}
    		}
    	}    	
    }
    public boolean checkForCollision(int team,String type) {
    	if(team==1) {
    		for(int i=0;i<team1.length;i++) {
    			if(type=="slow") {
	    			if(slowProj.x>getBoundingBox((Player)team1[i])[0] && slowProj.x<getBoundingBox((Player)team1[i])[1]&&slowProj.y>getBoundingBox((Player)team1[i])[2] && slowProj.y<getBoundingBox((Player)team1[i])[3]) {
	    				((Player)team1[i]).isAlive=false;
	    				((Player)team1[i]).sprite.setVisible(false);
	    				if(team1[i].ballType!="") {
	    					respawnBall("fast");
	    				}
	    				return true;
	    			}
    			}
    			else {
	    			if(fastProj.x>getBoundingBox((Player)team1[i])[0] && fastProj.x<getBoundingBox((Player)team1[i])[1]&&fastProj.y>getBoundingBox((Player)team1[i])[2] && fastProj.y<getBoundingBox((Player)team1[i])[3]) {
	    				((Player)team1[i]).isAlive=false;   	
	    				((Player)team1[i]).sprite.setVisible(false);
	    				if(team1[i].ballType!="") {
	    					respawnBall("slow");
	    				}
	    				return true;
	    			}
    			}
    		}
    	}
    	else {
    		for(int i=0;i<team2.length;i++) {
    			if(type=="slow") {
	    			if(slowProj.x>getBoundingBox((Player)team2[i])[0] && slowProj.x<getBoundingBox((Player)team2[i])[1]&&slowProj.y>getBoundingBox((Player)team2[i])[2] && slowProj.y<getBoundingBox((Player)team2[i])[3]) {
	    				team2[i].isAlive=false;
	    				team2[i].sprite.setVisible(false);
	    				if(team2[i].ballType!="") {
	    					respawnBall("fast");
	    				}
	    				return true;
	    			}
    			}
    			else {
	    			if(fastProj.x>getBoundingBox((Player)team2[i])[0] && fastProj.x<getBoundingBox((Player)team2[i])[1]&&fastProj.y>getBoundingBox((Player)team2[i])[2] && fastProj.y<getBoundingBox((Player)team2[i])[3]) {
	    				((Player)team2[i]).isAlive=false;  
	    				((Player)team2[i]).sprite.setVisible(false);
	    				if(team2[i].ballType!="") {
	    					respawnBall("slow");
	    				}
	    				return true;
	    			}
    			}
    		}   		
    	}
    	return false;
    }
    public double[] getBoundingBox(Player obj) {
    	double[] coords = new double[] {obj.x,(double)obj.x+50,obj.y,(double)obj.y+50};
    	return coords;
    }
	public Object[] getTeam(int num) {
		Object[] obj=null;
		if(num==1) {
			obj=team1;
		}
		else {
			obj=team2;
		}
		return obj;
	}
	/*public Player[] getJoueurs() {
		return joueurs;
	}
	public Bot[] getBots() {
		return bots;
	}*/
	public slowProj getslowProj() {
		return slowProj;
	}
	public fastProj getfastProj() {
		return fastProj;
	}
}

