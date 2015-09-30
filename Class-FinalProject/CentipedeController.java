
import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CentipedeController extends WindowController implements KeyListener {

    // The height of the menu bar at the top of the window plus the height
    // of a JLabel put in the south of the content pane.  You can use this
    // constant to make a window big enough to hold a canvas of the desired
    // size and the menu and JLabel.  This constant was created to work
    // four our solution on Steve's Mac laptop --- you may wish to adjust 
    // it if you are on a PC or decide to lay out the window differently.
    private static final int MENU_AND_HEIGHT = 54;

    //Number of shrooms to draw in field
    private static final int SHROOM_COUNT = 40;
    
    private static final int ZAPPER_X = (Field.CANVAS_WIDTH-Zapper.ZAPPER_SIZE)/2;
    private static final int ZAPPER_Y = Field.CANVAS_HEIGHT-Zapper.ZAPPER_SIZE;
    
    private static final int BUG_LENGTH = 10; 
    
    
    private ScoreKeeper score;
    
    //Field Variables
    private Field field;

    //Zapper Variables
    private Zapper zapper;
    private Location zapperLocation;
    
    private Centipede centipede;

    public void begin() {
        
        // Make window large enough to hold canvas of desired size, the menu, and a JLabel
        this.setSize(Field.CANVAS_WIDTH, Field.CANVAS_HEIGHT + MENU_AND_HEIGHT);
        
        // Create the black background
        new FilledRect(0, 0, Field.CANVAS_WIDTH, Field.CANVAS_HEIGHT, canvas).setColor(Color.BLACK);
        Container container = getContentPane();

        //Create playing field
        Image shroomImage = getImage("shroom3.gif");
        field = new Field(shroomImage, SHROOM_COUNT, canvas); 
                
        //Create the scorekeeper 
        JLabel messageLabel = new JLabel(); 
        Container content = getContentPane(); 
        content.add(messageLabel, BorderLayout.SOUTH); 
        score = new ScoreKeeper(messageLabel); 
        
        //Creating the zapper
        zapperLocation = new Location (ZAPPER_X, ZAPPER_Y);
        zapper = new Zapper(zapperLocation, score, canvas);

        //Create the centipede
        Image segImage = getImage("segment1.gif"); 
        centipede = new Centipede(segImage,BUG_LENGTH,zapper,field,score,canvas); 

        //Set up key event handlers:
        canvas.addKeyListener(this);
        container.addKeyListener(this);
        container.requestFocus();
        container.validate();
    }

    // Keyboard Events
    public void keyPressed(KeyEvent event) {
        if(!score.isGameOver()){
        //Left
        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            zapper.left(); 
        } 
        //Right
        else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            zapper.right(); 
        }
        //Spacebar
        else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            zapper.shoot(field,centipede); 
        }
    }
    }

    // Part of KeyListener, but we don't need 
    // to do anything here
    public void keyTyped(KeyEvent evt) {  }

    public void keyReleased(KeyEvent evt) {  }

}
