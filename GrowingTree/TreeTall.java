import objectdraw.*;
import java.awt.*;
import java.util.Vector; 

public class TreeTall extends ActiveObject 
{

    public final static double PI = 3.141593; 
    
    //references for run method 
    private double startX = 0; 
    private double startY = 0; 
    DrawingCanvas canvasRef; 
    private int numBranches;

    //Random generators for length of tree, max branches, and tree trunk straightness 
    private RandomIntGenerator  initialLengthRand = new RandomIntGenerator(25,40);
    private RandomIntGenerator  maxBranchRand = new RandomIntGenerator(6,8);
    private RandomIntGenerator currentXRand = new RandomIntGenerator(-10,10); 
    
    //Color 
    private Color BROWN = new Color(205,175,149); 
    
    //Vector reference 
    private Vector branchVector; 
    
    //Tree trunk
    Trunk trunk; 
    

    //need to change these values
    //starting Y of tree 
    public double currentHeight;
    //x position of tree
    public double currentX;
    
    public double currentXtemp; 
    public double currentYtemp; 

    public TreeTall(double x, double y, int branchNum, Vector branchVectorRef, DrawingCanvas canvas)
    {
        //give references their reference 
        startX = x;
        startY = y; 
        canvasRef = canvas; 
        numBranches = branchNum;
        branchVector = branchVectorRef; 
        
        //randomize the brown color of each new tree 
        BROWN = new Color(205+2*currentXRand.nextValue(),175+2*currentXRand.nextValue(),149+2*currentXRand.nextValue()); 
        
        start(); 
        
    }
    public void run()
    {
        
        //reuse maxBranchRand to determine tree's height 
        int treeHeight = maxBranchRand.nextValue()-2; 
        
        //starting Y of tree 
        currentHeight = startY; 
        
        //x position of tree
        currentX = startX; 
        
        //counter for while loop 
        int i = 0; 
        
        while(i<treeHeight){
            
            //Create a new part of the tree 
            //double currentXtemp = currentX + currentXRand.nextValue(); 
            
            //currentHeight always changes so startY cannot be used, startX is used above since x variation isn't as important
            //double currentYtemp = currentHeight - 50 + currentXRand.nextValue(); 
            //startY = currentHeight;
            
            //target points 
            double targetX = currentXRand.nextValue(); 
            double targetY = -50 + currentXRand.nextValue(); 
            
            
            
            
            currentXtemp = currentX + targetX;
            currentYtemp = currentHeight + targetY; 
            //startY = currentHeight; 
            
            //Draw tree to new part 
            trunk = new Trunk (currentX,currentHeight,currentX,currentHeight,this,canvasRef);
            branchVector.add(trunk); 
            
            //Animation section for tree growth 
                //counter for while loop 
                 int count = 1; 
                 
            
            while(count<=21){
                //trunk.setEnd(currentX,currentHeight);  //original 
                
                count ++; 

                //OBSOLETE
                //Slowly move trunk to its target location 
                //currentX += targetX/20; 
                //currentHeight += targetY/20; //original
                
                //please check trunk class for explanation of why //original hash tags present 
                

                trunk.setEnd(trunk.getEnd().getX() + targetX/20,trunk.getEnd().getY() + targetY/20);  

                pause(42); 
            }

        
            //Set current values to the new x and y section of the tree's end 
                //currentX = currentXtemp;
                //currentHeight = currentYtemp; //original 
                
                currentX = trunk.getEnd().getX(); 
                currentHeight = trunk.getEnd().getY();
            
            
            //reuse currentXRand num generator to randomize if tree limbs will appear 
            int branchProbability = currentXRand.nextValue(); 
            
            //50% chance on left or right side, 25% chance limb will grow 
            //value 7 is max branch parameter to treeV branches 
            if(branchProbability<0)
            {
                if(branchProbability<-5)
                {
                    new TreeV(currentX,currentHeight,numBranches,initialLengthRand.nextValue(),PI/6,BROWN,7,branchVector,canvasRef); 
                    numBranches = 0; //reset maxbranch counter 
                }
                
            }        
            else if(branchProbability>0)
            {
                if(branchProbability>5)
                {
                    new TreeV(currentX,currentHeight,numBranches,initialLengthRand.nextValue(),5*PI/6,BROWN,7,branchVector,canvasRef); 
                    numBranches = 0; //reset maxbranch counter 
                }
            }
            
            //Always have top branch of tree 
            if(i==treeHeight-1)
            {
                new TreeV(currentX,currentHeight,numBranches,initialLengthRand.nextValue(),PI/2,BROWN,7,branchVector,canvasRef); 
                numBranches = 0; //reset maxbranch counter 
            }
            
            
            //countdown for while loop 
            i++; 
       }
    }
    

    
    

}
