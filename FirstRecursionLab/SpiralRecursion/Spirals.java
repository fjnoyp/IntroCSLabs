
import objectdraw.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

/*
 * This extra makes an active object move accross the lines traced by the spiral 
 * The spiral uses your coding that you created for making spirals with different magnitude and bumps
 * Recursion is used to make the active object leave a "shadow" of where it has traveled and bulge in size 
 * whenever it moves
 *  Basically, a string of circles moves towards the most recent circle in the chain, but does so after a certain
 *  delay.  The first most circle is moved and passes down its new location to the preivous circle in the chain, this processed
 *  is repeated down the chain 
 * Recursion is also used to give the active object the next line segment point for where it should move to 
 * 
 * The Spiral Equation (that you gave us in class)
 * equation:  radius = theta * (1 + m * sin(b * theta))
 *   - m is the magnitude of the wave placed over the
 *       spiral curve
 *   - b is the frequency (or number of bumps) of the
 *       wave placed over the spiral curve.
 * 
 *  This version also uses a linear scale to fade from
 *  blue to red.
 */

public class Spirals extends WindowController implements ChangeListener {

    //for the spiral 
    private static final double THETA_STEP = 0.1;
    private static final double MAX_THETA = 40 * Math.PI;
    private static final int SPIRAL_LOCATION = 350; 
    
    //jslider 
    private JSlider magnitudeSlider; 
    private JSlider bumpsSlider; 
    
    //values that modify spiral 
    private double magnitude; 
    private double bumps; 
    
    //the line segments that make up the spiral, used to feed reference locations to the shadow object
    private Segment currentSegment; 
    private Segment moveSegment; 
    
    //This object is the "shadow" of the object as it moves 
    private Shadow shadowObject; 
    //While the name may seem like a misnomer, the shadowguide is in fact the first oval of the moving 
    //object which all other shadow objects move towards 
    private ShadowGuide firstOval; 
    
    
    //create the active object that moves around on the screen and create the shadow objects that create
    //the "shadow effect" whenever the active object moves 
    public void begin()
    {
        Text instructions = new Text ("Move BOTH Sliders to change the spiral, the ball will move along the spiral",0,0,canvas);
        //Create the shadow object 
        firstOval = new ShadowGuide(moveSegment,currentSegment,canvas); 
        shadowObject = new Shadow(firstOval.getOval(),0,canvas); 
        
        this.resize(700,800); 
        
        //GUI components
        //create two sliders that change different behaviors of the curve that we see on the screen 
        magnitudeSlider = new JSlider(JSlider.HORIZONTAL,1,50,1); 
        bumpsSlider = new JSlider(JSlider.HORIZONTAL,1,50,1); 
       
        JPanel topLayout = new JPanel(); 
        topLayout.setLayout(new GridLayout(2,1)); 
        
        topLayout.add(magnitudeSlider); 
        topLayout.add(bumpsSlider); 
        
        Container contentPaneTop = getContentPane();
        contentPaneTop.add(topLayout, BorderLayout.NORTH); 
        contentPaneTop.validate();
        
        magnitudeSlider.addChangeListener(this); 
        bumpsSlider.addChangeListener(this); 
    }

    //Based on the slider's selection value, change the value of the spiral's magnitude or bumps
    public void stateChanged(ChangeEvent event)
    {
        
        if(event.getSource()==magnitudeSlider){
            magnitude = magnitudeSlider.getValue(); 
        }

        if(event.getSource()==bumpsSlider){
            bumps = bumpsSlider.getValue(); 
        }
        //Create a new spiral based on the changed values of magnitude and bumps 
        Spiral(); 
    }
    public void onMouseMove(Location point)
    {
        
        
    }
    public void Spiral()
    {

       
        //Whenever we change spirals, we need to clear the screen 
        //the remove from canvas methods also stop their run methods while removing the objects themselves
        firstOval.removeFromCanvas(); 
        shadowObject.removeFromCanvas(); 
        
        //cleans up any left over objects just in case 
        //this cleans up the spirals 
        canvas.clear(); 
        
        
        //Since we cleared the screen, we need to create a new shadow object 
        //the first currentSegment used as a reference to access the lines of the spiral.  the other 
        //segment is used to reset the shadowGuide after it has reached through all the spiral segments 
        firstOval = new ShadowGuide(currentSegment,currentSegment,canvas); 
        shadowObject = new Shadow(firstOval.getOval(),0,canvas); 
        
        //Where the spiral is created 
        Location center = new Location (SPIRAL_LOCATION,SPIRAL_LOCATION); 
        Location lastPoint = center;  //used when creating the spiral 
        
        double theta = 0;
        
        //this code is the same as given in the handout except for the added recursion part (see in code) 
        while (theta < MAX_THETA) {
            double radius = theta * (1 + Math.sin(bumps * theta) * magnitude);

            // Convert polar to cartesian
            Location newPoint = 
                new Location(center.getX() + radius * Math.cos(theta),
                             center.getY() + radius * Math.sin(theta));
                              
            Line currentLine = new Line(lastPoint, newPoint, canvas);
            
            //Pass the last segment into this parameter and the current line 
            //each currentSegment can therefore have a reference to the previous line segment 
            currentSegment = new SpiralSegment(currentSegment,currentLine,canvas); 
            
            // Compute the color of the line segment
            // When theta is small, we want very little red
            //   and lots of blue.  As theta grows, the opposite
            //   is true.  the (int)(...) is a type cast to
            //   force Java to treat a double value as an int.
            int red = (int)(255 * theta / MAX_THETA);
            currentLine.setColor(new Color(red,0,255-red));
            
            lastPoint = newPoint;
            theta = theta + THETA_STEP;
        }
    }
}