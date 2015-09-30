
import objectdraw.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

/*
 * Billiard Ball physics simulator.  The user can interact with the blue ball and collide it into 
 * the mass of black balls, each of which interacts with the other using the laws of momentum 
 */

public class Billiards extends WindowController{

    //Location of the black balls 
    private static final int STARTING_POS_X = 100; 
    private static final int STARTING_POS_Y = 200; 

    //Characteristics of the balls 
    private static final int BALL_SIZE = 25; 
    private static final int BALL_MASS = 25; 

    //Array to hold all balls 
    BilliardBall[] ballArray = new BilliardBall[16];

    //Physics engine 
    private PhysicsController physics; 
    
    //Ball we can interact with (blue one) 
    private BilliardBall userBall;
    
    //Keep track of mouse press positions for blue ball speed 
    private Location previousLocation = new Location(0,0);

    //Determine if the blue ball(user ball) has been clicked on
    private boolean clicked; 

    //Movement speed of blue ball on release 
    private double dx; 
    private double dy; 

    //Add on elements and instructions 
    public void begin()
    {
        //Blue ball 
        userBall = new BilliardBall(STARTING_POS_X,50,BALL_SIZE,BALL_MASS,canvas); 

        //Create the triangle of blue balls
        setUpBalls(STARTING_POS_X,STARTING_POS_Y); 

        //input ball array we want to have collide with each other 
        physics = new PhysicsController(ballArray);
        
        //Instructions for the user 
        new Text("Billiard ball physics.  Click on the blue ball, drag and release.",5,5,canvas); 

    }
    
    //Makes a triangle of balls 
    public void setUpBalls(int startingPosX, int startingPosY)
    {
        //counts # element in the ballArray 
        int counter = 0; 
        //run 5 times 
        for(int i = 1; i<6; i++){

            //30 is ball spacing 
            for(int k = 0; k<i; k++){
                BilliardBall tempBall =  new BilliardBall(startingPosX+30*k,startingPosY + 30*i,BALL_SIZE,BALL_MASS,canvas); 
                ballArray[counter] = tempBall; 
                counter++; 
            }

            //adjust starting position of next row 
            startingPosX -= BALL_SIZE/2;
        }

        //The "blue" ball used to hit the other balls 
        ballArray[15] = userBall;
        ballArray[15].setColor(Color.BLUE); 
    }

    //if the user has clicked, track the location of the click 
    public void onMousePress(Location point)
    {
        if(userBall.contains(point)){
            clicked = true; 
            previousLocation = point; 
        }

    }

    //when the user has clicked and begins dragging the mouse calculat the future velocity of the blue ball 
    public void onMouseDrag(Location point)
    {
        if(clicked){

            dx = point.getX()-previousLocation.getX();
            dy = point.getY()-previousLocation.getY();

        }
    }
    
    //when the user releases the mouse button and has already clicked, set the velocity 
    //of the blue ball to be the distance that the user has moved the mouse key 
    public void onMouseRelease(Location point)
    {  
        if(clicked){
            
            //limit velocities 
            if(dx>30)dx=30;
            if(dy>30)dy=30; 

            userBall.setVX(-dx); 
            userBall.setVY(-dy);  

            clicked = false; 

        }

    }

}
 