import java.awt.*;
import objectdraw.*;

public class Field {

    // The following two constants are declared to be "public"
    // so that other classes can refer to them.  We did this here
    // because the size of the canvas, the motion of the segments,
    // and other aspects of the grame are all dependent on how
    // wide and tall the mushrooms are.  See CentipedeController
    // for an example of how to refer to public static constants
    // in other classes.
    // dimensions of the canvas
    static public final int CANVAS_WIDTH = 400;
    static public final int CANVAS_HEIGHT = 512;

    // size of mushroom images
    static private final int SHROOM_SIZE = 16;  

    // dimensions of the mushroom array
    static private final int NUM_ROWS = CANVAS_HEIGHT / SHROOM_SIZE;
    static private final int NUM_COLS = CANVAS_WIDTH / SHROOM_SIZE;

    //Array holding all shrooms
    private VisibleImage[][] shrooms;

    //Random generators to show certain shrooms
    RandomIntGenerator xGenerator = new RandomIntGenerator(0,NUM_COLS-1);
    RandomIntGenerator yGenerator = new RandomIntGenerator(0,NUM_ROWS-3);

    //Mushroom Field Constructor 
    public Field(Image shroomImage, int shroomCount, DrawingCanvas aCanvas)
    {
        int counter = shroomCount;
        shrooms = new VisibleImage[NUM_COLS][NUM_ROWS];
        for (int i=0; i<NUM_COLS; i++)
        {
            for (int j=0; j<NUM_ROWS; j++)
            {
                //Create and hide shrooms
                shrooms[i][j] = new VisibleImage(shroomImage, SHROOM_SIZE*i, SHROOM_SIZE*j, aCanvas);
                shrooms[i][j].hide();
            }
        }

        //Sets certain the appropriate amount of shrooms to visible
        do
        {
            int x = xGenerator.nextValue();
            int y = yGenerator.nextValue();
            //If statement to make sure the same shroom isn't set visible twice
            if (shrooms[x][y].isHidden())
            {
                shrooms[x][y].show();
                counter--;
            }
        }while (counter>0);        
    }

    //Returns true if a point is contained in a visible shroom
    public boolean overlapsShroom(Location point)
    {
        //Stores the row and column the point is in
        if(point.getX()<CANVAS_WIDTH && point.getY()<CANVAS_HEIGHT && point.getY()>0){
            int column = getColumn(point.getX());
            int row = getRow(point.getY());
            if (shrooms[column][row].contains(point)&&!shrooms[column][row].isHidden())
            {
                return true;    
            }
        }

        
            return false;
    }

    //Shows a shroom at a given x / y position
    public void showShroom(double x, double y)
    {
        if(x<CANVAS_WIDTH && y<CANVAS_HEIGHT){
            int row = getRow(y);
            int column = getColumn(x);
            shrooms[column][row].show();
        }

    }
    //Hides a shroom at a given x / y position
    public void hideShroom(double x, double y)
    {
        if(x<CANVAS_WIDTH && y<CANVAS_HEIGHT){
            int row = getRow(y);
            int column = getColumn(x);
            shrooms[column][row].hide();
        }

    }

    // Helper methods to convert between position on the canvas and spot in the mushroom 2D array.    
    // Convert a y coordinate in pixels to the corresponding row in the mushroom array
    private int getRow(double y) {
        return (int)(y / SHROOM_SIZE);
    }
    // Convert a x coordinate in pixels to the corresponding column in the mushroom array
    private int getColumn(double x) {
        return (int)(x / SHROOM_SIZE);
    }

}
