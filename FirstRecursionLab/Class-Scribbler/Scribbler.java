import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import objectdraw.*;

/*
 * A very simple drawing program.
 */
public class Scribbler extends WindowController
implements ActionListener {

  // User modes for what operation is selected.
  // We are using ints rather than boolean to allow for extension to
  // other modes
  private static final int DRAWING = 1;
  private static final int MOVING = 2;
  private static final int COLORING = 3;

  // the current scribble
  private ScribbleInterface currentScribble;
  private ScribbleInterface selectedScribble; 

  // the collection of scribbles
  private ScribbleCollectionInterface scribbles;

  // stores last point for drawing or dragging
  private Location lastPoint;

  // whether the most recent scribble has been selected for moving
  private boolean draggingScribble;

  // buttons that allow user to select modes
  private JButton setDraw, setMove, setErase, setColor;

  // Choice menu to select color
  private JComboBox chooseColor;

  // new color for scribble
  private Color newColor;

  // label indicating current mode
  private JLabel modeLabel;

  // the current mode -- drawing mode by default
  private int chosenAction = DRAWING;
  
  //
  //ScribbleInterface currentScribbles; 

  // create and hook up the user interface components
  public void begin() {


    //Adding GUIs 
    setDraw = new JButton("Draw");
    setMove = new JButton("Move");
    setColor = new JButton("Color");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(setDraw);
    buttonPanel.add(setMove);
    buttonPanel.add(setColor);

    chooseColor = new JComboBox();
    chooseColor.addItem("red");
    chooseColor.addItem("blue");
    chooseColor.addItem("green");
    chooseColor.addItem("yellow");

    setErase = new JButton("Erase last");
    JPanel choicePanel = new JPanel();
    choicePanel.add(setErase);
    choicePanel.add(chooseColor);

    JPanel controlPanel = new JPanel(new GridLayout(3,1));
    modeLabel = new JLabel("Current mode: drawing");
    controlPanel.add(modeLabel);
    controlPanel.add(buttonPanel);
    controlPanel.add(choicePanel);

    Container contentPane = this.getContentPane();
    contentPane.add(controlPanel, BorderLayout.SOUTH);

    // add listeners
    setDraw.addActionListener(this);
    setMove.addActionListener(this);
    setErase.addActionListener(this);
    setColor.addActionListener(this);

    // make the current scribble empty
    currentScribble = new EmptyScribble();

    this.validate();
    
    //initial part of scribbles stack  
    scribbles = new EmptyScribbleCollection();
  }

  /*
   * If in drawing mode then start with empty scribble.
   * If in moving mode then prepare to move.
   */
  //pass a scribble into this method to change its clor to selected Item 
  public void setColor(ScribbleInterface scribble)
  {
      //pass a reference for selectedItem to make code readable 
          String color = (String) chooseColor.getSelectedItem(); 
          
          //based on string result, set the parameter(scribble) to a certain color 
          if (color == "red")
          scribble.setColor(Color.RED); 
         
          
          else if (color == "blue")
          scribble.setColor(Color.BLUE); 
         
          
          else if (color == "green")
          scribble.setColor(Color.GREEN); 
          
          
          else if (color == "yellow")
          scribble.setColor(Color.YELLOW); 
          
      
      }
      
//when the mouse is pressed, determine which action has been decided (drawing, moving, etc) and act accordingly
//based on action chosen 
  public void onMousePress(Location point) {
      //this information pased to the variable is used multiple times, hence the reason that 
      //the instance variable is declared here outside the MOVING and COLORING conditions 
      //which both use it 
      
      selectedScribble = scribbles.scribbleSelected(point);
    if (chosenAction == DRAWING) {
      // start with an empty scribble for drawing
      currentScribble = new EmptyScribble();
    } 
    else if (chosenAction == MOVING) {
      //check if we are actually moving something, if so draggingScribble is true 
      if(selectedScribble!=null)
      draggingScribble = selectedScribble.contains(point);
      //allows dragging if true 
    } else if (chosenAction == COLORING) {
      //conditional to prevent null pointer, empty scribble returns null 
      if(selectedScribble!=null)
      setColor(selectedScribble);
    }
   // remember point of press for drawing or moving
   lastPoint = point;
}
  /*
   * If in drawing mode, add a new segment to scribble.
   * If in moving mode then move it.
   */
  public void onMouseDrag(Location point) {
    if (chosenAction == DRAWING) {
      // add new line segment to current scribble
      Line newSegment = new Line(lastPoint, point, canvas);
      currentScribble = new NonEmptyScribble(newSegment, currentScribble);
    } else if (chosenAction == MOVING) {
      if (draggingScribble) {
        // move current scribble
        selectedScribble.move(point.getX()-lastPoint.getX(),point.getY()-lastPoint.getY()); 
      }
    }
    // update for next move or draw
    lastPoint = point;
  }
    //Create a new scribbles, pass reference to currentScribble
    //Only create new scribble if we have indeed been drawing them, otherwise, if we release
    //mouse for moving or coloring, no new scribble should be created 
  public void onMouseRelease(Location point) {
      
    //whenever the user is drawing and unclicks(which means they have finished drawing) create a new 
    //scribbleCollection that references the last line of this newly created scribble 
    if(chosenAction==DRAWING)scribbles = new NonEmptyScribbleCollection(currentScribble,scribbles); 
    
    //after we let go of mouse, we should no longer be able to move objects 
    //without this, the user can drag then rapidly unclick then drag again and still be able to
    //move the scribbles 
    draggingScribble = false; 
    
  }

  /*
   * Set mode according to JButton pressed.
   */
  //based on the button pressed, change the mode selected 
  //if the erase button is selected, erase most recent element 
  public void actionPerformed(ActionEvent e) {
      
    
    
    if (e.getSource() == setDraw) {
      chosenAction = DRAWING;
      modeLabel.setText("Current mode: drawing");
    } else if (e.getSource() == setMove) {
      chosenAction = MOVING;
      modeLabel.setText("Current mode: moving");
    } else if (e.getSource() == setColor) {
      chosenAction = COLORING; 
      modeLabel.setText("Current mode: coloring"); 
    } else if (e.getSource() == setErase) {

     //remove the most recently added scribble 
     if(currentScribble!=null)currentScribble.removeFromCanvas(); 
     
     //move down the "chain" of scribbleCollection to previous scribble 
     if(scribbles.getFirst()!=null) //determine if we are referring to empty or non empty collection
     scribbles = scribbles.getRest(); 
     
     //empty scribbles is useful in that it returns null, thereby allowing us to determine when we 
     //have reached the last of the scribbles 
     
     //set currentScribble (what will be deleted next) to new scribble get first 
     currentScribble = scribbles.getFirst(); 
    }
  }

}
