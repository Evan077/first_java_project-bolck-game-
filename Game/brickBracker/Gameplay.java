package brickBracker;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.SwingUtilities;
public class Gameplay extends JPanel implements KeyListener, ActionListener{
       boolean play = false;
       private int score = 0;
         private int totalBricks = 21;
         private Timer timer;
         private int delay =8;
            private int playerX = 310;
            Random x= new Random();
            Random y= new Random();
            private int ballposX = x.nextInt(600-5+1)+5;
            private int ballposY = y.nextInt(500-220+1)+220;
            
            private int ballXdir = x.nextInt(2) == 0 ? -1 : 1;
            private int ballYdir = -2;
            private MapGenerator map;
    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setPreferredSize(new Dimension(692, 592));
        map = new MapGenerator(3, 7);
        timer = new Timer(delay, this);
        timer.start();
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }
    @Override
    protected void paintComponent(Graphics g){
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();

            //background
            g.setColor(Color.black);
            g.fillRect(1,1,width-2,height-2);
            //drawing map
            map.draw((Graphics2D)g);

            //borders
            g.setColor(Color.yellow);
            g.fillRect(0, 0, 3, height);
            g.fillRect(0, 0, width, 3);
            g.fillRect(width-3, 0, 3, height);
            //scores
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Score: " + score, 540, 30);
            g.drawString("Total Bricks: " + totalBricks, 5, 500);

            //the paddle
            g.setColor(Color.green);
            g.fillRect(playerX, 550, 100, 8);
            //the ball
            g.setColor(Color.yellow);
            g.fillOval(ballposX, ballposY, 20, 20);

            if(ballposY > 570){
                play = false;
                ballXdir =0;
                ballYdir =0;
                g.setColor(Color.RED);
                g.setFont(new Font("serif",Font.BOLD,30));
                g.drawString("Game Over, Scores: " + score, 190, 300);

                g.setFont(new Font("serif",Font.BOLD,20));
                g.drawString("Press Enter to Restart", 230, 350);
            }


            if(totalBricks <=0){
                play = false;
                ballXdir =0;
                ballYdir =0;
                g.setColor(Color.RED);
                g.setFont(new Font("serif",Font.BOLD,30));
                g.drawString("You Won, Scores: " + score, 190, 300);

                g.setFont(new Font("serif",Font.BOLD,20));
                g.drawString("Press Enter to Restart", 230, 350);
            }
            g.dispose();
    }
    @Override
    public void keyTyped(KeyEvent e) {}
     @Override
    public void keyReleased(KeyEvent e) {} 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
             if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                 ballYdir = -ballYdir;
             }
             for(int i=0;i<map.map.length;i++){
                int lj=0;
                  for(int j=0;j<map.map[0].length;j++){
                        if(map.map[i][j]>0){
                            int brickX=j*map.brickWidth+80;
                            int brickY=i*map.brickHeight+50;
                            int brickWidth=map.brickWidth;
                            int brickHeight=map.brickHeight;
                            
                            Rectangle rect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
                            Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
                            Rectangle brickRect=rect;
                            
                            if(ballRect.intersects(brickRect)){
                                map.setBrickValue(0, i, j);
                                totalBricks--;
                                score+=5;
                                
                                if(ballposX +19 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width){
                                    ballXdir = -ballXdir;
                                }else{
                                    ballYdir = -ballYdir;
                                }
                                lj++;
                                break;
                            }
                        }
                  }
                    if(lj==1){
                        break;
                    }
             }
            ballposX += ballXdir;
            ballposY += ballYdir;

            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }

        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode()==KeyEvent.VK_RIGHT){
              if(playerX>=600){
                  playerX=600;
                }
                else{
                    moveRight();
                }
       }
       if(e.getKeyCode()==KeyEvent.VK_LEFT){
             if(playerX<=10){
                  playerX=10;
                }
                else{
                    moveLeft();
                }
       }
         if(e.getKeyCode()==KeyEvent.VK_ENTER){
              if(!play){
                play = true;
                Random x= new Random();
                Random y= new Random();
                ballposX = x.nextInt(600-5+1)+5;
                ballposY = y.nextInt(500-220+1)+220;
                ballXdir = x.nextInt(2) == 0 ? -1 : 1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);
                
                repaint();
              }
         }

    }
     public void moveRight(){
        play = true;
        playerX+=20;
          repaint();
     }
     public void moveLeft(){
        play = true;
        playerX-=20;
          repaint();
     }
    
}
