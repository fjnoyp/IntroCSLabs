
import objectdraw.*;
import java.awt.*;

/*
 * Billiard Balls.  This code handles the movement of the billiard ball and has a series of methods 
 * that allow other classes to modify this billiard ball 
 */

public class BilliardBall extends ActiveObject 
{

//characteristics of the ball 

    private double vx = 0; 
    private double vy = 0;

    private int mass; 

    private FilledOval billiardBall; 

    private DrawingCanvas canvas; 

    //allows user to stop the movement code of this ball to stop
    public boolean running = true; 

    //construct ball by giving it position, size, mass
    public BilliardBall(double x, double y, int size, int mass, DrawingCanvas canvas)
    {
        this.canvas = canvas; 
        this.mass = mass; 
        billiardBall = new FilledOval(x,y,size,size,canvas);
        this.start(); 
    }

    //call GetArray from main class to get array of objects to collide with 

    //detect collisions with other balls/forces and move/react accordingly 
    public void run()
    {
        while(running)
        {
            this.checkBoundaries(); 
            billiardBall.move(vx,vy); 

            //Friction 
            vy*=0.95; 
            vx*=0.95;

            //Stop movement below certain velocities 
            if(Math.abs(vx)<0.1)vx=0; 
            if(Math.abs(vy)<0.1)vy=0;

            //FPS 24 
            pause(60);
        }
    }
    //check if the billiard ball has moved beyond the stage parameters. If it has, move it back and make it bounce 
    public void checkBoundaries()
    {
        if(this.getX()+billiardBall.getWidth()>canvas.getWidth())
        {
            this.setX(canvas.getWidth()-billiardBall.getWidth()); 
            vx = -vx;

        }
        if(this.getX()<0)
        {
            this.setX(0);  
            vx = -vx; 

        }
        if(this.getY()+billiardBall.getHeight()>canvas.getHeight())
        {
            this.setY(canvas.getHeight()-billiardBall.getHeight()); 
            vy = -vy; 

        }
        if(this.getY()<0)
        {
            this.setY(0); 
            vy = -vy; 

        }

    }

    //reference current vx and vy
    //input outside object's vx, vy, and mass (of object B) 
    //configure to run for collision between balls, pool stick, and table edges (for now just set mass to 0)
    //Use conservation of momentum equation to find resultant velocity 

    //we assume "coefficient of restitution" is 0, use momentum equations 
    //public void collision(double vxB, double vyB, double massB)
    public void collision(BilliardBall objectB)
    {
        //if this object is not completly at rest 
        if(vx!=0 || vy!=0){
            momentumCollision(this,objectB);
        }
        //the object calling the method is not moving 
        if(vy==0 && vx==0){
            momentumCollision(objectB,this); 
        }

    }

