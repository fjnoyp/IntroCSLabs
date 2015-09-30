import objectdraw.*;
/*
This is a collection of scribbles but only of the scribbles last points.  
*/

public class NonEmptyScribbleCollection implements ScribbleCollectionInterface {
    private ScribbleInterface recentScribble; 
    private ScribbleCollectionInterface restScribbles; 
  public NonEmptyScribbleCollection(ScribbleInterface addedScribble, ScribbleCollectionInterface theRest) {
    recentScribble = addedScribble; 
    restScribbles = theRest; 
  }

  // pre:
  // post: returns the scribble that contains the point;
  //    if none contain it, returns an empty scribble
  public ScribbleInterface scribbleSelected(Location point) {
      //if the scribble contains point, return the scribble
      if(recentScribble.contains(point)) return recentScribble;
      
      //else, call the same method for rest, thereby passing the method down the stack 
      else return restScribbles.scribbleSelected(point); 


  }

  // pre: 
  // post: returns the first scribble in the collection;
  //   returns empty scribble if the collection is empty.
  public ScribbleInterface getFirst() {
    return recentScribble; 
  }

  // pre:
  // post: returns the list of scribbles excluding the first.
  //   returns empty scribble collection if the list is empty.
  public ScribbleCollectionInterface getRest() {
    return restScribbles; 
  }
}