import java.awt.event.*;
import java.awt.*;
import objectdraw.*;
/**
This is a series of ovals.  This oval creates another oval that is slightly more white and has reference to this circle. 
Whenever this oval moves, the reference of itself that it sent the oval that it created is updated so that its created
oval moves to this oval's new location.  Delay makes this occurance more visible to the user 
 */
public class Shadow extends ActiveObject 
{
private static final int OVAL_SIZE = 50; 
//this and rest , simple recursion structure 
    private FilledOval thisCircle; 
    private FilledOval previousCircle; 
    private int thisColor; 
    
    private DrawingCanvas canvasRef; 
    
    //we use trigonometry to add a bulge effect to this oval, we need theta for the trig
    private double theta; 
    
    //what this object creates 
    private Shadow childI;
    
    //boolean to stop run statement 
    private boolean running = true; 

    public Shadow(FilledOval previous, int color, DrawingCanvas canvas)
    {
        //give references their references 
        previousCircle = previous; 
        thisColor = color; 
        canvasRef = canvas; 
        
        
        //this object is a circle
        thisCircle = new FilledOval(previousCircle.getX(),previousCircle.getY(),previousCircle.getWidth(),previousCircle.getHeight(),canvas);

        //make the newly created circle more white 
        thisColor += 2; 
        Color currentColor = new Color(thisColor,thisColor,thisColor); 
        thisCircle.setColor(currentColor); 
        
        thisCircle.sendToBack(); 
        
        //if the new circle isn't too white, create another circle 
        if(thisColor<240) 
        childI = new Shadow(thisCircle, thisColor, canvas); 
        
        this.start();
    }
    
    //moves this circle 
    public void moveTo(double x, double y)
    {
        thisCircle.moveTo(x,y); 
    }
    
    //move the shadow objects to the previous circle's location 
    
    //note to self: while(true) is not a good parameter for run methods.  Even if the object is removed, the while(true) run 
    //method will still continue to operate in the background, causing many issues with memory 
    public void run()
    {
        double size = 0; 
        while(running)
        {
            pause(10); 
            
            //increase theta thereby changing the size function's sin aspect 
            //50 is the default size of the shadow oval 
            theta += 0.1;
            size = OVAL_SIZE + 10*Math.sin(theta); 
            
            //when we resize the object, we need to reference the object's current location so that 
            //the enlarged circle is at this same location 
            Location previousLocation = new Location(thisCircle.getX()+thisCircle.getWidth()/2,thisCircle.getY()+thisCircle.getHeight()/2);
            
            //change the size of this circle and move it back to its original center location 
            thisCircle.setSize(size,size);

            thisCircle.moveTo(previousLocation.getX()-size/2,previousLocation.getY()-size/2); 


            
            pause(10); 
            
            //move this circle to its previous circle (which was passed down to it through the constructor method) 
            thisCircle.moveTo(previousCircle.getX(),previousCircle.getY()); 
            
            
            
        }
    }
    
    //removes this object, sends the message down the chain, and ends the runtime method 
    public void removeFromCanvas()
    {
        //stop the run method 
        running = false; 
        
        //remove the object 
        thisCircle.removeFromCanvas();
        
        //we can only remove childI if it exists, childI only exists if thisColor<240 
        if(thisColor<240)childI.removeFromCanvas(); 
    }

}
