import objectdraw.*;
import java.awt.*;
/**
Interface for the segments.  We need an empty segment and a non empty segment when using recursion.  The non empty segemnt is 
important so that the a null pointer won't occur when we reach through all segments as the ShadowGuide class 
 */
public interface Segment
{
    public void removeFromCanvas(); 
    public Segment getLast(); 
    public Location returnLocation(); 
}