    //objectA does not have all x,y zero speeds 
    /* This method is based on a book's version of collisions between objects with mass.  I did not come up
     * with this method myself.  The idea behind this method is that we rotate the objects' positions, and velocities to one 
     * angle of rotation.  Afterwards, we then use the equation for momentum on one axis to determine the reusulting velocities.  From these velocities,
     * we rotate back our results to whatever axis of rotation the balls were moving at beforehand.  
     * 
     * the equation #*cos+#*sin is the basic equation used to rotate the object by a certain degree.  
     */
    public void momentumCollision(BilliardBall objectA, BilliardBall objectB) 
    {
        //Separate objects where they overlap 
        Location objectLocation = new Location(objectB.getCenter()); 
        
        double dx = objectA.getCenter().getX() - objectLocation.getX(); 
        double dy = objectA.getCenter().getY() - objectLocation.getY(); 

        double angle = Math.atan2(dy,dx); 
        double distance = this.getSize()-objectA.distanceTo(objectLocation);

        double cos = Math.cos(angle); 
        double sin = Math.sin(angle); 

        //move the balls out of the distance in which they overlap 
        objectA.move(distance*cos,distance*sin); 
        objectB.move(-distance*cos,-distance*sin);
        
        
        //Momentum calculations 
        dx = objectB.getX() - objectA.getX(); 
        dy = objectB.getY() - objectA.getY(); 
        double dist = Math.sqrt(dx*dx + dy*dy); 

        angle = Math.atan2(dy,dx); 
        sin = Math.sin(angle); 
        cos = Math.cos(angle); 

        //Rotate objectA's position 
        double x0 = 0; 
        double y0 = 0; 

        //Rotate objectB's position 
        double x1 = dx*cos+dy*sin; 
        double y1 = dy*cos-dx*sin; 

        //Rotate ballA's velocity 
        double vx0 = vx*cos+vy*sin; 
        double vy0 = vy*cos-vx*sin; 

        //Rotate ballB's velocity 
        double vx1 = objectB.getVX()*cos+objectB.getVY()*sin; 
        double vy1 = objectB.getVY()*cos-objectB.getVX()*sin; 

        //Collision 
        double vxTotal = vx0 - vx1; 
        vx0 = (mass-objectB.getMass())*vx0 + 2*objectB.getMass()*vx1 / (mass+objectB.getMass()); 
        vx1 = vxTotal + vx0; 
        x0+=vx0;
        x1+=vx1; 

        //Rotate positions back 
        double x0Final = x0*cos-y0*sin; 
        double y0Final = y0*cos+x0*sin; 
        double x1Final = x1*cos-y1*sin; 
        double y1Final = y1*cos+x1*sin; 

        //planned obsolescence, varies between type of object affected 
        objectB.react(x1Final, y1Final, vx1, vy1, cos, sin, objectA); 

        //adjust positions to actual screen positions 
        objectA.setX(objectA.getX() + x0Final); 
        objectA.setY(objectA.getY() + y0Final); 

        //rotate velocities back 
        objectA.setVX(vx0 * cos - vy0 * sin); 
        objectA.setVY(vy0 * cos + vx0 * sin); 

    }
    
    //adjust positions to actual screen positions 
    //rotate velocities back 
    public void react(double x1Final, double y1Final, double vx1, double vy1, double cos, double sin, BilliardBall objectA){
        this.setX(objectA.getX() + x1Final); 
        this.setY(objectA.getY() + y1Final); 
        this.setVX(vx1 * cos - vy1 * sin); 
        this.setVY(vy1 * cos + vx1 * sin);
    }

    //The bottom methods are just basic methods, self explanatory.
    
    public double getX()
    {
        return billiardBall.getX(); 
    }

    public double getY()
    {
        return billiardBall.getY(); 
    }

    public void setX(double x) 
    {
        billiardBall.moveTo(x,billiardBall.getY()); 
    }

    public void setY(double y)
    {
        billiardBall.moveTo(billiardBall.getX(),y);  
    }

    public double getSize()
    {
        return billiardBall.getHeight();     
    }

    public double getVX()
    {
        return vx; 
    }

    public double getVY()
    {
        return vy; 
    }

    public void setVX(double vx)
    {
        this.vx = vx; 
    }

    public void setVY(double vy)
    {
        this.vy = vy;
    }

    public boolean contains(Location point)
    {
        return billiardBall.contains(point);
    }

    public void move(double dx, double dy)
    {
        billiardBall.move(dx,dy); 
    }

    public void moveTo(double x, double y)
    {
        billiardBall.moveTo(x-this.getSize()/2,y-this.getSize()/2); 
    }

    public void moveTo(Location point)
    {
        this.moveTo(point.getX(),point.getY()); 
    }

    public Location getCenter()
    {
        //create a location at the center of this object 
        Location center = new Location(billiardBall.getX()+billiardBall.getWidth()/2.0,
                billiardBall.getY()+billiardBall.getHeight()/2.0); 
        return center; 
    }

    //computes distance between this object's center and another point (should be another object's center point as well)
    public double distanceTo(Location point)
    {
        //System.out.println(this.getCenter().distanceTo(point));
        return this.getCenter().distanceTo(point); 

    }

    public int getMass()
    {
        return mass;
    }

    public void setColor(Color color)
    {
        billiardBall.setColor(color);
    }

}
