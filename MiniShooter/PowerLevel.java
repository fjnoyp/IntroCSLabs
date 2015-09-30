import objectdraw.*;
import java.awt.*;

public class PowerLevel extends ActiveObject
{
    //Objects for projectile power 
    private FilledRect powerLevel; 
    private FramedRect powerLevelFrame; 
    private Text powerLabel; 
    
    //Time indicators 
    private int clickLength; 
    

    public PowerLevel(DrawingCanvas canvas)
    {

        //Objects for projectile power 
        //always create at 10,200
        //make it 30,10 size bar 
        powerLevel = new FilledRect(10,200,30,10,canvas); 
        
        this.start(); 
        


    }

    public void run()
    {
        //there cannot be a while powerLevel.x < some value because the power level bar can reach 
        //the max but 
        while(clickLength<60){
         
            clickLength ++; 
            
  
            powerLevel.moveTo(10,200 - (3 * clickLength)); 
 
            pause(50);
        }
    }
    
    public void removeFromCanvas()
    {
        powerLevel.removeFromCanvas();
 
    }
}

