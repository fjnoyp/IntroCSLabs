
import objectdraw.*;
import java.awt.*;

public class Segment {

    // width of segment image
    static private final int SEGMENT_WIDTH = 16;  

    // number of pixels a segment should travel in the X direction 
    static private final int SEGMENT_STEP_X = SEGMENT_WIDTH / 4;

    // number of pixels a segment should travel in the Y direction
    static private final int SEGMENT_STEP_Y = 16;

    //segment graphic 
    private VisibleImage body; 

    //boolean keep track of direction 
    private boolean directionRight = true; 

    //mushroom field reference 
    private Field field;

    //zapper reference 
    private Zapper zapper; 

    public Segment(Image segmentPic, Location point, Zapper zapper, Field field, DrawingCanvas canvas){

        body = new VisibleImage(segmentPic, point.getX(), point.getY(), canvas); 
        this.field = field; 
        this.zapper = zapper; 

    }

    public void step(){
        
        if(zapper.overlaps(body)){
            zapper.score.endGame(); 
        }

        if(directionRight&&isAlive()){

            //hit detection location of segment moving to right 
            Location bodyLocation = new Location (body.getX()+body.getWidth(),body.getY()); 

            if(field.overlapsShroom(bodyLocation) || bodyLocation.getX()>field.CANVAS_WIDTH){
                directionRight = false; 
                body.move(0,SEGMENT_STEP_Y); 

            }

            else{
                body.move(SEGMENT_STEP_X,0); 
            }

        }
        
        else{
            
            if(field.overlapsShroom(body.getLocation()) || getX()<0){
                directionRight = true; 
                body.move(0,SEGMENT_STEP_Y); 
 
            }
            
            else{
                body.move(-SEGMENT_STEP_X,0); 
            }
            
            
        }
    }
    
    public boolean contains(Location point){
        return body.contains(point); 
    }
    
    public void kill(){
        body.hide(); 
    }
    
    public boolean isAlive(){
        return !body.isHidden(); 
    }
    
    public double getX(){
        return body.getX(); 
    }
    
    public double getY(){
        return body.getY(); 
    }

}
