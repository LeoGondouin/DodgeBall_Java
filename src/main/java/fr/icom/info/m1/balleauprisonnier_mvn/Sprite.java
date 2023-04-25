package fr.icom.info.m1.balleauprisonnier_mvn;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.util.Duration;

class Sprite extends ImageView {
    private final Rectangle2D[] walkClips;
    private final Rectangle2D[] shootClips;
    private final Rectangle2D[] stratClips;
    private final Rectangle2D[] stratClipsBlend;
    private int numCells;
    private int numCellsWalk;
    private int numCellsShoot;
    private int numCellsStrat;

    private final Timeline stratTimeLine;
    private final Timeline stratTimeLineBlend;
    private final Timeline walkTimeline;
    private final IntegerProperty frameCounter = new SimpleIntegerProperty(0);
    private final Timeline shootTimeline;
    private Timeline timeline;
    public boolean isRunning;
    public boolean isFollowing;
    
    public Sprite(Image animationImage, int numCells, int numRows, Duration frameTime, String side) {
        this.numCells = numCells;

        double cellWidth  = 64;//animationImage.getWidth() / numCells; //64x64
        double cellHeight = 64;//animationImage.getHeight() / numRows;


        numCellsWalk = 9;

        int lineNumber = 8;
        if(side == "top"){
            lineNumber += 2;
        }

        walkClips = new Rectangle2D[numCellsWalk];
        for (int i = 0; i < numCellsWalk; i++) {
            walkClips[i] = new Rectangle2D(
                    i * cellWidth, cellHeight*lineNumber,
                    cellWidth, cellHeight
            );
        }

        setImage(animationImage);
        setViewport(walkClips[0]);

        walkTimeline = new Timeline(
                new KeyFrame(frameTime, event -> {
                    frameCounter.set((frameCounter.get() + 1) % numCellsWalk);
                    setViewport(walkClips[frameCounter.get()]);
                })
        );

        numCellsShoot = 13;
        lineNumber += 8;

        shootClips = new Rectangle2D[numCellsShoot];
        for (int i = 0; i < numCellsShoot; i++){
            shootClips[i] = new Rectangle2D(
                    i * cellWidth, cellHeight*lineNumber,
                    cellWidth, cellHeight
            );
        }

        shootTimeline = new Timeline(
                new KeyFrame(frameTime, event -> {
                    frameCounter.set((frameCounter.get() + 1) % numCellsShoot);
                    setViewport(shootClips[frameCounter.get()]);
                }));
        
        lineNumber = 0;
        if(side=="top") {
        	lineNumber=2;
        }
        numCellsStrat = 6;
        stratClips = new Rectangle2D[numCellsStrat];
        for (int i = 0; i<numCellsStrat; i++){
        	stratClips[i] = new Rectangle2D(
                    i * cellWidth, cellHeight*lineNumber,
                    cellWidth, cellHeight
            );
        }
        
        stratTimeLine = new Timeline(
                new KeyFrame(frameTime, event -> {
                    frameCounter.set((frameCounter.get() + 1) % numCellsStrat);
                    setViewport(stratClips[frameCounter.get()]);
                }));

        lineNumber = 1;
        if(side=="top") {
        	lineNumber=3;
        }  
        stratClipsBlend = new Rectangle2D[numCellsStrat];    
        for (int i =0; i<numCellsStrat ; i++){
        	stratClipsBlend[i] = new Rectangle2D(
                    i * cellWidth, cellHeight*lineNumber,
                    cellWidth, cellHeight
            );
        }
        stratTimeLineBlend = new Timeline(
                new KeyFrame(frameTime, event -> {
                    frameCounter.set((frameCounter.get() + 1) % numCellsStrat);
                    setViewport(stratClipsBlend[frameCounter.get()]);
                }));

        timeline = walkTimeline;
        isRunning = false;
        isFollowing = false;
    }

    public void playContinuously() {
        isRunning = true;
        frameCounter.set(0);
        timeline = walkTimeline;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.stop();
        timeline.playFromStart();
    }

    public void playShoot(){
        frameCounter.set(0);
        timeline.stop();
        timeline = shootTimeline;
        timeline.setCycleCount(numCellsShoot);
        timeline.setOnFinished(e -> playContinuously());
        timeline.playFromStart();
    }
    
    public void playFollowStrat(){
        frameCounter.set(0);
        timeline.stop();
        timeline = stratTimeLine;
        timeline.setCycleCount(numCellsStrat);
        timeline.setOnFinished(e -> playContinuously());
        timeline.playFromStart();
    }
    public void playBlendStrat(){
        frameCounter.set(0);
        timeline.stop();
	    timeline = stratTimeLineBlend;
	    timeline.setCycleCount(numCellsStrat);    
	    timeline.setOnFinished(e -> playContinuously());
	    timeline.playFromStart();
    }

    public void stop() {
    	isRunning=false;
        frameCounter.set(0);
        setViewport(walkClips[frameCounter.get()]);
        walkTimeline.stop();
    }
}
