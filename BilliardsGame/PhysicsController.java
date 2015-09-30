import objectdraw.*;

//Controls the collisions between the objects in the array given to it as a parameter 
public class PhysicsController extends ActiveObject
{
    //frequency we check for collisions 
    private final int CHECK_FREQUENCY = 25; 
    
    //the array we control 
    BilliardBall[] ballArray; 
    

    //input array of objects that can collide with each other 
    public PhysicsController(BilliardBall[] ballArray)
    {
        this.ballArray = ballArray; 

        this.start(); 
    }

    //Check if there are collisions between the billiard balls and initialize collision code if that happens 
    public void run()
    {
        //min distance until a collision is happening (they are all circles) 
        double minDistance = ballArray[1].getSize(); 

        //checks for collisions between all elements in ballArray 
        //uses double for loops to prevent "overchecking" collisions 
        //ex. check collision of element[0] with 1 2 3 4 5 ... length-1 
        //                              [1] 2 3 4 5 ... length - 1 
        //                              [2] 3 4 5 ... length - 1
        while(true)
        {

            //Check for collisions between all ball objects int he array 
            for(int i=0; i<ballArray.length-1; i++)
            {

                //location of the object we are checking to collide with j for loop elements 
                Location objectLocation = new Location(ballArray[i].getCenter()); 

                //check if element [i] has collided with elements [j loop]  If this has happened, run the collision code between the two objects 
                for(int j = ballArray.length-1; j>i; j--) 
                    if(ballArray[j].distanceTo(objectLocation)<minDistance)
                        //collision(ballArray[j],ballArray[i],minDistance); 
                        ballArray[j].collision(ballArray[i]); 
            }

            //frequency of checks 
            //more checks mean less errors but more CPU use 
            pause(CHECK_FREQUENCY); 
        }
    }

    //Calculate the information necessary to pass down to the collisionReaction method 
    public void collision(BilliardBall objectA, BilliardBall objectB, double minDistance)
    {
        Location objectLocation = new Location(objectB.getCenter()); 
        
        double dx = objectA.getCenter().getX() - objectLocation.getX(); 
        double dy = objectA.getCenter().getY() - objectLocation.getY(); 

        double angle = Math.atan2(dy,dx); 
        double distance = minDistance-objectA.distanceTo(objectLocation);

        double cos = Math.cos(angle); 
        double sin = Math.sin(angle); 

        //move the balls out of the distance in which they overlap 
        objectA.move(distance*cos,distance*sin); 
        objectB.move(-distance*cos,-distance*sin);

        //calculate the resultant angle of one of the balls after collision 
        double resultAngle = Math.PI/2;
        if(angle<Math.PI/2)resultAngle = -resultAngle; 
        else resultAngle = angle - 90; 

        //based on which ball is stationary (or if they are both stationary), run a modified collision code method 
        if(objectB.getVX()==0||objectB.getVY()==0) collisionReaction(distance,angle,resultAngle,cos,sin,objectA,objectB); 

        else if(objectA.getVX()==0||objectA.getVY()==0) collisionReaction(-distance,angle,resultAngle,cos,sin,objectA,objectB); 

        else collisionReaction(distance,angle,0,cos,sin,objectA,objectB); 

    }
    //Use the distance in which the balls originally overlapped to determine the final velocities of the objects 
    //Object A, the moving object, should have a different angle of departure due to momentum physics
    //this is due to the momentum equation v1 * m1 + v2 * m2 = v1 * m1 + v2 * m2
    //mass is the same and v2 = 0 (assume) 
    //this equation simplifies to v1^2 = v1^2 + v2^2 hence the reason why the moving object goes off at a 90 degree angle 
    //this is why resultAngle is 90 degrees (90 or -90 depending on angle of collision) 
    public void collisionReaction(double distance, double angle, double resultAngle, double cos, double sin, BilliardBall objectA, BilliardBall objectB)
    {

        objectA.setVX(distance*Math.cos(angle+resultAngle));
        objectA.setVY(distance*Math.sin(angle+resultAngle));

        objectB.setVX(-distance*cos);
        objectB.setVY(-distance*sin);

    }

}
