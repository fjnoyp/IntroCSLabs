import objectdraw.*;
import java.awt.*;

/**
This is a line and a reference to the last spiralSegment

This class returns the position of the line (this line) and uses the reference to the past spiral Segment to eventually
supply us with line positions of previous lines 
 */
public class SpiralSegment implements Segment 
{
    private Segment previous; 
    private Line currentLine; 
    
    /**
     * Constructor for objects of class SpiralSegment
     */
    public SpiralSegment(Segment last, Line thisLine, DrawingCanvas canvas)
    {
        previous = last; 
        currentLine = thisLine; 
        
    }
    //returns the locatino of this line 
    public Location returnLocation()
    {
        Location currentLocation = new Location(currentLine.getEnd().getX(),currentLine.getEnd().getY()); 
        return currentLocation; 
    }
    //returns the previous segment 
    public Segment getLast()
    {
        return previous; 
    }

    //not used now, removes line 
    public void removeFromCanvas()
    {
        currentLine.removeFromCanvas(); 
        previous.removeFromCanvas(); 
        
    }
}
