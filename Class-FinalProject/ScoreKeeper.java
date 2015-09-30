import objectdraw.*;
import javax.swing.*;
import java.awt.*;


public class ScoreKeeper {

    //check gameover 
    public boolean gameOver = false; 

    //score 
    int score = 0;
    
    //reference to message label 
    JLabel messageLabel; 
    
public ScoreKeeper(JLabel messageLabel){
    this.messageLabel = messageLabel; 
    messageLabel.setText("Score:"); 
}

public void increment(int incr){
    score+=incr; 
    messageLabel.setText("Score: " + score); 
}

public void endGame(){
    messageLabel.setText("GAME OVER " + "Final Score:" + score); 
    gameOver = true;
}

public boolean isGameOver(){
    return gameOver; 
}

}