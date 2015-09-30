import objectdraw.*;
import java.awt.*;
import java.util.Random; 
import java.util.ArrayList; 
import java.awt.Point; 
/*
 *   This is the source code for the algorithm you asked us to implement for extra credit.  Please ignore the other code. 
 *   Please note the paths are the lines.  
     */

public class TheProgram extends WindowController{
static Random random = new Random(); 
    int width = 1000; 
    int height = 1000; 
  
    int numNode = width*height; 
    boolean[][] isInsideChecker = new boolean[width][height]; 
    ArrayList<Association<IntPoint,IntPoint>> toConsider = new ArrayList<Association<IntPoint,IntPoint>>(); 
    public void begin(){
        toConsider.add(new Association<IntPoint,IntPoint>(new IntPoint(0,0), new IntPoint(0,0))); 
        while(!toConsider.isEmpty()){
            System.out.println("running");
            int randomIndex = random.nextInt(toConsider.size()); 
            IntPoint current = toConsider.get(randomIndex).elem1; 
            if(!isInsideChecker[current.x][current.y]){
                addNeighbors(current); 
                isInsideChecker[current.x][current.y] = true; 
                IntPoint lastPoint = toConsider.get(randomIndex).elem2; 
                new Line(10+current.x*10,10+current.y*10,10+lastPoint.x*10,10+lastPoint.y*10,canvas);
            }
            toConsider.remove( randomIndex ); 
        }
    }
    
    public void addNeighbors(IntPoint point){
        //add left neighbor 
        if(point.x!=0){
            toConsider.add(new Association<IntPoint,IntPoint>(new IntPoint(point.x-1,point.y),point)); 
        }
        if(point.y!=0){
            toConsider.add(new Association<IntPoint,IntPoint>(new IntPoint(point.x,point.y-1),point)); 
        }
        if(point.x!=width-1){
            toConsider.add(new Association<IntPoint,IntPoint>(new IntPoint(point.x+1,point.y),point)); 
        }
        if(point.y!=height-1){
            toConsider.add(new Association<IntPoint,IntPoint>(new IntPoint(point.x,point.y+1),point)); 
        }
    }
}

class IntPoint{
    public int x, y;
    public IntPoint(int x, int y){
        this.x = x; this.y = y; 
    }
}

class Association<K,E>{
    public K elem1; 
    public E elem2; 
    public Association(K k, E e){
        this.elem1 = k; this.elem2 = e; 
    }
}
