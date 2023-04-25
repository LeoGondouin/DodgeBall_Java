package fr.icom.info.m1.balleauprisonnier_mvn;


import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.Panel;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Classe principale de l'application 
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application 
{
	
	/**
	 * En javafx start() lance l'application
	 *
	 * On cree le SceneGraph de l'application ici
	 * @see http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
	 * 
	 */
	@Override
	public void start(Stage stage) throws Exception 
	{
		// Nom de la fenetre
        stage.setTitle("Balle au prisonnier");

        Group root = new Group();
        Scene scene = new Scene( root );
        Button btnPause = new Button("Pause");
        Button btnStart = new Button("Start");
        Rectangle menuPane = new Rectangle(100,640,Color.BLACK);
        menuPane.setLayoutX(590);
        //menuPane.setStyle("-fx-background-color: black;");
        btnPause.setLayoutX(600);
        btnPause.prefWidth(24);
        btnStart.relocate(600,24);
        btnStart.prefWidth(24);
        
        // On cree le terrain de jeu et on l'ajoute a la racine de la scene
        Field gameField = Field.FieldSingleTonManager(scene, 600, 640);
        btnPause.setOnAction(event->{
        	if(!gameField.paused) {
        		gameField.paused=true;
        		btnPause.setText("Resume");
        	}
        	else {
        		gameField.paused=false;
        		btnPause.setText("Pause");
        	}
        });
        btnStart.setOnAction(event->{
        	gameField.notstarted=false;
        	btnStart.setDisable(true);
        });
        //Field gameField2 = Field.FieldSingleTonManager(scene, 500, 500 );
        //Field gameField3 = Field.FieldSingleTonManager(scene, 500, 500 );
        root.getChildren().add( gameField );
        root.getChildren().add(menuPane);
        root.getChildren().add( btnPause );
        root.getChildren().add(btnStart);
        
		root.getChildren().add(((Player)gameField.getTeam(1)[0]).sprite);
		root.getChildren().add(((Bot)gameField.getTeam(1)[1]).sprite);
		root.getChildren().add(((Bot)gameField.getTeam(1)[2]).sprite);
		
		root.getChildren().add(((Player)gameField.getTeam(2)[0]).sprite);
		root.getChildren().add(((Bot)gameField.getTeam(2)[1]).sprite);
		root.getChildren().add(((Bot)gameField.getTeam(2)[2]).sprite);
		
		root.getChildren().add(gameField.getslowProj().img);
		root.getChildren().add(gameField.getfastProj().img);
		//root.getChildren().add(gameField.getJoueurs()[2].sprite);
		//root.getChildren().add(gameField.getslowProj().sprite);	
        // On ajoute la scene a la fenetre et on affiche
        stage.setScene( scene );
        stage.show();
	}
	
    public static void main(String[] args) 
    {
        //System.out.println( "Hello World!" );
    	Application.launch(args);
    }
}
