import javax.swing.*;
import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import cs134lib.*;

//brightness changer 

public class BrightnessDialog extends AdjustmentDialog implements ChangeListener
{

    private JSlider percent; 
    private JLabel label; 
  
    //create the sliders and jlabel that are unique for this dialog box 
  public BrightnessDialog() {
  
      percent = new JSlider(JSlider.HORIZONTAL,0,200,50); 
      
      label = new JLabel("Brightness:",JLabel.CENTER); 

      JPanel topContents = new JPanel(new GridLayout(2,1)); 
      topContents.add(label); 
      topContents.add(percent); 
      
      Container contentPanes = this.getContentPane();
      contentPanes.add(topContents, BorderLayout.NORTH); 
      contentPanes.validate(); 
      
      percent.addChangeListener(this); 

      
  }

  //change brightness based on slider values 
  protected int[][] adjustPixelArray(int[][] pixels) {

     //Multiply each pixel of the image by the percentage that it will be based on the new brightness 
     
     for(int x = 0; x<pixels.length; x++)
      for(int y = 0; y<pixels[0].length; y++){
          double sliderValue = pixels[x][y]*(percent.getValue()/100.0); 
          pixels[x][y] = (int) sliderValue;
      }
      
       return pixels;
     
  }
  
  //whenever the slider is changed, call the adjust image method on the display image again 
  public void stateChanged(ChangeEvent e)
  {
      this.adjustImage(); 
      label.setText("Brightness: " + percent.getValue() + "%"); 
      
  }

}

