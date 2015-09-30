import java.awt.*;
import javax.swing.*;
import objectdraw.*;

public class Trunk extends ActiveObject implements TreeInterface 
 {
    Line trunk; 
    TreeTall tree; 
    
    double X,Y = 0; 
    
    public Trunk(double startX, double startY, double endX, double endY, TreeTall tree, DrawingCanvas canvasRef)
    {
        trunk = new Line(startX,startY,endX,endY,canvasRef); 
        this.tree = tree;
        start();
    }
    
    public void run()
    {
        System.out.println("TRUNK" + tree.currentHeight); 
        pause(19);
    }
    
    public void move(double dx,double dy)
    {
        //issue with wrong dx dy input to tree currentx and currentHeight ? 
        //or wrong currentX currentHeight 
        
        trunk.move(dx,dy); 
        
        //something fundamentally wrong with variables in tree class
        //note that the first segment always works when moved 

        //tree.currentX += dx; 
        //tree.currentHeight += dy;  //original 
        
        //The commented out code with //original at their ends 
        //why did this system not work?  


        //System.out.println((double)tree.currentHeight); 
    }
    
    public Location getEnd()
    {
        return trunk.getEnd();
    }
    
    public void setEnd(double x, double y)
    {
        trunk.setEnd(x,y); 
    }
    

    
    
 }
