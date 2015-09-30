import javax.swing.*;
import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import cs134lib.*;

/*
 * This dialog box opens up a non modal box.  Upon opening, this class chops up the display image 
 * into 100 images.  The user can interact with these images by clicking and dragging them and by double clicking 
 * to swap positions of clicked on images.  The user can move a slider and then click on randomize 
 * to have all the images swap their positions to varying degrees (based on the slider's current value) 
 */


public class PuzzleDialog extends JDialog implements ActionListener, ChangeListener
{

    // Location and size of dialog box
    private static final int LOCATION_X = 400;
    private static final int LOCATION_Y = 200;
    private static final int WIDTH = 450;
    private static final int HEIGHT = 140;

//revert back to original button 
    private JButton cancelButton = new JButton("Revert Back");

    // The image display in the canvas of the main window
    private VisibleImage display;

    // The Image representation of the displayed image prior to transforming it.
    private Image original;

    //we need a reference to the canvas 
    private DrawingCanvas canvas; 

    //array to hold visible images created from cutting up the original image 
    private VisibleImage[] imageBlocks = new VisibleImage[100];

    //elements for randomizing the locations of the visible images 
    private JSlider randomization; 
    private JButton label; 
    
    //helps choose a random element in the visible images array 
    private RandomIntGenerator chooser = new RandomIntGenerator(0,99); 

    //Create GUI components of the dialog box 
    public PuzzleDialog(DrawingCanvas canvasRef) {

        // configure the title, screen location, size, etc. of the dialog box : 
        this.setTitle(this.getClass().getName());
        this.setLocation(LOCATION_X,LOCATION_Y);
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // add the ok and cancel buttons to the dialog box
        Container contentPane = this.getContentPane();
        JPanel buttons = new JPanel();
        buttons.add(cancelButton);
        contentPane.add(buttons, BorderLayout.SOUTH);
        contentPane.validate();

        // listen for events on our buttons
        cancelButton.addActionListener(this);

        //provide reference to the stage 
        canvas = canvasRef; 

        //elements for randomizing locations of puzzle pieces :
        randomization = new JSlider(JSlider.HORIZONTAL,0,200,50); 

        label = new JButton("Randomize to: ");
        
        //local variable for insructions
        JLabel instructions = new JLabel("Dragmouse to move image, click on two images to swap their positions"); 

        JPanel topContents = new JPanel(new GridLayout(3,1)); 
        topContents.add(label); 
        topContents.add(randomization); 
        topContents.add(instructions); 

        Container contentPanes = this.getContentPane();
        contentPanes.add(topContents, BorderLayout.NORTH); 
        contentPanes.validate(); 

        randomization.addChangeListener(this); 
        label.addActionListener(this); 
    }

    /*
    Create a puzzle out of the visible image 
     */
    public void createPuzzle(VisibleImage theDisplay) {
        // remember the visible image we are to update, as well as the
        // image currently being shown.

        //display is the visible image, display.getImage() is the actual image 
        //note that applyAdjustment method automatically gets display image so we don't need to pass a parameter to 
        //adjustImage anymore 
        display = theDisplay;
        display.hide(); 
        
        //extract image from display to divide it into puzzle pieces 
        original = display.getImage(); 
        
        //call the method to create puzzle pieces out of the display image 
        this.createPuzzlePieces();
        this.setVisible(true);
    }

    //conver the image into a pixel array and then call divideImage method on that array 
    public void createPuzzlePieces() {

        int[][] pixelArray = Images.imageToPixelArray(original);    
        divideImage(pixelArray); 

    }
    
    //This method creates 100 individual visible images out of the display image.  These 100 images are accessible
    //through the imageBlocks array 
    protected void divideImage(int[][] pixels){
        
        //Andrea Danyluck taught me how to manage the starting values and end values of the x and y for loops 
        //contained below 
        
        //These variables keep track of the positions in the array that holds the pixels in one of the 
        //100 image blocks 
        int tempX; 
        int tempY;   
        
        //iterate 100 times thereby creating 100 visible images 
        //each visible image is 64 by 48 pixels, 100 of them make up the entire original display image 
        for(int i = 0; i<100; i++)
        {

            //for every iteration, create a new array that holds the pixels for the current image 
            int[][] array = new int[64][48];
            
            //reset x for every iteration 
            tempX = 0; 

            //change the starting and end limits of the x for loop 
            //this for loop goes through all the x pixels of the new visible image
                   //we move through the display pixel array by going from left to right 10 times for each row and 100 times in total
                   //i%10 keeps "i" from 0 to 9, our start position for creatin each array 
            for(int x = 64*(i%10); x<64*(i%10)+64; x++){
                
                //reset y for each for loop y iteration 
                tempY = 0; 

                //due to the GUI panels, the actual y dimension of the pixel image is 480 and not 560 (stageWidth)
                for(int y = 48*(i/10); y<48*(i/10)+48; y++){
                    
                    //tempX and tempY must always go from 0 to 64 and 0 to 48 and these pixels are set to specific pixels 
                    //in the pixel array of the display image 
                    array[tempX][tempY] = pixels[x][y];
                    tempY++; 

                }

                tempX ++; 
            }
            
            //after we create each new box, turn it into a visible image and add it into the visible images array 
            Image tempImage; 
            tempImage = Images.pixelArrayToImage(array);  
            VisibleImage tempVisImage = new VisibleImage(tempImage,64*(i%10),48*(i/10),canvas);
            imageBlocks[i] = tempVisImage; 

        }

        //array length of 64, array[63] is the last element 
        //0,1,2,3 ... length-1 

    }

    //returns the visible image that contains the point 
    public VisibleImage imageSelected(Location point)
    {
   
        for(int i = 0; i<100; i++)
        {
            if(imageBlocks[i].contains(point)){

                imageBlocks[i].sendToFront(); 

                //visually the block is sent to front but we need to change its location in the array 
                //so that code-wise this block is in front 

                //store element we are moving to front 
                VisibleImage tempImage = imageBlocks[i]; 

                //move through elements in front of stored element and move them up a position 
                int k = i; 
                for(; k>0; k--)
                {
                    imageBlocks[k] = imageBlocks[k-1]; 
                }

                //the first element of the array is now the original imageBlocks[i] 
                imageBlocks[0] = tempImage; 

                return tempImage; 

            }

        }

        return null; 

    }

    /*
     * Handle both button events.  Right now, we just close
     * the dialog box by setting it to not be visible.
     */
    public void actionPerformed(ActionEvent e) {
        //if the cancel button is selected keep original image 
        if(e.getSource()==cancelButton){
            for(int i = 0; i<100; i++) imageBlocks[i].removeFromCanvas(); 
            display.show(); 
            this.setVisible(false);
        }
        
        //If we click on the randomize button, make the images swap their locations with another image's location
        //for the value of the randomization slider bar 
        if(e.getSource()==label){
            
            //get two random images that are part of the visible images array and have them swap locations 
            for(int i = 0; i<randomization.getValue(); i++){
                
                VisibleImage temp = imageBlocks[chooser.nextValue()];
                VisibleImage temp2 = imageBlocks[chooser.nextValue()];
                
                Location tempLoc = temp2.getLocation(); 
                
                temp2.moveTo(temp.getLocation()); 
                temp.moveTo(tempLoc); 
                
                //is there a better way to do this? 
            }
            
            
        }

        
    }
    
    //change the display when the slider is moved
    public void stateChanged(ChangeEvent e) {
        label.setText("Randomize to: " + randomization.getValue()); 
    }

}
