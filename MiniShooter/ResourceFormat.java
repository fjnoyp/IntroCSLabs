import objectdraw.*;
import java.awt.*;

public class ResourceFormat extends ActiveObject 
{
//This class is a variation of the rotatable rectangle.  The difference is that the angle constants can be modified so that 
//the object bulges and warps as it rotates in space

    //pie constant 
    private static final double PI = 3.14159265359;  
    
    //public as they are used in reference elsewhere
    //points of the rectanglish object 
    public Location center,p1,p2,p3,p4; 
    //lines connecting these points 
    public Line line1,line2,line3,line4; 
    
    //the distance between p1 and center 
    private double diagonalLength;
    
    
    //constant angles between the points and the center 
    private double angleConstant, angle1, angle2, angle3, angle4; 
    
    //current rotation, constantly add to this value so that the object rotates 
    private double theta;
    
    //angle modifiers that change the angle constants between the center point and the exterior points
    private double a,b,c,d; 
    
    //This class is different in that angle values modify the angle difference between the points and the center points thereby 
    //adding in special effects such as bulging or twisting 

 
    public ResourceFormat(Location centerInit, double width, double height, DrawingCanvas canvas, double aTemp,double bTemp,double cTemp,double dTemp)
    {
        //centerInit : Initial center
          //diagonalLength length between center and p1, necessary since points are always a certain distance from the center 
          diagonalLength = Math.sqrt((width/2)*(width/2)+(height/2)*(height/2)); 
          
          //make points same distance from center and place them so they form a rectangle 
          p1 = new Location(centerInit.getX()+width/2,centerInit.getY()+height/2); 
          p2 = new Location(centerInit.getX()-width/2,centerInit.getY()+height/2); 
          p3 = new Location(centerInit.getX()-width/2,centerInit.getY()-height/2); 
          p4 = new Location(centerInit.getX()+width/2,centerInit.getY()-height/2); 
          
          //draw lines between points 
          line1 = new Line(p1,p2,canvas); 
          line2 = new Line(p2,p3,canvas); 
          line3 = new Line(p3,p4,canvas); 
          line4 = new Line(p4,p1,canvas);
          
          //find the constant angle between the center point and the exterior points, while the angle between them changes as the rectangle 
          //rotates, current rectangle rotation - angle constant is always amount of radians currently rotated 
          angleConstant = Math.atan(height/width); 
          angle1 = angleConstant; 
          angle2 = PI - angleConstant; 
          angle3 = angleConstant + PI; 
          angle4 = 2*PI - angleConstant; 
          
          //reference to center point of rectangle 
          center = centerInit;
          
          //pass references from parameters to this class's doubles 
          a = aTemp; 
          b = bTemp; 
          c = cTemp; 
          d = dTemp; 
          
          this.start(); 
        
    }
    
    public void rotation(double finalAngle,double a, double b, double c, double d)
    {
       
        
        //reference diagonalLength to shorten code 
        finalAngle = -finalAngle; 
        double dl = diagonalLength; 
        
        //a,b,c,d modify the angle constants between the point and the center giving this object rotational effects 
        
        //cool side note: having different angles for cos/sin adds a "bulge" effect on rotation 
          p1 = new Location (center.getX()+dl*Math.cos(angle1 + finalAngle),center.getY()+dl*Math.sin(angle1 + a + finalAngle)); 
          p2 = new Location (center.getX()+dl*Math.cos(angle2 + finalAngle),center.getY()+dl*Math.sin(angle2 + b + finalAngle)); 
          p3 = new Location (center.getX()+dl*Math.cos(angle3 + finalAngle),center.getY()+dl*Math.sin(angle3 + c + finalAngle)); 
          p4 = new Location (center.getX()+dl*Math.cos(angle4 + finalAngle),center.getY()+dl*Math.sin(angle4 + d + finalAngle)); 
          
          
          line1.setEndPoints(p1,p2); 
          line2.setEndPoints(p2,p3); 
          line3.setEndPoints(p3,p4); 
          line4.setEndPoints(p4,p1);
    }
    
    public void moveTo(double x, double y)
    {
        double dx = x - center.getX(); 
        double dy = y - center.getY();
        
        this.move(dx,dy);

    }
    public void move(double dx, double dy)
    {
        center = new Location (center.getX()+dx,center.getY()+dy); 
        p1 = new Location(p1.getX()+dx,p1.getY()+dy); 
        p2 = new Location(p2.getX()+dx,p2.getY()+dy); 
        p3 = new Location(p3.getX()+dx,p3.getY()+dy); 
        p4 = new Location(p4.getX()+dx,p4.getY()+dy); 

        line1.setEndPoints(p1,p2); 
        line2.setEndPoints(p2,p3); 
        line3.setEndPoints(p3,p4); 
        line4.setEndPoints(p4,p1);
    }
    public void run()
    {
        //while, true so it always runs continously 
        while(center.getX()<1300)
        {
            theta += .05; 
            this.rotation(theta,a,b,c,d);
            
            this.move(1,5*Math.sin(theta)); 
            
            pause(10); 
            
            if(center.getX()>1250)this.moveTo(-100,100);
        }
        
        
    }
}
