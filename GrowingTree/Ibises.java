import objectdraw.*;
import java.awt.*;
import java.util.Vector; 


/**
 *   CS 134 Laboratory 1 / Section 5
 *   Kyle Cheng 
 *   9/10/12
 */
public class Ibises extends WindowController {
    
    public final static double PI = 3.141593; 

    
    //previous point (for dragging)
    private Location previousLocation;
    private Location dragPoint; 
    
   // private int currentLength = 100; 
    
    //reference for tree to know number of branches 
    private int numBranches = 0; 
    
    //Random generators for length of tree, max branches, and tree trunk straightness 
    private RandomIntGenerator  initialLengthRand = new RandomIntGenerator(25,40);
    private RandomIntGenerator  maxBranchRand = new RandomIntGenerator(6,9);
    private RandomIntGenerator currentXRand = new RandomIntGenerator(-10,10); 
    
    //Color 
    private Color BROWN = new Color(205,175,149); 
    
    //Store number of trees ///active? 
    private int treeNum = 0; 

    //Vector for storing the trees 
    //initial storage capacity needs to be low(1) to prevent 
        //"ArrayIndexOutOfBoundsException" if the user tries to move the tree
        //when it is first being created (with 1 to 2 segments only grown so far) 
    private Vector <TreeInterface> branchVector = new Vector<TreeInterface>(1,1); 
    
    //variables for tree movement 
    public double dx; 
    public double dy; 
    
    //determining if the vector is still growing (ie, the tree is still growing) 
    int lastVectorSize;

    public void begin() {

        this.resize(1800,1000);

        new TreeV(900,850,numBranches,50,PI/2,BROWN,8,branchVector,canvas); 
        numBranches = 0; //reset maxbranch counter for future trees 
        
    }

    public void onMousePress(Location point) {
        lastVectorSize = branchVector.size(); 
        previousLocation = point; 

    }
    
    public void onMouseClick(Location point) {
        new TreeTall(point.getX(),point.getY(),numBranches,branchVector,canvas); 
        
    }
    
    public void onMouseMove(Location point) {
       

       
    }
    
    public void onMouseDrag(Location point) {
        dragPoint = point; 

        dx = dragPoint.getX()-previousLocation.getX(); 
        dy = dragPoint.getY()-previousLocation.getY(); 

          for(int i = 0; i<branchVector.capacity(); i++){
    
            branchVector.get(i).move(dx,dy); 
            
            previousLocation = point; 
          }

         //System.out.println("lVs" + lastVectorSize); 
        //System.out.println("nowSize" + branchVector.size()); 
        
      
        
    }

    

    

}