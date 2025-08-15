package snakegamepackage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Font;
public class Board extends JPanel implements ActionListener
{
    private int dots;
    private Image apple,dot,head;
    private final int dot_size=10;
    private final int all_dots=900;
    private final int x[]=new int[all_dots];
    private final int y[]=new int[all_dots];
    private int appleX, appleY;
    private Timer timer;
    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;
    private boolean inGame=true;
    public Board()
    {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);
        loadImages();
        initGame();
    }
    public final void loadImages()
    {
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("snakegamepackage\\icons\\apple.png"));
        apple=i1.getImage();
        ImageIcon i2= new ImageIcon(ClassLoader.getSystemResource("snakegamepackage\\icons\\dot.png"));
        dot=i2.getImage();
        ImageIcon i3= new ImageIcon(ClassLoader.getSystemResource("snakegamepackage\\icons\\head.png"));
        head=i3.getImage();
    }
    public final void initGame()
    {
        dots=3;
        for(int i=0;i<dots;i++)
        {
            y[i]=50;
            x[i]=50-(i*dot_size);
        }
        locateApple();

        timer=new Timer(140, this);
        timer.start();
    }
    public void locateApple()
    {
        appleX = (int) (Math.random() * 29) * dot_size;
        appleY = (int) (Math.random() * 29) * dot_size;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g)
    {
        if(inGame)
        {
            g.drawImage(apple, appleX, appleY, this);
            for(int i=0;i<dots;i++)
            {
                if(i==0)
                    g.drawImage(head,x[i],y[i],this);
                else
                    g.drawImage(dot,x[i],y[i],this);
            }
            Toolkit.getDefaultToolkit().sync();  
        }
        else
            gameOver(g);
    }
    public void gameOver(Graphics g)
    {
        String msg="Game Over!";
        Font font = new Font("Arial",Font.BOLD,12);
        FontMetrics metrics=g.getFontMetrics(font);
        g.drawString(msg,((300-metrics.stringWidth(msg))/2), 150);
        g.setColor(Color.WHITE);
        g.setFont(font);
    }
    public void move()
    {
        for(int i=dots;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection)
            x[0]-=dot_size;
        if(rightDirection)
            x[0]+=dot_size;
        if(upDirection)
            y[0]-=dot_size;
        if(downDirection)
            y[0]+=dot_size;
    }
    public void checkApple()
    {
        if((x[0]==appleX) && (y[0]==appleY))
        {
            dots++;
            locateApple();
        }
    }
    public void checkCollision()
    {
        for(int i=1;i<dots;i++)
        {
            if((i>4) && (x[0]==x[i]) && (y[0]==y[i]))
                inGame=false;
        }
        if(y[0]>=300 || y[0]<=0 || x[0]>=300 || x[0]<=0)
            inGame=false;
        if(!inGame)
        {
            timer.stop();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
        {
            if(inGame)
            {
                checkApple();
                checkCollision();
                move();
            }
            repaint();
        }
    public class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            int key=e.getKeyCode();
            if((key==KeyEvent.VK_LEFT) && (!rightDirection))
            {
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if((key==KeyEvent.VK_RIGHT) && (!leftDirection))
            {
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if((key==KeyEvent.VK_UP) && (!downDirection))
            {
                upDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
            if((key==KeyEvent.VK_DOWN) && (!upDirection))
            {
                downDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
        }
    }
}
