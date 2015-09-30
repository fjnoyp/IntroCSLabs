import java.awt.*;
import objectdraw.*;

public class Centipede extends ActiveObject {
    
    // offset between segments when they are created
    static private final int SEGMENT_OFFSET_X = 12;
    static private final int SEGMENT_INIT_Y = 20; 
    
    private static final int CENTIPEDE_HIT_SCORE = 5; 
    
    // pause time between moving all of the segments
    static private final int CENTIPEDE_PAUSE = 25;
    
    //Array segments for centipede 
    private Segment[] segments; 
    
    //field reference 
    private Field field; 
    
    //zapper reference 
    private Zapper zapper; 
    
    //scorekeeper reference 
    private ScoreKeeper score; 
    
    
    public Centipede(Image segmentImage, int numSegments, Zapper zapper, Field field, ScoreKeeper score, DrawingCanvas canvas) {
        
        segments = new Segment[numSegments]; 
        
        //pass references 
        this.field = field; 
        this.zapper = zapper; 
        this.score = score;
        
        //create all the extra segments 
        for(int i = 0; i < numSegments; i++){
            //location for new segments 
            Location segLocation = new Location(SEGMENT_OFFSET_X*i,SEGMENT_INIT_Y); 
            
            //create new segment 
            segments[i] = new Segment(segmentImage, segLocation, zapper, field, canvas);
            
        }
        
        this.start();
    }
    
    public void run() {
        while(!score.gameOver){
            for(int i = 0; i < segments.length; i++){
                segments[i].step();  
            }
            
            pause(CENTIPEDE_PAUSE); 
        }        
    }
    
    public boolean tryToHitSegment(Location point){
        for(int i = 0; i < segments.length; i++){
            if(segments[i].contains(point) && segments[i].isAlive()){
                segments[i].kill(); 
                field.showShroom(point.getX(),point.getY()); 
                score.increment(CENTIPEDE_HIT_SCORE); 
                return true; 
            }
            
        }
        
        return false; 
        
    }
    
}
