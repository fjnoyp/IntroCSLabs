
import java.awt.*;
import objectdraw.*;

public class Missile extends ActiveObject {

    //missile object 
    private static final int MISSILE_LENGTH = 10; 
    private static final int MUSHROOM_HIT_SCORE = 1; 
    
    private Line missile; 
    
    static private final int PAUSE = 25;

    //reference variables for the missile 
    private Field field; 
    
    private Centipede centipede; 
    
    private ScoreKeeper score; 
    
    public Missile(Location point, Field field, ScoreKeeper score, Centipede centipede, DrawingCanvas canvas ) {
        
        //create missile 
        missile = new Line(point.getX(),point.getY(),point.getX(),point.getY()-MISSILE_LENGTH,canvas); 
        missile.setColor(Color.red); 
        
        //pass references 
        this.score = score; 
        this.centipede = centipede; 
        this.field = field; 
        
        this.start();
    }
    
    public void run() {
        while(missile.getStart().getY()>0 && !field.overlapsShroom(missile.getStart()) && !centipede.tryToHitSegment(missile.getStart())){

            missile.move(0,-MISSILE_LENGTH); 
            
            if(field.overlapsShroom(missile.getStart())){
               field.hideShroom(missile.getStart().getX(),missile.getStart().getY());
               score.increment(MUSHROOM_HIT_SCORE); 
            }

            pause(PAUSE); 
        }


        missile.removeFromCanvas();         
             

    }
    
}
