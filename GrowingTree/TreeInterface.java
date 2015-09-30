//we need a vector that contains both lines (that represent tree trunks) and vtrees 
//the reason we cannot have a vector with only lines is that the vtree.move method
//includes code that also moves its increment points, thereby allowing for the tree 
//the grow even if we move it with the mouse drag (previously the tree would literally
//splatter the canvas with streaks of brown and green 
public interface TreeInterface
{

    void move(double dx, double dy); 
}
