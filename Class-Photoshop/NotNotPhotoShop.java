import cs134lib.*;
import objectdraw.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/*
The lab.  There is an extra feature for having a puzzle game.  The code for this feature is within the puzzleDialog 
 */
public class NotNotPhotoShop extends WindowController implements ActionListener {

    // Size of window when created
    private static final int WINDOW_WIDTH = 640;
    private static final int WINDOW_HEIGHT = 560;

    // Buttons to open a new image file and take a picture
    //   with the camera
    private JButton openFileButton;
    private JButton snapshotButton;
    //private JButton invertButton; 
    //private JButton flipButton; 
    private JButton applyButton; 
    private JComboBox actionChoice; 

    // The image we are editing
    private VisibleImage display;

    //The dialog boxes
    private InvertDialog invert = new InvertDialog();
    private FlipDialog flip = new FlipDialog(); 
    private BrightnessDialog brightness = new BrightnessDialog(); 
    private PosterizeDialog posterize = new PosterizeDialog(); 

    private PuzzleDialog puzzle = new PuzzleDialog(canvas); 

    // visible image puzzle piece that we can move 
    private VisibleImage puzzlePiece; 

    //previous point for puzzle moving 
    private Location previousPoint; 

    //element for letting the user swap puzzle pieces 
    private boolean previousPuzzle = false;
    private VisibleImage piece1, piece2; 


    /*
     * Create the GUI components and load the original image from the harddrive. 
     * You will change this method when adding new componenets to the interface.
     */
    public void begin() {

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Create the buttons and add them to the window 
        openFileButton = new JButton("Open");
        snapshotButton = new JButton("Take Picture");
        applyButton = new JButton("Apply"); 

        //JComboBox for selecting different actions 
        actionChoice = new JComboBox(); 
        actionChoice.addItem("puzzle"); 
        actionChoice.addItem("invert"); 
        actionChoice.addItem("flip"); 
        actionChoice.addItem("brightness"); 
        actionChoice.addItem("posterize"); 

        JPanel bottomPanel = new JPanel(new GridLayout(2,2));
        bottomPanel.add(openFileButton);
        bottomPanel.add(snapshotButton);

        bottomPanel.add(actionChoice);
        bottomPanel.add(applyButton); 

        // Add the components to the window
        Container contentPane = getContentPane();
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        contentPane.validate();

        // Make me listen for button clicks
        snapshotButton.addActionListener(this);
        openFileButton.addActionListener(this);
        applyButton.addActionListener(this); 

        // load the beach scene at startup
        display = new VisibleImage(getImage("gray-1.jpg"), 0, 0, canvas);

    }
    /*
     * Handle events from the buttons.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openFileButton) {
            //Create chooser to get files 
            JFileChooser chooser = new JFileChooser(); 

            //display new image if an actual image is selected 
            if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getAbsolutePath(); 
                Image newImage = Toolkit.getDefaultToolkit().getImage(name); 
                display.setImage(newImage); 
            }

        } else if (e.getSource() == snapshotButton) {
            //Camera snapshot, get image from the camera 
            Camera camera = new Camera(); 
            Image newImage = camera.getImage(); 
            display.setImage(newImage);

            //Based on the selected actionChoice, call a different adjustment class on the display image 
        } else if (e.getSource() == applyButton)  {
            if(actionChoice.getSelectedItem() == "flip")
                flip.applyAdjustment(display); 

            else if(actionChoice.getSelectedItem() == "invert")
                invert.applyAdjustment(display);

            else if(actionChoice.getSelectedItem() == "brightness")
                brightness.applyAdjustment(display); 

            else if (actionChoice.getSelectedItem() == "posterize")
                posterize.applyAdjustment(display); 

            else if (actionChoice.getSelectedItem() == "puzzle")
                puzzle.createPuzzle(display); 

        }
    }

    //if the display is hidden, that means that the puzzle game has been activated.  If so, get the puzzlePiece
    //that the mouse contains 
    public void onMousePress(Location point){

        if(display.isHidden())puzzlePiece = puzzle.imageSelected(point); 
        previousPoint = point; 

    }

    //if the puzzle piece is not null, move it 
    public void onMouseDrag(Location point){

        if(puzzlePiece!=null){
            double dx = point.getX() - previousPoint.getX(); 
            double dy = point.getY() - previousPoint.getY(); 
            puzzlePiece.move(dx,dy); 
            previousPoint = point; 
        }

    }

    //if the display is hidden, that means that the puzzle game has been activated.
    //if so, get the piece that the mouse has clicked on 
    //if the user clicks again, swap the locations of the two pieces that have been clicked on 
    public void onMouseClick(Location point){
    if(display.isHidden()){
        //if there is no first piece selected get the first piece 
        if(piece1==null){
            piece1 = puzzle.imageSelected(point);

            previousPuzzle = (piece1 != null);
        }
        
        //if we click again, get the second piece and swap their locations 
        else {
            piece2 = puzzle.imageSelected(point); 

            if(piece2 != null){
                Location tempLoc = piece2.getLocation(); 
                piece2.moveTo(piece1.getLocation()); 
                piece1.moveTo(tempLoc); 
                
                //reset for next swap 
                previousPuzzle = false; 
                piece1 = null; 
                piece2 = null;
            }

        }
    }
    }
}