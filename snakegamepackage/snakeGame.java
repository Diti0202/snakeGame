package snakegamepackage;
import javax.swing.*;
public class snakeGame extends JFrame
{
    snakeGame()
    {
        super("Snake Game");
        add(new Board());///object of the board class
        pack();//refreshes the frames to update changes  
        setLocationRelativeTo(null);//so the frame is in the center of the window
        setResizable(false);
    }
    
    public static void main(String[] args)
    {
        new snakeGame().setVisible(true);
    }
}