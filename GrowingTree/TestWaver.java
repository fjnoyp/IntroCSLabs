import objectdraw.*;
import java.awt.*;

public class TestWaver extends ActiveObject
{
    public Line seg1,seg2; 
    double theta;
 
    public TestWaver(DrawingCanvas canvas)
    {
               
       //Finding angle between start point and end point 
       seg1 = new Line(10,10,50,50,canvas); 


       
       //correct degree found 
       seg2 = new Line(50,50,90,90,canvas);
       
       //System.out.pintln(
       
       start(); 

    }
    public void run()
    {
       double dy = seg1.getEnd().getY() - seg1.getStart().getY(); 
       double dx = seg1.getEnd().getX() - seg1.getStart().getX(); 
       double degree = Math.atan2(dy,dx); 
       double degree2 = Math.atan2(dy,dx); 
       
       while(true){
       degree = degree + 0.05*Math.cos(theta); 
       
       seg1.setEnd(10+56*Math.cos(degree),10+56*Math.sin(degree));
       
       seg2.moveTo(seg1.getEnd()); 
       
       degree2 = degree2 + 0.05*Math.cos(theta+.5); 
       
       seg2.setEnd(seg2.getStart().getX()+56*Math.cos(degree2),seg2.getStart().getY()+56*Math.sin(degree2));
       
       theta += 0.1;
       pause(41);
        
      }       
       //System.out.println(Math.toDegrees(Math.atan2(dy,dx)));
    }

}
