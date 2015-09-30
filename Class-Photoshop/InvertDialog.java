

/*
 * Your Name and Lab Here...
 * 
 * A dialog box that takes a visible image and inverts the
 * shade of each pixel. 
 */
public class InvertDialog extends AdjustmentDialog {

    //invert the pixels 

  protected int[][] adjustPixelArray(int[][] pixels) {

     for(int x = 0; x<pixels.length; x++)
      for(int y = 0; y<pixels[0].length; y++) 
       pixels[x][y] = 255 - pixels[x][y]; 
       
       return pixels;
     
  }

}
