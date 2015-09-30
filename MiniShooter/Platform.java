import objectdraw.*;
import java.awt.*;

public class Platform extends ActiveObject
{
    //center is location of object, tPoint is reference to location outside class 
    public Location center, tPoint; 
    
    //Objects of the platform 
    private FilledRect base; 
    private FilledOval bottom, top; 
    
    //Location for where the turret will be created 
    public Location turretLocation; 
    
    //Create rotatable rectangles that make up the turrets 
    private RotatableRect turret, turretEnd; 
    
    public double currentAngle; 
    
    boolean fired; 
    
    //reference to drawing canvas since a projectile is created in the run method 
    public DrawingCanvas canvasRef; 
    


    
    public Platform(Location centerInit, DrawingCanvas canvas)
    {
      //Create reference to canvas for other methods/objects
      canvasRef = canvas;
      
      //Create platform objects in relation to center point 
      center = centerInit; 
      
        //Create turret end 
        turretLocation = new Location(center.getX(),center.getY()-40); 
        turret = new RotatableRect(turretLocation, 30, 100, canvas); 
        turretEnd = new RotatableRect(turretLocation, 20, 80, canvas); 
        
        //Create the platform 
      base = new FilledRect(center.getX()-50,center.getY()-50,100,50,canvas);
      bottom = new FilledOval(center.getX()-50,center.getY()-50,100,100,canvas); 
      top = new FilledOval(center.getX()-60/2,center.getY()-70,60,60,canvas); 
      

      
      //Color for object 
      Color lightGray = new Color(238,233,233);
      Color darkGray = new Color(139,137,137); 
       
      
      top.setColor(Color.lightGray); 
      bottom.setColor(Color.darkGray); 
      base.setColor(Color.darkGray); 
      
      this.start(); 
 
    }
    public void moveTo(double x, double y)
    {
        double dx = x - center.getX(); 
        double dy = y - center.getY(); 
        
        this.move(dx,dy); 
    }
    public void move(double dx, double dy)
    {
        //This code does not properly move the turret, yet this code is not necessary for the game anymore 
        //Kept in case of future use 
        center = new Location(center.getX() + dx, center.getY() + dy); 
        base.move(dx,dy); 
        bottom.move(dx,dy); 
        top.move(dx,dy); 
        turret.move(dx,dy); 
    }
    public void cannonRotation(double finalAngle, Location point) 
    {
       //rotate only if not firing 
        if(!fired){
        //position rectangle at end of pivot joint to give it extra length 
        //we find location of where the turret should be based on current rotation and then use trigonometry to move the turret to the 
        //target location 
        
        //finds current angle 
        double angle = Math.atan2((point.getY()-center.getY()), point.getX()-center.getX()); 
        
        //move turret components 
        turret.moveTo(center.getX() + 50 * Math.cos(angle),center.getY() - 40 + 50 * Math.sin(angle)); 
        turretEnd.moveTo(center.getX() + 100 * Math.cos(angle),center.getY() - 40 + 100 * Math.sin(angle)); 
        
        //rotate the turret (rotatable rectangles) to correct angle 
        turret.rotation(finalAngle-3.14/2);
        turretEnd.rotation(finalAngle-3.14/2); 
        

       }
    }
    public void fire(Location point, double angle, double power, Text scoreRef, int scoreNum, ResourceFormat resource)
    {

        //On fire method called, fires is true and create a new projectile 
        fired = true; 
        
        //create projectile 
        new Projectile(turretLocation,angle,power,scoreRef,scoreNum,resource,canvasRef);
        
        
        tPoint = point;
    }
    public void run()
    {
        //Check for fired boolean continuously, need an infinite while loop 
        while(true){
            
        double recoilDistance = 100; 
        double theta = 0; 
        
        while(fired==true)
        {
        //find the angle, this is necessary for moving the turret end back 
        //every so often, this causes a nullPointer exception, I think the problem is that 
        //the mouse can be off screen when this method initializes, and since the mouse parameter is passed 
        //down through multiple classes, any break in this series of passing down can cause the occasional error 
        double angle = Math.atan2((tPoint.getY()-center.getY()), tPoint.getX()-center.getX()); 
        
        //the turret end moves back in "recoil" from the shot, we need a recoilDistance to tell turret end 
        //how far to move back 
        
        //recoilDistance has a cos functions so that it moves from 100  to 50 (as cos goes from -1 to 1, so 50 * cos goes from 100 to 0)
        recoilDistance = 50 + 50 * Math.cos(theta);  
        
        //changes value of the cos function in recoilDistance 
        theta += .157; 
        
        //can be 10 to 50, consider having delay time be modifiable by game 
        pause(10); 
        
        //move the turret end to the recoilDistance 
        turretEnd.moveTo(center.getX() + recoilDistance * Math.cos(angle),center.getY() - 40 + recoilDistance * Math.sin(angle)); 
       
        //after recoil has occured, end this statement 
          if(theta > 6.28 && recoilDistance >= 99)fired = false;   
         
        }
        
        //pause by 41 to get 24 fps 
        pause(41); 
        }
    }
    
        

}
