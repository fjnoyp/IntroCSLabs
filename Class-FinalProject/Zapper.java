
import objectdraw.*;
import java.awt.*;

public class Zapper {
    
    //Size of zapper
    public static final int ZAPPER_SIZE = 24;
    private static final int GUN_SIZE = ZAPPER_SIZE/8; 
    
    //Zapper step 
    private static final int ZAPPER_STEP = 10; 

    //Body of zapper
    private FilledRect body; 
    private FilledRect gun; 
    
    //Score reference
    public ScoreKeeper score; 
    
    //Canvas reference 
    private DrawingCanvas canvas; 
    
    //Zapper constructor class
    public Zapper(Location point, ScoreKeeper score, DrawingCanvas canvas)
    {
        //REVIEW ALL CLASSES FOR CORRECT INSTANCE VARIABLE PLACEMENT 
        
        //Draws the zapper 
        body = new FilledRect(point, ZAPPER_SIZE, ZAPPER_SIZE, canvas);
        body.setColor(Color.RED);
        
        //Draw gun 
        gun = new FilledRect(body.getX()+(ZAPPER_SIZE-GUN_SIZE)/2,body.getY()-GUN_SIZE,GUN_SIZE,GUN_SIZE,canvas); 
        gun.setColor(Color.RED); 
        
        //score reference 
        this.score = score; 
        this.canvas = canvas; 
        
    }
    
    public void left(){
       if(body.getX()>0){
           body.move(-ZAPPER_STEP,0);
           gun.move(-ZAPPER_STEP,0);
        }       
    }
    
    public void right(){
        if(body.getX()+ZAPPER_SIZE<canvas.getWidth()){
            body.move(ZAPPER_STEP,0); 
            gun.move(ZAPPER_STEP,0); 
        }
    }
    
    public void shoot(Field field, Centipede centipede){
        Location gunEnd = new Location(gun.getX()+GUN_SIZE/2,gun.getY()); 
        new Missile(gunEnd,field,score,centipede,canvas); 
        
    }
    
    public boolean overlaps(VisibleImage image){
            return (body.overlaps(image) || gun.overlaps(image));
    }

   
}
