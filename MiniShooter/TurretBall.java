import objectdraw.*;
import java.awt.*;
import java.applet.*;

/**
 *   CS 134 Laboratory 1 / Section 5
 *   Kyle Cheng 
 *   9/10/12
 */
public class TurretBall extends WindowController {
    //I wanted to add onto the exising BoxBall class but the features that I was thinking about adding conflict with the features 
    //that we need to have in the box ball program.  This game is based on the boxBall idea but I have another version, BoxBall in 
    //this same folder that follows all the guidelines that you requested we have for this lab.  The main difference from the BoxBall 
    //lab guidelines is that the projectile's power can be adjusted and changed based on the rotation of the turret, and that the projectile
    //comes from a turret rather than a single mouse click.  


    //Please note this is a work in progress so some code that isn't explicitly used is still important for me
    //as I need to use them in the future or for guiding my work 

    //PIE! - I need this for the trigonometry in this program 
    public static final double PI = 3.14159265359;
    
    //Valuable text that tells me about the game variables, i keep this because I will be using it in the future if I add other features 
    private Text debug;
    //currently, the debug text object displays the "power" of the projectile as it is shot from the turret.  It's location just on top of the 
    //power level rectangle makes its meaning implied since its value changes whenever the user shoots the projectile at different power levels 
    
    //Angles for detecting the angle between the center point and mouse point 
    public double angle; 
    private double finalAngle; 

    //Line drawn between center and mouse point 
    //1,1,1,1 since the line is modified immediately when the mouse moves (so these values are insignificant) 
    private Line line = new Line(1,1,1,1,canvas); 
    
    //The center point where the platform character will be created 
    //600 and 500 is the location of center 
    private Location center = new Location(600,550);
    
    
    //test 2 is the platform + turret on the screen 
    //it is called test 2 because there are still more features I'd like to add to it 
    
    //private RotatableRect test; 
    private Platform test2; 
     
    //Audio files , turn on your speakers!
    private AudioClip cannonFire; 
    
    //Determines length of mouse click, this is for finding power of the shot 
    private double startTime; 
    public double clickLength; 
    


    //Objects for projectile power 
    private FramedRect powerLevelFrame; 
    private Text powerLabel; 
    private PowerLevel powerBar; 
    
    //Canvas reference 
    DrawingCanvas canvasRef; 
    
    //Previous point
    private Location prevPoint;
    
    //Resource Objects, hitting these with the projectile should increase score 
    //they are called resource objects and energy because I originally intended to use them for powering the cannon 
    private ResourceFormat energy; 
    
    //Text for score 
    private Text scoreText; 
    public int score; 
   
    //Text for instructions 
    private Text instructions; 
    
    //The below disabled code was my attempt to create an array to hold the points of the resourceFormat objects that I created.  My goal was to have 
    //multiple resourceFormat class objects be created on screen at once but I didn't know how to pass as a parameter all of their points 
    //so that the projectile created by the Platform would know when to register a hit.  I thought that an array might work but ran out of time to implement 
    //this idea fully.  When we want to detect collision between one object and many other objects, how would one do this if the collision detection 
    //runs in the object's run method?  
    
    /*
    //Create an array to hold the location points of the moving object (energy) so that projectile knows if collision has occured 
    //Create excess space if other points are added if the feature of creating new resource formats is created 
    private static final int NUM_POINTS = 20; //constants should be on top but it's easier if it's here 
    Location[] collisionPoints = new Location[NUM_POINTS]; 
    */

    public void begin() {
        /*
        //fill the Location array with blank locations so that nullPointReference won't happen in case some array spots aren't taken 
        for(int i = 0; i < NUM_POINTS; i ++){
            Location locationPlaceHolder = new Location(-100,0); 
            collisionPoints[i] = locationPlaceHolder;  
        }
        */
        
        //Create instructions (60,10 are coordinates of instuctions) 
        instructions = new Text("Shoot the moving object with your cannon!  Shoot farther by holding the mouse button before releasing", 60, 10, canvas); 
        
        //Create score Text (10,240 coordinates of scoreText) 
        scoreText = new Text("Score:" + score,10,240,canvas); 
        
        //beta stage of the resource format rotatable object 
        //this is the weird bulging object on the stage 
        Location energyLocation = new Location(-100,50); 
        energy = new ResourceFormat(energyLocation,100,100,canvas,1.1,-1.7,-1,.7); 

        
        
        //Canvas reference 
        canvasRef = canvas;
        
   
        
        //Objects for projectile power 
        //10,20 are x coordinates, 30, 190 are powerLevelFrame width and height 
        powerLevelFrame = new FramedRect(10,20,30,190,canvas); 
        powerLabel = new Text("Power Level", 10, 215, canvas); 
        

        //Resize canvas
        this.resize(1200,1200);
        
        //Audio files 
        cannonFire = getAudio("cannon_fire.wav"); 
        
        //Location of debug text, 10, 10 
        debug = new Text("", 10,0,canvas); 
        
        //Have test 2 create the platform + turret that we see on stage 
        test2 = new Platform(center,canvas); 
          
    
    }


    public void onMouseMove(Location point) {
    
          //Reference for the mouse point on the stage, there is a double reference to the mouse point 
          //since the location of p1 might be changed to be another location 
          Location p1 = new Location(point.getX(),point.getY()); 
          
          //Reset and draw the line each time mouse moves, draw line between platform's point and the mouse point 
          line.removeFromCanvas(); 
          line = new Line(test2.turretLocation,p1,canvas);
   
          //find angle between two points on the 360 degree plane 
          angle = Math.atan2((p1.getY()-center.getY()), p1.getX()-center.getX());
          
          //modify angle found by atan2 (since output values are not 0 to 360 degrees) 
          if(angle >= -PI && angle < 0){
              finalAngle = -angle;
          }
        
          if(angle >= 0 && angle <= PI){
              finalAngle = 2*PI - angle;
          }
          
          //The above code finds the angle between the center point of the platform and the mouse point
          //This code is no longer in use, but I use it for reference for other programs 
          
          //On mouse move, adjust the rotation of the turret on the platform 
         test2.cannonRotation(finalAngle, p1); 
        

    }

    public void onMouseEnter(Location point) {
    }


    public void onMouseExit(Location point) {

    }

    public void onMousePress(Location point) {
        
        //Find the start time of mouse press 
        startTime = System.currentTimeMillis(); 
        
        //Create a new power bar that will tell us how long we have clicked 
        powerBar = new PowerLevel(canvasRef); 
        
        //Parameter for platform recoil, we need the previous point so any mouse changes won't mess 
        //up the fire method of the platform class 
        prevPoint = point; 
        


    }
    
    public void onMouseClick(Location point) {
        
        
    }

    public void onMouseRelease(Location point) {
        
        //Determine the length of the click
        //The lenght of the click is passed as a parameter to test2 and determines strenght of the shot 
        clickLength = System.currentTimeMillis() - startTime; 
        clickLength = clickLength/50; 
        
        //Restrict the max value of the click to 60 
        if(clickLength > 60)clickLength = 60; 
        
        //Tells me the power of the shot, for debug purpose 
        debug.setText(clickLength); 
        
        //change 60 back to click length 
        
        //Run the fire method of the platform class, this recoils the turret and creates a projectile 
        test2.fire(prevPoint,angle,clickLength,scoreText,score,energy); 
        //sound effects!
        cannonFire.play();
        
        //remove the powerbar that told us the duration of our shot 
        powerBar.removeFromCanvas(); 
  
  
  

    }

    public void onMouseDrag(Location point){
        
    }
}