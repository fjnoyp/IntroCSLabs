import javax.swing.*;
import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import cs134lib.*;
//flips 

public class FlipDialog extends AdjustmentDialog {

//Due to inheritance, flipDialog has all the features of invertDialog 
//we define adjustPixelArray here to tell Java how this class is different than its superclass
 
//flip the pixels  
protected int[][] adjustPixelArray(int[][] pixels) {
    
     for(int x = 0; x<pixels.length/2; x++)
      for(int y = 0; y<pixels[0].length; y++){
      int temp = pixels[x][y]; 
      pixels[x][y] = pixels[(pixels.length-1-x)][y]; 
      pixels[(pixels.length-1-x)][y] = temp;
      }
      
       return pixels;
     
  }

}
