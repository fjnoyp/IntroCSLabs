
import objectdraw.*;
import java.awt.*;

/**
This is the first oval in the shadow "chain" of ovals.  This oval moves, thereby causing all the other circles down the chain 
to move with it.  
 */
public class ShadowGuide extends ActiveObject 
{

    private static final int OVAL_SIZE = 50; 
//We need the filledOval (which is the moving object) and the other segments.  We use these segments to find the new 
//location for where the filledOval should move to 
    private FilledOval firstOval; 
    private Segment currentSegment; 
    private Segment moveSegment; 
    
    //activates and deactivates the shadow guide run method 
    private boolean running = true; 

    /**
     * Constructor for objects of class ShadowGuide
     */
    public ShadowGuide(Segment moveSegmentRef, Segment currentSegmentRef, DrawingCanvas canvas)
    {
        //create the oval, the height and width of this object will determine the height and width of all the other ovals 
        //down the line 
        //100,100 is the location of the firstOval when it is first created, but it is not significant since the oval moves
        //along the spiral's segments after a spiral has been created 
        firstOval = new FilledOval(100,100,OVAL_SIZE,OVAL_SIZE,canvas); 
        firstOval.hide(); 
        
        //the segments are necessary so that we have a reference for the spiral's line segments' locations 
        currentSegment = currentSegmentRef; 
        moveSegment = moveSegmentRef; 
        this.start(); 
        
    }
    //Moves this oval along the spiral 
    public void run()
    {
        while(running){
        if(currentSegment!=null){
            
            ///Find the distance we need to travel to get to the next segment of the spiral 
            int count = 0;
            double dx = moveSegment.returnLocation().getX()-firstOval.getX(); 
            double dy = moveSegment.returnLocation().getY()-firstOval.getY(); 
            
          //move the oval to the target location in increments 
           while(count<10){
            
            
           firstOval.move(dx/10,dy/10); 
            
           count++;
            
           pause(10); 
          }
            
          //in case the oval isn't exactly on the spot of the next line segment, force it to do so
            firstOval.moveTo(moveSegment.returnLocation()); 
            
          //get the next line segment in the spiral which we will use as the new target location for where to move to 
            moveSegment = moveSegment.getLast(); 
            
            //If this segment doesn't actually exist, go back to the last segment of the spiral 
            //this is the reason we need two segments in this class's parameter
            //while they are both the same at first, one is used as a reference back to the last segment in the spiral 
        
            
            if(moveSegment.getLast()==null)
            moveSegment = currentSegment; 

        }
        else break; 
        }
    }
    
    //moves the oval 
    public void moveTo(Location point)
    {
        firstOval.moveTo(point); 
    }
    
    //returns the oval of this shadowGuide 
    public FilledOval getOval()
    {
        return firstOval; 
    }
    
    //remove the shadow guide whenever the canvas is cleared and end the run method
    public void removeFromCanvas()
    {
        running = false; 
        firstOval.removeFromCanvas(); 
    }
    
    

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */

}
