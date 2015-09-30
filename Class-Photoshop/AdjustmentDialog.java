import javax.swing.*;
import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import cs134lib.*;

/*
The superclass for all the dialogs.  This creates the basic dialog box that appears whenever the user
clicks apply.  The subclasses differ by making changes to the protected function adjust pixels array 
 */
public class AdjustmentDialog extends JDialog implements ActionListener {

  // Location and size of dialog box
  private static final int LOCATION_X = 400;
  private static final int LOCATION_Y = 200;
  private static final int WIDTH = 250;
  private static final int HEIGHT = 140;

  // Buttons to accept or reject the change.
  private JButton okButton = new JButton("OK");
  private JButton cancelButton = new JButton("Cancel");
  
  // The image display in the canvas of the main window
  private VisibleImage display;

  // The Image representation of the displayed image prior to transforming it.
  private Image original;

  public AdjustmentDialog() {
    // configure the title, screen location, size, etc. of the dialog box
    this.setModal(true);
    this.setTitle(this.getClass().getName());
    this.setLocation(LOCATION_X,LOCATION_Y);
    this.setSize(WIDTH, HEIGHT);
    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

    // add the ok and cancel buttons to the dialog box
    Container contentPane = this.getContentPane();
    JPanel buttons = new JPanel();
    buttons.add(okButton);
    buttons.add(cancelButton);
    contentPane.add(buttons, BorderLayout.SOUTH);
    contentPane.validate();

    // listen for events on our buttons
    okButton.addActionListener(this);
    cancelButton.addActionListener(this);
  }

  /*
   * Apply the image transformation and ask user to accept/reject.  
   */
  public void applyAdjustment(VisibleImage theDisplay) {
    // remember the visible image we are to update, as well as the
    // image currently being shown.
    
    //display is the visible image, display.getImage() is the actual image 
    //note that applyAdjustment method automatically gets display image so we don't need to pass a parameter to 
    //adjustImage anymore 
    display = theDisplay;
    original = display.getImage(); 
    this.adjustImage();
    this.setVisible(true);
  }

  /*
   * Handle both button events.  Right now, we just close
   * the dialog box by setting it to not be visible.
   */
  public void actionPerformed(ActionEvent e) {
    //if the cancel button is selected keep original image 
    if(e.getSource()==cancelButton)
    display.setImage(original); 
    
    //always remove dialogue box after 
    this.setVisible(false);
  }

  /*
   * Applies the desired transformation to the image.  Make separate color arrays for each color value, r, g, b and apply
   * the adjustPixelArray method to each color spectrum 
   */
  protected void adjustImage() { 
     int[][] red = Images.imageToPixelArray(original,Images.RED_CHANNEL); 
     int[][] green = Images.imageToPixelArray(original,Images.GREEN_CHANNEL); 
     int[][] blue = Images.imageToPixelArray(original,Images.BLUE_CHANNEL); 
     
     Image newImage = Images.pixelArraysToImage(this.adjustPixelArray(red),this.adjustPixelArray(green),this.adjustPixelArray(blue)); 
     
     display.setImage(newImage); 
   
  }
  
  //varies between subclasses 
  protected int[][] adjustPixelArray(int[][] pixels) {

       return pixels;
     
  }

}
