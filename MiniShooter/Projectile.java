import objectdraw.*;
import java.awt.*;
public class Projectile extends ActiveObject
{
    private Location startLocation; 
    
    private FilledOval projectile; 
    
    //diameter of the projectile 
    private static final int P_DIAMETER = 20; 
    
    //reference to main class text score box 
    private Text score; 
    //score reference 
    private int currentScore;
    
    //x and y velocities 
    private double vx, vy;
    
    //RespourceFormat reference 
    //rf = resource format => rfRef
    private ResourceFormat rfRef; 
   

    public Projectile(Location point, double angle, double power, Text scoreRef, int scoreNum, ResourceFormat resource, DrawingCanvas canvas)
    {
        //pass reference to score 
        //Text
        score = scoreRef; 
        //Score value
        currentScore = scoreNum; 
        
        //pass reference to energy (ResourceFormat) (it is the bulging object) 
        rfRef = resource; 
        
        
        //let point be turretLocation point 
        startLocation = point; 
        
        //determines the start location of the projectile, it is 140 distance from the turret location point whose angle 
        //is determined by the angle between the mouse point and the turret location point 
        double projectileX = point.getX() + 140 * Math.cos(angle); 
        double projectileY = point.getY() + 140 * Math.sin(angle);
        
        //Create projectile 
        projectile = new FilledOval(projectileX-P_DIAMETER/2,projectileY-P_DIAMETER/2,P_DIAMETER,P_DIAMETER,canvas);  
        
        //Colorize projectile 
        Color DarkBlue = new Color(83,104,120); 
        projectile.setColor(DarkBlue); 
        
        //Calculate velocity of projectile
        vx = power * Math.cos(angle); 
        vy = power * Math.sin(angle); 
        
        
        this.start(); 
    }
    
    public void run()
    {
        //while the projectile is within canvas space 
        while(projectile.getY()<1200+P_DIAMETER){
            
            projectile.move(vx,vy); 
            vy += 2; 
            pause(41); 
            //pause is 41 for 24 FPS
            
          //find distance between energy (resourceObject) and the projectile 
          double dx = (rfRef.center.getX()-projectile.getX()) * (rfRef.center.getX()-projectile.getX());
          double dy = (rfRef.center.getY()-projectile.getY()) * (rfRef.center.getY()-projectile.getY());
          double distance = Math.sqrt(dx+dy); 
          
          //I tried the below code but this didn't work for some reason... how come?   
          // if(projectile.contains(rfRef.p1) || projectile.contains(rfRef.p2) || projectile.contains(rfRef.p3) || projectile.contains(rfRef.p4) || projectile.contains(rfRef.center) || )
          
          //alternative method for rough collision detection 
            if(distance<100)
            {
                //This references and modifies the score board in the main class 
                currentScore += 5;
                score.setText("Score:" + currentScore);
                
                
                //This score resets every time the projectile is created again.  While this feature is ok with what I have so far, how would I prevent the score from resetting every time? 
            }

        }
        projectile.removeFromCanvas(); 
        
        
    }
    
    
    


}
