import javax.swing.*;
import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import cs134lib.*;

/*
posterizes the image 
 */
public class PosterizeDialog extends AdjustmentDialog implements ChangeListener
{

    private JSlider levels; 
    private JLabel label; 
    
    //creates the label and slider for this specific dialog box 
  public PosterizeDialog() {
  
      levels = new JSlider(JSlider.HORIZONTAL,2,256,50); 
      
      label = new JLabel("Levels:",JLabel.CENTER); 

      JPanel topContents = new JPanel(new GridLayout(2,1)); 
      topContents.add(label); 
      topContents.add(levels); 
      
      Container contentPanes = this.getContentPane();
      contentPanes.add(topContents, BorderLayout.NORTH); 
      contentPanes.validate(); 
      
      levels.addChangeListener(this); 

      
  }

  //posterize the image based on slider values 
  protected int[][] adjustPixelArray(int[][] pixels) {
      
     int bandWidth = 256/levels.getValue();
     
     for(int x = 0; x<pixels.length; x++)
      for(int y = 0; y<pixels[0].length; y++){

          pixels[x][y] = (pixels[x][y]/bandWidth)*bandWidth; 
      }
      
       return pixels;
     
  }
  
  //whenever the slider value changes, change the image 
  public void stateChanged(ChangeEvent e)
  {
      this.adjustImage(); 
      label.setText("Levels: " + levels.getValue()); 
      
  }

}