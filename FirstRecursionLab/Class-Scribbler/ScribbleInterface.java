import objectdraw.*;
import java.awt.Color;

/*
 * The methods supported by all scribble classes.
 */
public interface ScribbleInterface {

  // returns whether point is contained in scribble
  public boolean contains(Location point);

  // move scribble by dx in x-direction and dy in y-direction
  public void move(double xOffset, double yOffset);
  
  public void setColor(Color color); 
  
  public void removeFromCanvas(); 
}
