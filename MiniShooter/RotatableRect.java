import objectdraw.*;
import java.awt.*;

public class RotatableRect
{
    //Pie constant for trigonometry 
    private static final double PI = 3.14159265359;  
    
    //center is center point of rectangle, p1,p2,p3,p4 are points of the rectangle 
    private Location center,p1,p2,p3,p4; 
    
    //lines between rectangle points
    private Line line1,line2,line3,line4; 
    
    //length between center point and p1
    private double diagonalLength;
    
    //the angle that always holds true between center point and p1,p2,p3,p4 
    private double angleConstant, angle1, angle2, angle3, angle4; 
    
 
    public RotatableRect(Location centerInit, double width, double height, DrawingCanvas canvas)
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
        
    }
    
    public void rotation(double finalAngle)
    {
        //opposite finalAngle since failing to do so makes rotation of positive seem negative 
        //reference diagonalLength to shorten code 
        finalAngle = -finalAngle; 
        double d = diagonalLength; 
        
        //change the locations of the p1,p2,p3,p4 points of the rectangle based on the current rotation 
           //their locations are different because the angle between the center point and these points are different 
        //cool side note: having different angles for cos/sin adds a "bulge" effect on rotation 
          p1 = new Location (center.getX()+d*Math.cos(angle1 + finalAngle),center.getY()+d*Math.sin(angle1 + finalAngle)); 
          p2 = new Location (center.getX()+d*Math.cos(angle2 + finalAngle),center.getY()+d*Math.sin(angle2 + finalAngle)); 
          p3 = new Location (center.getX()+d*Math.cos(angle3 + finalAngle),center.getY()+d*Math.sin(angle3 + finalAngle)); 
          p4 = new Location (center.getX()+d*Math.cos(angle4 + finalAngle),center.getY()+d*Math.sin(angle4 + finalAngle)); 
          
        //modify the lines to create the new connections between the rotated points   
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
        //It is important to move the center as well since it is the reference for where the rectangle is on the canvas
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
}
