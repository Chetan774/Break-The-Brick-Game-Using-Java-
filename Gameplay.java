package com.company.BrickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import  javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener
{

    private boolean play =  false;
    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 0;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;

    private int ballXdir = -3;
    private int ballYdir = -4;

    private MapGenerator map;

    Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);//added keylistner to work with keylistners in class
        setFocusable(true);//
        setFocusTraversalKeysEnabled(false);//to not focus on transversal keys like tab, shift
        timer  = new Timer(delay,this);
        timer.start();

    }

    public void paint(Graphics g )
    {
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //drawing map
        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        if(totalBricks <= 0)
        {
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("You won Your score is  "+ score,260,300);

            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Press Enter to Restart :  ",230,350);

        }
        if(ballposY > 570)
        {
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Game Over, score : "+ score,190,300);

            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Press Enter to Restart :  ",230,350);

        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

         if(play)
        {
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;//intersection betn ball and paddle
            }
            A : for (int i = 0; i < map.map.length; i++)
            {
                for (int j = 0; j < map.map[0].length; j++)
                {
                    if(map.map[i][j] > 0)
                    {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect = rect;

                        if(brickRect.intersects(ballRect))
                        {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;


                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)
                            {
                                ballXdir  = -ballXdir;
                            }
                            else
                            {
                                ballYdir = -ballYdir;
                            }

                            break A;

                        }
                    }

                }

            }
            ballposX+=ballXdir; // for downward motion of ball
            ballposY-=ballYdir;
        }


        if(ballposX < 0)
        {
            ballXdir = -ballXdir;
        }
        if(ballposY < 0)
        {
            ballYdir = -ballYdir;
        }
        if(ballposX > 670)
        {
            ballXdir = -ballXdir;
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(playerX >= 600)
            {
              playerX = 600;
            }
            else
            {
                moveright();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {

            if(playerX < 10)
            {
                playerX = 10;
            }
            else
            {
                moveleft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(play)
            {
                play =  true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);
                repaint();
            }
        }
    }
    public void moveright()
    {
        play = true;//bcoz at start the play is set to false
        playerX+=30;// to move 30 pixels to right side
    }
    public void moveleft()
    {
        play = true;
        playerX-=30;
    }

}





