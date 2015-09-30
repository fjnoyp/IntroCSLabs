import objectdraw.*;
import java.awt.*;
import java.util.Vector; 


public class TreeV extends ActiveObject implements TreeInterface
{
    //PI constant for trig 
    private final static double PI = 3.141593;
    
    //angle randomizer to change angle difference between branching pairs 
    private RandomDoubleGenerator angleRand = new RandomDoubleGenerator(-.328,0.328); 
    
    //randomize time waited before creating next branch segment of growth 
    private RandomIntGenerator pauseRand = new RandomIntGenerator(1,200); 

    //All references to be passed from constructor to run method 
        double startX = 0; 
        double startY = 0; 
        int currentBranch = 0; 
        double branchLength = 0; 
        double currentAngle = 0; 
        Color currentColor; 
        int branchMaximum = 0; 
        DrawingCanvas canvasRef; 
        
    //The branches 
        Line branchI;
        Line branchII;
    
    //Increments until branch grows to its spot 
        double incrementXI = 0; 
        double incrementYI = 0; 
        
        double incrementXII = 0;
        double incrementYII = 0;
        
    //Degree references 
        double degreeI;
        double degreeII;
    
    //Array references 
        //Line [] branchesArray; 
        //int arrayNumber; 
    //Vector reference 
        public Vector branchVector;
        
        private TreeV childI, childII; 
        
    //Dx and Dy references (no longer in use) 
        private double dx; 
        private double dy; 

      
    public TreeV(double x, double y, int count, double currentLength, double angle, Color color, int maxBranches, Vector branchVectorRef, DrawingCanvas canvas)
    {
        //pass references to run method 
        
        //reference to start position of this branch 
        startX = x;
        startY = y; 
        currentBranch = count; 
        branchLength = currentLength; 
        currentAngle = angle; 
        currentColor = color; 
        branchMaximum = maxBranches; 
        canvasRef = canvas; 

        branchVector = branchVectorRef; 
        
        


       start();
    }

    public void run()
    {
           //The two new branches that grow 
           branchI = new Line (startX,startY,startX,startY,canvasRef);
           branchII = new Line (startX,startY,startX,startY,canvasRef);
           
           //branchVector.add(branchI); 
           //branchVector.add(branchII); 
           
           this.branchVector.add(this); 
        //System.out.println(this); 
           
           //arrayNumber+=2; 
           
           //multi use of array 
            
           //Set Color
           if(currentBranch!=branchMaximum-1){
           branchI.setColor(currentColor); 
           branchII.setColor(currentColor); 
           }
           
           else{
           currentColor = new Color(85,107,47); 
           branchI.setColor(currentColor); 
           branchII.setColor(currentColor); 
           }
        
           //Target locations for branches to grow to
           //randomize angle differences between branches 
           
           //Anglef or branch I , slightly randomize 
           double angleDiff = PI/10 + angleRand.nextValue();
           
           //target values for where the branchI should be growing to 
           double xTargetI = branchLength*Math.cos(currentAngle - angleDiff);
           double yTargetI = -branchLength*Math.sin(currentAngle - angleDiff);
           
          
           //Angle for the branchII , slightly randomize 
           double angleDiff2 = PI/10 + angleRand.nextValue(); 
           
           //target value for where the branchII should be growing to 
           double xTargetII = branchLength*Math.cos(currentAngle + angleDiff2);
           double yTargetII = -branchLength*Math.sin(currentAngle + angleDiff2);
           
           //Initialize increments of movement for branchI and branch II 
           incrementXI = startX;
           incrementYI = startY;
           incrementXII = startX;
           incrementYII = startY;
           
           //counter for while loop 
           int count = 0; 
        
           
        while(count<20)
        {
           //Slowly move branch to target growth point 
           branchI.setEnd(incrementXI,incrementYI); 
           branchII.setEnd(incrementXII,incrementYII); 
            
           //Slowly move increment values to target value
           
 
           count ++; 
           
           
           incrementXI += xTargetI/20;
           incrementYI += yTargetI/20;
           incrementXII += xTargetII/20;
           incrementYII += yTargetII/20;
           
           pause(42); 
            
        }
        
       //diminish future branch length 
       branchLength = branchLength * .8;
       
       //count to maximum branches 
       currentBranch ++; 

       //Create two new branches one at each of this branch's end 
       //Do not do this when the current branch (or the layer of newly created branches) has reached a level equal to the branchMaximum 
       if(currentBranch!=branchMaximum){
           
       TreeV childI = new TreeV(branchI.getEnd().getX(),branchI.getEnd().getY(),currentBranch,branchLength,currentAngle - angleDiff,currentColor,branchMaximum,branchVector,canvasRef);

       TreeV childII = new TreeV(branchII.getEnd().getX(),branchII.getEnd().getY(),currentBranch,branchLength,currentAngle + angleDiff2,currentColor,branchMaximum,branchVector,canvasRef);

       }
       

      /*
      double dyI = branchI.getEnd().getY() - branchI.getStart().getY(); 
      double dxI = branchI.getEnd().getX() - branchI.getStart().getX(); 
          
      double dyII = branchII.getEnd().getY() - branchII.getStart().getY(); 
      double dxII = branchII.getEnd().getX() - branchII.getStart().getX(); 
      
      double degreeI = Math.atan2(dyI,dxI); 
      double degreeII = Math.atan2(dyII,dxII); 
      
      double theta = 0; 
      
      while(true)
      {
          childI.moveTo(this.thisEnd()); 
          childII.moveTo(this.thisEnd()); 
          
          degreeI = degreeI + 0.05*Math.cos(theta); 
          degreeII = degreeII + 0.05*Math.cos(theta); 
          
          branchI.moveTo(startX,startY); 
          branchII.moveTo(startX,startY); 
          
          branchI.setEnd(startX+branchLength*1.25*Math.cos(degreeI),startY+branchLength*1.25*Math.sin(degreeI));
          branchII.setEnd(startX+branchLength*1.25*Math.cos(degreeII),startY+branchLength*1.25*Math.sin(degreeII));
          
          
          
          //adjust these values to get better performance 
          theta += 0.1;
          
          pause(100); 
          
        }
        */
        
    }
    /*
    public Location thisEnd()
    {
        Location End = new Location(startX+branchLength*1.25*Math.cos(degreeI), startY+branchLength*1.25*Math.sin(degreeI));
        return End;
    }
    public void moveTo(Location point)
    {
        this.moveTo(point); 
    }
    */
   
       public void move(double dx,double dy)
    {
        branchI.move(dx,dy); 
        branchII.move(dx,dy); 
        incrementXI += dx;
        incrementYI += dy;
        incrementXII += dx;
        incrementYII += dy;
        
    }



}
